package bx.app.ui.navigation.data

import kotlinx.serialization.Serializable

@Serializable
sealed class NavigationRoute() {
    @Serializable object Decks : NavigationRoute()
    @Serializable data class DeckSettings(val id: String? = null) : NavigationRoute()
    @Serializable data class DeckCards(val id: String? = null) : NavigationRoute()
    @Serializable data class DeckLevels(val id: String? = null) : NavigationRoute()
    @Serializable data class DeckLearn(val id: String? = null) : NavigationRoute()
    @Serializable data class CardFront(val id: String? = null) : NavigationRoute()
    @Serializable data class CardBack(val id: String? = null) : NavigationRoute()
    @Serializable data class Level(val id: String? = null) : NavigationRoute()
    @Serializable data class LearnLevel(val id: String? = null) : NavigationRoute()
    @Serializable data class LearnPhase(val id: String? = null) : NavigationRoute()

    fun sameRoute(other: NavigationRoute) = this::class.simpleName == other::class.simpleName

    companion object {
        fun getCurrentNavigationRoute(current: String?): NavigationRoute {
            if (current == null) return Decks

            return if (current.contains(Decks::class.simpleName.toString())) Decks
            else if (current.contains(CardBack::class.simpleName.toString())) CardBack()
            else if (current.contains(CardFront::class.simpleName.toString())) CardFront()
            else if (current.contains(DeckCards::class.simpleName.toString())) DeckCards()
            else if (current.contains(DeckLearn::class.simpleName.toString())) DeckLearn()
            else if (current.contains(DeckLevels::class.simpleName.toString())) DeckLevels()
            else if (current.contains(DeckSettings::class.simpleName.toString())) DeckSettings()
            else if (current.contains(LearnLevel::class.simpleName.toString())) LearnLevel()
            else if (current.contains(LearnPhase::class.simpleName.toString())) LearnPhase()
            else if (current.contains(Level::class.simpleName.toString())) Level()
            else Decks
        }
    }
}