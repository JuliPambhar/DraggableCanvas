package com.example.draggablecanvas.ui.utils

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.unit.IntSize
import kotlin.math.abs

fun getTouchingEdgeLine(
    parentSize: IntSize,
    r1: Rect,
    r2: Rect,
    threshold: Float
): Pair<Offset, Offset>? {
    // Right edge of r1 near Left edge of r2
    if (abs(r1.right - r2.left) <= threshold) {
        return Offset(r1.right, 0f) to Offset(r1.right, parentSize.height.toFloat())
    }

    // Left edge of r1 near Right edge of r2
    if (abs(r1.left - r2.right) <= threshold) {
        return Offset(r1.left, 0f) to Offset(r1.left, parentSize.height.toFloat())
    }

    // Bottom edge of r1 near Top edge of r2
    if (abs(r1.bottom - r2.top) <= threshold) {
        return Offset(0f, r1.bottom) to Offset(parentSize.width.toFloat(), r1.bottom)
    }

    // Top edge of r1 near Bottom edge of r2
    if (abs(r1.top - r2.bottom) <= threshold) {
        return Offset(0f, r1.top) to Offset(parentSize.width.toFloat(), r1.top)
    }

    return null
}
