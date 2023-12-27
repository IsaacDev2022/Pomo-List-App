package com.pomolist.feature_task.presentation.timer

import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PointMode
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.inset
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.delay
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.launch
import kotlin.math.PI
import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun TimerScreenTest(navController: NavHostController) {
    Surface(
        color = Color(0xFF101010),
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            contentAlignment = Alignment.Center
        ) {
            /* Timer(
                totalTime = 100L * 1000L,
                handleColor = Color.Green,
                inactiveBarColor = Color.DarkGray,
                activeBarColor = Color(0xFF37B900),
                modifier = Modifier.size(200.dp)
            ) */
            Timer()
        }
    }
}

/* @Composable
fun Timer(
    totalTime: Long,
    handleColor: Color,
    inactiveBarColor: Color,
    activeBarColor: Color,
    modifier: Modifier = Modifier,
    initialValue: Float = 1f,
    strokeWidth: Dp = 5.dp
) {
    var size by remember {
        mutableStateOf(IntSize.Zero)
    }
    var value by remember {
        mutableStateOf(initialValue)
    }
    var currentTime by remember {
        mutableStateOf(totalTime)
    }
    var isTimerRunning by remember {
        mutableStateOf(false)
    }
    LaunchedEffect(key1 = currentTime, key2 = isTimerRunning) {
        if(currentTime > 0 && isTimerRunning) {
            delay(100L)
            currentTime -= 100L
            value = currentTime / totalTime.toFloat()
        }
    }
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .onSizeChanged {
                size = it
            }
    ) {
        Text(
            text = (currentTime / 1000L).toString(),
            fontSize = 44.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
        Button(
            onClick = {
                if(currentTime <= 0L) {
                    currentTime = totalTime
                    isTimerRunning = true
                } else {
                    isTimerRunning = !isTimerRunning
                }
            },
            modifier = Modifier.align(Alignment.BottomCenter),
            colors = ButtonDefaults.buttonColors(
                containerColor = if (!isTimerRunning || currentTime <= 0L) {
                    Color.Green
                } else {
                    Color.Red
                }
            )
        ) {
            Text(
                text = if (isTimerRunning && currentTime >= 0L) "Stop"
                else if (!isTimerRunning && currentTime >= 0L) "Start"
                else "Restart"
            )
        }
    }
} */

@Composable
fun Timer() {
    val timerTextScale = remember { Animatable(1f) }

    var minutes by remember { mutableStateOf(1L) }
    var seconds by remember { mutableStateOf(0L) }
    var pomodoroQtd by remember { mutableStateOf(3) }

    var running by remember { mutableStateOf(false) }

    var playPauseToggle by remember { mutableStateOf(false) }

    var totalTime = minutes * 60L + seconds

    var elapsedTime by remember { mutableStateOf(0L) }

    val coroutineScope = rememberCoroutineScope()

    var currentTime by remember { mutableStateOf(totalTime) }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(200.dp))
        Column() {
            Text(
                text = abs(totalTime - elapsedTime).formatDuration(),
                fontWeight = FontWeight.Bold,
                fontSize = 50.sp,
                color = Color.White,
                modifier = Modifier
                    .scale(timerTextScale.value)
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "0 / ${pomodoroQtd}",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = Color.White,
                modifier = Modifier
                    .scale(timerTextScale.value)
            )
        }
        Spacer(modifier = Modifier.height(40.dp))
        Row() {
            Button(
                modifier = Modifier
                    .width(80.dp)
                    .height(60.dp),
                onClick = {
                    elapsedTime = 0L
                    currentTime = totalTime
                }
            ) {
                Icon(
                    modifier = Modifier
                        .height(40.dp)
                        .width(40.dp),
                    imageVector = Icons.Filled.Refresh,
                    contentDescription = null,
                    tint = Color.White
                )
            }
            Spacer(modifier = Modifier.width(20.dp))
            Button(
                modifier = Modifier
                    .width(100.dp)
                    .height(60.dp),
                onClick = {
                    if (playPauseToggle) {
                        running = false
                        coroutineScope.coroutineContext.cancelChildren()
                    } else {
                        running = true
                        coroutineScope.launch {
                            while (pomodoroQtd > 0) {
                                // elapsedTime = 0
                                // running = false
                                minutes = 1
                                seconds = 0
                                elapsedTime = 0
                                running = false
                                while (elapsedTime < totalTime) {
                                    ensureActive()
                                    elapsedTime += 1
                                    delay(1000)
                                    currentTime -= 1L
                                }
                                if (elapsedTime == totalTime) {
                                    minutes = 0L
                                    seconds = 30L
                                    elapsedTime = 0
                                    var totalTimeInterval = minutes + seconds
                                    currentTime = totalTime
                                    while (elapsedTime < totalTimeInterval) {
                                        ensureActive()
                                        elapsedTime += 1
                                        delay(1000)
                                        currentTime -= 1L
                                    }
                                    while (elapsedTime > 0) {
                                        ensureActive()
                                        elapsedTime -= 1
                                        delay(100)
                                    }
                                    pomodoroQtd -= 1
                                }
                            }

                            /*while (elapsedTime < totalTime) {
                                ensureActive()
                                elapsedTime += 1
                                delay(1000)
                                currentTime -= 1L
                                value = currentTime / totalTime.toFloat()
                            }
                            while (elapsedTime > 0) {
                                ensureActive()
                                elapsedTime -= 1
                                delay(100)
                            }
                            if (elapsedTime == 0L) {
                                minutes = 0
                                seconds = 30
                            }
                            elapsedTime = 0
                            running = false */
                            // pomodoroQtd -= 1
                        }
                    }
                    playPauseToggle = !playPauseToggle
                }) {
                /* Text(
                    text = if (running && elapsedTime >= 0L) "Stop"
                    else if (!running && elapsedTime >= 0L) "Start"
                    else "Restart"
                ) */
                if (running && elapsedTime >= 0L) {
                    Icon(
                        imageVector = Icons.Filled.Stop,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier
                            .height(40.dp)
                            .width(40.dp)
                    )
                }
                if (!running && elapsedTime >= 0L) {
                    Icon(
                        imageVector = Icons.Filled.PlayCircle,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier
                            .height(40.dp)
                            .width(40.dp)
                    )
                }
            }
        }
    }
}