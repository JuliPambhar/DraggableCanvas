package com.example.draggablecanvas.ui.screen

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.center
import androidx.compose.ui.unit.dp
import com.example.draggablecanvas.ui.utils.getTouchingEdgeLine
import kotlin.math.abs

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OverlayCanvasApp(viewModel: OverlayViewModel) {
    val showSheet = remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { showSheet.value = true }) {
                Text("Add")
            }
        }) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
                .padding(paddingValues)
        ) {
            var parentSizePx by remember { mutableStateOf(IntSize(0, 0)) }
            val touchLines = remember { mutableStateListOf<Pair<Offset, Offset>>() }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .background(Color.White)
                    .align(Alignment.Center)
                    .onSizeChanged { parentSizePx = it }
            ) {
                viewModel.bitmaps.forEachIndexed { index, item ->
                    DraggableOverlay(
                        item = item,
                        parentSizePx = parentSizePx,
                        initialOffset = Offset(20f * index, 20f * index),
                        onDrag = { rect ->
                            // Update this bitmap's state
                            viewModel.updateOverlay(index, rect)

                            // Clear touching lines
                            touchLines.clear()

                            // Overlay is vertically center.
                            val thresholdPixels = 4f
                            if (abs(rect.center.x - parentSizePx.center.x) < thresholdPixels) {
                                val verticalLine =
                                    Offset(parentSizePx.width.toFloat() / 2, 0f) to Offset(
                                        parentSizePx.width.toFloat() / 2,
                                        parentSizePx.height.toFloat()
                                    )
                                touchLines.add(verticalLine)
                            }

                            // Overlay is horizontally center.
                            if (abs(rect.center.y - parentSizePx.center.y) < thresholdPixels) {
                                val horizontal =
                                    Offset(0f, parentSizePx.height.toFloat() / 2) to Offset(
                                        parentSizePx.width.toFloat(),
                                        parentSizePx.height.toFloat() / 2
                                    )
                                touchLines.add(horizontal)
                            }

                            //Detect Overlay touching lines
                            viewModel.bitmaps.filter { it.overlay.id != item.overlay.id }
                                .forEach { other ->
                                    val edgeLine =
                                        getTouchingEdgeLine(
                                            parentSizePx,
                                            other.rect,
                                            rect,
                                            thresholdPixels
                                        )
                                    edgeLine?.let { touchLines.add(it) }
                                }
                        },
                    )
                }

                // Draw the yellow grid and touching lines
                Canvas(modifier = Modifier.fillMaxSize()) {
                    touchLines.forEach { (start, end) ->
                        drawLine(
                            color = Color.Yellow,
                            start = start,
                            end = end,
                            strokeWidth = 2f
                        )
                    }
                }
            }

            if (showSheet.value) {
                ModalBottomSheet(
                    onDismissRequest = { showSheet.value = false }, sheetState = sheetState
                ) {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(4), modifier = Modifier.fillMaxSize()
                    ) {
                        items(viewModel.availableOverlays) { overlay ->
                            OverlayGridItem(
                                overlay = overlay, onClick = {
                                    if (viewModel.bitmaps.size < 2) {
                                        viewModel.addOverlay(overlay)
                                        showSheet.value = false
                                    }
                                })
                        }
                    }
                }
            }
        }
    }
}