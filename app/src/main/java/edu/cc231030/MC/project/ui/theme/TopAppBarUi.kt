package edu.cc231030.MC.project.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import edu.cc231030.MC.project.ui.theme.style.TopBarBackground


// TopBar Composable for title and Navigation
@Composable
fun TopAppBar(title: String, navController: NavController, navigation: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .background(TopBarBackground),
                verticalAlignment = Alignment.CenterVertically
    ) {
        if (title != "Sessions") {
            IconButton(
                onClick = { navController.navigateUp() },
                modifier = Modifier
                    .padding(start = 8.dp)
            ) {
                Icon(Icons.Filled.ArrowBack, contentDescription = "Back", modifier = Modifier.size(34.dp))
            }
        } else {
            // Spacer if the back button is not needed
            Spacer(modifier = Modifier.width(48.dp))
        }
        Text(
            text = title, modifier = Modifier
                .weight(1f),
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            color = Color.Black,
            textAlign = TextAlign.Center
        )
        if (navigation != "no") {
            IconButton(
                onClick = { navController.navigate(navigation) },
                modifier = Modifier.padding(end = 8.dp)
            ) {
                Icon(Icons.Filled.Add, contentDescription = "Add", modifier = Modifier.size(34.dp))
            }
        } else {
            Spacer(modifier = Modifier.width(48.dp))

        }
    }

}