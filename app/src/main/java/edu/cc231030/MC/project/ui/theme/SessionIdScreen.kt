package edu.cc231030.MC.project.ui.theme

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import edu.cc231030.MC.project.data.ExerciseRepository
import edu.cc231030.MC.project.ui.SessionViewModelFactory
import edu.cc231030.MC.project.ui.viewModels.SessionsViewModel

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

    val sessionState = viewModel.currentSession.collectAsState()
    val currentSession = sessionState.value.currentSession

    LaunchedEffect(sessionId) {
        viewModel.getSessionById(sessionIdInt)
    }

    Column(modifier = modifier.padding(10.dp)) {
        Text(text = "Session ID: ${currentSession.name}")
    LazyColumn {
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
