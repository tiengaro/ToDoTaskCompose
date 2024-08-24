package com.example.to_docompose.ui.screens.list

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.example.to_docompose.data.models.Priority
import com.example.to_docompose.data.models.ToDoTask
import com.example.to_docompose.ui.theme.LARGE_PADDING
import com.example.to_docompose.ui.theme.MEDIUM_PADDING
import com.example.to_docompose.ui.theme.PRIORITY_INDICATOR_SIZE
import com.example.to_docompose.ui.theme.TASK_ITEM_ELEVATION
import com.example.to_docompose.ui.theme.taskItemBackgroundColor
import com.example.to_docompose.ui.theme.taskItemTextColor

@Composable
fun ListContent(
    toDoTasks: List<ToDoTask>,
    navigationToTaskScreen: (taskId: Int) -> Unit
) {
    if (toDoTasks.isEmpty()) {
        EmptyContent()
    } else {
        DisplayTasks(toDoTasks, navigationToTaskScreen)
    }
}

@Composable
private fun DisplayTasks(toDoTasks: List<ToDoTask>, navigationToTaskScreen: (taskId: Int) -> Unit) {
    LazyColumn {
        items(
            items = toDoTasks,
            key = { it.id },
            itemContent = { task ->
                TaskItem(
                    toDoTask = task,
                    navigationToTaskScreen = navigationToTaskScreen
                )
            }
        )
    }
}

@Composable
fun TaskItem(
    toDoTask: ToDoTask,
    navigationToTaskScreen: (taskId: Int) -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = MaterialTheme.colorScheme.taskItemBackgroundColor,
        shape = RectangleShape,
        shadowElevation = TASK_ITEM_ELEVATION,
        onClick = {
            navigationToTaskScreen(toDoTask.id)
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(LARGE_PADDING)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = MEDIUM_PADDING),
                    text = toDoTask.title,
                    color = MaterialTheme.colorScheme.taskItemTextColor,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                )
                Box {
                    Canvas(modifier = Modifier.size(PRIORITY_INDICATOR_SIZE)) {
                        drawCircle(color = toDoTask.priority.color)
                    }
                }
            }
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = toDoTask.description,
                color = MaterialTheme.colorScheme.taskItemTextColor,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Preview
@Composable
private fun TaskItemPreview() {
    TaskItem(
        toDoTask = ToDoTask(
            id = 0,
            title = "Title",
            description = "Description",
            priority = Priority.HIGH
        ),
        navigationToTaskScreen = {}
    )
}