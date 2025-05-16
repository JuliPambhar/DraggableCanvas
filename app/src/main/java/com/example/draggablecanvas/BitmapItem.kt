package com.example.draggablecanvas

import androidx.compose.ui.geometry.Rect
import com.example.draggablecanvas.data.models.Overlay

data class BitmapItem(
    val overlay: Overlay,
    var rect: Rect = Rect(0f, 0f, 0f, 0f)
)