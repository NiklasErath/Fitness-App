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
import edu.cc231030.MC.project.ui.ExerciseViewModelFactory
import edu.cc231030.MC.project.ui.theme.style.InteractionButton
import edu.cc231030.MC.project.ui.theme.style.InteractionLightButton
import edu.cc231030.MC.project.ui.theme.style.paddingButton
import edu.cc231030.MC.project.ui.viewModels.ExerciseViewModel

// Screen to edit the Description of an exercise
@Composable
fun EditExerciseDescription(
    modifier: Modifier = Modifier,
    navController: NavController,
    exerciseRepository: ExerciseRepository,
    exerciseId: String?
) {

    // exerciseId to Int
    val exerciseIdInt = exerciseId?.toIntOrNull() ?: 0


    val viewModel: ExerciseViewModel = viewModel(
        factory = ExerciseViewModelFactory(exerciseRepository)
    )

    // state the information of the current exercise that gets edited
    val exerciseState = viewModel.currentExercise.collectAsState()
    val currentExercise = exerciseState.value.currentExercise

    // remember the description for the textfield
    val exerciseDescription = remember { mutableStateOf("") }

    //  on launch get the exercise information by the id and store the description in the state
    LaunchedEffect(exerciseIdInt, currentExercise.description) {
        viewModel.getExerciseById(exerciseId = exerciseIdInt)
        exerciseDescription.value = currentExercise.description
    }

    Column {
        TopAppBar("Edit Description", navController = navController, navigation = "no")

        OutlinedTextField(
            value = exerciseDescription.value,
            onValueChange = { newDescription -> exerciseDescription.value = newDescription },
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
                if (exerciseDescription.value.isNotEmpty()) {
                    //pass the name and description to the function to update the exercise
                    viewModel.updateExercise(currentExercise.id, currentExercise.name, exerciseDescription.value)
                    //navigate back to Exercise Screen
                    navController.navigate("ExerciseScreen")
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
            modifier = Modifier.padding(paddingButton)
                .fillMaxWidth()
        ) {
            Text("Cancel")
        }
    }
}