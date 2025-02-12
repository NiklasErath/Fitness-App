package edu.cc231030.MC.project.ui.theme

import edu.cc231030.MC.project.ui.viewModels.ExerciseViewModel
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import edu.cc231030.MC.project.data.ExerciseRepository
import edu.cc231030.MC.project.ui.ExerciseViewModelFactory
import edu.cc231030.MC.project.ui.theme.style.InteractionButton
import edu.cc231030.MC.project.ui.theme.style.paddingButton
import edu.cc231030.MC.project.ui.theme.style.InteractionLightButton


// create / add an exercise
@Composable
fun AddExerciseScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    exerciseRepository: ExerciseRepository
) {
    // remember to store the state, and directly access the value.
    val exerciseName = remember { mutableStateOf("") }
    val exerciseDescription = remember { mutableStateOf("") }

    val viewModel: ExerciseViewModel = viewModel(
        factory = ExerciseViewModelFactory(exerciseRepository)
    )


    Column() {
        TopAppBar("Add New Exercise", navController = navController, "no")

        OutlinedTextField(
            value = exerciseName.value,
            onValueChange = { newName -> exerciseName.value = newName },
            label = { Text("Exercise Name") },
            modifier = Modifier.padding(12.dp)
                .fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                disabledContainerColor = Color.Gray
            )
        )
        OutlinedTextField(
            value = exerciseDescription.value,
            onValueChange = { newDescription -> exerciseDescription.value = newDescription },
            label = { Text("Exercise Description") },
            modifier = Modifier.padding(12.dp)
                .fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                disabledContainerColor = Color.Gray
            )
        )

        Button(
            onClick = {
                if (exerciseName.value.isNotEmpty()) {
                    //pass the name to the function to create a new exercise
                    viewModel.addExercise(exerciseName.value, exerciseDescription.value)
                    // navigate back to previous Screen
                    navController.popBackStack()
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = InteractionButton),
            modifier = Modifier.padding(paddingButton)
                .fillMaxWidth()
        ) {
            Text("Create Exercise")
        }
        Button(
            onClick = {
                navController.navigateUp()
            },
            colors = ButtonDefaults.buttonColors(containerColor = InteractionLightButton),
            modifier = Modifier.padding(paddingButton)
                .fillMaxWidth()
        ) {
            Text("Cancel")
        }
    }
}



