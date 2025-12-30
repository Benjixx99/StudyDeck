package bx.app.ui.navigation

import android.content.Context
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import bx.app.data.enums.CardSide
import bx.app.presentation.data.IdValidator
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
    composableDeckSettings(screenManager)
    composableDeckCards(navController, screenManager)
    composableDeckLearn(navController, screenManager)
    composableDeckLevels(navController, screenManager)
    composableCardFront(screenManager)
    composableCardBack(screenManager)
    composableLevel(screenManager)
    composable<NavigationRoute.LearnPhase> { screenManager.LearnPhase() }
    composableLearnLevel(navController, screenManager)
}

internal fun NavGraphBuilder.composableDecks(navHostController: NavHostController, screenManager: ScreenManager) {
    composable<NavigationRoute.Decks> {
        screenManager.Decks(
            onClickCreateNewDeck = {
                navHostController.navigateWithSettingBackStack(
                    route = NavigationRoute.DeckSettings(id = IdValidator.INSERT.toString()),
                    backStackRoute = NavigationRoute.Decks
                )
            },
            onClickDeck = {
                id ->
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
                    route = NavigationRoute.CardFront(id = IdValidator.INSERT.toString()),
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
            onClickCreateNewLevel = {
                navHostController.navigateWithSettingBackStack(
                    route = NavigationRoute.Level(id = IdValidator.INSERT.toString()),
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
            onClickLearn = {
                id ->
                if (id == LearnData.RANDOM_ID) {
                    navHostController.navigateWithSettingBackStack(
                        route = NavigationRoute.LearnPhase(id = id.toString()),
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

internal fun NavGraphBuilder.composableCardFront(screenManager: ScreenManager) {
    composable<NavigationRoute.CardFront> {
        backStackEntry ->
         screenManager.Card(
            id = backStackEntry.arguments?.getString("id").toString().toLong(),
            cardSide = CardSide.FRONT
        )
    }
}

internal fun NavGraphBuilder.composableCardBack(screenManager: ScreenManager) {
    composable<NavigationRoute.CardBack> {
        backStackEntry ->
        screenManager.Card(
            id = backStackEntry.arguments?.getString("id").toString().toLong(),
            cardSide = CardSide.BACK
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