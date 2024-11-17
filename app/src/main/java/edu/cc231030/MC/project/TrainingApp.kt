package edu.cc231030.MC.project

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import edu.cc231030.MC.project.ui.ViewModel
import androidx.compose.runtime.collectAsState


@Composable
fun TrainingApp(modifier: Modifier = Modifier) {
    // Obtain the ViewModel instance
    val viewModel: ViewModel = viewModel()

    // Observe the data (in this case, the message) from the ViewModel
    val message = viewModel.message.collectAsState()

    // Layout with the text displaying data from the ViewModel
    Column(modifier = modifier.padding(16.dp)) {
        Text(text = message.value)
    }
}
