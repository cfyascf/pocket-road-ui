package com.example.pocket_road_ui.ui.screens.cardex

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pocket_road_ui.data.interfaces.ICardexRepository
import com.example.pocket_road_ui.data.local.SessionManager
import com.example.pocket_road_ui.domain.mapper.toEntity
import com.example.pocket_road_ui.domain.models.CardexKpis
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CardexViewModel @Inject constructor(
    private val repository: ICardexRepository,
    private val sessionManager: SessionManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(CardexUiState())
    val uiState = _uiState.asStateFlow()

    init {
        fetchGarageData()
    }

    fun fetchGarageData() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }

            val userId = sessionManager.userId.first()
            if (userId == null) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = "Usuário não identificado. Faça login novamente."
                    )
                }
                return@launch
            }

            val result = repository.getCardex(userId)

            result.onSuccess { dto ->
                val data = dto.data
                if(data == null) {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = "Erro")
                    }

                    return@launch
                }

                val cars = data.cars.map { it.toEntity() }
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        cars = cars,
                        kpis = CardexKpis(
                            data.capturedCount,
                            data.garageValue,
                            data.ranking)
                    )
                }
            }

            result.onFailure { error ->
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = "Erro ao carregar garagem: ${error.message}"
                    )
                }
            }
        }
    }
}