package edu.cc231030.MC.project.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.node.ModifierNodeElement
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import edu.cc231030.MC.project.data.Exercise
import edu.cc231030.MC.project.data.ExerciseSet
import edu.cc231030.MC.project.ui.ExerciseViewModelFactory
import edu.cc231030.MC.project.ui.States.ExerciseSetsUiState
import edu.cc231030.MC.project.ui.theme.style.ButtonBrown
import edu.cc231030.MC.project.ui.theme.style.ButtonBrownLight
import edu.cc231030.MC.project.ui.theme.style.ExerciseSetBackground
import edu.cc231030.MC.project.ui.theme.style.Purple40
import edu.cc231030.MC.project.ui.theme.style.paddingButton
import edu.cc231030.MC.project.ui.theme.style.paddingExercise
import edu.cc231030.MC.project.ui.theme.style.textFieldHeight
import edu.cc231030.MC.project.ui.theme.style.textFieldWidth
import edu.cc231030.MC.project.ui.theme.style.StyledTextField
import edu.cc231030.MC.project.ui.viewModels.ExerciseViewModel

@Composable
fun ExerciseItem(
    exercise: Exercise,
    exerciseSet: List<ExerciseSet>,
    onDelete: (Exercise) -> Unit,
    onDeleteSet: (ExerciseSet) -> Unit,
    onUpdateSet: (Int, Int, Int, Int) -> Unit,
    onAddSet: (Int, Int, Int) -> Unit,
    screen: String
) {


    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(paddingExercise),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = exercise.name, modifier = Modifier.weight(1f),
            color = Color.Black,
        )
        Button(onClick = { onDelete(exercise) },
            colors = ButtonDefaults.buttonColors(containerColor = ButtonBrownLight),
        ) {
            Text(text = "Delete")
        }
    }
    Column {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(paddingExercise)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Text("Reps")
                Text("Weight")
                if (screen != "exercise") {
                    Text("Done")
                }
                Text("Delete")
            }
            if (exerciseSet.isNotEmpty()) {
                // display the sets of the exercise
                exerciseSet.forEach { set ->
                    // individual states for each set - reps and weight
                    val setReps = remember { mutableStateOf(set.reps.toString()) }
                    val setWeight = remember { mutableStateOf(set.weight.toString()) }
// checkbox state
                    var checked by remember { mutableStateOf(false) }
                    val openTimer = remember { mutableStateOf(false) }



                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(1.dp, Color.Gray)
                            .background(color = ExerciseSetBackground)

                    ) {
                        Column {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceEvenly,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column (modifier = Modifier.padding(start = 8.dp)){
                                    StyledTextField(
                                        value = setReps.value,
                                        onValueChange = { newReps -> setReps.value = newReps },
                                        label = "${set.reps} x",
                                        modifier = Modifier,
                                        extra = "x"
                                    )
                                }
                                Column(modifier = Modifier.padding(start = 14.dp)) {
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
                                //checkbox
                                if (screen != "exercise") {
                                    Column (modifier = Modifier.padding(start = 10.dp)){
                                        Checkbox(modifier = Modifier,
                                            checked = checked,
                                            onCheckedChange = { newCheckedState ->
                                                checked = newCheckedState
                                                if (checked) {
                                                    openTimer.value = true
                                                }
                                                val repsInt = setReps.value.toIntOrNull() ?: 0
                                                val weightInt = setWeight.value.toIntOrNull() ?: 0
                                                onUpdateSet(
                                                    set.id,
                                                    set.exerciseId,
                                                    repsInt,
                                                    weightInt
                                                )
                                            }

                                        )
                                    }
                                }
                                // close timer tab
                                if (openTimer.value) {
                                    TimerPopUp(
                                        onDismissRequest = { openTimer.value = false },
                                    )
                                }
                                Button(
                                    onClick = { onDeleteSet(set) },
                                    modifier = Modifier.padding(start = 16.dp, top = 12.dp, bottom = 12.dp),
                                    colors = ButtonDefaults.buttonColors(containerColor = ButtonBrownLight),

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
                colors = ButtonDefaults.buttonColors(containerColor = ButtonBrown),
                onClick = { onAddSet(exercise.id, 0, 0) }
            ) {
                Text(text = "Add Set")
            }
        }
    }
}
