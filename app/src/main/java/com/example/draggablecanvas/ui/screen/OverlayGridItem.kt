package com.example.draggablecanvas.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.draggablecanvas.data.models.Overlay

@Composable
fun OverlayGridItem(overlay: Overlay, onClick: () -> Unit) {
    val painter = rememberAsyncImagePainter(overlay.source_url)

    Column(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable {
                onClick()
            },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painter,
            contentDescription = overlay.overlay_name,
            modifier = Modifier
                .height(40.dp)
                .fillMaxWidth()
        )
    }
}