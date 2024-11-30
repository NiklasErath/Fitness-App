package edu.cc231030.MC.project.ui.theme.style

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp


@Composable
fun StyledTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            textStyle = TextStyle(
                textAlign = TextAlign.End, // Align the value text to the right
                color = Color.Black,
                fontSize = 24.sp
            ),
            maxLines = 1,
            modifier = Modifier
                .width(textFieldWidth)
                .height(textFieldHeight)
                .background(Color.Magenta)
        )

        Text(
            text = " x",
            style = TextStyle(color = Color.Black)
        )
    }
}



