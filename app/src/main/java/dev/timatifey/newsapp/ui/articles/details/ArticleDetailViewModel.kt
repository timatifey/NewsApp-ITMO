package dev.timatifey.newsapp.ui.articles.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

/**
 * @author timatifey
 */
@HiltViewModel(assistedFactory = ArticleDetailViewModel.Factory::class)
class ArticleDetailViewModel @AssistedInject constructor(
    @Assisted url: String,
) : ViewModel() {

    private var hasLoadedInitialData = false

    private val _state = MutableStateFlow(ArticleDetailState())
    val state: StateFlow<ArticleDetailState> = _state
        .onStart {
            if (!hasLoadedInitialData) {
                /** Load initial data here **/
                hasLoadedInitialData = true
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = ArticleDetailState()
        )

    fun onAction(action: ArticleDetailAction) {
        when (action) {
            else -> TODO("Handle actions")
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(url: String): ArticleDetailViewModel
    }
}
