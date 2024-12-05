package edu.cc231030.MC.project.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import edu.cc231030.MC.project.data.Exercise
import edu.cc231030.MC.project.data.ExerciseSet
import edu.cc231030.MC.project.ui.theme.style.Purple40
import edu.cc231030.MC.project.ui.theme.style.paddingButton
import edu.cc231030.MC.project.ui.theme.style.paddingExercise
import edu.cc231030.MC.project.ui.theme.style.textFieldHeight
import edu.cc231030.MC.project.ui.theme.style.textFieldWidth
import edu.cc231030.MC.project.ui.theme.style.StyledTextField

@Composable
fun ExerciseItem(
    exercise: Exercise,
    exerciseSet: List<ExerciseSet>,
    onDelete: (Exercise) -> Unit,
    onDeleteSet: (ExerciseSet) -> Unit,
    onUpdateSet: (Int, Int, Int, Int) -> Unit,
    onAddSet: (Int, Int, Int) -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(paddingExercise),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically 
    ) {
        Text(text = exercise.name, modifier = Modifier.weight(1f),
            color = Color.White,
        )
        Button(onClick = { onDelete(exercise) }) {
            Text(text = "Delete")
        }
    }
    Column {
        OutlinedCard(
            modifier = Modifier
                .fillMaxWidth()
                .padding(paddingExercise)
        ) {
            if (exerciseSet.isNotEmpty()) {
                // display the sets of the exercise
                exerciseSet.forEach { set ->
                    // individual states for each set - reps and weight
                    val setReps = remember { mutableStateOf(set.reps.toString()) }
                    val setWeight = remember { mutableStateOf(set.weight.toString()) }

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(1.dp, Color.Gray)
                            .padding(paddingExercise)
                    ) {
                        Column {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceAround
                            ) {
                                Column {
                                    Text(
                                        text = "Reps",
                                        textAlign = TextAlign.Center
                                    )
                                    StyledTextField(
                                        value = setReps.value,
                                        onValueChange = { newReps -> setReps.value = newReps },
                                        label = "${set.reps} x",
                                        modifier = Modifier,
                                        extra = "x"
                                    )
                                }
                                Column {
                                    Text(
                                        text = "Weight",
                                        textAlign = TextAlign.Center,
                                    )
                                    StyledTextField(
                                        value = setWeight.value,
                                        onValueChange = { newWeight ->
                                            setWeight.value = newWeight
                                        },
                                        label = "${set.weight} kg",
                                        modifier = Modifier,
                                        extra = "kg"
                                    )
                                }
                                Button(
                                    onClick = {
                                        // convert reps and weight to Int or Null
                                        val repsInt = setReps.value.toIntOrNull() ?: 0
                                        val weightInt = setWeight.value.toIntOrNull() ?: 0
                                        onUpdateSet(set.id, set.exerciseId, repsInt, weightInt)
                                    }, modifier = Modifier.padding(paddingButton)

                                ) {
                                    Text(text = "Save")
                                }

                                Button(
                                    onClick = { onDeleteSet(set) },
                                    modifier = Modifier.padding(paddingButton)
                                ) {
                                    Text(text = "X")
                                }
                            }
                        }
                    }
                }
            } else {
                Text(
                    text = "No sets added yet.",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp) // Padding around the text
                        .wrapContentWidth(Alignment.CenterHorizontally) // Center the text horizontally
                )
            }
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(paddingButton),
                onClick = { onAddSet(exercise.id, 0, 0) }
            ) {
                Text(text = "Add Set")
            }
        }
    }
}
