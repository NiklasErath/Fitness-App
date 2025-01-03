package edu.cc231030.MC.project.ui.theme

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import edu.cc231030.MC.project.data.ExerciseRepository
import edu.cc231030.MC.project.ui.SessionViewModelFactory
import edu.cc231030.MC.project.ui.theme.style.paddingButton
import edu.cc231030.MC.project.ui.viewModels.SessionsViewModel
import kotlinx.coroutines.time.delay
import java.time.Duration

@Composable
fun SessionTimer(
    exerciseRepository: ExerciseRepository,
){

    val viewModel: SessionsViewModel = viewModel(
        factory = SessionViewModelFactory(exerciseRepository)
    )

    val sessionTime by viewModel.sessionTime.collectAsState()

    // Format time as HH:MM:SS
    fun Long.formatTime(): String {
        val hours = this / 3600
        val minutes = (this % 3600) / 60
        val seconds = this % 60
        return String.format("%02d:%02d:%02d", hours, minutes, seconds)
    }

    OutlinedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = "Workout time: ${sessionTime.toLong().formatTime()}",
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
    }
}