@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)

package com.pomolist.feature_task.presentation.home.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.outlined.PlayCircle
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pomolist.feature_task.domain.model.Task
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
    var colorPriority = Color(android.graphics.Color.parseColor("#8DF181"))

    if (task.priority.equals("1 - Baixo")) {
        colorPriority = Color(android.graphics.Color.parseColor("#8DF181"))
    }
    if (task.priority.equals("2 - Media")) {
        colorPriority = Color(android.graphics.Color.parseColor("#ECE48B"))
    }
    if (task.priority.equals("3 - Alta")) {
        colorPriority = Color(android.graphics.Color.parseColor("#f5ad89"))
    }
    if (task.priority.equals("4 - Urgente")) {
        colorPriority = Color(android.graphics.Color.parseColor("#f07171"))
    }

    val checkedState = rememberSaveable { mutableStateOf(false) }

    if (checkedState.value) {
        colorPriority = Color(android.graphics.Color.parseColor("#a6aba6"))
        task.completed = true
    } else {
        task.completed = false
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 14.dp, vertical = 12.dp)
            .combinedClickable(
                onClick = {},
                onLongClick = { onEditTask }
            ),
        shape = RoundedCornerShape(corner = CornerSize(16.dp)),
        onClick = onEditTask,
        colors = CardDefaults.cardColors(
            containerColor = colorPriority
        )
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(verticalArrangement = Arrangement.Center) {
                Text(
                    text = "${task.name}",
                    style = if (checkedState.value) MaterialTheme.typography.titleMedium.copy(textDecoration = TextDecoration.LineThrough)
                    else MaterialTheme.typography.titleMedium,
                    modifier = Modifier.testTag("txtName")
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "${task.completed}",
                    style = if (checkedState.value) MaterialTheme.typography.bodyMedium.copy(textDecoration = TextDecoration.LineThrough)
                    else MaterialTheme.typography.bodyMedium,
                    modifier = Modifier
                        .widthIn(0.dp, 150.dp)
                        .testTag("txtDescription")
                )
            }
            Row {
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
                        text = "${task.minutes} min",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                Column(verticalArrangement = Arrangement.SpaceBetween) {
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
                Checkbox(
                    checked = checkedState.value,
                    onCheckedChange = { checkedState.value = it }
                )
            }
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun PreviewTaskItem() {
//    PomoListTheme {
//        Surface {
//            TaskItem(task = Task(
//                name = "Isaac",
//                description = "Aaaa",
//                priority = "Aaaaa"
//            ),
//                onEditTask = {  },
//                onDeleteTask = {  },
//                onPomodoroTask = {  }
//            )
//        }
//    }
//}