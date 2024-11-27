package edu.cc231030.MC.project.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import edu.cc231030.MC.project.data.Exercise
import edu.cc231030.MC.project.data.ExerciseRepository
import edu.cc231030.MC.project.data.db.ExerciseDatabase
import edu.cc231030.MC.project.ui.theme.ExerciseScreen
import edu.cc231030.MC.project.ui.theme.AddExerciseScreen
import edu.cc231030.MC.project.ui.theme.SessionScreen
import edu.cc231030.MC.project.ui.theme.SessionIdScreen
import edu.cc231030.MC.project.ui.theme.AddSessionScreen
import edu.cc231030.MC.project.ui.theme.SessionAddExercise





enum class Routes(val route: String) {
    Home("SessionScreen"),
    Exercises("ExerciseScreen"),
    Session("SessionIdScreen/{sessionId}"),
    AddSession("addSessionScreen"),
    AddExercise("addExerciseScreen"),
    SessionAddExercise("SessionAddExercise/{sessionId}")
}

@Composable
fun TrainingApp(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    val context = LocalContext.current
    val database = ExerciseDatabase.getDatabase(context)
    val dao = database.exerciseDao()
    val exerciseRepository = ExerciseRepository(dao)


    // Box for the padding/modifier from the MainActivity
    Box(modifier = modifier) {
        NavHost(navController = navController, Routes.Home.route) {
            composable(Routes.Exercises.route) {
                ExerciseScreen(modifier = Modifier, navController = navController, exerciseRepository = exerciseRepository)
            }
            composable(Routes.AddExercise.route) {
                AddExerciseScreen(modifier = modifier, navController = navController, exerciseRepository = exerciseRepository)
            }
            composable(Routes.Home.route) {
                SessionScreen(modifier = modifier, navController = navController, exerciseRepository = exerciseRepository)
            }
            composable(Routes.AddSession.route) {
                AddSessionScreen(modifier = modifier, navController = navController, exerciseRepository = exerciseRepository)
            }
            composable(
                Routes.Session.route,
                arguments = listOf(navArgument("sessionId") { type = NavType.StringType })
            ) {
                // if argument null it returns null to avoid NullPointerExperction
                val sessionId = it.arguments?.getString("sessionId") // retrieves the ID from the argument bundle
                SessionIdScreen(
                    modifier = modifier,
                    navController = navController,
                    exerciseRepository = exerciseRepository,
                    sessionId = sessionId
                )
            }
            composable(Routes.SessionAddExercise.route,
                arguments = listOf(navArgument("sessionId") {type = NavType.StringType})
                ){
                val sessionId = it.arguments?.getString("sessionId")
            SessionAddExercise(modifier=Modifier, navController = navController, exerciseRepository = exerciseRepository, sessionId = sessionId)
            }

        }
    }
}
