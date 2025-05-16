package com.example.draggablecanvas.data.models

data class Overlays(
    val title: String,
    val id: Int,
    val items: List<Overlay>,
    val thumbnail_url: String
)