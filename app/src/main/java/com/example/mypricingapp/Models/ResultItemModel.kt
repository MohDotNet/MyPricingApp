package com.example.mypricingapp.Models

import androidx.compose.ui.graphics.painter.Painter

data class ResultItemModel(
    val Description: String,
    val Price: String,
    val itemPicture: Painter
)
