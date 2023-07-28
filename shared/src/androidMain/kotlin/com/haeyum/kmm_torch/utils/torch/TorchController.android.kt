package com.haeyum.kmm_torch.utils.torch

import android.content.Context
import android.hardware.camera2.CameraCharacteristics
import android.hardware.camera2.CameraManager
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow

actual class TorchController(context: Context) {
    private val cameraManager: CameraManager =
        context.getSystemService(Context.CAMERA_SERVICE) as CameraManager

    private val cameraId: String? = cameraManager.cameraIdList.find { cameraId ->
        cameraManager.getCameraCharacteristics(cameraId)
            .get(CameraCharacteristics.FLASH_INFO_AVAILABLE) == true
    }

    @Throws(Exception::class)
    actual fun turnOn() {
        cameraId ?: throw Exception("No device found")
        cameraManager.setTorchMode(cameraId, true)
    }

    @Throws(Exception::class)
    actual fun turnOff() {
        cameraId ?: throw Exception("No device found")
        cameraManager.setTorchMode(cameraId, false)
    }

    actual val isAvailable: Boolean
        get() = cameraId?.let {
            cameraManager.getCameraCharacteristics(it)
                .get(CameraCharacteristics.FLASH_INFO_AVAILABLE)
        } ?: false

    actual val isEnabledFlow = callbackFlow {
        val callback = object : CameraManager.TorchCallback() {
            override fun onTorchModeChanged(cameraId: String, enabled: Boolean) {
                trySend(enabled)
            }
        }

        cameraManager.registerTorchCallback(callback, null)

        awaitClose {
            cameraManager.unregisterTorchCallback(callback)
        }
    }
}