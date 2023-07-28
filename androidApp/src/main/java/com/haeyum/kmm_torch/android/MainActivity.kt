package com.haeyum.kmm_torch.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.haeyum.kmm_torch.utils.torch.TorchController

class MainActivity : ComponentActivity() {
    private val torchController by lazy {
        TorchController(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MyApplicationTheme {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    val isTorchEnabled by torchController.isEnabledFlow.collectAsState(initial = false)
                    var isVisibleTorchError by remember { mutableStateOf(false) }

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(MaterialTheme.colors.background),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(text = "Torch Enabled: $isTorchEnabled")

                        Button(onClick = {
                            try {
                                torchController.turnOn()
                            } catch (e: Exception) {
                                isVisibleTorchError = true
                            }
                        }) {
                            Text(text = "Turn On")
                        }
                        Button(onClick = {
                            try {
                                torchController.turnOff()
                            } catch (e: Exception) {
                                isVisibleTorchError = true
                            }
                        }) {
                            Text(text = "Turn Off")
                        }
                    }

                    AnimatedVisibility(visible = isVisibleTorchError) {
                        AlertDialog(
                            onDismissRequest = { isVisibleTorchError = false },
                            confirmButton = {
                                Button(onClick = { isVisibleTorchError = false }) {
                                    Text(text = "OK")
                                }
                            },
                            title = {
                                Text(text = "ERROR: TORCH NOT FOUND")
                            },
                            text = {
                                Text(text = "This device does not have a camera with a flash.")
                            }
                        )
                    }
                }
            }
        }
    }
}