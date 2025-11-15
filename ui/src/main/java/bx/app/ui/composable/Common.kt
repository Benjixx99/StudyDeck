package bx.app.ui.composable

import android.annotation.SuppressLint
import android.media.MediaPlayer
import android.net.Uri
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import bx.app.data.enums.CardSideType
import bx.app.ui.data.CardTypeText
import kotlin.math.roundToInt
import bx.app.ui.R

@Composable
internal fun BaseTextField(
    modifier: Modifier = Modifier,
    valueText: String = "",
    labelText: String = "",
    placeholderText: String = "",
    isSingleLine: Boolean = true,
    maxLines: Int = 1,
) {
    var basicTextField by remember { mutableStateOf(valueText) }

    TextField(
        value = basicTextField,
        onValueChange = { if (it.length < 100) basicTextField = it },
        label = { Text(labelText) },
        placeholder = { Text(placeholderText) },
        colors = TextFieldDefaults.colors(
            unfocusedTextColor = MaterialTheme.colorScheme.primary,
            focusedTextColor = if (basicTextField.contains("5")) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary
        ),
        singleLine = isSingleLine,
        maxLines = maxLines,
        modifier = modifier.fillMaxWidth(),
    )
}

@Composable
internal fun MultiLineTextField(
    modifier: Modifier = Modifier,
    valueText: String = "",
    labelText: String = "",
    placeholderText: String = "",
    maxLines: Int = 4,
) {
    BaseTextField(
        modifier = modifier.height(160.dp),
        valueText = valueText,
        labelText = labelText,
        placeholderText = placeholderText,
        isSingleLine = false,
        maxLines = maxLines,
    )
}

/**
 * SingleLineTextField
 */
@Composable
internal fun SingleLineTextField(
    modifier: Modifier = Modifier,
    valueText: String = "",
    labelText: String = "",
) {
    BaseTextField(
        modifier = modifier.height(60.dp),
        valueText = valueText,
        labelText = labelText,
        placeholderText = "",
    )
}

@Composable
internal fun BaseText(text: String, style: TextStyle, modifier: Modifier, color: Color, textAlign: TextAlign?, maxLines: Int) {
    Text(
        text = text,
        style = style,
        color = color,
        modifier = modifier,
        textAlign = textAlign,
        maxLines = maxLines
    )
}

@Composable
internal fun MediumText(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = Color.Unspecified,
    textAlign: TextAlign? = null,
    maxLines: Int = Int.MAX_VALUE) {
    BaseText(
        text = text,
        style = MaterialTheme.typography.bodyMedium,
        modifier = modifier,
        color = color,
        textAlign = textAlign,
        maxLines = maxLines)
}

@Composable
internal fun SmallText(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = Color.Unspecified,
    textAlign: TextAlign? = null,
    maxLines: Int = Int.MAX_VALUE) {
    BaseText(
        text = text,
        style = MaterialTheme.typography.bodySmall,
        modifier = modifier,
        color = color,
        textAlign = textAlign,
        maxLines = maxLines)
}

@Composable
internal fun LargeText(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = Color.Unspecified,
    textAlign: TextAlign? = null,
    maxLines: Int = Int.MAX_VALUE) {
    BaseText(
        text = text,
        style = MaterialTheme.typography.bodyLarge,
        modifier = modifier,
        color = color,
        textAlign = textAlign,
        maxLines = maxLines)
}

/**
 * The MainDropdownMenu lets users import and export data
 */
@Composable
internal fun MainDropdownMenu() {
    var expanded by remember { mutableStateOf(false) }
    Box {
        IconButton(onClick = { expanded = !expanded }) {
            Icon(
                imageVector = Icons.Filled.Menu,
                contentDescription = ""
            )
        }
        IconButton(onClick = { expanded = !expanded }) {
            Icon(Icons.Default.MoreVert, contentDescription = "More options")
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(
                text = { Text("Import") },
                onClick = { }
            )
            DropdownMenuItem(
                text = { Text("Export") },
                onClick = { }
            )
        }
    }
}

/**
 * This component is used to search a specific text
 */
@SuppressLint("ComposableNaming")
@Composable
internal fun SearchBar(modifier: Modifier = Modifier): String {
    var basicTextField by remember { mutableStateOf("") }

    TextField(
        value = basicTextField,
        onValueChange = {
            basicTextField = it
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                tint = MaterialTheme.colorScheme.onTertiaryContainer,
                contentDescription = "Search icon",
            )
        },
        maxLines = 1,
        placeholder = {
            Text(stringResource(R.string.placeholder_search))
        },
        colors = TextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.tertiaryContainer,
            unfocusedContainerColor = MaterialTheme.colorScheme.tertiaryContainer
        ),
        modifier = modifier.fillMaxWidth().heightIn(min = 20.dp).padding(horizontal = 10.dp)
    )
    return basicTextField
}

/**
 * This button is used to create a new element
 */
// TODO: Improve the draggable behaviour from this button
@Composable
internal fun ButtonInCorner(onClick: () -> Unit = {}) {
    Box(
        modifier = Modifier.fillMaxSize().padding(10.dp)
    ) {
        var offsetX by remember { mutableFloatStateOf(0f) }
        Button(
            onClick = onClick,
            shape = CircleShape,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(bottom = 85.dp, end = 20.dp)
                .size(50.dp)
                .offset { IntOffset(offsetX.roundToInt(), 0) }
                .draggable(
                    orientation = Orientation.Horizontal,
                    state = rememberDraggableState { delta -> offsetX += delta },
                ),
        ) {
            Text(text = "+", textAlign = TextAlign.Center, fontSize = 20.sp)
        }
    }
}

/**
 * CardTypeSegmentedControl is a UI component for the card screen
 */
@Composable
internal fun cardTypeSegmentedControl(modifier: Modifier = Modifier): CardSideType {
    var cardSideType by remember { mutableStateOf<CardSideType>(CardSideType.TEXT) }
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        SegmentedControlButton(text = CardTypeText.TEXT, selectedCardSideType = cardSideType, onClick = { cardSideType = CardSideType.TEXT })
        SegmentedControlButton(text = CardTypeText.AUDIO, selectedCardSideType = cardSideType, onClick =  { cardSideType = CardSideType.AUDIO })
    }
    return cardSideType
}

/**
 * SegmentedControlButton is a UI component used by CardTypeSegmentedControl
 */
@Composable
internal fun SegmentedControlButton(
    text: String,
    selectedCardSideType: CardSideType,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        modifier = modifier.width(180.dp),
        onClick = onClick,
        shape = RectangleShape,
        colors = ButtonColors(
            contentColor = MaterialTheme.colorScheme.primary,
            containerColor = if ((selectedCardSideType == CardSideType.TEXT && text == CardTypeText.TEXT)
                || (selectedCardSideType == CardSideType.AUDIO && text == CardTypeText.AUDIO)) MaterialTheme.colorScheme.onPrimary
            else MaterialTheme.colorScheme.onSecondary,
            disabledContainerColor = Color.Transparent,
            disabledContentColor = Color.Transparent
        )
    ) {
        Text(text = text)
    }
}

/**
 * AudioPlayer is a UI component to let the user play and stop a audio file
 */
@Composable
internal fun AudioPlayer(mediaPlayer: MediaPlayer?, audioFile: Uri?) {
    var mediaPlayerState by remember { mutableStateOf<Boolean>(true) }

    mediaPlayerState = audioFile != null
    Column {
        Row {
            IconButton(
                onClick = {
                    if (mediaPlayer != null) { if (mediaPlayerState) mediaPlayer.start() else mediaPlayer.pause() }
                    mediaPlayerState = !mediaPlayerState
                },
                enabled = (audioFile != null)
            ) {
                Icon(
                    painter =
                        if (mediaPlayerState)
                            painterResource(id = android.R.drawable.ic_media_play)
                        else
                            painterResource(id = android.R.drawable.ic_media_pause),
                    contentDescription = "",
                    Modifier.size(100.dp)
                )
            }
        }
    }
}
