package dev.timatifey.newsapp.ui.articles.details

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

/**
 * @author timatifey
 */
@Serializable
data object ArticleDetailRoute

fun NavController.navigateToArticleDetailScreen(navOptions: NavOptions? = null) {
    navigate(route = ArticleDetailRoute, navOptions = navOptions)
}

fun NavGraphBuilder.ArticleDetailSection() {
    composable<ArticleDetailRoute> {
        ArticleDetailRoot()
    }
}

