package ir.miare.ranking

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.miare.common.resource.getOrElse
import ir.miare.domain.usecase.GetPlayersUseCase
import ir.miare.domain.model.PlayerSortOption
import ir.miare.domain.util.merge
import ir.miare.ui.util.Paginator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class RankingScreenViewModel @Inject constructor(
    getPlayersUseCase: GetPlayersUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(value = RankingScreenState())
    internal val state = _state.asStateFlow()

    val paginator = Paginator(
        scope = viewModelScope,
        fetch = { offset, limit ->
            val result = getPlayersUseCase(
                sortBy = state.value.sortOption,
                offset = offset,
                limit = limit
            ).getOrElse { exception ->
                throw exception
            }

            _state.update {
                if (it.loading || it.refreshing) {
                    it.copy(
                        loading = false,
                        refreshing = false,
                        players = result
                    )
                } else {
                    it.copy(
                        players = it.players.merge(
                            incoming = result,
                            keySelector = { leagueWithPlayers -> leagueWithPlayers.league.id },
                        ),
                        endReached = offset > 0 && result.isEmpty()
                    )
                }
            }

            result
        }
    )

    init {
        _state.update { it.copy(loading = true) }
        paginator.refresh()
    }

    fun sort(sortOption: PlayerSortOption) {
        _state.update { it.copy(sortOption = sortOption) }
        refresh()
    }

    fun refresh() {
        _state.update { it.copy(refreshing = true, endReached = false) }
        paginator.refresh()
    }

    fun next() {
        _state.update { it.copy(loadingNextPage = true) }
        paginator.next().invokeOnCompletion {
            _state.update { it.copy(loadingNextPage = false) }
        }
    }

}