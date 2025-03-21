package dev.timatifey.newsapp.ui.articles.details

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

/**
 * @author timatifey
 */
@Composable
fun ArticleDetailRoot(
    viewModel: ArticleDetailViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    ArticleDetailScreen(
        state = state,
        onAction = viewModel::onAction,
    )
}

@Composable
private fun ArticleDetailScreen(
    state: ArticleDetailState,
    onAction: (ArticleDetailAction) -> Unit,
) {

}

