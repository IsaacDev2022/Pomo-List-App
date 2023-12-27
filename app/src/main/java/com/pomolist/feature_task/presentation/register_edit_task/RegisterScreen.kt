@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class,
    ExperimentalMaterial3Api::class
)

package com.pomolist.feature_task.presentation.register_edit_task

import android.app.TimePickerDialog
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.material.icons.filled.Backpack
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PointMode
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.pomolist.R
import com.pomolist.core.navigation.Screen
import com.pomolist.feature_task.presentation.register_edit_task.components.InputText
import com.pomolist.feature_task.presentation.register_edit_task.components.PomodoroDiaLog
import com.pomolist.ui.theme.primaryColor
import kotlinx.coroutines.flow.collectLatest
import android.content.Context
import android.util.Log
import androidx.compose.ui.input.key.onKeyEvent
import com.maxkeppeker.sheets.core.models.base.rememberSheetState
import com.maxkeppeler.sheets.clock.ClockDialog
import com.maxkeppeler.sheets.clock.models.ClockSelection
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

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
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(start = 40.dp, end = 16.dp, bottom = 16.dp)
        ) {
            Row() {
                Icon(
                    imageVector = Icons.Filled.ArrowBackIos,
                    contentDescription = null,
                    tint = primaryColor,
                    modifier = Modifier
                        .padding(0.dp, 20.dp, 20.dp, 10.dp)
                        .height(30.dp)
                        .width(30.dp)
                        .clickable { navController.navigate(Screen.HomeScreen.route) }
                )
                Text(
                    text = "Adicionar Tarefa",
                    modifier = Modifier
                        .padding(20.dp),
                    color = primaryColor,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }
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
    /* var name by rememberSaveable { mutableStateOf("") }
    var description by rememberSaveable { mutableStateOf("") }
    var priority by rememberSaveable { mutableStateOf("") }
    var date by rememberSaveable { mutableStateOf("") }
    var time by rememberSaveable { mutableStateOf("") } */

    var dialogShowing = remember {
        mutableStateOf(false)
    }

    Column(modifier = Modifier.fillMaxWidth()) {
        Spacer(modifier = modifier.height(30.dp))
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(0.9f),
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
            }
        )

        // InputText(text = name, label = "Nome", onTextChange = { onEvent(RegisterEvent.EnteredName(it)) })

        Spacer(modifier = modifier.height(10.dp))

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .height(150.dp),
            value = description,
            onValueChange = { onEvent(RegisterEvent.EnteredDescription(it)) },
            label = { Text("Descrição") },
            leadingIcon = {
                Icon(
                    modifier = Modifier.padding(end = 10.dp),
                    imageVector = Icons.Filled.Call,
                    tint = primaryColor,
                    contentDescription = "Imagem"
                )
            }
        )
        // InputText(text = description, label = "Descrição", onTextChange = { onEvent(RegisterEvent.EnteredDescription(it)) })

        Spacer(modifier = modifier.height(10.dp))

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(0.9f),
            value = priority,
            onValueChange = { onEvent(RegisterEvent.EnteredPriority(it)) },
            label = { Text("Prioridade") },
            leadingIcon = {
                Icon(
                    modifier = Modifier.padding(end = 10.dp),
                    imageVector = Icons.Filled.Check,
                    tint = primaryColor,
                    contentDescription = "Imagem"
                )
            }
        )
        Spacer(modifier = modifier.height(10.dp))
        // InputText(text = priority, label = "Prioridade", onTextChange = { onEvent(RegisterEvent.EnteredPriority(it)) })

        Row {
            val focusManager = LocalFocusManager.current
            var showDatePickerDialog by remember {
                mutableStateOf(false)
            }
            val datePickerState = rememberDatePickerState()
            var selectedDate by remember {
                mutableStateOf("")
            }
            if (showDatePickerDialog) {
                DatePickerDialog(
                    onDismissRequest = { showDatePickerDialog = false },
                    confirmButton = {
                        Button(
                            onClick = {
                                datePickerState
                                    .selectedDateMillis?.let { millis ->
                                        selectedDate = millis.toBrazilianDateFormat()
                                    }
                                showDatePickerDialog = false
                            }) {
                            Text(text = "Escolher data")
                        }
                    }
                ) {
                    DatePicker(state = datePickerState)
                }
            }

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth(0.4f)
                    .onFocusEvent {
                        if (it.isFocused) {
                            showDatePickerDialog = true
                            focusManager.clearFocus(force = true)
                        }
                    },
                value = date,
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
            
            val clockState = rememberSheetState()
            ClockDialog(
                state = clockState,
                selection = ClockSelection.HoursMinutes { hours, minutes ->
                    Log.d("Selecinar Data", "$hours:$minutes")
                }
            )

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .onFocusEvent {
                        clockState.show()
                    },
                value = time,
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
        // InputText(text = date, label = "Data", onTextChange = { onEvent(RegisterEvent.EnteredDate(it)) })

        Spacer(modifier = modifier.height(10.dp))
        // InputText(text = time, label = "Horário", onTextChange = { onEvent(RegisterEvent.EnteredTime(it) )})

        // InputText(text = time, label = "Pomodoro", onTextChange = { onEvent(RegisterEvent.EnteredTime(it) )})
        
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
            shape = RoundedCornerShape(10.dp)
        ) {
            Text(text = "Pomodoro")
        }

        Spacer(modifier = modifier.height(40.dp))
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
            .height(50.dp),
        onClick = { onInsertTask() },
        shape = RoundedCornerShape(10.dp)
    ) {
        Text(
            text = "Confirmar",
            fontSize = 15.sp
        )
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