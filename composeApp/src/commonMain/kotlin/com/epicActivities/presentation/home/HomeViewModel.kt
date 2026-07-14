package com.epicActivities.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.epicActivities.domain.usecase.ExchangeStravaCodeUseCase
import com.epicActivities.platform.StravaTokenStorage
import com.epicActivities.platform.UrlOpener
import com.epicActivities.presentation.common.StravaCodeHolder
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

private const val CLIENT_ID = "265079"
private const val REDIRECT_URI = "epicactivities://strava-callback"
private const val SCOPE = "activity:read_all"

class HomeViewModel : ViewModel() {
    private val tokenStorage = StravaTokenStorage()
    private val urlOpener = UrlOpener()
    private val exchangeCodeUseCase = ExchangeStravaCodeUseCase()

    private val _state = MutableStateFlow(HomeState())
    val state: StateFlow<HomeState> = _state.asStateFlow()

    private val _navigateToActivities = MutableSharedFlow<Unit>(replay = 0)
    val navigateToActivities: SharedFlow<Unit> = _navigateToActivities.asSharedFlow()

    init {
        _state.update { it.copy(isConnected = tokenStorage.load() != null) }

        viewModelScope.launch {
            StravaCodeHolder.code.collect { code ->
                if (code != null) {
                    exchangeCode(code)
                    StravaCodeHolder.consume()
                }
            }
        }
    }

    fun connectStrava() {
        val authUrl = "https://www.strava.com/oauth/mobile/authorize" +
            "?client_id=$CLIENT_ID" +
            "&redirect_uri=$REDIRECT_URI" +
            "&response_type=code" +
            "&approval_prompt=auto" +
            "&scope=$SCOPE"
        urlOpener.openUrl(authUrl)
    }

    fun disconnect() {
        tokenStorage.clear()
        _state.update { it.copy(isConnected = false, error = null) }
    }

    private fun exchangeCode(code: String) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            exchangeCodeUseCase(code)
                .onSuccess {
                    _state.update { it.copy(isLoading = false, isConnected = true) }
                    _navigateToActivities.emit(Unit)
                }
                .onFailure { e ->
                    _state.update { it.copy(isLoading = false, error = e.message ?: "Error al conectar con Strava") }
                }
        }
    }
}
