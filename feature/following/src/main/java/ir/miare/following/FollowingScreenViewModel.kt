package ir.miare.following

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.miare.common.resource.onFailure
import ir.miare.common.resource.onSuccess
import ir.miare.domain.usecase.GetFollowedPlayersUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FollowingScreenViewModel @Inject constructor(
    private val getFollowedPlayersUseCase: GetFollowedPlayersUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(value = FollowingScreenState())
    internal val state = _state.asStateFlow()

    init {
        getPlayers()
    }

    private fun getPlayers() {
        viewModelScope.launch {
            _state.update { it.copy(loading = true) }
            getFollowedPlayersUseCase(sortBy = state.value.sortOption)
                .collect { resource ->
                    resource
                        .onFailure {
                            _state.update { it.copy(loading = false) }
                        }
                        .onSuccess { players ->
                            _state.update { it.copy(loading = false, players = players) }
                        }
                }
        }
    }

}