package edu.cc231030.MC.project.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun TrainingApp(modifier: Modifier = Modifier) {

    val viewModel: ViewModel = viewModel()
    val message by viewModel.message.collectAsState()

    Column(modifier = modifier.padding(16.dp)) {
        Text(text = message)

        Button(
            onClick = { viewModel.updateMessage("Training Updated!") },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text("Update Training Message")
        }
    }
}
