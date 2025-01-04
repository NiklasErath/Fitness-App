package edu.cc231030.MC.project.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import edu.cc231030.MC.project.data.Exercise
import edu.cc231030.MC.project.data.ExerciseSet
import edu.cc231030.MC.project.ui.theme.style.InteractionLightButton
import edu.cc231030.MC.project.ui.theme.style.ExerciseItemBackground
import edu.cc231030.MC.project.ui.theme.style.DeleteLightButton
import edu.cc231030.MC.project.ui.theme.style.CheckBoxColor
import edu.cc231030.MC.project.ui.theme.style.ExerciseSetBackground
import edu.cc231030.MC.project.ui.theme.style.paddingButton
import edu.cc231030.MC.project.ui.theme.style.paddingExercise
import edu.cc231030.MC.project.ui.theme.style.StyledTextField


// exercise layout composable - layout to display the exercise
@Composable
fun ExerciseItem(
    exercise: Exercise,
    exerciseSet: List<ExerciseSet>,
    onDelete: (Exercise) -> Unit,
    onDeleteSet: (ExerciseSet) -> Unit,
    onUpdateSet: (Int, Int, Int, Int) -> Unit,
    onAddSet: (Int, Int, Int) -> Unit,
    screen: String,
    navController: NavController
) {

    Column(modifier = Modifier.padding(paddingExercise)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(ExerciseItemBackground),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = exercise.name,
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 2.dp),
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                color = Color.Black,
            )
            Button(
                onClick = { onDelete(exercise) },
                colors = ButtonDefaults.buttonColors(containerColor = DeleteLightButton),
            ) {
                Text(text = "Delete")
            }
        }
        if (screen != "session") {
            OutlinedCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp)
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
            Button(
                onClick = {
                    navController.navigate("EditExerciseDescription/${exercise.id}")
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = InteractionLightButton),
            ) {
                Text(text = "Edit Description")
            }
        }
    }
    // EXERCISE SET****************************************
    Column {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(paddingExercise)
                .shadow(5.dp, shape = RoundedCornerShape(12.dp)),
            colors = CardDefaults.cardColors(ExerciseSetBackground),
            shape = RoundedCornerShape(12.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 3.dp, bottom = 3.dp),
                horizontalArrangement = Arrangement.SpaceAround,
            ) {
                Text("Reps", fontWeight = Bold)
                Text("Weight", fontWeight = Bold)
                if (screen != "exercise") {
                    Text("Done", fontWeight = Bold)
                }
                Text("Delete", fontWeight = Bold)
            }
            // border line to separate
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(2.dp)
                    .background(Color.Gray)
            )
            if (exerciseSet.isNotEmpty()) {
                // display the sets of the exercise
                exerciseSet.forEach { set ->
                    // individual states for each set - reps and weight
                    val setReps = remember { mutableStateOf(set.reps.toString()) }
                    val setWeight = remember { mutableStateOf(set.weight.toString()) }
                    // checkbox state
                    var checked by remember { mutableStateOf(false) }
                    val openTimer = remember { mutableStateOf(false) }

                    Column {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(color = ExerciseSetBackground)

                        ) {
                            Column {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceEvenly,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Column(modifier = Modifier.padding(start = 8.dp)) {
                                        if (screen != "session") {
                                            StyledTextField(
                                                value = setReps.value,
                                                onValueChange = { newReps ->
                                                    setReps.value = newReps
                                                },
                                                label = "${set.reps} x",
                                                modifier = Modifier,
                                                extra = "x",
                                                readonly = true,
                                            )
                                        } else {
                                            StyledTextField(
                                                value = setReps.value,
                                                onValueChange = { newReps ->
                                                    setReps.value = newReps
                                                },
                                                label = "${set.reps} x",
                                                modifier = Modifier,
                                                extra = "x",
                                                readonly = false,
                                            )
                                        }
                                    }
                                    Column(modifier = Modifier.padding(start = 14.dp)) {
                                        if (screen != "session") {
                                            StyledTextField(
                                            value = setWeight.value,
                                            onValueChange = { newWeight ->
                                                setWeight.value = newWeight
                                            },
                                            label = "${set.weight} kg",
                                            modifier = Modifier,
                                            extra = "kg",
                                            readonly = true,
                                        )
                                        } else {
                                            StyledTextField(
                                                value = setWeight.value,
                                                onValueChange = { newWeight ->
                                                    setWeight.value = newWeight
                                                },
                                                label = "${set.weight} kg",
                                                modifier = Modifier,
                                                extra = "kg",
                                                readonly = false,
                                            )
                                        }
                                    }
                                    //not display checkbox on exercise screen
                                    if (screen != "exercise") {
                                        Column(modifier = Modifier.padding(start = 10.dp)) {
                                            Checkbox(modifier = Modifier,
                                                checked = checked,
                                                colors = CheckboxDefaults.colors(
                                                    checkedColor = CheckBoxColor,    // Color when checked
                                                    uncheckedColor = Color.Gray,   // Color when unchecked
                                                    checkmarkColor = Color.White   // Color of the checkmark
                                                ),
                                                onCheckedChange = { newCheckedState ->
                                                    checked = newCheckedState
                                                    if (checked) {
                                                        openTimer.value = true
                                                    }
                                                    val repsInt = setReps.value.toIntOrNull() ?: 0
                                                    val weightInt =
                                                        setWeight.value.toIntOrNull() ?: 0
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
                                    // delete Exercise Set
                                    Button(
                                        onClick = { onDeleteSet(set) },
                                        modifier = Modifier.padding(
                                            start = 16.dp,
                                            top = 12.dp,
                                            bottom = 12.dp
                                        ),
                                        colors = ButtonDefaults.buttonColors(containerColor = DeleteLightButton),

                                        ) {
                                        Text(text = "X")
                                    }
                                }
                            }
                        }
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(1.dp)
                                .background(Color.Gray)
                        )
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
            // Add new set to the exercise
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(paddingButton),
                colors = ButtonDefaults.buttonColors(containerColor = InteractionLightButton),
                onClick = { onAddSet(exercise.id, 0, 0) }
            ) {
                Text(text = "Add Set")
            }
        }
    }
}
