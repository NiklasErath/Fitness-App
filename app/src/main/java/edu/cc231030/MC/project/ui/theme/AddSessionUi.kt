package edu.cc231030.MC.project.ui.theme

import edu.cc231030.MC.project.ui.viewModels.SessionsViewModel
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import edu.cc231030.MC.project.data.ExerciseRepository
import edu.cc231030.MC.project.ui.SessionViewModelFactory
import edu.cc231030.MC.project.ui.theme.style.paddingButton

@Composable
fun AddSessionScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    exerciseRepository: ExerciseRepository
) {
    // remember to store the state, and directly access the value.
    val sessionName = remember { mutableStateOf("") }
    val sessionDescription = remember { mutableStateOf("") }
    val viewModel: SessionsViewModel = viewModel(
        factory = SessionViewModelFactory(exerciseRepository)
    )


    Column() {
        topAppBar("Add New Session", navController = navController,"no")

        Text(text = "Add New Session")

        OutlinedTextField(
            value = sessionName.value,
            onValueChange = { newName -> sessionName.value = newName },
            label = { Text("Session Name") }
        )

        OutlinedTextField(
            value = sessionDescription.value,
            onValueChange = { newDescription -> sessionDescription.value = newDescription },
            label = { Text("Session Description") }
        )

        Button(
            onClick = {
                if (sessionName.value.isNotEmpty()) {
                    //pass the name and description to the function to create a new session
                    viewModel.addSession(sessionName.value, sessionDescription.value)
                    //navigate back to previous Screen
                    navController.navigate("SessionScreen")
                }
            },
            modifier = Modifier.padding(paddingButton)
        ) {
            Text("Create Session")
        }
        Button(
            onClick = {
                navController.navigateUp()
            },
            modifier = Modifier.padding(paddingButton)

        ) {
            Text("Cancel")
        }
    }
}



