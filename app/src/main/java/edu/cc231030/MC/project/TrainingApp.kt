package edu.cc231030.MC.project.ui

import ExerciseViewModel
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import edu.cc231030.MC.project.data.ExerciseRepository
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import edu.cc231030.MC.project.ui.ExercisesUiState

@Composable
fun TrainingApp(modifier: Modifier = Modifier) {
    val context = LocalContext.current

    // initialize the ExerciseDatabase
    val database = ExerciseDatabase.getDatabase(context)
    val dao = database.exerciseDao()
    val exerciseRepository = ExerciseRepository(dao)

    // ExerciseViewModel with the repository
    val viewModel: ExerciseViewModel = viewModel(
        factory = ExerciseViewModelFactory(exerciseRepository)
    )

    // Collect the exercises from the ViewModel as a State
    val exercisesState by viewModel.exercises.collectAsState(initial = ExercisesUiState(emptyList()))

    //display the exercises
    Column(modifier = modifier) {
        exercisesState.exercises.forEach { exercise ->
            Text(text = exercise.name)
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
