package dev.timatifey.newsapp.ui.articles.list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil3.compose.AsyncImage
import dev.timatifey.newsapp.R
import dev.timatifey.newsapp.ui.theme.NewsAppTheme

/**
 * @author timatifey
 */
@Composable
fun ArticlesListRoot(
    viewModel: ArticlesListViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val query by viewModel.searchQuery.collectAsStateWithLifecycle()

    ArticlesListScreen(
        state = state,
        query = query,
        onAction = viewModel::onAction,
        articles = ArticleCategory.entries.map { category ->
            category to viewModel.getArticles(category).collectAsLazyPagingItems()
        },
        onQueryChanged = viewModel::onSearchChange,
    )
}

@Composable
private fun ArticlesListScreen(
    state: ArticlesListState,
    query: String,
    onAction: (ArticlesListAction) -> Unit,
    onQueryChanged: (String) -> Unit,
    articles: List<Pair<ArticleCategory, LazyPagingItems<ArticleUi>>>,
) {
    Column {
        SearchField(
            query = query,
            onValueChanged = onQueryChanged,
        )

        Spacer(modifier = Modifier.height(32.dp))

        LazyColumn {
            items(count = articles.size) {
                val (category, list) = articles[it]
                CategoryRow(category, list)
                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}

@Composable
private fun CategoryRow(
    category: ArticleCategory,
    articles: LazyPagingItems<ArticleUi>
) {
    Column(modifier = Modifier.padding(horizontal = 32.dp)) {
        Text(
            text = category.toString(),
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(32.dp))
        LazyRow(
            modifier = Modifier
                .fillMaxWidth(),
        ) {
            items(
                count = articles.itemCount,
            ) { index ->
                val article = articles[index]
                if (article == null) {
                    Box(modifier = Modifier.size(100.dp)) // placeholder
                } else {
                    ArticleItem(
                        article = article,
                    )
                    Spacer(modifier = Modifier.size(32.dp))
                }
            }

            item {
                when (val state = articles.loadState.refresh) {
                    is LoadState.Loading -> {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(300.dp)
                                .padding(32.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }

                    else -> Unit
                }
            }

            item {
                when (val state = articles.loadState.append) {
                    is LoadState.Loading -> {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(300.dp)
                                .padding(32.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }

                    else -> Unit
                }
            }
        }
    }
}

@Composable
private fun ArticleItem(
    article: ArticleUi,
) {
    Card(
        modifier = Modifier
            .height(300.dp)
            .width(200.dp)
    ) {
        Box(contentAlignment = Alignment.Center) {
            AsyncImage(
                modifier = Modifier.fillMaxSize(),
                model = article.urlToImage,
                contentDescription = null,
                contentScale = ContentScale.Crop,
            )
            Surface(
                color = Color(0x66000000),
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    contentAlignment = Alignment.BottomStart
                ) {
                    Text(
                        text = article.title ?: "",
                        color = Color.White,
                    )
                }
            }
        }
    }
}

@Composable
private fun SearchField(query: String, onValueChanged: (String) -> Unit) {
    TextField(
        value = query,
        onValueChange = onValueChanged,
        leadingIcon = {
            Icon(
                imageVector = Icons.Rounded.Search,
                tint = MaterialTheme.colorScheme.onBackground,
                contentDescription = "Search icon"
            )
        },
        maxLines = 1,
        placeholder = { Text(text = stringResource(R.string.hint_search_query)) },
        singleLine = true,
        modifier = Modifier
            .fillMaxWidth()
            .background(color = MaterialTheme.colorScheme.background, shape = RectangleShape)
    )
}

@Preview
@Composable
private fun PreviewItem() {
    NewsAppTheme {
        ArticleItem(
            article = ArticleUi(
                author = null,
                title = "Title",
                description = null,
                url = null,
                urlToImage = null,
                publishedAt = null,
                content = null,
            )
        )
    }
}