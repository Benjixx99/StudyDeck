package bx.app.ui.screen

import android.content.Context
import android.media.MediaPlayer
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.navigation.NavHostController
import bx.app.core.hasInvalidId
import bx.app.core.hasValidId
import bx.app.core.maxLength
import bx.app.data.enums.CardSide
import bx.app.data.enums.CardSideType
import bx.app.presentation.viewmodel.CardWithSidesViewModel
import bx.app.presentation.viewmodel.TopBarViewModel
import bx.app.ui.ModifierManager
import bx.app.ui.composable.AudioPlayer
import bx.app.ui.composable.CardTypeSegmentedControl
import bx.app.ui.composable.ConfirmationDialog
import bx.app.ui.composable.LargeText
import bx.app.ui.composable.MultiLineTextField
import bx.app.ui.getFileNameFromUri

/**
 * This screen displays the content of a specific card
 */
@Composable
internal fun CardScreen(
    context: Context,
    cardWithSidesViewModel: CardWithSidesViewModel,
    navHostController: NavHostController,
    topBarViewModel: TopBarViewModel,
    cardSide: CardSide,
    deleteCard: (id: Long) -> Unit,
) {
    topBarViewModel.setTitle("Card")

    val card by cardWithSidesViewModel.cardViewModel.card.collectAsState()
    val activeId = if (cardSide == CardSide.FRONT) card.frontSideId else card.backSideId
    val activeType = if (cardSide == CardSide.FRONT) card.frontSideType else card.backSideType
    var showExitDialog by remember { mutableStateOf(false) }
    var cardSideType by remember { mutableStateOf<CardSideType>(activeType) }
    LaunchedEffect(activeType) { cardSideType = activeType }

    BackHandler {
        showExitDialog = (card.frontSideId.hasInvalidId() xor (card.backSideId.hasInvalidId()))
        if (!showExitDialog) navHostController.popBackStack()
    }

    ConfirmationDialog(
        isVisible = showExitDialog,
        message = (if (card.frontSideId.hasInvalidId()) "Front" else "Back")
                + " side of the card has no value! \nDiscard changes?",
        onConfirm = {
            deleteCard(card.id)
            showExitDialog = false
            navHostController.popBackStack()
        },
        onDismiss = { showExitDialog = false }
    )

    if (card.id.hasValidId()) {
        if (activeId.hasInvalidId()) {
            cardWithSidesViewModel.textSideViewModel.resetTextSide()
            cardWithSidesViewModel.audioSideViewModel.resetAudioSide()
        }
        when (activeType) {
            CardSideType.TEXT  -> cardWithSidesViewModel.audioSideViewModel.resetAudioSide()
            CardSideType.AUDIO -> cardWithSidesViewModel.textSideViewModel.resetTextSide()
        }
    }
    cardWithSidesViewModel.textSideViewModel.getTextSideByCard(card, cardSide)
    cardWithSidesViewModel.audioSideViewModel.getAudioSideByCard(card, cardSide)

    Column(
        modifier = ModifierManager.paddingMostTopModifier.fillMaxSize(),
    ) {
        CardTypeSegmentedControl(
            selectedCardSideType = cardSideType,
            onClick = {
                cardSideType = it
                cardWithSidesViewModel.changeCardSideType(it, cardSide)
            }
        )
        when (cardSideType) {
            CardSideType.TEXT -> TextSide(cardWithSidesViewModel, cardSide)
            CardSideType.AUDIO -> AudioSide(context, cardWithSidesViewModel, cardSide)
        }
    }
}

@Composable
private fun TextSide(
    cardWithSidesViewModel: CardWithSidesViewModel,
    cardSide: CardSide
) {
    val textSide by cardWithSidesViewModel.textSideViewModel.textSide.collectAsState()

    Column(
        modifier = ModifierManager.paddingTopModifier
            .fillMaxSize()
            .padding(start = 10.dp, end = 10.dp, bottom = 40.dp)
            .imePadding()
    ) {
        MultiLineTextField(
            modifier = Modifier.fillMaxSize(),
            labelText = "Text to learn",
            valueText = textSide.text,
            maxLines = 20,
            onValueChange = { cardWithSidesViewModel.changeText(it, cardSide) }
        )
    }
}

@Composable
private fun AudioSide(
    context: Context,
    cardWithSidesViewModel: CardWithSidesViewModel,
    cardSide: CardSide
) {
    val audioSide by cardWithSidesViewModel.audioSideViewModel.audioSide.collectAsState()
    var mediaPlayer = if (audioSide.path.isNotEmpty()) MediaPlayer.create(context, audioSide.path.toUri()) else null
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.OpenDocument()) {
        cardWithSidesViewModel.changeAudioData(
            path = it.toString(),
            fileName = context.getFileNameFromUri(it).maxLength(34).replace(".mp3", ""),
            cardSide = cardSide
        )
    }

    Row(
        modifier = ModifierManager.paddingTopModifier
            .fillMaxWidth()
            .height(80.dp)
            .padding(horizontal = 10.dp)
            .border(1.dp, MaterialTheme.colorScheme.outline)
            .background(MaterialTheme.colorScheme.primaryContainer)
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = {
                        if (audioSide.path.isEmpty()) { launcher.launch(arrayOf("audio/*")) }
                    }
                )
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        AudioPlayer(mediaPlayer)
        LargeText(
            text = if (audioSide.fileName.isNotEmpty()) audioSide.fileName else "Select file",
            maxLines = 1,
            modifier = Modifier.padding(start = 10.dp)
        )
        Spacer(Modifier.weight(1.0f))
        IconButton(
            onClick = {
                mediaPlayer?.stop()
                cardWithSidesViewModel.changeAudioData("", "", cardSide)
            }
        ) {
            Icon(
                imageVector = Icons.Default.Clear,
                contentDescription = "",
                Modifier.size(100.dp)
            )
        }
    }
}