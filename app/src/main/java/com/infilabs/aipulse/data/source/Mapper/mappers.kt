package com.infilabs.aipulse.data.source.Mapper

import com.infilabs.aipulse.data.source.Domain.SourceData
import com.infilabs.aipulse.data.source.Remote.SourceItemDto
import com.infilabs.aipulse.data.source.Room.SourceEntity

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
