@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)

package com.pomolist.feature_task.presentation.home.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.PlayCircle
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pomolist.feature_task.domain.model.Task
import com.pomolist.feature_task.presentation.register_edit_task.RegisterScreen
import com.pomolist.ui.theme.PomoListTheme
import com.pomolist.ui.theme.primaryColor

@Composable
fun TaskItem(
    modifier: Modifier = Modifier,
    task: Task,
    onEditTask: () -> Unit,
    onDeleteTask: () -> Unit,
    onPomodoroTask: () -> Unit
) {
    var isExpanded by remember { mutableStateOf(false) }
    var gender by remember { mutableStateOf("") }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 14.dp, vertical = 12.dp)
            .combinedClickable(
                onClick = {},
                onLongClick = { onEditTask }
            ),
        shape = RoundedCornerShape(corner = CornerSize(16.dp)),
        onClick = onEditTask
    ) {
        Row(
            modifier = Modifier.padding(16.dp)
        ) {
            Column(verticalArrangement = Arrangement.Center) {
                Text(
                    text = "${task.name}",
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "${task.description}",
                    style = MaterialTheme.typography.bodyMedium
                )
                /* Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "${task.priority}",
                    style = MaterialTheme.typography.bodySmall
                ) */
                /*Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "${task.minutes}",
                    style = MaterialTheme.typography.bodySmall
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "${task.seconds}",
                    style = MaterialTheme.typography.bodySmall
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "${task.amount}",
                    style = MaterialTheme.typography.bodySmall
                ) */
            }
            Spacer(modifier = Modifier.width(80.dp))
            Column {
                Row {
                    Text(
                        text = "0",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "/",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "${task.amount}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "${task.minutes} minutos",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            Row {
                /*IconButton(onClick = onEditTask) {
                    Icon(
                        imageVector = Icons.Filled.Edit,
                        contentDescription = null,
                        tint = Color.Green
                    )
                }
                IconButton(onClick = onDeleteTask) {
                    Icon(
                        imageVector = Icons.Filled.Delete,
                        contentDescription = null,
                        tint = Color.Red
                    )
                }
                IconButton(onClick = onPomodoroTask) {
                    Icon(
                        imageVector = Icons.Filled.Settings,
                        contentDescription = null,
                        tint = Color.Blue
                    )
                }*/
                IconButton(onClick = onPomodoroTask) {
                    Icon(
                        modifier = Modifier.size(size = 40.dp),
                        imageVector = Icons.Outlined.PlayCircle,
                        contentDescription = null,
                        tint = primaryColor
                    )
                }

                IconButton(onClick = onDeleteTask) {
                    Icon(
                        modifier = Modifier.size(size = 30.dp),
                        imageVector = Icons.Filled.Delete,
                        contentDescription = null,
                        tint = primaryColor
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewTaskItem() {
    PomoListTheme {
        Surface {
            TaskItem(task = Task(
                name = "Isaac",
                description = "Aaaa",
                priority = "Aaaaa"
            ),
                onEditTask = {  }
            ,   onDeleteTask = {  }
            ,   onPomodoroTask = {  }
            )
        }
    }
}