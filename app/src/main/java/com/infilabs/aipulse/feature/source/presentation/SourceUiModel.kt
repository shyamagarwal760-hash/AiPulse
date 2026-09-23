package com.infilabs.aipulse.feature.source.presentation

import com.infilabs.aipulse.data.source.Domain.SourceData

data class SourceUiModel(
    val name: String? = null,
    val description: String? = null,
)

fun SourceData.toUiModel(): SourceUiModel {
    return SourceUiModel(
        name = name,
        description = description,
    )
}
