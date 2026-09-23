package com.example.aipulse.data.article.room.RemoteKey

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("remote_keys")
data class RemoteKeysEntity(
    @PrimaryKey
    val articleUrl : String,
    val nextPage : Int? = null,
    val prevPage : Int? = null
)
