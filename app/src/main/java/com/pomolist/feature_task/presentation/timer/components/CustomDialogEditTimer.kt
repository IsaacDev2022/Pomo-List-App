@file:OptIn(ExperimentalMaterial3Api::class)

package com.pomolist.feature_task.presentation.timer.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import com.pomolist.feature_task.presentation.timer.screens.TimerViewModel

@Composable
fun CustomDialogEditTimer(
    onDimiss: (value: String) -> Unit,
    minutes: Long,
    seconds: Long,
    viewModel: TimerViewModel = viewModel()
) {
    var textMinutes = remember { mutableStateOf(minutes.toString()) }
    var textSeconds = remember { mutableStateOf(seconds.toString()) }

    Dialog(onDismissRequest = { onDimiss("") }) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(30.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextField(value = textMinutes.value, onValueChange = { textMinutes.value = it })
            Spacer(modifier = Modifier.height(30.dp))
            TextField(value = textSeconds.value, onValueChange = { textSeconds.value = it })

            Spacer(modifier = Modifier.height(50.dp))
            Row {
                Button(onClick = { onDimiss("") }) {
                    Text(text = "Cancelar")
                }
                Button(onClick = {
                    onDimiss(textMinutes.value)
                    // viewModel.alterTimeText(textMinutes.value, textSeconds.value)
                    // viewModel.minuteTime = textMinutes
                    // viewModel.secondsTime = textSeconds
                }) {
                    Text(text = "Confirmar")
                }
            }
        }
    }
}