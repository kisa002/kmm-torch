package com.haeyum.kmm_torch.utils.torch

import kotlinx.coroutines.flow.Flow

expect class TorchController {
    fun turnOn()
    fun turnOff()
    val isAvailable: Boolean
    val isEnabledFlow: Flow<Boolean>
}