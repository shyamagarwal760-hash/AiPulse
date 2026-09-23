package com.example.aipulse.data.source.Room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("sources")
data class SourceEntity(
    @PrimaryKey
    val id : String,
    val name : String? = null,
    val description : String? = null
)
