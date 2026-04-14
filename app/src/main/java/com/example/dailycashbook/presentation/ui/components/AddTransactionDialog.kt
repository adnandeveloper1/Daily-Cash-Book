package com.example.dailycashbook.presentation.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import com.example.dailycashbook.core.theme.CashInGreen
import com.example.dailycashbook.core.theme.CashOutRed
import com.example.dailycashbook.core.theme.LocalDimensions
import com.example.dailycashbook.domain.model.Transaction
import com.example.dailycashbook.domain.model.TransactionType

@Composable
fun AddTransactionDialog(
    transactionType: TransactionType,
    onDismiss: () -> Unit,
    onConfirm: (Transaction) -> Unit
) {
    val dimensions = LocalDimensions.current
    var amountText by remember { mutableStateOf("") }
    var descriptionText by remember { mutableStateOf("") }

    val title = if (transactionType == TransactionType.IN) "Add Cash In" else "Add Cash Out"
    val color = if (transactionType == TransactionType.IN) CashInGreen else CashOutRed

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(text = title, color = color)
        },
        text = {
            Column {
                OutlinedTextField(
                    value = amountText,
                    onValueChange = { amountText = it },
                    label = { Text("Amount") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(dimensions.paddingSmall))
                OutlinedTextField(
                    value = descriptionText,
                    onValueChange = { descriptionText = it },
                    label = { Text("Description") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val amount = amountText.toDoubleOrNull() ?: 0.0
                    if (amount > 0 && descriptionText.isNotBlank()) {
                        val transaction = Transaction(
                            amount = amount,
                            transactionType = transactionType,
                            timestamp = System.currentTimeMillis(),
                            description = descriptionText
                        )
                        onConfirm(transaction)
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = color)
            ) {
                Text("Save", color = MaterialTheme.colorScheme.onError)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel", color = MaterialTheme.colorScheme.onSurface)
            }
        }
    )
}
