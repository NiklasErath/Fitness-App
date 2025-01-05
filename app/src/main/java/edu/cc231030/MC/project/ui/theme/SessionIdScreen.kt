package edu.cc231030.MC.project.ui.theme

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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import edu.cc231030.MC.project.data.ExerciseRepository
import edu.cc231030.MC.project.ui.SessionViewModelFactory
import edu.cc231030.MC.project.ui.ExerciseViewModelFactory
import edu.cc231030.MC.project.ui.States.ExerciseSetsUiState
import edu.cc231030.MC.project.ui.States.ExercisesUiState
import edu.cc231030.MC.project.ui.theme.style.DeleteLightButton
import edu.cc231030.MC.project.ui.theme.style.ExerciseItemBackground
import edu.cc231030.MC.project.ui.theme.style.InteractionHighlightButton
import edu.cc231030.MC.project.ui.theme.style.InteractionLightButton
import edu.cc231030.MC.project.ui.theme.style.SessionBackground
import edu.cc231030.MC.project.ui.theme.style.paddingButton
import edu.cc231030.MC.project.ui.viewModels.SessionsViewModel
import edu.cc231030.MC.project.ui.viewModels.ExerciseViewModel

// specific session screen
@Composable
fun SessionIdScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    exerciseRepository: ExerciseRepository,
    sessionId: String?
) {

    // sessionId to Int
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


    // collect exercise sets
    val exerciseSet by exerciseSetViewModel.exerciseSets.collectAsState(
        initial = ExerciseSetsUiState(
            emptyList()
        )
    )

    // store the current session information
    val sessionState = viewModel.currentSession.collectAsState()
    val currentSession = sessionState.value.currentSession

    val exercisesState by exerciseViewModel.exercises.collectAsState(
        initial = ExercisesUiState(
            emptyList()
        )
    )

    exerciseViewModel.getExercisesById(currentSession.exercises)
    // start timer when starting a workout
    viewModel.startTimer()

    // get the session by the id when open the screen
    LaunchedEffect(sessionId) {
        viewModel.getSessionById(sessionIdInt)
    }


    Column {
        TopAppBar(
            currentSession.name,
            navController = navController,
            navigation = "sessionAddExercise/${sessionId}"
        )
        LazyColumn {
            item {
                SessionTimer(exerciseRepository)
            }
            if (exercisesState.exercises.isEmpty()) {
                item { Text(text = "No exercises available", modifier = modifier) }
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
                            .padding(10.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = ExerciseItemBackground
                        )
                    ) {
                        ExerciseItem(
                            exercise = exercise,
                            exerciseSet = correspondingExerciseSets,
                            onDelete = { exercise ->
                                viewModel.deleteExerciseFromSession(
                                    sessionIdInt,
                                    exercise.id
                                )
                            },
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
                            },
                            screen = "session",
                            navController = navController
                        )
                    }
                }
            }
            item {
                OutlinedCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp)
                        .shadow(5.dp, shape = RoundedCornerShape(12.dp)),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.outlinedCardColors(SessionBackground)
                ) {
                    Column {
                        OutlinedCard(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 12.dp, start = 12.dp, end = 12.dp)
                        ) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Text(text = "Description:", fontWeight = FontWeight.Bold)
                                Text(text = currentSession.description)
                            }
                        }
                        Button(
                            onClick = {
                                navController.navigate("EditSessionDescription/${currentSession.id}")
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(paddingButton),
                            colors = ButtonDefaults.buttonColors(containerColor = InteractionLightButton),
                        ) {
                            Text(text = "Edit Description")

                        }
                    }
                }
            }
            // finish the Workout and stop the timer
            item {
                Button(
                    onClick = {
                        viewModel.stopTimer()
                        navController.navigateUp()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = InteractionHighlightButton),
                    modifier = Modifier
                        .padding(paddingButton)
                        .fillMaxWidth()
                ) {
                    Text("Finish Workout")

                }
            }
            // delete the current session
            item {
                Button(
                    onClick = {
                        viewModel.deleteSession(sessionIdInt)
                        navController.navigate("SessionScreen")
                    },
                    modifier = Modifier
                        .padding(paddingButton)
                        .fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = DeleteLightButton),

                    ) {
                    Text("Delete Session")

                }
            }
        }

    }
}


