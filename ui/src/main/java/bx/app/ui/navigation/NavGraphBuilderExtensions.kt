package bx.app.ui.navigation

import android.content.Context
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import bx.app.core.IdPolicy
import bx.app.data.enums.CardSide
import bx.app.presentation.viewmodel.HideNavigationBarViewModel
import bx.app.presentation.viewmodel.TopBarViewModel
import bx.app.ui.data.LearnData
import bx.app.ui.ScreenManager
import bx.app.ui.navigation.data.NavigationRoute

/**
 * This extension function of [NavGraphBuilder] is the main navigation function of this app
 * and contains all the destinations
 *
 * @param navHostController Is the main navigation controller
 * @param context Is the application context
 */
fun NavGraphBuilder.navHostDestinations(
    navHostController: NavHostController,
    context: Context,
    topBarViewModel: TopBarViewModel,
    hideNavigationBarViewModel: HideNavigationBarViewModel,
) {
    val screenManager = ScreenManager(
        context = context,
        navHostController = navHostController,
        topBarViewModel = topBarViewModel,
        hideNavigationBarViewModel = hideNavigationBarViewModel
    )

    composableDecks(navHostController, screenManager, hideNavigationBarViewModel)
    composableDeckSettings(screenManager)
    composableDeckCards(navHostController, screenManager)
    composableDeckLevels(navHostController, screenManager)
    composableDeckLearn(navHostController, screenManager)
    composableCardFront(screenManager)
    composableCardBack(screenManager)
    composableLevel(screenManager)
    composableLearnLevel(navHostController, screenManager)
    composableRandomLearningPhase(screenManager)
}

internal fun NavGraphBuilder.composableDecks(
    navHostController: NavHostController,
    screenManager: ScreenManager,
    hideNavigationBarViewModel: HideNavigationBarViewModel
) {
    composable<NavigationRoute.Decks> {
        screenManager.Decks(
            onClickCreateNewDeck = {
                hideNavigationBarViewModel.setHide(true)
                navHostController.navigateWithSettingBackStack(
                    route = NavigationRoute.DeckSettings(id = IdPolicy.getInsertId().toString()),
                    backStackRoute = NavigationRoute.Decks
                )
            },
            onClickDeck = {
                id ->
                hideNavigationBarViewModel.setHide(false)
                navHostController.navigateWithSettingBackStack(
                    route = NavigationRoute.DeckCards(id = id.toString()),
                    backStackRoute = NavigationRoute.Decks
                )
            },
        )
    }
}

internal fun NavGraphBuilder.composableDeckSettings(screenManager: ScreenManager) {
    composable<NavigationRoute.DeckSettings> {
        backStackEntry ->
        screenManager.DeckSettings(id = backStackEntry.arguments?.getString("id").toString().toLong())
    }
}

internal fun NavGraphBuilder.composableDeckCards(navHostController: NavHostController, screenManager: ScreenManager) {
    composable<NavigationRoute.DeckCards> {
        backStackEntry ->
        screenManager.DeckCards(
            id = backStackEntry.arguments?.getString("id").toString().toLong(),
            onClickCreateNewCard = {
                navHostController.navigateWithSettingBackStack(
                    route = NavigationRoute.CardFront(id = IdPolicy.getInsertId().toString()),
                    backStackRoute = backStackEntry.toRoute()
                )
            },
            onClickCard =  {
                id ->
                navHostController.navigateWithSettingBackStack(
                    route = NavigationRoute.CardFront(id = id.toString()),
                    backStackRoute = backStackEntry.toRoute()
                )
            }
        )
    }
}

internal fun NavGraphBuilder.composableDeckLevels(navHostController: NavHostController, screenManager: ScreenManager) {
    composable<NavigationRoute.DeckLevels> {
        backStackEntry ->
        screenManager.DeckLevels(
            id = backStackEntry.arguments?.getString("id").toString().toLong(),
            onClickCreateNewLevel = {
                navHostController.navigateWithSettingBackStack(
                    route = NavigationRoute.Level(id = IdPolicy.getInsertId().toString()),
                    backStackRoute = backStackEntry.toRoute()
                )
            },
            onClickLevel = {
                id ->
                navHostController.navigateWithSettingBackStack(
                    route = NavigationRoute.Level(id = id.toString()),
                    backStackRoute = backStackEntry.toRoute()
                )
            }
        )
    }
}

internal fun NavGraphBuilder.composableDeckLearn(navHostController: NavHostController, screenManager: ScreenManager) {
    composable<NavigationRoute.DeckLearn> {
        backStackEntry ->
        screenManager.DeckLearn(
            id = backStackEntry.arguments?.getString("id").toString().toLong(),
            onClickLearn = {
                id ->
                if (id == LearnData.RANDOM_ID) {
                    navHostController.navigateWithSettingBackStack(
                        route = NavigationRoute.RandomLearningPhase(id = id.toString()),
                        backStackRoute = backStackEntry.toRoute()
                    )
                }
                else {
                    navHostController.navigateWithSettingBackStack(
                        route = NavigationRoute.LearnLevel(id = id.toString()),
                        backStackRoute = backStackEntry.toRoute()
                    )
                }
            }
        )
    }
}

internal fun NavGraphBuilder.composableLearnLevel(navHostController: NavHostController, screenManager: ScreenManager) {
    composable<NavigationRoute.LearnLevel> {
        backStackEntry ->
        screenManager.LearnLevel(
            onClickLearn = {
                navHostController.navigateWithSettingBackStack(
                    route = NavigationRoute.LearnPhase(),
                    backStackRoute = backStackEntry.toRoute()
                )
            }
        )
    }
}

internal fun NavGraphBuilder.composableRandomLearningPhase(screenManager: ScreenManager) {
    composable<NavigationRoute.RandomLearningPhase> {
        backStackEntry ->
        screenManager.RandomLearningPhase(
            id = backStackEntry.arguments?.getString("id").toString().toLong()
        )
    }
}

internal fun NavGraphBuilder.composableCardFront(screenManager: ScreenManager) {
    composable<NavigationRoute.CardFront> {
        backStackEntry ->
         screenManager.Card(
            id = backStackEntry.arguments?.getString("id").toString().toLong(),
            cardSide = CardSide.FRONT,
        )
    }
}

internal fun NavGraphBuilder.composableCardBack(screenManager: ScreenManager) {
    composable<NavigationRoute.CardBack> {
        backStackEntry ->
        screenManager.Card(
            id = backStackEntry.arguments?.getString("id").toString().toLong(),
            cardSide = CardSide.BACK,
        )
    }
}

internal fun NavGraphBuilder.composableLevel(screenManager: ScreenManager) {
    composable<NavigationRoute.Level> {
        backStackEntry ->
        screenManager.Level(
            id = backStackEntry.arguments?.getString("id").toString().toLong()
        )
    }
}