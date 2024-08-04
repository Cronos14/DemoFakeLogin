package com.rtga.fakelogin.ui.alert

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.window.DialogProperties

@Composable
fun AlertDialog(
    alertData: AlertData,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text(alertData.confirmButtonLabel)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(alertData.cancelButtonLabel)
            }
        },
        title = { Text(alertData.title) },
        text = { Text(alertData.description) },
        properties = DialogProperties(dismissOnBackPress = true, dismissOnClickOutside = true)
    )
}