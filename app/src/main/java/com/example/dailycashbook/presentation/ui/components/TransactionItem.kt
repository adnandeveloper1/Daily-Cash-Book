package com.example.dailycashbook.presentation.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.example.dailycashbook.R
import com.example.dailycashbook.core.theme.CardBackgroundGreen
import com.example.dailycashbook.core.theme.CashInGreen
import com.example.dailycashbook.core.theme.CashOutRed
import com.example.dailycashbook.core.theme.LocalDimensions
import com.example.dailycashbook.domain.model.Transaction
import com.example.dailycashbook.domain.model.TransactionType
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun TransactionItem(transaction: Transaction) {
    val dimensions = LocalDimensions.current
    val formatter = SimpleDateFormat("E, dd MMM yyyy - hh:mm a", Locale.getDefault())
    val dateString = formatter.format(Date(transaction.timestamp))

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = CardBackgroundGreen // Green constraint for cards
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = dimensions.elevationSmall)
    ) {
        Column(
            modifier = Modifier.padding(dimensions.paddingMedium)
        ) {
            Text(
                text = dateString,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(dimensions.paddingSmall))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = transaction.description,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
                
                val amountColor = if (transaction.transactionType == TransactionType.IN) {
                    CashInGreen
                } else {
                    CashOutRed
                }
                
                val operator = if (transaction.transactionType == TransactionType.IN) "+" else "-"
                
                Text(
                    text = "$operator ${transaction.amount}",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = amountColor
                )
            }
        }
    }
}
