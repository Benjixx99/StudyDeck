package bx.app.ui.navigation

import android.content.Context
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import bx.app.presentation.viewmodel.TopBarViewModel
import bx.app.ui.data.LearnData
import bx.app.ui.ScreenManager
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
    composable<NavigationRoute.DeckSettings> { screenManager.DeckSettings() }
    composableDeckCards(navController, screenManager)
    composableDeckLearn(navController, screenManager)
    composableDeckLevels(navController, screenManager)
    composable<NavigationRoute.CardFront> { screenManager.Card() }
    composable<NavigationRoute.CardBack> { screenManager.Card() }
    composable<NavigationRoute.Level> { screenManager.Level() }
    composable<NavigationRoute.LearnPhase> { screenManager.LearnPhase() }
    composableLearnLevel(navController, screenManager)
}

internal fun NavGraphBuilder.composableDecks(navController: NavHostController, screenManager: ScreenManager) {
    composable<NavigationRoute.Decks> {
        screenManager.Decks(
            onClickCreateNewDeck = {
                navController.navigateWithSettingBackStack(NavigationRoute.DeckSettings(), NavigationRoute.Decks)
            },
            onClickDeck = {
                id ->
                navController.navigateWithSettingBackStack(NavigationRoute.DeckCards(id = id.toString()), NavigationRoute.Decks)
            },
        )
    }
}

internal fun NavGraphBuilder.composableDeckCards(navController: NavHostController, screenManager: ScreenManager) {
    composable<NavigationRoute.DeckCards> {
        backStackEntry ->
        screenManager.DeckCards(
            onClickCreateNewCard = {
                navController.navigateWithSettingBackStack(NavigationRoute.CardFront(), backStackEntry.toRoute())
            },
            onClickCard =  {
                id ->
                navController.navigateWithSettingBackStack(NavigationRoute.CardFront(id = id.toString()), backStackEntry.toRoute())
            }
        )
    }
}

internal fun NavGraphBuilder.composableDeckLevels(navController: NavHostController, screenManager: ScreenManager) {
    composable<NavigationRoute.DeckLevels> {
        backStackEntry ->
        screenManager.DeckLevels(
            onClickCreateNewLevel = {
                navController.navigateWithSettingBackStack(NavigationRoute.Level(), backStackEntry.toRoute())
            },
            onClickLevel = {
                id ->
                navController.navigateWithSettingBackStack(NavigationRoute.Level(id = id.toString()), backStackEntry.toRoute())
            }
        )
    }
}

internal fun NavGraphBuilder.composableDeckLearn(navController: NavHostController, screenManager: ScreenManager) {
    composable<NavigationRoute.DeckLearn> {
        backStackEntry ->
        screenManager.DeckLearn(
            onClickLearn = {
                id ->
                if (id == LearnData.RANDOM_ID) {
                    navController.navigateWithSettingBackStack(NavigationRoute.LearnPhase(id = id.toString()), backStackEntry.toRoute())
                }
                else {
                    navController.navigateWithSettingBackStack(NavigationRoute.LearnLevel(id = id.toString()), backStackEntry.toRoute())
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
