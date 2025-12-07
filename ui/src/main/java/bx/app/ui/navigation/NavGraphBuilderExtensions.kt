package bx.app.ui.navigation

import android.content.Context
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import bx.app.presentation.viewmodel.TopBarViewModel
import bx.app.ui.data.LearnData
import bx.app.ui.ScreenManager
import bx.app.ui.navigation.data.DatabaseOperation
import bx.app.ui.navigation.data.NavigationRoute

/**
 * This extension function of [NavGraphBuilder] is the main navigation function of this app
 * and contains all the destinations
 *
 * @param navController Is the main navigation controller
 * @param context Is the application context
 */
fun NavGraphBuilder.navHostDestinations(
    navController: NavHostController,
    context: Context,
    topBarViewModel: TopBarViewModel,
) {
    val screenManager = ScreenManager(context, topBarViewModel)

    composableDecks(navController, screenManager)
    composableDeckSettings(screenManager)
    composableDeckCards(navController, screenManager)
    composableDeckLearn(navController, screenManager)
    composableDeckLevels(navController, screenManager)
    composable<NavigationRoute.CardFront> { screenManager.Card() }
    composable<NavigationRoute.CardBack> { screenManager.Card() }
    composable<NavigationRoute.Level> { screenManager.Level() }
    composable<NavigationRoute.LearnPhase> { screenManager.LearnPhase() }
    composableLearnLevel(navController, screenManager)
}

internal fun NavGraphBuilder.composableDecks(navHostController: NavHostController, screenManager: ScreenManager) {
    composable<NavigationRoute.Decks> {
        screenManager.Decks(
            onClickCreateNewDeck = {
                navHostController.navigateWithSettingBackStack(NavigationRoute.DeckSettings(id = DatabaseOperation.INSERT.toString()), NavigationRoute.Decks)
            },
            onClickDeck = {
                id ->
                navHostController.navigateWithSettingBackStack(NavigationRoute.DeckCards(id = id.toString()), NavigationRoute.Decks)
            },
        )
    }
}

internal fun NavGraphBuilder.composableDeckSettings(screenManager: ScreenManager) {
    composable<NavigationRoute.DeckSettings> {
        backStackEntry ->
        val id = backStackEntry.arguments?.getString("id").toString().toLong()
        screenManager.DeckSettings(id = id)
    }
}

internal fun NavGraphBuilder.composableDeckCards(navHostController: NavHostController, screenManager: ScreenManager) {
    composable<NavigationRoute.DeckCards> {
        backStackEntry ->
        val id = backStackEntry.arguments?.getString("id").toString().toLong()

        screenManager.DeckCards(
            id = id,
            onClickCreateNewCard = {
                navHostController.navigateWithSettingBackStack(NavigationRoute.CardFront(), backStackEntry.toRoute())
            },
            onClickCard =  {
                id ->
                navHostController.navigateWithSettingBackStack(NavigationRoute.CardFront(id = id.toString()), backStackEntry.toRoute())
            }
        )
    }
}

internal fun NavGraphBuilder.composableDeckLevels(navHostController: NavHostController, screenManager: ScreenManager) {
    composable<NavigationRoute.DeckLevels> {
        backStackEntry ->
        screenManager.DeckLevels(
            onClickCreateNewLevel = {
                navHostController.navigateWithSettingBackStack(NavigationRoute.Level(), backStackEntry.toRoute())
            },
            onClickLevel = {
                id ->
                navHostController.navigateWithSettingBackStack(NavigationRoute.Level(id = id.toString()), backStackEntry.toRoute())
            }
        )
    }
}

internal fun NavGraphBuilder.composableDeckLearn(navHostController: NavHostController, screenManager: ScreenManager) {
    composable<NavigationRoute.DeckLearn> {
        backStackEntry ->
        screenManager.DeckLearn(
            onClickLearn = {
                id ->
                if (id == LearnData.RANDOM_ID) {
                    navHostController.navigateWithSettingBackStack(NavigationRoute.LearnPhase(id = id.toString()), backStackEntry.toRoute())
                }
                else {
                    navHostController.navigateWithSettingBackStack(NavigationRoute.LearnLevel(id = id.toString()), backStackEntry.toRoute())
                }
            }
        )
    }
}

internal fun NavGraphBuilder.composableLearnLevel(navController: NavHostController, screenManager: ScreenManager) {
    composable<NavigationRoute.LearnLevel> {
        backStackEntry ->
        screenManager.LearnLevel(
            onClickLearn = {
                navController.navigateWithSettingBackStack(NavigationRoute.LearnPhase(), backStackEntry.toRoute())
            }
        )
    }
}
