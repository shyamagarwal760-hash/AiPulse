package com.example.aipulse.feature.source.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aipulse.data.source.Mapper.toSourceData
import com.example.aipulse.data.source.Room.SourceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SourceViewModel @Inject constructor(
    private val sourceRepository: SourceRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow<SourceUiState>(SourceUiState.Loading)
    val uiState: StateFlow<SourceUiState> = _uiState.asStateFlow()

    init {
        refreshPage()
        loadSources()
    }

    private fun loadSources() {
        viewModelScope.launch {
            val sources = sourceRepository.observeSources()
                .map { it ->
                    it.map {
                        it.toUiModel()
                    }
                }.collect { source ->
                    _uiState.value = SourceUiState.Success(sources = source)
                }

        }

    }

    private fun refreshPage(){
        _uiState.value = SourceUiState.Loading
       viewModelScope.launch {
           runCatching {
               sourceRepository.refreshData()
           }.onFailure {error ->
               Timber.e(error)
               _uiState.value = SourceUiState.Error(error.message.toString())
           }
       }
    }
}
