package bx.app.ui.composable

import android.annotation.SuppressLint
import android.media.MediaPlayer
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
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
private fun BaseTextField(
    modifier: Modifier = Modifier,
    valueText: String = "",
    labelText: String = "",
    placeholderText: String = "",
    readOnly: Boolean = false,
    isError: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    isSingleLine: Boolean = true,
    maxLines: Int = 1,
    onValueChange: (String) -> Unit = {},
) {
    TextField(
        value = valueText,
        onValueChange = { if (it.length < 100) onValueChange(it) },
        modifier = modifier.fillMaxWidth(),
        readOnly = readOnly,
        label = { Text(labelText) },
        placeholder = { Text(placeholderText) },
        isError = isError,
        keyboardOptions = keyboardOptions,
        singleLine = isSingleLine,
        maxLines = maxLines,
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = MaterialTheme.colorScheme.onPrimaryContainer,
            unfocusedIndicatorColor = MaterialTheme.colorScheme.onSecondaryContainer,
            focusedLabelColor = MaterialTheme.colorScheme.onPrimaryContainer,
            unfocusedLabelColor = MaterialTheme.colorScheme.onSecondaryContainer,
            focusedTextColor = MaterialTheme.colorScheme.onPrimaryContainer,
            unfocusedTextColor = MaterialTheme.colorScheme.onSecondaryContainer,
            focusedContainerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
            unfocusedContainerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
        ),
    )
}

/**
 * This composable displays a TextField with multiple lines
 */
@Composable
internal fun MultiLineTextField(
    modifier: Modifier = Modifier,
    valueText: String = "",
    labelText: String = "",
    placeholderText: String = "",
    readOnly: Boolean = false,
    isError: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    maxLines: Int = 4,
    onValueChange: (String) -> Unit = {},
) {
    BaseTextField(
        modifier = modifier.height(160.dp),
        valueText = valueText,
        labelText = labelText,
        placeholderText = placeholderText,
        readOnly = readOnly,
        isError = isError,
        keyboardOptions = keyboardOptions,
        isSingleLine = false,
        maxLines = maxLines,
        onValueChange = onValueChange,
    )
}

/**
 * This composable displays a TextField with one line
 */
@Composable
internal fun SingleLineTextField(
    modifier: Modifier = Modifier,
    valueText: String = "",
    labelText: String = "",
    placeholderText: String = "",
    readOnly: Boolean = false,
    isError: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    onValueChange: (String) -> Unit = {},
) {
    BaseTextField(
        modifier = modifier.height(60.dp),
        valueText = valueText,
        labelText = labelText,
        placeholderText = placeholderText,
        readOnly = readOnly,
        isError = isError,
        keyboardOptions = keyboardOptions,
        onValueChange = onValueChange,
    )
}

@Composable
private fun BaseText(text: String, style: TextStyle, modifier: Modifier, color: Color, textAlign: TextAlign?, maxLines: Int) {
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
                imageVector = Icons.Filled.MoreVert,
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
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 20.dp)
            .padding(horizontal = 10.dp)
    )
    return basicTextField
}

/**
 * This button is used to create a new item
 */
// TODO: Improve the draggable behaviour from this button
@Composable
internal fun ButtonInCorner(onClick: () -> Unit = {}) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
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
 * This composable is used to display actions that can be performed on selected items
 */
@Composable
internal fun SelectionBottomBar(onDelete: () -> Unit = {}) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
            .padding(bottom = 40.dp)
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .fillMaxWidth()
                .height(80.dp)
        ) {
            Button(
                onClick = onDelete,
                modifier = Modifier.fillMaxSize(),
                shape = RoundedCornerShape(20.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.surfaceContainerLow,
                    contentColor = MaterialTheme.colorScheme.onSurface
                ),
                contentPadding = PaddingValues(horizontal = 16.dp)
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        tint = MaterialTheme.colorScheme.onSurface,
                        contentDescription = "Delete"
                    )
                    LargeText(text = "Delete")
                }
            }
        }
    }
}

/**
 * This composable is used to display a bottom bar with a deletion button
 * and let the user confirm the decision
 */
@Composable
internal fun DeleteSelectionBar(
    selectedIds: MutableSet<Long>,
    deleteAction: (id: Long) -> Unit
) {
    var delete by remember { mutableStateOf(false) }
    SelectionBottomBar(onDelete = { delete = true })
    ConfirmationDialog(
        isVisible = delete,
        message = "Delete selected " + if (selectedIds.size == 1) "item?" else "items?",
        onConfirm = {
            selectedIds.forEach { deleteAction(it) }
            selectedIds.clear()
        },
        onDismiss = { delete = false },
        confirmText = "Delete",
        dismissText = "Cancel"
    )
}

/**
 * CardTypeSegmentedControl is a UI component for the card screen
 */
@Composable
internal fun CardTypeSegmentedControl(
    selectedCardSideType: CardSideType,
    modifier: Modifier = Modifier,
    onClick: (CardSideType) -> Unit = {}
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        SegmentedControlButton(
            text = CardTypeText.TEXT,
            selectedCardSideType = selectedCardSideType,
            onClick = { onClick(CardSideType.TEXT) }
        )
        SegmentedControlButton(
            text = CardTypeText.AUDIO,
            selectedCardSideType = selectedCardSideType,
            onClick =  { onClick(CardSideType.AUDIO) }
        )
    }
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
internal fun AudioPlayer(mediaPlayer: MediaPlayer?) {
    var mediaPlayerState by remember { mutableStateOf(true) }

    mediaPlayerState = true
    Column {
        Row {
            IconButton(
                onClick = {
                    if (mediaPlayer == null) return@IconButton
                    if (mediaPlayerState) mediaPlayer.start() else mediaPlayer.pause()
                    mediaPlayerState = !mediaPlayerState
                }
            ) {
                Icon(
                    painter =
                        if (mediaPlayerState)
                            painterResource(android.R.drawable.ic_media_play)
                        else
                            painterResource(android.R.drawable.ic_media_pause),
                    contentDescription = "",
                    Modifier.size(48.dp)
                )
            }
        }
    }
}
