package com.haeyum.kmm_torch.presentation.components

import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.window.DialogProperties

@Composable
actual fun Alert(title: String, text: String, confirmText: String, onConfirm: () -> Unit) {
    AlertDialog(
        onDismissRequest = { },
        confirmButton = {
            Button(onClick = onConfirm) {
                Text(text = confirmText)
            }
        },
        title = {
            Text(text = title)
        },
        text = {
            Text(text = text)
        },
        properties = DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false)
    )
}