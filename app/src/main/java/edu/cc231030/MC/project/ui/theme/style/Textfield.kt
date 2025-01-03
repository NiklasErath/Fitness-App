package edu.cc231030.MC.project.ui.theme.style

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun StyledTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    extra: String
) {

    Row(verticalAlignment = Alignment.CenterVertically) {
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            textStyle = TextStyle(
                textAlign = TextAlign.End,
                color = Color.Black,
                fontSize = 24.sp
            ),
            maxLines = 1,
            singleLine = true,
            modifier = Modifier
                .width(textFieldWidth)
                .height(textFieldHeight)
                .background(Color.White)
                .drawBehind {
                    val strokeWidth = 2.dp.toPx()
                    val y = size.height + 10 - strokeWidth / 2 // Position of the underline
                    drawLine(
                        color = Color.Black, // Color of the underline
                        start = androidx.compose.ui.geometry.Offset(0f, y),
                        end = androidx.compose.ui.geometry.Offset(size.width, y),
                        strokeWidth = strokeWidth
                    )
                },
        )

        Text(modifier = Modifier
            .align(alignment = Alignment.Bottom)
            .padding(start = 4.dp),
            text = extra,
            style = TextStyle(color = Color.Black)
        )
    }
}



