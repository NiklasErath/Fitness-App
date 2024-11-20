package edu.cc231030.MC.project.ui.theme

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
import edu.cc231030.MC.project.ui.viewModels.SessionsViewModel
import kotlinx.coroutines.flow.MutableStateFlow

@Composable
fun SessionIdScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    exerciseRepository: ExerciseRepository,
    sessionId: String?
) {
    val viewModel: SessionsViewModel = viewModel(
        factory = SessionViewModelFactory(exerciseRepository)
    )



    Column(modifier = modifier.padding(16.dp)) {
        Text(text = "Session ID: $sessionId")
    }

    Button(onClick = {
        navController.navigateUp()
    }) {
        Text("Back")
    }
}
