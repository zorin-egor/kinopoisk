package com.sample.kinopoisk.core.ui.component

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

fun getLazyListHeight(
    size: Int,
    columns: Int,
    itemHeight: Dp,
    paddingCells: Dp,
    paddingContent: Dp
): Dp {
    val half = size / columns + size % columns
    return paddingCells * (half - 1) + itemHeight * half + paddingContent * 2
}

@SuppressLint("DesignSystem")
@Composable
fun <Item> ListContentWidget(
    items: List<Item>,
    onKey: (Item) -> String,
    modifier: Modifier = Modifier,
    onBottomEvent: (() -> Unit)? = null,
    isBottomProgress: Boolean = false,
    prefetch: Int = 3,
    contentType: (item: Item) -> Any? = { null },
    content: @Composable (Item) -> Unit
) {
    val listState = rememberLazyListState()

    listState.setEdgeEvents(
        debounce = 1000,
        prefetch = prefetch,
        onTopList = { index ->
            println("ItemsOrganizationsContent() - onTopList: $index")
        },
        onBottomList = { index ->
            println("ItemsOrganizationsContent() - onBottomList: $index")
            onBottomEvent?.invoke()
        }
    )

    Column(modifier = modifier.fillMaxWidth().fillMaxHeight()) {
        LazyColumn(
            state = listState,
            modifier = Modifier
                .fillMaxSize()
                .weight(1.0f),
            contentPadding = PaddingValues(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(
                items = items,
                key = onKey,
                contentType = contentType
            ) {
                content(it)
            }
        }

        if (isBottomProgress) {
            LinearProgressIndicator(modifier = Modifier
                .fillMaxWidth()
                .height(4.dp))
        }
    }
}

@Composable
fun LazyListState.setEdgeEvents(prefetch: Int = 3, onTopList: ((Int) -> Unit)? = null, onBottomList: (Int) -> Unit): Boolean {
    return remember(this) {
        println("LazyListState.setEdgeEvents() - remember")
        derivedStateOf {
            println("LazyListState.setEdgeEvents() - derivedStateOf")
            val totalItems = layoutInfo.totalItemsCount
            val lastItem = layoutInfo.visibleItemsInfo.lastOrNull()
            val firstItem = layoutInfo.visibleItemsInfo.firstOrNull()
            val lastIndex = lastItem?.index ?: 0
            val firstIndex = firstItem?.index ?: 0

            when {
                layoutInfo.totalItemsCount == 0 -> false
                lastIndex + prefetch > totalItems -> {
                    println("LazyListState.setEdgeEvents($totalItems, $prefetch, $lastIndex) - onBottomList")
                    onBottomList(lastIndex)
                    true
                }
                firstIndex - prefetch < 0 -> {
                    println("LazyListState.setEdgeEvents($totalItems, $prefetch, $firstIndex) - onTopList")
                    onTopList?.run {
                        invoke(firstIndex)
                        true
                    } ?: false
                }
                else -> false
            }
        }
    }.value
}

@SuppressLint("ComposableNaming")
@Composable
fun LazyListState.setEdgeEvents(
    debounce: Long = 500L,
    prefetch: Int = 3,
    onTopList: ((Int) -> Unit)? = null,
    onBottomList: (Int) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    var eventTopJob: Job? = remember { null }
    var eventBottomJob: Job? = remember { null }

    setEdgeEvents(
        prefetch = prefetch,
        onTopList = onTopList@ { index ->
            if (eventTopJob?.isActive == true) return@onTopList
            eventTopJob = coroutineScope.launch {
                onTopList?.invoke(index)
                delay(debounce)
            }
        },
        onBottomList = onBottomList@ { index ->
            if (eventBottomJob?.isActive == true) return@onBottomList
            eventBottomJob = coroutineScope.launch {
                onBottomList(index)
                delay(debounce)
            }
        }
    )
}