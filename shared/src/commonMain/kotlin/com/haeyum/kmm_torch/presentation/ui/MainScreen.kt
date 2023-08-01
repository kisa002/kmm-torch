@file:OptIn(ExperimentalAnimationApi::class)

package com.haeyum.kmm_torch.presentation.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.haeyum.kmm_torch.presentation.components.Alert
import com.haeyum.kmm_torch.utils.torch.TorchController

@Composable
fun MainScreen(torchController: TorchController) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        val isTorchEnabled by torchController.isEnabledFlow.collectAsState(false)
        var isVisibleTorchError by remember { mutableStateOf(false) }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF212020)),
            contentAlignment = Alignment.Center
        ) {
            TextButton(
                onClick = {
                    try {
                        if (isTorchEnabled)
                            torchController.turnOff()
                        else
                            torchController.turnOn()
                    } catch (e: Exception) {
                        isVisibleTorchError = true
                    }
                },
                modifier = Modifier
                    .fillMaxWidth(.45f)
                    .aspectRatio(1f)
                    .clip(CircleShape)
                    .then(
                        if (isTorchEnabled) Modifier.background(Color(0xFFFB8000)) else Modifier.background(
                            brush = Brush.verticalGradient(
                                listOf(
                                    Color(0xFF333333),
                                    Color(0xFF282828)
                                )
                            ),
                        )
                    )
                    .border(
                        width = 2.dp,
                        color = Color.Black,
                        shape = CircleShape
                    ),
                shape = CircleShape,
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.Transparent,
                    contentColor = Color.White
                ),
                contentPadding = PaddingValues(0.dp)
            ) {
                Text(
                    text = if (isTorchEnabled) "ON" else "OFF",
                    fontSize = 28.sp
                )
            }

            AnimatedVisibility(
                visible = isVisibleTorchError,
                enter = fadeIn() + scaleIn(),
                exit = fadeOut() + scaleOut()
            ) {
                Alert(
                    title = "ERROR: TORCH NOT FOUND",
                    text = "This device does not have a camera with a flash.",
                    confirmText = "OK",
                    onConfirm = {
                        isVisibleTorchError = false
                    }
                )
            }
        }
    }
}