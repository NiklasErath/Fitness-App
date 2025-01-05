package edu.cc231030.MC.project.ui.theme


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import edu.cc231030.MC.project.data.ExerciseRepository
import edu.cc231030.MC.project.ui.SessionViewModelFactory
import edu.cc231030.MC.project.ui.theme.style.InteractionButton
import edu.cc231030.MC.project.ui.theme.style.InteractionLightButton
import edu.cc231030.MC.project.ui.theme.style.paddingButton
import edu.cc231030.MC.project.ui.viewModels.SessionsViewModel

// Screen to edit the Description of a session
@Composable
fun EditSessionDescription(
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

    // state the information of the current session that gets edited
    val sessionState = viewModel.currentSession.collectAsState()
    val currentSession = sessionState.value.currentSession

    // remember the description for the textfield
    val sessionDescription = remember { mutableStateOf("") }

    //  on launch get the session information by the id and store the description in the state
    LaunchedEffect(sessionIdInt, currentSession.description) {
        viewModel.getSessionById(sessionId = sessionIdInt)
        sessionDescription.value = currentSession.description
    }

    Column {
        TopAppBar("Edit Description", navController = navController, navigation = "no")
        Text(text=currentSession.name)
        OutlinedTextField(
            value = sessionDescription.value,
            onValueChange = { newDescription -> sessionDescription.value = newDescription },
            label = { Text("") },
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                disabledContainerColor = Color.Gray
            )
        )

        Button(
            onClick = {
                if (sessionDescription.value.isNotEmpty()) {
                    //pass the name, exercises and description to the function to update the session
                    viewModel.updateSession(currentSession.id, currentSession.name, currentSession.exercises,sessionDescription.value)
                    //navigate back to Session Screen
                    navController.navigateUp()
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = InteractionButton),
            modifier = Modifier
                .padding(paddingButton)
                .fillMaxWidth()
        ) {
            Text("Update")
        }
        Button(
            onClick = {
                navController.navigateUp()
            },
            colors = ButtonDefaults.buttonColors(containerColor = InteractionLightButton),
            modifier = Modifier.padding(start = 12.dp, end=12.dp)
                .fillMaxWidth()
        ) {
            Text("Cancel")
        }
    }
}