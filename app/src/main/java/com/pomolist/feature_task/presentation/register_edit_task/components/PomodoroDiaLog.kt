@file:OptIn(ExperimentalMaterial3Api::class)

package com.pomolist.feature_task.presentation.register_edit_task.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LockClock
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.pomolist.feature_task.presentation.register_edit_task.screens.RegisterViewModel
import com.pomolist.ui.theme.primaryColor

@Composable
fun PomodoroDiaLog(
    onDimiss: (value: String) -> Unit,
    minutesTask: Long,
    secondsTask: Long,
    amountTask: Long,
    registerViewModel: RegisterViewModel = hiltViewModel()
) {
    Dialog(onDismissRequest = { onDimiss("") }) {
        val minutes = remember { mutableStateOf(minutesTask.toString()) }
        val seconds = remember { mutableStateOf(secondsTask.toString()) }
        val amount = remember { mutableStateOf(amountTask.toString()) }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(30.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(value = minutes.value, onValueChange = { minutes.value = it }, leadingIcon = {
                Icon(
                    modifier = Modifier.padding(end = 10.dp),
                    imageVector = Icons.Filled.LockClock,
                    tint = primaryColor,
                    contentDescription = "Imagem"
                )
            })
            Spacer(modifier = Modifier.height(30.dp))
            OutlinedTextField(value = seconds.value, onValueChange = { seconds.value = it }, leadingIcon = {
                Icon(
                    modifier = Modifier.padding(end = 10.dp),
                    imageVector = Icons.Filled.LockClock,
                    tint = primaryColor,
                    contentDescription = "Imagem"
                )
            })
            Spacer(modifier = Modifier.height(30.dp))
            OutlinedTextField(value = amount.value, onValueChange = { amount.value = it }, leadingIcon = {
                Icon(
                    modifier = Modifier.padding(end = 10.dp),
                    imageVector = Icons.Filled.LockClock,
                    tint = primaryColor,
                    contentDescription = "Imagem"
                )
            })

            Spacer(modifier = Modifier.height(50.dp))
            Row {
                Button(onClick = { onDimiss("") }) {
                    Text(text = "Cancelar")
                }
                Spacer(modifier = Modifier.width(30.dp))
                Button(onClick = {
                    onDimiss(
                        minutes.value
                    )
                    // viewModel.alterTimeText(textMinutes.value, textSeconds.value)
                    // viewModel.minuteTime = textMinutes
                    // viewModel.secondsTime = textSeconds
                        registerViewModel.minute.value = minutes.value.toLong()
                        registerViewModel.second.value = seconds.value.toLong()
                        registerViewModel.amount.value = amount.value.toLong()
                }) {
                    Text(text = "Confirmar")
                }
            }
        }
    }
}