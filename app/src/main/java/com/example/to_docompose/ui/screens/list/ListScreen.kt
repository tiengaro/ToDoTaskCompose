package com.example.to_docompose.ui.screens.list

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
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
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlin.random.Random

data class TestData(
    val id: Int,
    val test: String
)

fun getRandomColor() = Color(
    Random.nextInt(256),
    Random.nextInt(256),
    Random.nextInt(256),
    alpha = 255
)

@SuppressLint("UnrememberedMutableState")
@Composable
fun ComplexModelExample(test: SnapshotStateList<TestData>, onClick: (item: TestData) -> Unit) {
    Log.d("TAG", "ListScreen: update UI 2")
    Column(modifier = Modifier.fillMaxSize()) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(test, key = { it.hashCode() }) { item ->

                Log.d("TAG", "ListScreen: update UI 3")
                Text(
                    text = item.test,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .padding(16.dp)
                        .border(3.dp, getRandomColor())
                        .align(Alignment.CenterHorizontally)
                        .clickable {
                            onClick.invoke(item)
                        }
                    )
            }
        }


//        Button(onClick = onClick) {
//            Text("Add Item")
//        }
    }
}

@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalCoroutinesApi::class)
@Composable
fun ListScreen(navigateToTaskScreen: (taskId: Int) -> Unit) {
    val onSearchClicked = {

    }

    val testArray = arrayListOf<TestData>().apply {
        for (i in 0..15) {
            this.add(TestData(i, "$i"))
        }
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
                Log.d("TAG", "ListScreen: update UI 1")

                val test = mutableStateListOf<TestData>().apply {
                    addAll(testArray)
                }

                var selected = 0
                val onClick = remember {
                    {item: TestData ->
//            test[selected] = test[selected].copy(test = "${selected}")
                        val item = test[item.id].copy(test = "${item.id} selected")
                        test[item.id] = item

                        selected  = item.id
                    }
                }

                ComplexModelExample(test, onClick)
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