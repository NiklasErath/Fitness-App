package edu.cc231030.MC.project.ui.theme

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button

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
import kotlinx.coroutines.time.delay
import java.time.Duration


@Composable
fun TimerPopUp(onDismissRequest: () -> Unit) {
    var time by remember { mutableIntStateOf(5) }

// Launch the effect to update the timer every second
    LaunchedEffect(time) {
        if (time > 0) {
            delay(Duration.ofMillis(1000))
            time--  // decrease the timeLeft after 1000 millis
        }
    }
    Dialog(onDismissRequest = { onDismissRequest() }) {
        Box(
            modifier = Modifier
                .fillMaxSize() // Fill the entire screen
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .align(Alignment.BottomCenter),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column( // Use Column to allow multiple child views
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
                    Button(
                        onClick = { onDismissRequest() },
                        modifier = Modifier.fillMaxWidth(0.8f) // Optional: width is 80% of card width
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

