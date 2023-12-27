@file:OptIn(ExperimentalMaterial3Api::class)

package com.pomolist.feature_task.presentation.home

import android.R
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.Timer
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.pomolist.core.navigation.Screen
import com.pomolist.feature_task.domain.model.Task
import com.pomolist.feature_task.presentation.home.components.DrawerItem
import com.pomolist.feature_task.presentation.home.components.ModalDrawer
import com.pomolist.feature_task.presentation.home.components.NavigationDrawerItems
import com.pomolist.feature_task.presentation.home.components.TaskItem
import com.pomolist.feature_task.presentation.timer.TimerScreen
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
    var selectedItemIndex by rememberSaveable {
        mutableStateOf(0)
    }

    val items = listOf(
        DrawerItem(
            title = "Home",
            selectedIcon = Icons.Filled.Home,
            unselectedIcon = Icons.Outlined.Home,
        ),
        DrawerItem(
            title = "Cronometro",
            selectedIcon = Icons.Filled.Timer,
            unselectedIcon = Icons.Outlined.Timer
        ),
        DrawerItem(
            title = "Pomodoro da Tarefa",
            selectedIcon = Icons.Filled.Favorite,
            unselectedIcon = Icons.Outlined.FavoriteBorder,
        ),
        DrawerItem(
            title = "Configurações",
            selectedIcon = Icons.Filled.Settings,
            unselectedIcon = Icons.Outlined.Settings,
        )
    )

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
                    /*items.forEachIndexed { index, drawerItem ->
                        NavigationDrawerItem(label = {
                            Text(text = drawerItem.title)
                        }, selected = index == selectedItemIndex, onClick = {
                            selectedItemIndex = index
                            scope.launch {
                                navigationState.close()
                            }
                        }, icon = {
                            Icon(
                                imageVector = if (index == selectedItemIndex) {
                                    drawerItem.selectedIcon
                                } else drawerItem.unselectedIcon,
                                contentDescription = drawerItem.title,
                                tint = primaryColor
                            )
                        }, badge = {
                            drawerItem.badgeCount?.let {
                                Text(text = drawerItem.badgeCount.toString(), color = primaryColor)
                            }
                        }, modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                        )
                    } */
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
                        },
                        actions = {
                            IconButton(onClick = { /*TODO*/ }) {
                                Icon(
                                    modifier = Modifier
                                        .size(60.dp)
                                        .padding(end = 10.dp),
                                    imageVector = Icons.Filled.Search,
                                    contentDescription = "Pesquisar",
                                    tint = primaryColor
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
fun SearchAction() {
    IconButton(onClick = { /*TODO*/ }) {
        Icon(
            imageVector = Icons.Filled.Search,
            contentDescription = "Pesquisar",
            tint = primaryColor
        )
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
    var selected by remember { mutableStateOf(false) }
    Column(
        modifier = modifier
    ) {
        Row(modifier = Modifier.padding(15.dp, 0.dp)) {
            FilterChip(
                modifier = Modifier.padding(5.dp, 0.dp),
                selected = selected,
                onClick = { selected = !selected },
                label = { Text(text = "Iniciados") }
            )
            FilterChip(
                modifier = Modifier.padding(5.dp, 0.dp),
                selected = selected,
                onClick = { selected = !selected },
                label = { Text(text = "Terminados") }
            )
            FilterChip(
                modifier = Modifier.padding(5.dp, 0.dp),
                selected = selected,
                onClick = { selected = !selected },
                label = { Text(text = "Atrasados") }
            )
        }
        Row(modifier = Modifier.padding(15.dp, 0.dp)) {
            FilterChip(
                modifier = Modifier.padding(5.dp, 0.dp),
                selected = selected,
                onClick = { selected = !selected },
                label = { Text(text = "Em Andamento") }
            )
        }

        LazyColumn {
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
