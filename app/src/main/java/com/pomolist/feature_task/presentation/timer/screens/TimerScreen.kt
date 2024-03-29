@file:OptIn(ExperimentalMaterial3Api::class)

package com.pomolist.feature_task.presentation.timer.screens

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.provider.Settings.Global.getString
import android.text.format.DateUtils
import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.content.ContextCompat.startForegroundService
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.pomolist.R
import com.pomolist.core.navigation.Screen
import com.pomolist.feature_task.presentation.MainActivity
import com.pomolist.feature_task.presentation.timer.TimerService
import com.pomolist.feature_task.presentation.timer.components.NotificationService
import com.pomolist.ui.theme.primaryColor
import com.pomolist.ui.theme.secondaryColor
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.delay
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.launch
import kotlin.math.abs

@Composable
fun TimerScreen(
    navController: NavController,
    modifier: Modifier = Modifier
) {

    val timerTextScale = remember { Animatable(1f) }

    var minutes by rememberSaveable { mutableStateOf(1L) }
    var seconds by rememberSaveable { mutableStateOf(0L) }
    var pomodoroQtd by remember { mutableStateOf(3) }

    var totalTime by remember { mutableLongStateOf(minutes * 60L + seconds) }

    var running by remember { mutableStateOf(false) }

    var playPauseToggle by remember { mutableStateOf(false) }
    var elapsedTime by remember { mutableLongStateOf(0L) }
    val coroutineScope = rememberCoroutineScope()
    var currentTime by remember { mutableLongStateOf(totalTime) }

    // Variáveis do arco
    var value by remember { mutableStateOf(1f) }
    var size by remember { mutableStateOf(IntSize.Zero) }

    // Notificações
//    val timerNotification = NotificationService(LocalContext.current)

    // Service
//    var context: Context = LocalContext.current
//    var foregroundService: Intent? = null
//
//    foregroundService = Intent(context, TimerService::class.java)

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(secondaryColor)
    ) {
        Row() {
            Icon(
                imageVector = Icons.Filled.Home,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier
                    .padding(20.dp)
                    .height(40.dp)
                    .width(40.dp)
                    .testTag("homeIcon")
                    .clickable { navController.navigate(Screen.HomeScreen.route) }
            )
            Spacer(modifier = Modifier.width(220.dp))
            Icon(
                imageVector = Icons.Filled.Settings,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier
                    .padding(20.dp)
                    .height(40.dp)
                    .width(40.dp)
                    .clickable { navController.navigate(Screen.HomeScreen.route) }
            )
        }

        Column(
            modifier = modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = modifier.height(100.dp))
            Box(contentAlignment = Alignment.Center,
                modifier = modifier
                    .onSizeChanged {
                        size = it
                    }
            ) {
                Canvas(modifier = Modifier
                    .size(300.dp)
                ) {
                    drawArc(
                        color = Color.White,
                        startAngle = 360f,
                        sweepAngle = 360f,
                        useCenter = false,
                        size = Size(size.width.toFloat(), size.height.toFloat()),
                        style = Stroke(10.dp.toPx(), cap = StrokeCap.Round)
                    )
                    drawArc(
                        color = primaryColor,
                        startAngle = 270f,
                        sweepAngle = 360f * value,
                        useCenter = false,
                        size = Size(size.width.toFloat(), size.height.toFloat()),
                        style = Stroke(10.dp.toPx(), cap = StrokeCap.Round)
                    )
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = abs(totalTime - elapsedTime).formatDuration(),
                        fontWeight = FontWeight.Bold,
                        fontSize = 50.sp,
                        color = Color.White,
                        modifier = Modifier
                            .scale(timerTextScale.value)
                    )
                    Spacer(modifier = modifier.height(10.dp))
                    Text(
                        text = "0 / ${pomodoroQtd}",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = Color.White,
                        modifier = Modifier
                            .scale(timerTextScale.value)
                    )
                }
            }

            Spacer(modifier = modifier.height(50.dp))

            Row() {
                Button(
                    modifier = modifier
                        .width(80.dp)
                        .height(60.dp),
                    onClick = {
                        elapsedTime = 0L
//                        currentTime = totalTime
                        value = 1f
                    },
                    colors = ButtonDefaults.buttonColors(primaryColor)
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

                Spacer(modifier = modifier.width(20.dp))

                Button(
                    modifier = modifier
                        .width(100.dp)
                        .height(60.dp),
                    onClick = {
                        if (playPauseToggle) {
                            running = false
                            coroutineScope.coroutineContext.cancelChildren()
//                            context.stopService(foregroundService)
                        } else {
//                            context.startService(foregroundService)
                            running = true
                            coroutineScope.launch {
                                while (pomodoroQtd > 0) {
                                    minutes = 1L
                                    seconds = 0L
                                    while (elapsedTime < totalTime) {
                                        elapsedTime++
                                        delay(1000)
                                        value = currentTime / totalTime.toFloat()
                                    }
                                    if (elapsedTime == totalTime) {
                                        minutes = 0L
                                        seconds = 30L
                                        elapsedTime = 0
                                        var totalTimeInterval = minutes + seconds
                                        currentTime = totalTime
//                                        timerNotification.showNotification()
                                        while (elapsedTime < totalTimeInterval) {
                                            elapsedTime++
                                            delay(1000)
                                            value = currentTime / totalTime.toFloat()
                                        }
                                        while (elapsedTime > 0) {
                                            elapsedTime--
                                            delay(100)
                                            value = currentTime / totalTime.toFloat()
                                        }
//                                        timerNotification.showNotification()
                                        pomodoroQtd -= 1
                                    }
                                }
                            }
                        }
                        playPauseToggle = !playPauseToggle
                    },
                    colors = ButtonDefaults.buttonColors(primaryColor)
                ) {
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

                Spacer(modifier = modifier.width(20.dp))

                Button(
                    modifier = modifier
                        .width(80.dp)
                        .height(60.dp),
                    onClick = {
                        elapsedTime = 0L
//                        timerNotification.showNotification()
                    },
                    colors = ButtonDefaults.buttonColors(primaryColor)
                ) {
                    Icon(
                        modifier = Modifier
                            .height(40.dp)
                            .width(40.dp),
                        imageVector = Icons.Outlined.Notifications,
                        contentDescription = null,
                        tint = Color.White
                    )
                }
            }

            Spacer(modifier = modifier.height(40.dp))
        }
    }
}

fun Long.formatDuration(): String = DateUtils.formatElapsedTime(this)