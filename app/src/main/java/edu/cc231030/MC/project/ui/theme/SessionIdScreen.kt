package edu.cc231030.MC.project.ui.theme

import android.util.Log
import androidx.compose.foundation.layout.Column
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

    val sessionState = viewModel.currentSession.collectAsState()
    val currentSession = sessionState.value.currentSession
    val exercisesState by exerciseViewModel.exercises.collectAsState(
        initial = ExercisesUiState(
            emptyList()
        )
    )
    //  val exercises = currentSession.exercises.map { id -> viewMode  }
    /*
        currentSession.exercises.forEach() { exerciseId ->
            exerciseViewModel.getExerciseById(exerciseId)
        }

     */

    exerciseViewModel.getExerciseById(currentSession.exercises)

    // get the session by the id when open the screen
    LaunchedEffect(sessionId) {
        viewModel.getSessionById(sessionIdInt)
    }

    Column(modifier = modifier.padding(10.dp)) {
        Text(text = "Session: ${currentSession.name}")
        if (currentSession.exercises.isEmpty()) {
            Text(text = "No Exercises in this Session")
        } else {

            Log.d("Session", "Exercises: ${currentSession.exercises}")
            LazyColumn {
                itemsIndexed(exercisesState.exercises) { index, exercise ->
                    OutlinedCard(modifier = Modifier.padding(bottom = 8.dp)) {
                        val order = index + 1
                        Text(
                            text = "$order Exercise: ${exercise.name}",
                            modifier = Modifier.padding(16.dp)
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

