package edu.cc231030.MC.project.ui.theme

import ExerciseViewModel
import android.util.Log
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import edu.cc231030.MC.project.data.Exercise
import edu.cc231030.MC.project.data.ExerciseRepository
import edu.cc231030.MC.project.data.ExerciseSet
import edu.cc231030.MC.project.ui.ExerciseSetsUiState
import edu.cc231030.MC.project.ui.ExerciseViewModelFactory
import edu.cc231030.MC.project.ui.ExercisesUiState

// Ui for the Main Screen

@Composable
fun ExerciseItem(
    exercise: Exercise,
    exerciseSet: List<ExerciseSet>,
    onDelete: (Exercise) -> Unit,
    onDeleteSet: (ExerciseSet) -> Unit,
    onAddSet: (Int, Int, Int) -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = exercise.name, modifier = Modifier.weight(1f))
        Button(onClick = { onDelete(exercise) }) {
            Text(text = "Delete")
        }
    }
    Column {
        Button(
            onClick = { onAddSet(exercise.id, 0, 0) }
        ) {
            Text(text = "Add Set")
        }

        if (exerciseSet.isNotEmpty()) {
            // display the sets of the exercise
            exerciseSet.forEach { set ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    Text(text = "Reps: ${set.reps}, Weight: ${set.weight}")
                    Button(onClick = { onDeleteSet(set) }) {
                        Text(text = "Delete")
                    }
                }
            }
        } else {
            Text(text = "No sets added yet.")
        }
    }
}


@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    exerciseRepository: ExerciseRepository
) {

    val viewModel: ExerciseViewModel = viewModel(
        factory = ExerciseViewModelFactory(exerciseRepository)
    )

    val exercisesState by viewModel.exercises.collectAsState(
        initial = ExercisesUiState(
            emptyList()
        )
    )
    val exerciseSet by viewModel.exerciseSets.collectAsState(
        initial = ExerciseSetsUiState(
            emptyList()
        )
    )


    Column(modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        // Button to navigate to AddExerciseScreen
        Button(onClick = {
            navController.navigate("addExerciseScreen") // Navigate to AddExerciseScreen
            viewModel.onAddButtonClicked() // Optional: Can trigger any actions when navigating
        }) {
            Text("Add new Exercise!")
        }


        // Display exercises
        if (exercisesState.exercises.isEmpty()) {
            Text(text = "No exercises available", modifier = modifier)
        } else {
            // display exxercises on the screen
            exercisesState.exercises.forEach { exercise ->
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
                        onDelete = { exercise -> viewModel.deleteExercise(exercise) },
                        onDeleteSet = { exerciseSet -> viewModel.deleteExerciseSet(exerciseSet) },
                        onAddSet = { exerciseId, reps, weight ->
                            viewModel.addExerciseSet(
                                exerciseId,
                                reps,
                                weight
                            )
                        })
                }
            }
        }
    }
}
