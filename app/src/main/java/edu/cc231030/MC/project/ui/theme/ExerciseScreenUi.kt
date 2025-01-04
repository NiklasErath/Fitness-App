package edu.cc231030.MC.project.ui.theme

import edu.cc231030.MC.project.ui.viewModels.ExerciseViewModel
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import edu.cc231030.MC.project.data.ExerciseRepository
import edu.cc231030.MC.project.ui.States.ExerciseSetsUiState
import edu.cc231030.MC.project.ui.ExerciseViewModelFactory
import edu.cc231030.MC.project.ui.States.ExercisesUiState
import edu.cc231030.MC.project.ui.theme.style.InteractionButton
import edu.cc231030.MC.project.ui.theme.style.ExerciseItemBackground
import edu.cc231030.MC.project.ui.theme.style.paddingButton

// Exercise Screen to display all the exercises
@Composable
fun ExerciseScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    exerciseRepository: ExerciseRepository
) {

    val viewModel: ExerciseViewModel = viewModel(
        factory = ExerciseViewModelFactory(exerciseRepository)
    )

    // collect and state exercise information
    val exercisesState by viewModel.exercises.collectAsState(
        initial = ExercisesUiState(
            emptyList()
        )
    )

    // collect and state exerciseSets information
    val exerciseSet by viewModel.exerciseSets.collectAsState(
        initial = ExerciseSetsUiState(
            emptyList()
        )
    )


    Column(modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        TopAppBar("Exercises", navController = navController, "addExerciseScreen")
        LazyColumn {

            // Display exercises
            if (exercisesState.exercises.isEmpty()) {
                item {
                    Text(text = "No exercises available", modifier = modifier)
                }
            } else {
                // display exercises on the screen
                // itemsIndexed iterates over the exercise List while providing both the index and the element at each iteration - index is order , element the objects
                itemsIndexed(exercisesState.exercises) { index, exercise ->
                    val correspondingExerciseSets =
                        exerciseSet.exerciseSet.filter { it.exerciseId == exercise.id }
                    // filter the set for each exercise
                    OutlinedCard(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp)
                            .shadow(5.dp, shape = RoundedCornerShape(12.dp)),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = ExerciseItemBackground
                        )
                    ) {
                        // Exercise Item for each exercise
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
                            },
                            screen = "exercise",
                            navController = navController
                        )
                    }
                }
            }
            item {
                Button(
                    onClick = {
                        navController.navigate("SessionScreen")
                        //      viewModel.onAddButtonClicked()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = InteractionButton),
                    modifier = Modifier
                        .padding(paddingButton)
                        .fillMaxWidth()

                ) {
                    Text("SessionScreen")
                }
            }
        }
    }
}
