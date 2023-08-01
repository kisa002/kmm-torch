package com.haeyum.kmm_torch.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.haeyum.kmm_torch.presentation.ui.MainUI
import com.haeyum.kmm_torch.utils.torch.TorchController

class MainActivity : ComponentActivity() {
    private val torchController by lazy {
        TorchController(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MyApplicationTheme {
                MainUI(torchController = torchController)
            }
        }
    }
}