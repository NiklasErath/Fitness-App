package edu.cc231030.MC.project.ui.theme

import edu.cc231030.MC.project.ui.viewModels.ExerciseViewModel
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
import edu.cc231030.MC.project.ui.ExerciseViewModelFactory
import edu.cc231030.MC.project.ui.theme.style.paddingButton

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
        topAppBar("Add New Exercise", navController = navController, "no")

        Text(text = "Add New Exercise")

        OutlinedTextField(
            value = exerciseName.value,
            onValueChange = { newName -> exerciseName.value = newName },
            label = { Text("Exercise Name") }
        )
        OutlinedTextField(
            value = exerciseDescription.value,
            onValueChange = { newDescription -> exerciseDescription.value = newDescription },
            label = { Text("Exercise Description") }
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
            modifier = Modifier.padding(paddingButton)
        ) {
            Text("Create Exercise")
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



