package bx.app.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import bx.app.core.hasValidId
import bx.app.data.enums.CardSide
import bx.app.data.model.CardModel
import bx.app.data.model.TextSideModel
import bx.app.data.repository.TextSideRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TextSideViewModel(private val repo: TextSideRepository) : ViewModel() {
    private val _textSide = MutableStateFlow<TextSideModel>(getInitialTextSide())

    val textSide: StateFlow<TextSideModel> = _textSide

    fun getTextSideById(id: Long) = viewModelScope.launch { _textSide.value = repo.getById(id) }

    fun getTextSideByCard(card: CardModel, cardSide: CardSide) = viewModelScope.launch {
        val id =
            if (cardSide.isFront())
                card.frontSideId.takeIf { card.frontSideType.isText() }
            else
                card.backSideId.takeIf { card.backSideType.isText() }

        if (id != null && id.hasValidId()) getTextSideById(id)
    }

    fun insertTextSide(textSide: TextSideModel) = viewModelScope.launch { repo.insert(textSide) }

    fun resetTextSide() { _textSide.value = getInitialTextSide() }
    fun resetTextSide(textSide: TextSideModel) {
        resetTextSide()
        _textSide.value = textSide
    }

    private fun getInitialTextSide(): TextSideModel {
        return TextSideModel(
            text = "",
            side = CardSide.FRONT,
            cardId = 0
        )
    }
}