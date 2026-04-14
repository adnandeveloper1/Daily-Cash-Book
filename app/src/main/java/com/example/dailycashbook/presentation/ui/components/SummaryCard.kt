package com.example.dailycashbook.presentation.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
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
import com.example.dailycashbook.core.theme.BalanceCardGreen
import com.example.dailycashbook.core.theme.CashInGreen
import com.example.dailycashbook.core.theme.CashOutRed
import com.example.dailycashbook.core.theme.LocalDimensions
import com.example.dailycashbook.domain.usecase.BalanceResult

@Composable
fun SummaryCard(balanceResult: BalanceResult) {
    val dimensions = LocalDimensions.current

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = BalanceCardGreen // Green constraint for summary cards
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = dimensions.elevationMedium)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensions.paddingMedium),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            SummaryItem(
                label = stringResource(id = R.string.total_cash_in),
                amount = balanceResult.totalIn,
                color = CashInGreen
            )
            SummaryItem(
                label = stringResource(id = R.string.total_cash_out),
                amount = balanceResult.totalOut,
                color = CashOutRed
            )
            SummaryItem(
                label = stringResource(id = R.string.main_balance),
                amount = balanceResult.balance,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }
    }
}

@Composable
private fun SummaryItem(label: String, amount: Double, color: androidx.compose.ui.graphics.Color) {
    val dimensions = LocalDimensions.current
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(dimensions.paddingSmall)
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )
        Text(
            text = amount.toString(),
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = color
        )
    }
}
