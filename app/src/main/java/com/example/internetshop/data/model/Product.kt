package com.example.internetshop.data.model

data class Product(
    val id: String = "",
    val Name: String = "",
    val Description: String = "",
    val ExtendedDescription: String = "",
    val ImageUrl: String = "",
    val Price: Double = 0.0,
    val Category: String = "",
    val AverageRating: Double = 0.0,
    val PurchaseCount: Int = 0,
    val ViewCount: Int = 0,
    val Tags: String = ""
)

