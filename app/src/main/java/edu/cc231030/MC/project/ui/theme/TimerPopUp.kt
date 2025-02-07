package edu.cc231030.MC.project.ui.theme

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import edu.cc231030.MC.project.ui.theme.style.InteractionButton
import kotlinx.coroutines.time.delay
import java.time.Duration

// Timer Pop up composable when an exercise is done
@Composable
fun TimerPopUp(onDismissRequest: () -> Unit) {
    var time by remember { mutableIntStateOf(5) }

    // start timer when launch composable
    LaunchedEffect(time) {
        if (time > 0) {
            delay(Duration.ofMillis(1000))
            time--
        }
    }
    Dialog(onDismissRequest = { onDismissRequest() }) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .align(Alignment.BottomCenter),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceEvenly
                ) {
                    Box(
                        modifier = Modifier
                            .padding(12.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        if (time != 0) {
                            Text(
                                text = "Rest: $time",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .wrapContentHeight(),
                                textAlign = TextAlign.Center,
                            )
                        } else {
                            Text(
                                text = "Continue Workout",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .wrapContentHeight(),
                                textAlign = TextAlign.Center,
                            )
                        }
                    }
                    // button to skip the timer
                    Button(
                        onClick = { onDismissRequest() },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = InteractionButton),
                        ) {
                        if (time != 0) {
                            Text(text = "Skip")
                        } else {
                            Text(text = "Continue")
                        }

                    }
                }
            }
        }


    }
}

