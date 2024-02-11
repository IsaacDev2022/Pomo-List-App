@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class,
    ExperimentalMaterial3Api::class
)

package com.pomolist.feature_task.presentation.register_edit_task.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.pomolist.core.navigation.Screen
import com.pomolist.feature_task.presentation.register_edit_task.components.PomodoroDiaLog
import com.pomolist.ui.theme.primaryColor
import kotlinx.coroutines.flow.collectLatest
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.filled.TextDecrease
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.pomolist.feature_task.presentation.register_edit_task.RegisterEvent
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone

@Composable
fun RegisterScreen(
    navController: NavController,
    registerViewModel: RegisterViewModel = hiltViewModel()
) {
    val nameState = registerViewModel.nameTask.value
    val descriptionState = registerViewModel.descriptionTask.value
    val priorityState = registerViewModel.priorityTask.value
    val dateState = registerViewModel.dateTask.value
    val timeState = registerViewModel.timeTask.value
    val minutes = registerViewModel.minute.value
    val seconds = registerViewModel.second.value
    val amount = registerViewModel.amount.value

    LaunchedEffect(key1 = true) {
        registerViewModel.uiEventFlow.collectLatest { event ->
            when (event) {
                is RegisterViewModel.UiEvent.SaveTask -> {
                    navController.navigateUp()
                }

                else -> {}
            }
        }
    }

    Scaffold(
        bottomBar = {
            BottomBar(
                onInsertTask = { registerViewModel.onEvent(RegisterEvent.SaveTask) }
            )
        },
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        modifier = Modifier
                            .padding(start = 10.dp),
                        text = "Adicionar Tarefa",
                        color = primaryColor,
                        textAlign = TextAlign.Center
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.navigate(Screen.HomeScreen.route)
                    }) {
                        Icon(
                            modifier = Modifier
                                .size(60.dp)
                                .padding(start = 10.dp),
                            tint = primaryColor,
                            imageVector = Icons.Filled.ArrowBackIos,
                            contentDescription = "Voltar"
                        )
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(start = 40.dp, end = 16.dp, bottom = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(50.dp))
            RegisterContent(
                modifier = Modifier,
                name = nameState.text,
                description = descriptionState.text,
                priority = priorityState.text,
                date = dateState.text,
                time = timeState.text,
                minutes = minutes,
                seconds = seconds,
                amount = amount,
                onEvent = { registerViewModel.onEvent(it) }
            )
        }
    }
}

@Composable
fun RegisterContent(
    modifier: Modifier = Modifier,
    name: String,
    description: String,
    priority: String,
    date: String,
    time: String,
    minutes: Long,
    seconds: Long,
    amount: Long,
    onEvent: (RegisterEvent) -> Unit
) {
    var dialogShowing = remember {
        mutableStateOf(false)
    }

    Column(modifier = Modifier.fillMaxWidth()) {
        Spacer(modifier = modifier.height(30.dp))
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .testTag("NameTF"),
            value = name,
            onValueChange = { onEvent(RegisterEvent.EnteredName(it)) },
            label = { Text("Nome") },
            leadingIcon = {
                Icon(
                    modifier = Modifier.padding(end = 10.dp),
                    imageVector = Icons.Filled.Person,
                    tint = primaryColor,
                    contentDescription = "Imagem"
                )
            },
            keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Sentences)
        )

        Spacer(modifier = modifier.height(10.dp))

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .height(150.dp)
                .testTag("DescriptionTF"),
            value = description,
            onValueChange = { onEvent(RegisterEvent.EnteredDescription(it)) },
            label = { Text("Descrição") },
            leadingIcon = {
                Icon(
                    modifier = Modifier.padding(end = 10.dp),
                    imageVector = Icons.Filled.TextDecrease,
                    tint = primaryColor,
                    contentDescription = "Imagem"
                )
            },
            keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Sentences)
        )

        Spacer(modifier = modifier.height(10.dp))

        dropDowmMenu(
            priority = priority,
            onEvent = {
                Log.e("EventInput", it)
                onEvent(RegisterEvent.EnteredPriority(it))
            }
        )
        Spacer(modifier = modifier.height(10.dp))

        Row {
            val focusManager = LocalFocusManager.current
            // Date Picker
            val datePickerState = rememberDatePickerState()
            var showDatePicker by remember { mutableStateOf(false) }

            var selectedDate by remember { mutableStateOf(date) }

            if (showDatePicker) {
                DatePickerDialog(
                    onDismissRequest = { /*TODO*/ },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                showDatePicker = false
                                datePickerState
                                    .selectedDateMillis?.let { millis ->
                                        selectedDate = millis.toBrazilianDateFormat()
                                    }
                                onEvent(RegisterEvent.EnteredDate(selectedDate))
                            }
                        ) { Text("OK") }
                    },
                    dismissButton = {
                        TextButton(
                            onClick = {
                                showDatePicker = false
                            }
                        ) { Text("Cancel") }
                    }
                )
                {
                    DatePicker(state = datePickerState)
                }
            }

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth(0.4f)
                    .onFocusEvent {
                        if (it.isFocused) {
                            showDatePicker = true
                            focusManager.clearFocus(force = true)
                        }
                    }
                    .testTag("DateTF"),
                value = selectedDate,
                onValueChange = { onEvent(RegisterEvent.EnteredDate(it)) },
                label = { Text("Data") },
                leadingIcon = {
                    Icon(
                        modifier = Modifier.padding(end = 10.dp),
                        imageVector = Icons.Filled.DateRange,
                        tint = primaryColor,
                        contentDescription = "Imagem"
                    )
                }
            )

            Spacer(modifier = modifier.width(18.dp))
            // Time Picker
            val timePickerState = rememberTimePickerState(is24Hour = true)
            var showTimePicker by remember { mutableStateOf(false) }

            var selectedTime by remember { mutableStateOf(time) }

            val formatter = remember { SimpleDateFormat("hh:mm a", Locale.getDefault()) }

            if (showTimePicker) {
                TimePickerDialog(
                    title = "00:00",
                    onDismissRequest = { /*TODO*/ },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                val cal = Calendar.getInstance()
                                cal.set(Calendar.HOUR_OF_DAY, timePickerState.hour)
                                cal.set(Calendar.MINUTE, timePickerState.minute)
                                selectedTime = formatter.format(cal.time)
                                showTimePicker = false
                                onEvent(RegisterEvent.EnteredTime(selectedTime))
                            }
                        ) { Text("OK") }
                    },
                    dismissButton = {
                        TextButton(
                            onClick = {
                                showTimePicker = false
                            }
                        ) { Text("Cancel") }
                    }
                ) {
                    TimePicker(state = timePickerState)
                }
            }

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .onFocusEvent {
                        if (it.isFocused) {
                            showTimePicker = true
                            focusManager.clearFocus(force = true)
                        }
                    }
                    .testTag("TimeTF"),
                value = selectedTime,
                onValueChange = { onEvent(RegisterEvent.EnteredTime(it)) },
                label = { Text("Horário") },
                leadingIcon = {
                    Icon(
                        modifier = Modifier.padding(end = 10.dp),
                        imageVector = Icons.Filled.Timer,
                        tint = primaryColor,
                        contentDescription = "Imagem"
                    )
                }
            )
        }

        Spacer(modifier = modifier.height(10.dp))

        if (dialogShowing.value) {
            PomodoroDiaLog(
                onDimiss = {
                    dialogShowing.value = false
                },
                minutesTask = minutes,
                secondsTask = seconds,
                amountTask = amount
            )
        }

        Button(
            onClick = {
                dialogShowing.value = true
            },
            modifier = modifier
                .width(300.dp)
                .padding(top = 20.dp)
                .height(50.dp),
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(primaryColor)
        ) {
            Text(text = "Pomodoro")
        }

        Spacer(modifier = modifier.height(40.dp))
    }
}

@Composable
fun TimePickerDialog(
    title: String,
    onDismissRequest: () -> Unit,
    confirmButton: @Composable (() -> Unit),
    dismissButton: @Composable (() -> Unit)? = null,
    containerColor: Color = MaterialTheme.colorScheme.surface,
    content: @Composable () -> Unit,
) {
   Dialog(
       onDismissRequest = onDismissRequest,
       properties = DialogProperties(
           usePlatformDefaultWidth = false
       )
   ) {
       Surface(
           shape = MaterialTheme.shapes.extraLarge,
           tonalElevation = 6.dp,
           modifier = Modifier
               .width(IntrinsicSize.Min)
               .height(IntrinsicSize.Min)
               .background(
                   shape = MaterialTheme.shapes.extraLarge,
                   color = containerColor
               ),
           color = containerColor
       ) {
           Column(
               modifier = Modifier.padding(24.dp),
               horizontalAlignment = Alignment.CenterHorizontally
           ) {
               Text(
                   modifier = Modifier
                       .fillMaxWidth()
                       .padding(bottom = 20.dp),
                   text = title,
                   style = MaterialTheme.typography.labelMedium
               )
               content()
               Row(
                   modifier = Modifier
                       .height(40.dp)
                       .fillMaxWidth()
               ) {
                   Spacer(modifier = Modifier.weight(1f))
                   dismissButton?.invoke()
                   confirmButton()
               }
           }
       }
   }
}

@Composable
fun BottomBar(
    modifier: Modifier = Modifier,
    onInsertTask: () -> Unit
) {
    Button(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 30.dp, vertical = 30.dp)
            .height(50.dp)
            .semantics {
                 contentDescription = "Botão Confirmar"
            },
        onClick = { onInsertTask() },
        shape = RoundedCornerShape(10.dp),
        colors = ButtonDefaults.buttonColors(primaryColor)
    ) {
        Text(
            text = "Confirmar",
            fontSize = 15.sp
        )
    }
}

@Composable
fun dropDowmMenu(
    priority: String,
    onEvent: (String) -> Unit
) {
    val expanded = remember { mutableStateOf(false) }

    val list = listOf("1 - Baixa", "2 - Media", "3 - Alta", "4 - Urgente")

    ExposedDropdownMenuBox(
        expanded = expanded.value,
        onExpandedChange = {
            expanded.value = !expanded.value
        }
    ) {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .menuAnchor(),
            value = priority,
            onValueChange = onEvent,
            readOnly = true,
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded.value)
            },
            leadingIcon = {
                Icon(
                    modifier = Modifier.padding(end = 10.dp),
                    imageVector = Icons.Filled.Check,
                    tint = primaryColor,
                    contentDescription = "Imagem"
                )
            }
        )
        ExposedDropdownMenu(
            expanded = expanded.value,
            onDismissRequest = { expanded.value = false }
        ) {
            list.forEach {
                DropdownMenuItem(
                    text = { Text(text = it) },
                    onClick = {
                        onEvent(it)
                        expanded.value = false
                    }
                )
            }
        }
    }
}

fun Long.toBrazilianDateFormat(
    pattern: String = "dd/MM/yyyy"
): String {
    val date = Date(this)
    val formatter = SimpleDateFormat(
        pattern, Locale("pt-br")
    ).apply {
        timeZone = TimeZone.getTimeZone("GMT")
    }
    return formatter.format(date)
}