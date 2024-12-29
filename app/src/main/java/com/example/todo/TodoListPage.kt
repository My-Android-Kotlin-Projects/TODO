package com.example.todo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun TodoListPage(viewModel: TodoViewModel) {
    val todos by viewModel.todoList.observeAsState()
    var inputText by remember {
        mutableStateOf("")
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            OutlinedTextField(value = inputText, onValueChange = { text ->
                inputText = text
            }, label = { Text(text = "Enter Todo") }, modifier = Modifier
                .padding(8.dp)
                .weight(1f),
                maxLines = 1
            )
            Button(onClick = {
                viewModel.addTodo(inputText)
                inputText = ""
            }, modifier = Modifier.padding(8.dp)) {
                Text(text = "Add")
            }
        }
        todos?.let {
            LazyColumn(
                content = {
                    itemsIndexed(it) { index, todo ->
                        TodoItem(item = todo, onDelete = {
                            viewModel.deleteTodo(todo.id)

                        }, onCheckedChange = {
                            isChecked -> viewModel.updateTodoCheckedState(todo.id, isChecked)
                        })
                    }
                }
            )
        } ?: Text(
            text = "No Todos",
            fontSize = 20.sp,
            color = Color.White,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

    }
}

@Composable
fun TodoItem(item: Todo, modifier: Modifier = Modifier, onDelete: () -> Unit,onCheckedChange: (Boolean) -> Unit) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Color.DarkGray)
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically

    ) {

        Checkbox(checked = item.isDone,
            onCheckedChange = {
                onCheckedChange(it)
            })
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = SimpleDateFormat("HH:mm:ss dd/mm", Locale.ENGLISH).format(item.createdAt),
                fontSize = 10.sp,
                color = Color.LightGray
            )
            Text(text = item.title, fontSize = 20.sp, color = Color.White)
        }
        if (item.isDone) {
            IconButton(onClick = onDelete) {
                Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete Item")
            }
        }
    }

}