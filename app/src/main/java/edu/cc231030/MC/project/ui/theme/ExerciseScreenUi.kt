package edu.cc231030.MC.project.ui.theme

import edu.cc231030.MC.project.ui.viewModels.ExerciseViewModel
import androidx.compose.foundation.layout.Column
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
import edu.cc231030.MC.project.ui.States.ExerciseSetsUiState
import edu.cc231030.MC.project.ui.ExerciseViewModelFactory
import edu.cc231030.MC.project.ui.States.ExercisesUiState

// Ui for the Main Screen
@Composable
fun ExerciseScreen(
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
        Button(onClick = {
            navController.navigate("addExerciseScreen")
          //  viewModel.onAddButtonClicked()
        }) {
            Text("Add new Exercise!")
        }
        Button(onClick = {
            navController.navigate("SessionScreen")
      //      viewModel.onAddButtonClicked()
        }) {
            Text("SessionScreen")
        }


        // Display exercises
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
                            onDelete = { exercise -> viewModel.deleteExercise(exercise) },
                            onDeleteSet = { exerciseSet -> viewModel.deleteExerciseSet(exerciseSet) },
                            onUpdateSet = { id, exerciseId, reps, weight ->
                                viewModel.updateExerciseSet(
                                    id,
                                    exerciseId,
                                    reps,
                                    weight
                                )
                            },
                            onAddSet = { exerciseId, reps, weight ->
                                viewModel.addExerciseSet(
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
            navController.navigateUp()
        }) {
            Text("Back")
        }
    }
}
