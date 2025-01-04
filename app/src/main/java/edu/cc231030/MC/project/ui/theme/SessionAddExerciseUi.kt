package edu.cc231030.MC.project.ui.theme

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import edu.cc231030.MC.project.data.ExerciseRepository
import edu.cc231030.MC.project.ui.ExerciseViewModelFactory
import edu.cc231030.MC.project.ui.SessionViewModelFactory
import edu.cc231030.MC.project.ui.States.ExercisesUiState
import edu.cc231030.MC.project.ui.theme.style.InteractionButton
import edu.cc231030.MC.project.ui.theme.style.ExerciseItemBackground
import edu.cc231030.MC.project.ui.theme.style.AddButton
import edu.cc231030.MC.project.ui.theme.style.paddingButton
import edu.cc231030.MC.project.ui.theme.style.paddingScreen
import edu.cc231030.MC.project.ui.viewModels.ExerciseViewModel
import edu.cc231030.MC.project.ui.viewModels.SessionsViewModel

// Add an exercise to a Session Screen
@Composable
fun SessionAddExercise(
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

    val exerciseViewModel: ExerciseViewModel = viewModel(
        factory = ExerciseViewModelFactory(exerciseRepository)
    )

    // get the exercises for the session
    val exercisesState by exerciseViewModel.exercises.collectAsState(
        initial = ExercisesUiState(
            emptyList()
        )
    )

    // store the current session with the information
    val sessionState = viewModel.currentSession.collectAsState()
    val currentSession = sessionState.value.currentSession

    // get the current Session
    LaunchedEffect(sessionId) {
        viewModel.getSessionById(sessionIdInt)
    }


    Column {
        TopAppBar("Add Exercise", navController = navController, "no")

        Column(
            modifier = Modifier
                .padding(paddingScreen)
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                if (exercisesState.exercises.isEmpty()) {
                    item {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(
                                modifier = Modifier.fillMaxSize(),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = "No Exercises created yet",
                                    modifier = Modifier.padding(16.dp)
                                )
                                Text(
                                    text = "Create Exercises to add them!",
                                    modifier = Modifier
                                        .padding(16.dp),
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }
                } else {
                    itemsIndexed(exercisesState.exercises) { _, exercise ->
                        OutlinedCard(
                            modifier = Modifier
                                .fillMaxWidth()
                                .shadow(5.dp, shape = RoundedCornerShape(12.dp)),
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.outlinedCardColors(ExerciseItemBackground)
                        ) {
                            Column {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(paddingButton),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = exercise.name,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 24.sp,
                                    )
                                    if (exercise.id !in currentSession.exercises) {
                                        Log.d("TAG", "${exercise.id} not")
                                        Button(
                                            onClick = {
                                                viewModel.addExerciseToSession(
                                                    sessionIdInt,
                                                    exercise.id
                                                )
                                                navController.navigate("SessionIdScreen/${sessionId}")
                                            },
                                            colors = ButtonDefaults.buttonColors(containerColor = AddButton),
                                        ) {
                                            Text("+")
                                        }
                                    }

                                }
                                if (exercise.description != "") {
                                    OutlinedCard(
                                        modifier = Modifier
                                            .padding(12.dp)
                                            .fillMaxWidth()
                                    ) {
                                        Text(
                                            text = "Description:", fontWeight = Bold,
                                            modifier = Modifier.padding(
                                                start =
                                                10.dp, top = 10.dp
                                            )
                                        )
                                        Text(
                                            text = exercise.description,
                                            modifier = Modifier.padding(
                                                10.dp
                                            )
                                        )
                                    }
                                }
                            }
                        }
                    }

                }
                item {
                    Button(
                        onClick = {
                            navController.navigate("ExerciseScreen")
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = InteractionButton),
                        modifier = Modifier
                            .padding(paddingButton)
                            .fillMaxWidth()
                    ) {
                        Text("All Exercises")
                    }
                }

            }


        }
    }
}