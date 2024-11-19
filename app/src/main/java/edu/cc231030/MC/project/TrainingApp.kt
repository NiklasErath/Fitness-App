package edu.cc231030.MC.project.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import edu.cc231030.MC.project.data.ExerciseRepository
import edu.cc231030.MC.project.data.db.ExerciseDatabase
import edu.cc231030.MC.project.ui.theme.MainScreen
import edu.cc231030.MC.project.ui.theme.AddExerciseScreen
import edu.cc231030.MC.project.ui.theme.SessionScreen




@Composable
fun TrainingApp(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    val context = LocalContext.current
    val database = ExerciseDatabase.getDatabase(context)
    val dao = database.exerciseDao()
    val exerciseRepository = ExerciseRepository(dao)


    // Box for the padding/modifier from the MainActivity
    Box(modifier = modifier) {
        NavHost(navController = navController, startDestination = "mainScreen") {
            composable("mainScreen") {
                MainScreen(modifier = Modifier, navController = navController, exerciseRepository = exerciseRepository)
            }
            composable("addExerciseScreen") {
                AddExerciseScreen(modifier = modifier, navController = navController, exerciseRepository = exerciseRepository)
            }
            composable("SessionScreen") {
                SessionScreen(modifier = modifier, navController = navController, exerciseRepository = exerciseRepository)
            }
        }
    }
}
