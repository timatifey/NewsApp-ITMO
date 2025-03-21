package dev.timatifey.newsapp.ui.articles.list

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

/**
 * @author timatifey
 */
@Serializable
data object ArticlesListRoute

fun NavController.navigateToArticlesListScreen(navOptions: NavOptions? = null) {
    navigate(route = ArticlesListRoute, navOptions = navOptions)
}

fun NavGraphBuilder.articlesListSection() {
    composable<ArticlesListRoute> {
        ArticlesListRoot()
    }
}

