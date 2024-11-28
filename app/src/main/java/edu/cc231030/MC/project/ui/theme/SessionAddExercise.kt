package edu.cc231030.MC.project.ui.theme

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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


    Column(modifier = modifier.padding(16.dp)) {
        Text(text = "Session ID: $sessionId")
        if (exercisesState.exercises.isEmpty()) {
            Text(text = "No exercises available")
        } else {
            Column {
                OutlinedCard() {
                    Row {
                        exercisesState.exercises.forEach { exercise ->
                            Text(text = exercise.name)
                            Button(onClick = {
                                viewModel.addExerciseToSession(
                                    sessionIdInt,
                                    exercise.id
                                )
                            }) {
                                Text("+")
                            }
                        }
                    }
                }
            }
        }
        Button(onClick = {
            navController.navigateUp()
        }) {
            Text("Back")
        }

    }


}