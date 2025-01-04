package edu.cc231030.MC.project.ui.theme

import edu.cc231030.MC.project.ui.viewModels.SessionsViewModel
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import edu.cc231030.MC.project.data.ExerciseRepository
import edu.cc231030.MC.project.ui.SessionViewModelFactory
import edu.cc231030.MC.project.ui.States.SessionsUiState
import edu.cc231030.MC.project.ui.theme.style.InteractionButton
import edu.cc231030.MC.project.ui.theme.style.InteractionHighlightButton
import edu.cc231030.MC.project.ui.theme.style.SessionBackground
import edu.cc231030.MC.project.ui.theme.style.InteractionLightButton
import edu.cc231030.MC.project.ui.theme.style.paddingButton
import edu.cc231030.MC.project.ui.theme.style.paddingScreen


// Session Screen composable
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

    // only for test button to stop the timer
   // val openTimer = remember { mutableStateOf(false) }


    Column {
        TopAppBar("Sessions", navController = navController, navigation = "AddSessionScreen")
        Column(
            modifier = Modifier
                .padding(paddingScreen)
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // if no sessions created
                if (sessionsState.sessions.isEmpty()) {
                    item {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(
                                modifier = Modifier.fillMaxSize(),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = "No Exercises created yet",
                                    modifier = Modifier.padding(16.dp)
                                )
                                Text(
                                    text = "Navigate to the Exercise Screen to add some!",
                                    modifier = Modifier
                                        .padding(16.dp),
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }
                    // if there are sessions already
                } else {

                    itemsIndexed(sessionsState.sessions) { _, session ->
                        OutlinedCard(
                            modifier = Modifier
                                .fillMaxWidth()
                                .shadow(5.dp, shape = RoundedCornerShape(12.dp)),
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.outlinedCardColors(SessionBackground)
                        ) {
                            Column {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = session.name,
                                        modifier = Modifier.padding(paddingButton),
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 24.sp,
                                    )
                                    Button(
                                        onClick = {
                                            navController.navigate("SessionIdScreen/${session.id}")
                                        },
                                        colors = ButtonDefaults.buttonColors(containerColor = InteractionHighlightButton),
                                        modifier = Modifier.padding(paddingButton)
                                    ) {
                                        Text("Start")
                                    }
                                }
                                // if there is a description
                                if (session.description != "") {
                                    OutlinedCard(
                                        modifier = Modifier
                                            .padding(12.dp)
                                            .fillMaxWidth()
                                    ) {
                                        Text(
                                            text = "Description:", fontWeight= Bold,
                                            modifier = Modifier.padding(start =
                                                10.dp, top = 10.dp
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
                        colors = ButtonDefaults.buttonColors(containerColor = InteractionButton),
                        onClick = {
                            navController.navigate("exerciseScreen")
                        },
                    ) {
                        Text("All Exercises")
                    }
                }
                /* Test Button to test
                item {
                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = InteractionLightButton),
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

                 */

            }
        }
    }
}
