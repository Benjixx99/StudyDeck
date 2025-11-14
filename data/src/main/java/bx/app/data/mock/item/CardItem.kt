package bx.app.data.mock.item

import bx.app.data.enums.CardType

class CardItem(id: Int, name: String, description: String, val frontSide: CardSide, val backSide: CardSide) : BaseItem(id, name, description) {
//    val counter: Int = 0 // How often did one learn this card
//    val correctCounter: Int = 0 // How often did one know it
//    val falseCounter: Int = 0 // How often didn't one know it

    // TODO: Probably I need a function that gets the desired value from every item type for the search filter
    /**
     * getValuesFromCard extract the right values from the front and back side
     */
    fun getValuesFromCard(): Pair<String, String> {
        var front = if (this.frontSide is TextCardSide) this.frontSide.text else if (this.frontSide is AudioCardSide) this.frontSide.path else ""
        var back = if (this.backSide is TextCardSide) this.backSide.text else if (this.backSide is AudioCardSide) this.backSide.path else ""
        return Pair<String, String>(front, back)
    }
}

open class CardSide(val type: CardType) {

    // TODO: Not sure if I wanna do it like that! Just a quick solution
    /**
     * evaluateCardSideType is just a help function
     */
    fun evaluateCardSideType(): String {
        return if (this.type == CardType.Text) {
            this as TextCardSide
            this.text
        }
        else {
            this as AudioCardSide
            this.path
        }
    }
}

class TextCardSide(type: CardType, val text: String) : CardSide(type) {
}

class AudioCardSide(type: CardType, val path: String) : CardSide(type) {
}

