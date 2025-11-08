package bx.app.data.mock.item

class DeckItem(id: Int, name: String, description: String, counter: Int, isFavorite: Boolean) : BaseItem(id, name, description) {
    val counter: Int = 0
    val isFavorite: Boolean = false
}