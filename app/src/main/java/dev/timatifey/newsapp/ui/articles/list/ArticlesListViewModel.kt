package dev.timatifey.newsapp.ui.articles.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import androidx.room.Query
import androidx.room.util.query
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.timatifey.newsapp.data.ArticlesRepository
import dev.timatifey.newsapp.data.database.model.ArticleEntity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

/**
 * @author timatifey
 */
@OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
@HiltViewModel
class ArticlesListViewModel @Inject constructor(
    private val repository: ArticlesRepository,
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    private val _category = MutableStateFlow("")
    val category: StateFlow<String> = _category

    private val _country = MutableStateFlow("us")
    val country: StateFlow<String> = _country

    private var hasLoadedInitialData = false

    fun onSearchChange(query: String) {
        _searchQuery.value = query
    }

    private data class ArticlesParams(
        val query: String,
        val category: String,
        val country: String,
    )

    fun getArticles(category: ArticleCategory): Flow<PagingData<ArticleUi>> {
        return _searchQuery
            .debounce(2000L)
            .combine(_country) { query, country ->
                ArticlesParams(query = query, category = category.value, country = country)
            }
            .flatMapLatest { params ->
                repository.getArticles(
                    query = params.query,
                    country = params.country,
                    category = params.category,
                ).map { pagingData ->
                    pagingData.map { entity ->
                        ArticleUi(
                            author = entity.author,
                            title = entity.title,
                            description = entity.description,
                            url = entity.url,
                            urlToImage = entity.urlToImage,
                            publishedAt = entity.publishedAt,
                            content = entity.content,
                        )
                    }
                }
            }
            .cachedIn(viewModelScope)
    }

    private val _state = MutableStateFlow(ArticlesListState())
    val state: StateFlow<ArticlesListState> = _state
        .onStart {
            if (!hasLoadedInitialData) {
                /** Load initial data here **/
                hasLoadedInitialData = true
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = ArticlesListState()
        )

    fun onAction(action: ArticlesListAction) {
        when (action) {
            else -> TODO("Handle actions")
        }
    }
}
