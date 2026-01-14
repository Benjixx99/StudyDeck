package bx.app.ui.navigation.data

import kotlinx.serialization.Serializable

@Serializable
sealed class NavigationRoute() {
    @Serializable abstract class WithId() : NavigationRoute() { abstract var id: String? }

    @Serializable object Decks : NavigationRoute()
    @Serializable data class DeckSettings(override var id: String? = null) : WithId()
    @Serializable data class DeckCards(override var id: String? = null) : WithId()
    @Serializable data class DeckLevels(override var id: String? = null) : WithId()
    @Serializable data class DeckLearn(override var id: String? = null) : WithId()
    @Serializable data class CardFront(override var id: String? = null) : WithId()
    @Serializable data class CardBack(override var id: String? = null) : WithId()
    @Serializable data class Level(override var id: String? = null) : WithId()
    @Serializable data class LearnLevel(override var id: String? = null) : WithId()
    @Serializable data class RandomLearningPhase(override var id: String? = null) : WithId()
    @Serializable data class LevelLearningPhase(override var id: String? = null) : WithId()

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
            else if (current.contains(RandomLearningPhase::class.simpleName.toString())) RandomLearningPhase()
            else if (current.contains(LevelLearningPhase::class.simpleName.toString())) LevelLearningPhase()
            else if (current.contains(Level::class.simpleName.toString())) Level()
            else Decks
        }
    }
}