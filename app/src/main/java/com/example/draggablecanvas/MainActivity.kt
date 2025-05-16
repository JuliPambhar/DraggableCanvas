package com.example.draggablecanvas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.example.draggablecanvas.ui.screen.OverlayCanvasApp
import com.example.draggablecanvas.ui.screen.OverlayViewModel
import com.example.draggablecanvas.ui.theme.DraggableCanvasTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DraggableCanvasTheme {
                val viewModel: OverlayViewModel by viewModels<OverlayViewModel> { OverlayViewModel.Factory }
                OverlayCanvasApp(viewModel)
            }
        }
    }
}
