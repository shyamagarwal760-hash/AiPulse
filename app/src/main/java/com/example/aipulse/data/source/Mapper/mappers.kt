package com.example.aipulse.data.source.Mapper

import com.example.aipulse.data.source.Domain.SourceData
import com.example.aipulse.data.source.Remote.SourceItemDto
import com.example.aipulse.data.source.Room.SourceDAO
import com.example.aipulse.data.source.Room.SourceEntity
import javax.xml.transform.Source

fun SourceItemDto.toSourceEntity() : SourceEntity?{
    val sourceUrl = id ?: return null
    return SourceEntity(
        id = sourceUrl,
        name = name,
        description = description
    )
}

fun SourceEntity.toSourceData() : SourceData {
    return SourceData(
        name = name,
        description = description
    )
}
