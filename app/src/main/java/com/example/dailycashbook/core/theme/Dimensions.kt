package com.example.dailycashbook.core.theme

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class Dimensions(
    val paddingSmall: Dp = 8.dp,
    val paddingMedium: Dp = 16.dp,
    val paddingLarge: Dp = 24.dp,
    val paddingExtraLarge: Dp = 32.dp,
    
    val cornerRadiusSmall: Dp = 8.dp,
    val cornerRadiusMedium: Dp = 16.dp,
    val cornerRadiusLarge: Dp = 24.dp,
    
    val iconSizeSmall: Dp = 16.dp,
    val iconSizeMedium: Dp = 24.dp,
    val iconSizeLarge: Dp = 32.dp,
    
    val elevationSmall: Dp = 2.dp,
    val elevationMedium: Dp = 4.dp,
    val elevationLarge: Dp = 8.dp
)

val LocalDimensions = compositionLocalOf { Dimensions() }
