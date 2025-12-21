package com.example.pocket_road_ui.ui.screens.cardex

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pocket_road_ui.data.repository.ICarRepository
import com.example.pocket_road_ui.domain.model.Car
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

// 1. O Estado da UI (Fonte única da verdade)
data class CardexUiState(
    val isLoading: Boolean = false,
    val cars: List<Car> = emptyList(),
    val errorMessage: String? = null,

    // Estatísticas calculadas
    val capturedCount: Int = 0,
    val garageValue: String = "0",
    val ranking: String = "#---"
)

@HiltViewModel
class CardexViewModel @Inject constructor(
    private val repository: ICarRepository // Injete seu repositório aqui
) : ViewModel() {

    private val _uiState = MutableStateFlow(CardexUiState())
    val uiState = _uiState.asStateFlow()

    init {
        // Carrega os dados assim que a ViewModel nasce
        fetchGarageData()
    }

    fun fetchGarageData() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }

            // Simulação de delay de rede (Remova isso quando tiver API real)
            delay(1000)

            val result = repository.getCardex() // Supondo que retorne Result<List<Car>>

            result.onSuccess { carList ->
                // Calculando estatísticas baseadas nos dados recebidos
                val unlockedCars = carList.filter { it.unlocked }
                val totalValue = unlockedCars.sumOf { it.estimatedValue }

                // Formatação simples de dinheiro (pode usar NumberFormat depois)
                val formattedValue = if (totalValue >= 1000000) {
                    "${String.format("%.1f", totalValue / 1000000)}M"
                } else {
                    "${(totalValue / 1000).toInt()}k"
                }

                _uiState.update {
                    it.copy(
                        isLoading = false,
                        cars = carList,
                        capturedCount = unlockedCars.size,
                        garageValue = formattedValue,
                        ranking = "#840" // Isso viria de outro endpoint de user profile
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