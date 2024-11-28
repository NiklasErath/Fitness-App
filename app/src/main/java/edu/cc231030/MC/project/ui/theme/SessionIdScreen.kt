package edu.cc231030.MC.project.ui.theme

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import edu.cc231030.MC.project.data.ExerciseRepository
import edu.cc231030.MC.project.ui.SessionViewModelFactory
import edu.cc231030.MC.project.ui.ExerciseViewModelFactory
import edu.cc231030.MC.project.ui.States.ExerciseSetsUiState
import edu.cc231030.MC.project.ui.States.ExercisesUiState
import edu.cc231030.MC.project.ui.viewModels.SessionsViewModel
import edu.cc231030.MC.project.ui.viewModels.ExerciseViewModel


@Composable
fun SessionIdScreen(
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

    val exerciseSetViewModel: ExerciseViewModel = viewModel(
        factory = ExerciseViewModelFactory(exerciseRepository)
    )


    val exerciseSet by exerciseSetViewModel.exerciseSets.collectAsState(
        initial = ExerciseSetsUiState(
            emptyList()
        )
    )

    val sessionState = viewModel.currentSession.collectAsState()
    val currentSession = sessionState.value.currentSession
    val exercisesState by exerciseViewModel.exercises.collectAsState(
        initial = ExercisesUiState(
            emptyList()
        )
    )

    exerciseViewModel.getExerciseById(currentSession.exercises)

    // get the session by the id when open the screen
    LaunchedEffect(sessionId) {
        viewModel.getSessionById(sessionIdInt)
    }
    Column {
        Text(text = "${currentSession.name}")
        if (exercisesState.exercises.isEmpty()) {
            Text(text = "No exercises available", modifier = modifier)
        } else {
            // display exercises on the screen
            LazyColumn {
                // itemsIndexed iterates over the exercise List while providing both the index and the element at each iteration - index is order , element the objects
                itemsIndexed(exercisesState.exercises) { index, exercise ->
                    val correspondingExerciseSets =
                        exerciseSet.exerciseSet.filter { it.exerciseId == exercise.id }
                    // filter the set for each exercise
                    OutlinedCard(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp)
                    ) {
                        ExerciseItem(
                            exercise = exercise,
                            exerciseSet = correspondingExerciseSets,
                            onDelete = { exercise -> exerciseViewModel.deleteExercise(exercise) },
                            onDeleteSet = { exerciseSet ->
                                exerciseViewModel.deleteExerciseSet(
                                    exerciseSet
                                )
                            },
                            onUpdateSet = { id, exerciseId, reps, weight ->
                                exerciseViewModel.updateExerciseSet(
                                    id,
                                    exerciseId,
                                    reps,
                                    weight
                                )
                            },
                            onAddSet = { exerciseId, reps, weight ->
                                exerciseViewModel.addExerciseSet(
                                    exerciseId,
                                    reps,
                                    weight
                                )
                            }
                        )
                    }
                }
            }
        }
        Button(onClick = {
            navController.navigate("sessionAddExercise/${sessionId}")
        }) {
            Text("Add Exercises")
        }

        Button(onClick = {
            navController.navigateUp()
        }) {
            Text("Back")

        }
    }
}

