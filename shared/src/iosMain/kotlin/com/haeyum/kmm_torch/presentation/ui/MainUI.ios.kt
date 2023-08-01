package com.haeyum.kmm_torch.presentation.ui

import androidx.compose.ui.window.ComposeUIViewController
import com.haeyum.kmm_torch.utils.torch.TorchController

fun MainUIViewController() = ComposeUIViewController {
    val torchController = TorchController()

    MainScreen(torchController = torchController)
}