package com.example.dailycashbook.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.dailycashbook.R
import com.example.dailycashbook.core.theme.CashInGreen
import com.example.dailycashbook.core.theme.CashOutRed
import com.example.dailycashbook.core.theme.LocalDimensions
import com.example.dailycashbook.domain.model.TransactionType
import com.example.dailycashbook.presentation.ui.components.AddTransactionDialog
import com.example.dailycashbook.presentation.ui.components.FilterRow
import com.example.dailycashbook.presentation.ui.components.MainTopBar
import com.example.dailycashbook.presentation.ui.components.SummaryCard
import com.example.dailycashbook.presentation.ui.components.TransactionItem
import com.example.dailycashbook.presentation.viewmodel.CashBookViewModel

@Composable
fun CashBookScreen(
    viewModel: CashBookViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val dimensions = LocalDimensions.current

    var showAddDialog by remember { mutableStateOf(false) }
    var selectedTransactionType by remember { mutableStateOf(TransactionType.IN) }

    Scaffold(
        topBar = {
            MainTopBar()
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Spacer(modifier = Modifier.height(dimensions.paddingMedium))
            
            FilterRow(
                currentFilter = uiState.currentFilter,
                onFilterSelected = { viewModel.changeFilter(it) }
            )

            Spacer(modifier = Modifier.height(dimensions.paddingMedium))

            Box(modifier = Modifier.weight(1f)) {
                if (uiState.isLoading) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                } else if (uiState.transactions.isEmpty()) {
                    Text(
                        text = "No transactions found",
                        modifier = Modifier.align(Alignment.Center),
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                } else {
                    LazyColumn(
                        contentPadding = PaddingValues(horizontal = dimensions.paddingMedium),
                        verticalArrangement = Arrangement.spacedBy(dimensions.paddingSmall)
                    ) {
                        items(uiState.transactions, key = { it.id }) { transaction ->
                            TransactionItem(transaction = transaction)
                        }
                    }
                }
            }

            // Bottom Section (Summary + Buttons)
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(dimensions.paddingMedium)
            ) {
                SummaryCard(balanceResult = uiState.balanceResult)
                
                Spacer(modifier = Modifier.height(dimensions.paddingMedium))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(dimensions.paddingMedium)
                ) {
                    Button(
                        onClick = {
                            selectedTransactionType = TransactionType.IN
                            showAddDialog = true
                        },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(containerColor = CashInGreen)
                    ) {
                        Text(text = stringResource(id = R.string.btn_cash_in), color = MaterialTheme.colorScheme.onError)
                    }

                    Button(
                        onClick = {
                            selectedTransactionType = TransactionType.OUT
                            showAddDialog = true
                        },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(containerColor = CashOutRed)
                    ) {
                        Text(text = stringResource(id = R.string.btn_cash_out), color = MaterialTheme.colorScheme.onError)
                    }
                }
            }
        }
    }

    if (showAddDialog) {
        AddTransactionDialog(
            transactionType = selectedTransactionType,
            onDismiss = { showAddDialog = false },
            onConfirm = { transaction ->
                viewModel.addTransaction(transaction)
                showAddDialog = false
            }
        )
    }
}
