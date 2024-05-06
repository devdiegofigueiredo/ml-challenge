package com.android.ml_challenge.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.ml_challenge.R
import com.android.ml_challenge.ui.search.domain.SearchUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class SearchViewModel(private val searchUseCase: SearchUseCase) : ViewModel() {

    private val _uiSearchState = MutableStateFlow<UiSearchState<String>>(UiSearchState.Loading)
    val uiSearchState: StateFlow<UiSearchState<String>> = _uiSearchState

    fun search(text: String) {
        _uiSearchState.value = UiSearchState.Loading

        viewModelScope.launch {
            searchUseCase.isValidText(text)
                .flowOn(Dispatchers.IO)
                .catch { e ->
                    _uiSearchState.value = UiSearchState.Error(e.message.toString())
                }
                .collect {
                    if (it) {
                        _uiSearchState.value = UiSearchState.Success
                    } else {
                        _uiSearchState.value = UiSearchState.Empty
                    }
                }
        }
    }
}