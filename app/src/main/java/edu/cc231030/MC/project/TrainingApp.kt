package edu.cc231030.MC.project.ui

import ExerciseViewModel
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import edu.cc231030.MC.project.data.ExerciseRepository
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.rememberNavController
import edu.cc231030.MC.project.data.Exercise
import edu.cc231030.MC.project.data.db.ExerciseDatabase
import edu.cc231030.MC.project.ui.ExercisesUiState


@Composable
fun ExerciseItem(exercise: Exercise, onDelete: (Exercise) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = exercise.name, modifier = Modifier.weight(1f))
        Button(onClick = { onDelete(exercise) }) {
            Text(text = "Delete")
        }
    }
}

@Composable
fun TrainingApp(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val database = ExerciseDatabase.getDatabase(context)
    val dao = database.exerciseDao()
    val exerciseRepository = ExerciseRepository(dao)

    val viewModel: ExerciseViewModel = viewModel(
        factory = ExerciseViewModelFactory(exerciseRepository)
    )

    val exercisesState by viewModel.exercises.collectAsState(initial = ExercisesUiState(emptyList()))


    Column(modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        // We use lazy column for dynamic lists or large lists
        // it will only draw the visible contacts

        Button(onClick = {
            viewModel.onAddButtonClicked()
        }) {
            Text("Add new Exercise!")
        }
        if (exercisesState.exercises.isEmpty()) {
            Text(text = "No exercises available", modifier = modifier)
        } else {
            // Display exercises in a column
            exercisesState.exercises.forEach { exercise ->
                ExerciseItem(
                    exercise = exercise,
                    onDelete = { exercise -> viewModel.deleteExercise(exercise) }  // Pass full Exercise entity
                )
            }
        }
    }
}


//Column(modifier = modifier.padding(16.dp)) {
// Text(text = message)

//  Button(
// onClick = { viewModel.updateMessage("Training Updated!") },
//       modifier = Modifier.padding(top = 16.dp)
//  ) {
//      Text("Update Training Message")
//    }
//   }
//}
