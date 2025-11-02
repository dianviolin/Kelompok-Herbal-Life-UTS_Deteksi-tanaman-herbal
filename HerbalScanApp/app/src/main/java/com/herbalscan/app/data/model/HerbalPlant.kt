package com.herbalscan.app.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class HerbalPlant(
    val id: String,
    val name: String,
    val latinName: String,
    val description: String,
    val benefits: List<String>,
    val usage: String,
    val imageUrl: String,
    val category: String
) : Parcelable
