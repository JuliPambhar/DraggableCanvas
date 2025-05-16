package com.example.draggablecanvas.data

import com.example.draggablecanvas.data.models.Overlays
import retrofit2.http.GET

interface IOverlayService {
    @GET("scrl/test/overlays")
    suspend fun getOverlays(): List<Overlays>
}