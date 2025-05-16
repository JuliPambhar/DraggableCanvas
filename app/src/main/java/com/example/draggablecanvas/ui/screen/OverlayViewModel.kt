package com.example.draggablecanvas.ui.screen

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.geometry.Rect
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.draggablecanvas.BitmapItem
import com.example.draggablecanvas.data.IOverlayService
import com.example.draggablecanvas.data.RetrofitProvider
import com.example.draggablecanvas.data.models.Overlay
import kotlinx.coroutines.launch

class OverlayViewModel(private val service: IOverlayService) : ViewModel() {
    private val _bitmaps = mutableStateListOf<BitmapItem>()
    val bitmaps: SnapshotStateList<BitmapItem> = _bitmaps

    var availableOverlays: List<Overlay> = listOf()

    init {
        fetchOverlays()
    }

    fun fetchOverlays() {
        viewModelScope.launch {
            availableOverlays = service.getOverlays().first().items
        }
    }

    fun addOverlay(overlay: Overlay) {
        _bitmaps.add(BitmapItem(overlay))
    }

    fun updateOverlay(index: Int, rect: Rect) {
        _bitmaps[index].rect = rect
    }

    companion object {

        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {
                return OverlayViewModel(
                    RetrofitProvider.getOverlayService(),
                ) as T
            }
        }
    }
}