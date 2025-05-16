package com.example.draggablecanvas.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.draggablecanvas.BitmapItem

@Composable
fun DraggableOverlay(
    item: BitmapItem,
    parentSizePx: IntSize,
    initialOffset: Offset = Offset.Zero,
    onDrag: (Rect) -> Unit
) {
    var isSelected by remember { mutableStateOf(false) }
    val density = LocalDensity.current
    val context = LocalContext.current

    var imageSizePx by remember { mutableStateOf(IntSize.Zero) }
    var scaledSizeDp by remember { mutableStateOf(DpSize(0.dp, 0.dp)) }

    var offset by remember { mutableStateOf(initialOffset) }

    Box(
        modifier = Modifier
            .then(
                if (scaledSizeDp.width > 0.dp)
                    Modifier.size(scaledSizeDp)
                else Modifier
            )
            .offset { IntOffset(offset.x.toInt(), offset.y.toInt()) }
            .pointerInput(imageSizePx, parentSizePx) {
                detectDragGestures(
                    onDragStart = {
                        isSelected = true
                    },
                    onDrag = { change, dragAmount ->

                        offset = Offset(
                            (offset.x + dragAmount.x)
                                .coerceIn(0f, parentSizePx.width.toFloat() - imageSizePx.width),
                            (offset.y + dragAmount.y)
                                .coerceIn(0f, parentSizePx.height.toFloat() - imageSizePx.height)
                        )

                        onDrag.invoke(
                            Rect(
                                offset,
                                Size(imageSizePx.width.toFloat(), imageSizePx.height.toFloat())
                            )
                        )
                    },
                    onDragEnd = { isSelected = false }
                )
            }
    ) {
        AsyncImage(
            model = ImageRequest.Builder(context)
                .data(item.overlay.source_url)
                .listener(
                    onSuccess = { _, result ->
                        val width = result.drawable.intrinsicWidth
                        val height = result.drawable.intrinsicHeight
                        val scaleFactor = 0.3f
                        imageSizePx =
                            IntSize((width * scaleFactor).toInt(), (height * scaleFactor).toInt())

                        with(density) {
                            scaledSizeDp =
                                DpSize((width * scaleFactor).toDp(), (height * scaleFactor).toDp())
                        }
                    }
                )
                .build(),
            contentDescription = null,
            contentScale = ContentScale.Fit,
        )

        if (isSelected) {
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .background(Color.Blue.copy(alpha = 0.2f))
            )
        }
    }
}
