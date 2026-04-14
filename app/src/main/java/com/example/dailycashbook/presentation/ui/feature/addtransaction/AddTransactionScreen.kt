package com.example.dailycashbook.presentation.ui.feature.addtransaction

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.dailycashbook.R
import com.example.dailycashbook.core.theme.LocalDimensions
import com.example.dailycashbook.domain.model.TransactionType
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTransactionScreen(
    onNavigateBack: () -> Unit,
    viewModel: AddTransactionViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val dimensions = LocalDimensions.current

    LaunchedEffect(state.isSaved) {
        if (state.isSaved) {
            onNavigateBack()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = if (state.transactionType == TransactionType.IN)
                            stringResource(R.string.title_add_cash_in)
                        else stringResource(R.string.title_add_cash_out),
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = stringResource(R.string.content_desc_back),
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { /* Handle options */ }) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = stringResource(R.string.menu_options),
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            )
        },
        bottomBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(dimensions.paddingMedium),
                horizontalArrangement = Arrangement.spacedBy(dimensions.paddingSmall)
            ) {
                Button(
                    onClick = { viewModel.onEvent(AddTransactionEvent.OnSaveAndExit) },
                    modifier = Modifier
                        .weight(1f)
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                    ),
                    shape = RoundedCornerShape(dimensions.cornerRadiusMedium)
                ) {
                    Text(text = stringResource(R.string.btn_save_and_exit))
                }
                
                Button(
                    onClick = { viewModel.onEvent(AddTransactionEvent.OnSaveAndContinue) },
                    modifier = Modifier
                        .weight(1f)
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary, // Mandatory Green Primary Action Button
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    ),
                    shape = RoundedCornerShape(dimensions.cornerRadiusMedium)
                ) {
                    Text(text = stringResource(R.string.btn_save_and_continue))
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f))
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(dimensions.paddingMedium),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                shape = RoundedCornerShape(dimensions.cornerRadiusLarge),
                elevation = CardDefaults.cardElevation(defaultElevation = dimensions.elevationSmall)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(dimensions.paddingMedium)
                ) {
                    // Transaction Type Toggles
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Start
                    ) {
                        ToggleButton(
                            text = stringResource(R.string.title_add_cash_in),
                            isSelected = state.transactionType == TransactionType.IN,
                            onClick = { viewModel.onEvent(AddTransactionEvent.OnTransactionTypeChange(TransactionType.IN)) },
                            selectedColor = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.width(dimensions.paddingSmall))
                        ToggleButton(
                            text = stringResource(R.string.title_add_cash_out),
                            isSelected = state.transactionType == TransactionType.OUT,
                            onClick = { viewModel.onEvent(AddTransactionEvent.OnTransactionTypeChange(TransactionType.OUT)) },
                            selectedColor = MaterialTheme.colorScheme.error // Using error color for Cash Out conventionally
                        )
                    }

                    Spacer(modifier = Modifier.height(dimensions.paddingMedium))

                    // Date and Time selection
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(dimensions.paddingSmall)
                    ) {
                        val dateFormat = SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault())
                        val timeFormat = SimpleDateFormat("hh:mm a", Locale.getDefault())
                        val dateString = dateFormat.format(Date(state.timestamp))
                        val timeString = timeFormat.format(Date(state.timestamp))

                        // Date Box
                        Box(
                            modifier = Modifier
                                .weight(1.5f)
                                .border(
                                    width = 1.dp,
                                    color = MaterialTheme.colorScheme.outlineVariant,
                                    shape = RoundedCornerShape(dimensions.cornerRadiusMedium)
                                )
                                .padding(dimensions.paddingMedium),
                            contentAlignment = Alignment.Center
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text("<", color = MaterialTheme.colorScheme.onSurface)
                                Text(text = dateString, color = MaterialTheme.colorScheme.onSurface)
                                Text(">", color = MaterialTheme.colorScheme.onSurface)
                                // We'd ideally use an actual Calendar Icon
                            }
                        }

                        // Time Box
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .border(
                                    width = 1.dp,
                                    color = MaterialTheme.colorScheme.outlineVariant,
                                    shape = RoundedCornerShape(dimensions.cornerRadiusMedium)
                                )
                                .padding(dimensions.paddingMedium),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = timeString, color = MaterialTheme.colorScheme.onSurface)
                        }
                    }

                    Spacer(modifier = Modifier.height(dimensions.paddingMedium))

                    // Amount TextField
                    OutlinedTextField(
                        value = state.amount,
                        onValueChange = { viewModel.onEvent(AddTransactionEvent.OnAmountChange(it)) },
                        label = {
                            Text(
                                text = if (state.transactionType == TransactionType.IN)
                                    stringResource(R.string.hint_amount_cash_in)
                                else stringResource(R.string.hint_amount_cash_out)
                            )
                        },
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                        singleLine = true,
                        isError = state.isError,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = if(state.transactionType == TransactionType.IN) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error,
                            focusedLabelColor = if(state.transactionType == TransactionType.IN) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error
                        )
                    )

                    if (state.isError && state.errorMessage != null) {
                        Text(
                            text = state.errorMessage!!,
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.padding(start = dimensions.paddingSmall, top = 4.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(dimensions.paddingMedium))

                    // Notes TextField
                    OutlinedTextField(
                        value = state.notes,
                        onValueChange = { viewModel.onEvent(AddTransactionEvent.OnNotesChange(it)) },
                        placeholder = { Text(text = stringResource(R.string.hint_notes)) },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(dimensions.paddingMedium))

                    // Add Bills / Add Items Buttons
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(dimensions.paddingSmall)
                    ) {
                        OutlinedButton(
                            onClick = { /* TODO Handle Add Bills */ },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(dimensions.cornerRadiusMedium)
                        ) {
                            Text(text = stringResource(R.string.btn_add_bills))
                        }
                        
                        OutlinedButton(
                            onClick = { /* TODO Handle Add Items */ },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(dimensions.cornerRadiusMedium)
                        ) {
                            Text(text = stringResource(R.string.btn_add_items))
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ToggleButton(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    selectedColor: Color
) {
    val dimensions = LocalDimensions.current
    val backgroundColor = if (isSelected) selectedColor else MaterialTheme.colorScheme.surfaceVariant
    val contentColor = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(dimensions.cornerRadiusLarge))
            .background(backgroundColor)
            .clickable { onClick() }
            .padding(horizontal = dimensions.paddingMedium, vertical = dimensions.paddingSmall),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = contentColor,
            fontWeight = FontWeight.Medium,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}
