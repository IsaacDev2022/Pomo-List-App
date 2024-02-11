@file:OptIn(ExperimentalMaterial3Api::class)

package com.pomolist.feature_task.presentation.home.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.pomolist.core.navigation.Screen
import com.pomolist.feature_task.domain.model.Task
import com.pomolist.feature_task.presentation.home.HomeEvent
import com.pomolist.feature_task.presentation.home.components.NavigationDrawerItems
import com.pomolist.feature_task.presentation.home.components.TaskItem
import com.pomolist.ui.theme.primaryColor
import com.pomolist.ui.theme.secondaryColor
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    navController: NavController,
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    val state = homeViewModel.state.value

    val navigationState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    Surface {
        androidx.compose.material3.ModalNavigationDrawer(
            drawerContent = {
                ModalDrawerSheet {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(240.dp)
                            .background(
                                secondaryColor,
                                shape = RoundedCornerShape(bottomEnd = 30.dp, bottomStart = 30.dp)
                            ),
                        contentAlignment = Alignment.Center,
                    ) {
                        Column(
                            Modifier.wrapContentSize(),
                            verticalArrangement = Arrangement.SpaceAround,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                modifier = Modifier
                                    .size(100.dp),
                                tint = Color.White,
                                imageVector = Icons.Filled.Timer,
                                contentDescription = "Open Menu"
                            )
                            Spacer(modifier = Modifier.height(20.dp))
                            Text(
                                modifier = Modifier
                                    .width(180.dp),
                                text = "PomodoroList",
                                fontSize = 25.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(20.dp))

                    NavigationDrawerItems(navController, navigationState)
                }
            },
            drawerState = navigationState,
        ) {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = {
                            Text(
                                modifier = Modifier
                                    .padding(start = 10.dp),
                                text = "Home",
                                color = primaryColor,
                                textAlign = TextAlign.Center
                            )
                        },
                        navigationIcon = {
                            IconButton(onClick = {
                                scope.launch {
                                    navigationState.open()
                                }
                            }) {
                                Icon(
                                    modifier = Modifier
                                        .size(60.dp)
                                        .padding(start = 10.dp),
                                    tint = primaryColor,
                                    imageVector = Icons.Filled.Menu,
                                    contentDescription = "Open Menu"
                                )
                            }
                        }
                    )
                },
                content = { padding ->
                    HomeContent(
                        modifier = Modifier.padding(padding),
                        onDeleteTask = { homeViewModel.onEvent(HomeEvent.DeleteTask(it)) },
                        onEditTask = {
                            navController.navigate(
                                route = Screen.RegisterScreen.passId(it)
                            )
                        },
                        onPomodoroTask = {
                            navController.navigate(
                                route = Screen.TimerTaskScreen.passIdTimer(it)
                            )
                        },
                        tasks = state.tasks
                    )
                },
                floatingActionButton = {
                    HomeFab(
                        onFabClicked = {
                            navController.navigate(route = Screen.RegisterScreen.route)
                        }
                    )
                }
            )
        }
    }
}

@Composable
fun HomeContent(
    modifier: Modifier = Modifier,
    onDeleteTask: (task: Task) -> Unit,
    onEditTask: (id: Int?) -> Unit,
    onPomodoroTask: (id: Int?) -> Unit,
    tasks: List<Task> = emptyList()
) {
    var selectedIniciados by remember { mutableStateOf(false) }
    var selectedTerminados by remember { mutableStateOf(false) }
    var selectedAtrasados by remember { mutableStateOf(false) }
    var selectedEmAndamento by remember { mutableStateOf(false) }
    var selectedPrioridade by remember { mutableStateOf(false) }
    var taskListFilted = tasks
    Column(
        modifier = modifier
    ) {
        Row(modifier = Modifier.padding(15.dp, 0.dp)) {
            FilterChip(
                modifier = Modifier.padding(5.dp, 0.dp),
                selected = selectedIniciados,
                onClick = { selectedIniciados = !selectedIniciados },
                label = { Text(text = "Iniciados") }
            )
            FilterChip(
                modifier = Modifier.padding(5.dp, 0.dp),
                selected = selectedTerminados,
                onClick = { selectedTerminados = !selectedTerminados },
                label = { Text(text = "Terminados") }
            )
            FilterChip(
                modifier = Modifier.padding(5.dp, 0.dp),
                selected = selectedAtrasados,
                onClick = { selectedAtrasados = !selectedAtrasados },
                label = { Text(text = "Atrasados") }
            )
        }
        Row(modifier = Modifier.padding(15.dp, 0.dp)) {
            FilterChip(
                modifier = Modifier.padding(5.dp, 0.dp),
                selected = selectedEmAndamento,
                onClick = { selectedEmAndamento = !selectedEmAndamento },
                label = { Text(text = "Em Andamento") }
            )
            FilterChip(
                modifier = Modifier.padding(5.dp, 0.dp),
                selected = selectedPrioridade,
                onClick = { selectedPrioridade = !selectedPrioridade },
                label = { Text(text = "Por Prioridade") }
            )
        }

        LazyColumn {
            if (selectedPrioridade) {
                items(taskListFilted.sortedBy {
                    it.priority
                }) { task ->
                    TaskItem(
                        task = task,
                        onEditTask = { onEditTask(task.id) },
                        onDeleteTask = { onDeleteTask(task) },
                        onPomodoroTask = { onPomodoroTask(task.id) }
                    )
                }
            } else {
                items(tasks) { task ->
                    TaskItem(
                        task = task,
                        onEditTask = { onEditTask(task.id) },
                        onDeleteTask = { onDeleteTask(task) },
                        onPomodoroTask = { onPomodoroTask(task.id) }
                    )
                }
            }
        }
    }
}

@Composable
fun HomeFab(onFabClicked: () -> Unit) {
    FloatingActionButton(
        onClick = onFabClicked,
        shape = CircleShape,
        containerColor = secondaryColor
    ) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = "Adicionar",
            tint = Color.White
        )
    }
}
