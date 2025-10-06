package ir.miare.player

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.miare.common.resource.onFailure
import ir.miare.common.resource.onSuccess
import ir.miare.domain.usecase.FollowPlayerUseCase
import ir.miare.domain.usecase.UnfollowPlayerUseCase
import ir.miare.ui.mapper.domain
import ir.miare.ui.util.navigation.Screen
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlayerScreenViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val followPlayerUseCase: FollowPlayerUseCase,
    private val unfollowPlayerUseCase: UnfollowPlayerUseCase
) : ViewModel() {

    private val player: Screen.Player = savedStateHandle.toRoute()

    private val _state = MutableStateFlow(
        value = PlayerScreenState(playerWithLeague = player.domain())
    )
    internal val state = _state.asStateFlow()

    fun handleFollow() {
        val player = state.value.playerWithLeague.player
        if (player.followed) unfollow() else follow()
    }

    private fun follow() {
        viewModelScope.launch {
            followPlayerUseCase(player = state.value.playerWithLeague)
                .onFailure { exception ->  }
                .onSuccess {
                    _state.update {
                        it.copy(
                            playerWithLeague = it.playerWithLeague.copy(
                                player = it.playerWithLeague.player.copy(followed = true)
                            )
                        )
                    }
                }
        }
    }

    private fun unfollow() {
        viewModelScope.launch {
            unfollowPlayerUseCase(player = state.value.playerWithLeague)
                .onFailure { exception ->  }
                .onSuccess {
                    _state.update {
                        it.copy(
                            playerWithLeague = it.playerWithLeague.copy(
                                player = it.playerWithLeague.player.copy(followed = false)
                            )
                        )
                    }
                }
        }
    }

}