package edu.cc231030.MC.project.ui.theme

import androidx.compose.foundation.clickable
import edu.cc231030.MC.project.ui.viewModels.SessionsViewModel
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import edu.cc231030.MC.project.data.ExerciseRepository
import edu.cc231030.MC.project.ui.SessionViewModelFactory
import edu.cc231030.MC.project.ui.States.SessionsUiState


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


    Column {
        Text(text = "Your Sessions:")
        OutlinedCard() {
            Text(text = "This is already my project setup, but it contains all the requirements for the Demonstrator 2 assignment. I hope that's okay :) The idea is to have sessions that store exercises. Furthermore, the user can add sets to the exercises with 'reps' and 'weight' parameters."
            )
        }
        Button(onClick = {
            navController.navigate("exerciseScreen")
        }) {
            Text("Exercises")
        }

        Button(onClick = {
            navController.navigate("addSessionScreen")
        }) {
            Text("Add new Session!")
        }

        if (sessionsState.sessions.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No Sessions created",
                    modifier = Modifier.padding(16.dp)
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                itemsIndexed(sessionsState.sessions) { _, session ->
                    OutlinedCard(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp),
                        onClick = {
                            navController.navigate("SessionIdScreen/${session.id}")
                        }
                    ) {
                        Text(
                            text = session.name,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }

            }
        }
    }
}
