package com.example.androiddevchallenge

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

// Start building your app here!
@Composable
fun MyApp(viewModel: CountdownViewModel) {
    var isPause by remember { mutableStateOf(false) }
    var isStart by remember { mutableStateOf(false) }
    Surface(color = MaterialTheme.colors.background) {
        DrawCircle(-90f, 360 * viewModel.percent)
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(text = viewModel.getFormatTimeString(), style = MaterialTheme.typography.h3)
        }
        Row(
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {

            Button(onClick = {
                if (isPause) {
                    viewModel.restartCount()
                } else {
                    viewModel.startCount(3 * 60 * 1000)
                }
                isPause = false
                isStart = true
            }, enabled = isStart.not()) {
                Text(text = if (isPause) "RESTART" else "START")
            }
            Button(enabled = isStart, onClick = {
                isPause = true
                isStart = false
                viewModel.pauseCount()
            }) {
                Text(text = "PAUSE")
            }
            Button(onClick = {
                isPause = false
                isStart = false
                viewModel.resetCount()
            }) {
                Text(text = "RESET")
            }
        }
    }
}

@Composable
fun DrawCircle(startAngle: Float, sweepAngle: Float) {
    Canvas(modifier = Modifier.fillMaxSize()) {
        val canvasWidth = size.width
        val canvasHeight = size.height
        val centerOffset = Offset(x = canvasWidth / 2f, canvasHeight / 2f)
        val radius = 100.dp.toPx()
        val strokeWidth = 5.dp.toPx()
        drawCircle(
            style = Stroke(width = strokeWidth),
            color = Color.Gray,
            radius = radius,
            center = centerOffset
        )

        val x: Double = centerOffset.x + radius * cos((sweepAngle + startAngle) * PI / 180)
        val y: Double = centerOffset.y + radius * sin((sweepAngle + startAngle) * PI / 180)
        drawCircle(
            color = Color.Blue,
            radius = strokeWidth * 1.2f,
            center = Offset(x.toFloat(), y.toFloat())
        )
        drawArc(
            color = Color.Blue,
            topLeft = Offset(centerOffset.x - radius, centerOffset.y - radius),
            sweepAngle = sweepAngle,
            startAngle = startAngle,
            useCenter = false,
            size = Size(radius * 2, 2 * radius),
            style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
        )
    }
}
