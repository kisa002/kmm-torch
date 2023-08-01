package com.haeyum.kmm_torch.presentation.components

import androidx.compose.runtime.Composable

@Composable
expect fun Alert(title: String, text: String, confirmText: String, onConfirm: () -> Unit)