package com.datalayer.demo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.datalayer.demo.datalayer.cats.CatRepository
import com.datalayer.demo.datalayer.cats.api.Cat
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
sealed class CatsState {
    data object Loading : CatsState()
    data class Success(val cats: List<Cat>) : CatsState()
    data class Error(val message: String) : CatsState()
}
@HiltViewModel
class CatsViewModel
@Inject
    constructor(private val catsRepository: CatRepository)
    : ViewModel() {

    private val _catsState = MutableStateFlow<CatsState>(CatsState.Loading)
    val catsState: StateFlow<CatsState> = _catsState.asStateFlow()
    fun getCats() {
        viewModelScope.launch {
            _catsState.update { CatsState.Loading }
            try {
                catsRepository.getCats().collect {cats->
                    _catsState.update { CatsState.Success(cats) }
                }
            } catch (e: Exception) {
                _catsState.update { CatsState.Error(e.message ?: "Unknown error") }
            }
        }
    }

}