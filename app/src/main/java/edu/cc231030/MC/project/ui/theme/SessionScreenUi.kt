package edu.cc231030.MC.project.ui.theme

import androidx.compose.foundation.clickable
import edu.cc231030.MC.project.ui.viewModels.SessionsViewModel
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import edu.cc231030.MC.project.data.ExerciseRepository
import edu.cc231030.MC.project.ui.SessionViewModelFactory
import edu.cc231030.MC.project.ui.States.SessionsUiState
import edu.cc231030.MC.project.ui.theme.style.ItemBackground
import edu.cc231030.MC.project.ui.theme.style.paddingButton
import edu.cc231030.MC.project.ui.theme.style.paddingScreen


@Composable
fun SessionScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    exerciseRepository: ExerciseRepository
) {

    val viewModel: SessionsViewModel = viewModel(
        factory = SessionViewModelFactory(exerciseRepository)
    )

    val sessionsState by viewModel.sessions.collectAsState(
        initial = SessionsUiState(emptyList())
    )

    val openTimer = remember { mutableStateOf(false) }


    Column {
        topAppBar("Sessions", navController = navController, navigation = "AddSessionScreen")
        /* Text(
             text = "This is already my project setup, but it contains all the requirements for the Demonstrator 2 assignment. I hope that's okay :) The idea is to have sessions that store exercises. Furthermore, the user can add sets to the exercises with 'reps' and 'weight' parameters."
         )
         */
        Column(
            modifier = Modifier
                .padding(paddingScreen)
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                if (sessionsState.sessions.isEmpty()) {
                    item {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "No Sessions created",
                                modifier = Modifier.padding(16.dp)
                            )
                        }
                    }
                } else {

                    itemsIndexed(sessionsState.sessions) { _, session ->
                        OutlinedCard(
                            modifier = Modifier
                                .fillMaxWidth(),
                            colors = CardDefaults.cardColors(
                                containerColor = ItemBackground
                            )
                            /*   onClick = {
                                   navController.navigate("SessionIdScreen/${session.id}")
                               }

                             */
                        ) {
                            Column {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = session.name,
                                        modifier = Modifier.padding(paddingButton)
                                    )
                                    Button(
                                        onClick = {
                                            navController.navigate("SessionIdScreen/${session.id}")
                                        },
                                        modifier = Modifier.padding(paddingButton)
                                    ) {
                                        Text("Start")
                                    }
                                }
                                if (session.description != "") {
                                    OutlinedCard(
                                        modifier = Modifier
                                            .padding(12.dp)
                                            .fillMaxWidth()
                                    ) {
                                        Text(
                                            text = "Description:",
                                            modifier = Modifier.padding(
                                                10.dp
                                            )
                                        )
                                        Text(
                                            text = session.description,
                                            modifier = Modifier.padding(
                                                10.dp
                                            )
                                        )
                                    }
                                }
                            }
                        }
                    }

                }
                item {
                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = {
                            navController.navigate("exerciseScreen")
                        },
                    ) {
                        Text("All Exercises")
                    }
                }
                item {
                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = {
                            openTimer.value = true
                        }
                    ) {
                        Text("Test")
                    }

                    if (openTimer.value) {
                        TimerPopUp(
                            onDismissRequest = { openTimer.value = false },
                        )
                    }

                }
            }
        }
    }
}
