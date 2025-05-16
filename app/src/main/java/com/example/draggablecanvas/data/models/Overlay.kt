package com.example.draggablecanvas.data.models

data class Overlay(
    val id: Int,
    val overlay_name: String,
    val created_at: String,
    val category_id: Int,
    val source_url: String,
    val is_premium: Boolean,
    val includes_scrl_branding: Boolean,
    val premium_uses_last_48hrs: Int,
    val max_canvas_size: Int,
)