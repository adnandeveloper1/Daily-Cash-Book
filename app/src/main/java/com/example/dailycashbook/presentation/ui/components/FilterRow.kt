package com.example.dailycashbook.presentation.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ElevatedFilterChip
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.dailycashbook.R
import com.example.dailycashbook.core.theme.LocalDimensions
import com.example.dailycashbook.domain.model.FilterType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterRow(
    currentFilter: FilterType,
    onFilterSelected: (FilterType) -> Unit
) {
    val dimensions = LocalDimensions.current
    val filters = listOf(
        Pair(FilterType.ALL, R.string.filter_all),
        Pair(FilterType.DAILY, R.string.filter_daily),
        Pair(FilterType.WEEKLY, R.string.filter_weekly),
        Pair(FilterType.MONTHLY, R.string.filter_monthly),
        Pair(FilterType.YEARLY, R.string.filter_yearly)
    )

    LazyRow(
        contentPadding = PaddingValues(horizontal = dimensions.paddingMedium),
        horizontalArrangement = Arrangement.spacedBy(dimensions.paddingSmall)
    ) {
        items(filters) { (type, stringRes) ->
            val selected = currentFilter == type
            ElevatedFilterChip(
                selected = selected,
                onClick = { onFilterSelected(type) },
                label = { Text(stringResource(id = stringRes)) },
                colors = FilterChipDefaults.elevatedFilterChipColors(
                    selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                    selectedLabelColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    }
}
