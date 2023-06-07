package com.example69.projectx.data

import androidx.annotation.DrawableRes

data class BottomMenuContent(
    val title: String,
    val route:String,
    @DrawableRes val iconId: Int,
    val badgecount: Int=0
)