package com.example.to_docompose.ui.screens.list

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.to_docompose.R
import com.example.to_docompose.ui.theme.fabBackgroundColor
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow

data class Test(
    var id: MutableState<Int>,
    val test: String
)

data class ComplexModel(
    val id: Int,
    val name: MutableState<String>,
    val description: MutableState<String>,
    var items: PersistentList<String>
)

@Composable
fun ComplexModelExample() {
    val testArray = listOf(
        Test(1, "1"),
        Test(1, "2"),
        Test(1, "3"),
        Test(1, "4"),
    )
    val test = MutableSharedFlow<List<Test>>(replay = 1)
    val state by test.collectAsState(arrayListOf())

    LaunchedEffect(key1 = "a") {
        test.emit(testArray)
    }

    val model = remember {
        ComplexModel(
            id = 1,
            name = mutableStateOf("Item 1"),
            description = mutableStateOf("Description 1"),
            items = persistentListOf("Subitem 1", "Subitem 2", "Subitem 3")
        )
    }

    // Derived state to combine name and description
    val combinedText by remember {
        derivedStateOf { "${model.name.value}: ${model.description.value}" }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        TextField(
            value = model.name.value,
            onValueChange = { model.name.value = it },
            label = { Text("Name") }
        )

        TextField(
            value = model.description.value,
            onValueChange = { model.description.value = it },
            label = { Text("Description") }
        )

        Text(text = combinedText)

        LazyColumn(modifier = Modifier.weight(1f)) {
            items(model.items, key = { it }) { item ->
                Log.d("aaaaaaaaa", item)
                BasicText(
                    text = item,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                )
            }
        }

        Button(onClick = {
            // Update items in SnapshotStateList
            model.items = model.items.add("Subitem ${model.items.size + 1}")
        }) {
            Text("Add Item")
        }
    }
}

@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalCoroutinesApi::class)
@Composable
fun ListScreen(navigateToTaskScreen: (taskId: Int) -> Unit) {


    val onSearchClicked = {
        testArray[0].id.value++
        val a = test.tryEmit(testArray)
        val b = Log.d("aaaaaaaaa", a.toString())
    }

    Scaffold(
        topBar = {
            ListAppBar(
                onSearchClicked = onSearchClicked
            )
        },
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .background(color = MaterialTheme.colorScheme.background),
                contentAlignment = Alignment.Center
            ) {
                ComplexModelExample()
            }
        },
        floatingActionButton = { ListFab(navigateToTaskScreen) }
    )
}

@Composable
fun ListFab(onFabClicked: (taskId: Int) -> Unit) {
    FloatingActionButton(
        onClick = { onFabClicked(-1) },
        containerColor = MaterialTheme.colorScheme.fabBackgroundColor

    ) {
        Icon(
            imageVector = Icons.Filled.Add,
            contentDescription = stringResource(id = R.string.add_button),
            tint = Color.White
        )
    }
}

@Composable
@Preview(name = "List Screen")
fun ListScreenPreview() {
    ListScreen(navigateToTaskScreen = {})
}