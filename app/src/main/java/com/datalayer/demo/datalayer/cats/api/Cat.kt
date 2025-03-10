package com.datalayer.demo.datalayer.cats.api

import com.squareup.moshi.JsonClass


data class Cat(
    val id: String,
    val tags: List<String>,
    val mimetype: String,
    val createdAt: String,
    val downloadUrl: String?
)