package bx.app.ui.navigation

import android.content.Context
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import bx.app.presentation.viewmodel.TopBarViewModel
import bx.app.ui.navigation.data.NavigationRoutes
import bx.app.ui.data.LearnData
import bx.app.ui.screen.ScreenManager

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
    val screenManager = ScreenManager(Modifier, context, topBarViewModel)

    composableDecks(navController, screenManager)
    composable(route = NavigationRoutes.DECK_SETTINGS) { screenManager.DeckSettings() }
    composableDeckCards(navController, screenManager)
    composableDeckLearn(navController, screenManager)
    composableDeckLevel(navController, screenManager)
    composable(route = NavigationRoutes.CARD_FRONT) { screenManager.Card() }
    composable(route = NavigationRoutes.CARD_BACK) { screenManager.Card() }
    composable(route = NavigationRoutes.LEVEL) { screenManager.Level() }
    composable(route = NavigationRoutes.LEARN_PHASE) { screenManager.LearnPhase() }
    composableLearnLevel(navController, screenManager)
}

internal fun NavGraphBuilder.composableDecks(navController: NavHostController, screenManager: ScreenManager) {
    composable(route = NavigationRoutes.DECKS) {
        screenManager.Decks(
            onClickCreateNewDeck = {
                navController.navigateWithSettingBackStack(NavigationRoutes.DECK_SETTINGS, NavigationRoutes.DECKS)
            },
            onClickDeck = {
                navController.navigateWithSettingBackStack(NavigationRoutes.DECK_CARDS, NavigationRoutes.DECKS)
            },
        )
    }
}

internal fun NavGraphBuilder.composableDeckCards(navController: NavHostController, screenManager: ScreenManager) {
    composable(route = NavigationRoutes.DECK_CARDS) {
        screenManager.DeckCards(
            onClickCreateNewCard = {
                navController.navigateWithSettingBackStack(NavigationRoutes.CARD_FRONT, NavigationRoutes.DECK_CARDS)
            }
        )
    }
}

internal fun NavGraphBuilder.composableDeckLearn(navController: NavHostController, screenManager: ScreenManager) {
    composable(route = NavigationRoutes.DECK_LEARN) {
        screenManager.DeckLearn(
            onClickLearn = {
                    id ->
                if (id == LearnData.RANDOM_ID) {
                    navController.navigateWithSettingBackStack(NavigationRoutes.LEARN_PHASE, NavigationRoutes.DECK_LEARN)
                }
                else {
                    navController.navigateWithSettingBackStack(NavigationRoutes.LEARN_LEVEL, NavigationRoutes.DECK_LEARN)
                }
            }
        )
    }
}

internal fun NavGraphBuilder.composableDeckLevel(navController: NavHostController, screenManager: ScreenManager) {
    composable(route = NavigationRoutes.DECK_LEVELS) {
        screenManager.DeckLevels(
            onClickCreateNewLevel = {
                navController.navigateWithSettingBackStack(NavigationRoutes.LEVEL, NavigationRoutes.DECK_LEVELS)
            },
            onClickLevel = {
                navController.navigateWithSettingBackStack(NavigationRoutes.LEVEL, NavigationRoutes.DECK_LEVELS)
            }
        )
    }
}

internal fun NavGraphBuilder.composableLearnLevel(navController: NavHostController, screenManager: ScreenManager) {
    composable(route = NavigationRoutes.LEARN_LEVEL) {
        screenManager.LearnLevel(
            onClickLearn = {
                navController.navigateWithSettingBackStack(NavigationRoutes.LEARN_PHASE, NavigationRoutes.LEARN_LEVEL)
            }
        )
    }
}
