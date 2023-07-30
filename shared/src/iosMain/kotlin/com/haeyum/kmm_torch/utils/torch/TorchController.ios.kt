package com.haeyum.kmm_torch.utils.torch

import Observer.ObserverProtocol
import kotlinx.cinterop.COpaquePointer
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import platform.AVFoundation.AVCaptureDevice
import platform.AVFoundation.AVCaptureTorchModeOff
import platform.AVFoundation.AVCaptureTorchModeOn
import platform.AVFoundation.AVMediaTypeVideo
import platform.AVFoundation.isTorchAvailable
import platform.AVFoundation.setTorchMode
import platform.Foundation.NSKeyValueObservingOptionInitial
import platform.Foundation.NSKeyValueObservingOptionNew
import platform.Foundation.addObserver
import platform.Foundation.removeObserver
import platform.darwin.NSObject

actual class TorchController {
    private val device = AVCaptureDevice.defaultDeviceWithMediaType(AVMediaTypeVideo)

    @Throws(Exception::class)
    actual fun turnOn() {
        device ?: throw Exception("No device found")
        device.useConfiguration {
            device.setTorchMode(AVCaptureTorchModeOn)
        }
    }

    @Throws(Exception::class)
    actual fun turnOff() {
        device ?: throw Exception("No device found")
        device.useConfiguration {
            device.setTorchMode(AVCaptureTorchModeOff)
        }
    }

    actual val isAvailable: Boolean
        get() = device?.isTorchAvailable() == true

    actual val isEnabledFlow: Flow<Boolean> = callbackFlow {
        if (device == null) {
            println("WARNING: No device found. It's not possible to observe torch mode changes.")
            cancel()
            return@callbackFlow
        }

        val observer = TorchModeObserver {
            trySend(it == 1L)
        }

        device.addObserver(
            observer,
            forKeyPath = "torchMode",
            options = NSKeyValueObservingOptionInitial or NSKeyValueObservingOptionNew,
            context = null
        )

        awaitClose {
            device.removeObserver(observer, forKeyPath = "torchMode")
        }
    }.distinctUntilChanged()

    private fun AVCaptureDevice.useConfiguration(action: () -> Unit) {
        lockForConfiguration(null)
        action()
        unlockForConfiguration()
    }
}

class TorchModeObserver(private val onStatusChanged: (Long) -> Unit) : NSObject(),
    ObserverProtocol {
    override fun observeValueForKeyPath(
        keyPath: String?,
        ofObject: Any?,
        change: Map<Any?, *>?,
        context: COpaquePointer?
    ) {
        (change?.get("new") as? Long)?.let(onStatusChanged)
    }
}