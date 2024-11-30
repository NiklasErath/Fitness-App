package edu.cc231030.MC.project.ui.theme

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import edu.cc231030.MC.project.data.ExerciseRepository
import edu.cc231030.MC.project.ui.ExerciseViewModelFactory
import edu.cc231030.MC.project.ui.SessionViewModelFactory
import edu.cc231030.MC.project.ui.States.ExercisesUiState
import edu.cc231030.MC.project.ui.theme.style.paddingButton
import edu.cc231030.MC.project.ui.viewModels.ExerciseViewModel
import edu.cc231030.MC.project.ui.viewModels.SessionsViewModel

@Composable
fun SessionAddExercise(
    modifier: Modifier = Modifier,
    navController: NavController,
    exerciseRepository: ExerciseRepository,
    sessionId: String?

) {
    // String? back to an Int or 0
    val sessionIdInt = sessionId?.toIntOrNull() ?: 0

    val viewModel: SessionsViewModel = viewModel(
        factory = SessionViewModelFactory(exerciseRepository)
    )

    val exerciseViewModel: ExerciseViewModel = viewModel(
        factory = ExerciseViewModelFactory(exerciseRepository)
    )

    val exercisesState by exerciseViewModel.exercises.collectAsState(
        initial = ExercisesUiState(
            emptyList()
        )
    )


    Column() {
        topAppBar("Add new Exercise", navController = navController)

        Text(text = "Session ID: $sessionId")
        if (exercisesState.exercises.isEmpty()) {
            Text(text = "No exercises available")
        } else {
            Column {
                exercisesState.exercises.forEach { exercise ->
                    OutlinedCard(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(text = exercise.name)
                            Button(
                                onClick = {
                                    viewModel.addExerciseToSession(
                                        sessionIdInt,
                                        exercise.id
                                    )
                                },
                                modifier = Modifier.padding(paddingButton)
                            ) {
                                Text("+")
                            }
                        }
                    }
                }
            }
        }
        Button(
            onClick = {
                navController.navigate("ExerciseScreen")
            },
            modifier = Modifier.padding(paddingButton)
        ) {
            Text("All Exercises")
        }

        Button(
            onClick = {
                navController.navigateUp()
            },
            modifier = Modifier.padding(paddingButton)
        ) {
            Text("Back")
        }

    }


}