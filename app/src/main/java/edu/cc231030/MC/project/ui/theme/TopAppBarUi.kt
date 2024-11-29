package edu.cc231030.MC.project.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun topAppBar(title: String, navController: NavController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .background(Color.Black),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Button(
            onClick = { navController.navigateUp() },
            modifier = Modifier
                .padding(start = 8.dp)
        ) {
            Text(text = "Back")
        }
        Text(
            text = "$title", modifier = Modifier
                .weight(1f),
            color = Color.White,
            textAlign = TextAlign.Center
        )
        Button(
            onClick = { navController.navigateUp() },
            modifier = Modifier.padding(end = 8.dp)
        ) {
            Text(text = "Back")
        }
    }

}