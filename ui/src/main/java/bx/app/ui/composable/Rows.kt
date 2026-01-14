package bx.app.ui.composable

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

/**
 * This is the base row that is used by all other rows
 */
@Composable
internal fun BaseRow(modifier: Modifier = Modifier, content: @Composable RowScope.() -> Unit) {
    Row(
        modifier = modifier.fillMaxWidth().height(50.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        content()
    }
}

/**
 * This component displays a header text, a body text and a switch
 */
@Composable
internal fun SwitchTextRow(
    headerText: String,
    bodyText: String,
    modifier: Modifier = Modifier,
    isChecked: Boolean = false,
    onCheckedChange: (Boolean) -> Unit = {}
) {
    var isChecked by remember { mutableStateOf(isChecked) }
    BaseRow(modifier) {
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            MediumText(text = headerText)
            SmallText(text = bodyText)
        }
        Switch(
            checked = isChecked,
            onCheckedChange = {
                isChecked = !isChecked
                onCheckedChange(isChecked)
            }
        )
    }
}

// TODO: Need to find a better name for this function
/**
 * This component is clickable and opens a dialog to let the user select something from the dialog
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun ClickableRow(
    text: String,
    optionList: List<String>,
    modifier: Modifier = Modifier,
    selectedOptionId: Int = 0,
    onOptionChanged: (Int) -> Unit = {},
) {
    var openDialog by remember { mutableStateOf(false) }
    var selectedOptionText by remember { mutableStateOf(optionList[selectedOptionId]) }
    var selectedOptionId by remember { mutableIntStateOf(selectedOptionId) }

    BaseRow(modifier = modifier.combinedClickable(onClick = { openDialog = !openDialog })) {
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            MediumText(text)
            SmallText(selectedOptionText)
        }
    }
    if (openDialog) {
        RadioButtonGroupDialog(
            headerText = text,
            selectedOption = selectedOptionId,
            optionList = optionList,
            onDismissRequest = { openDialog = false },
            onSelectOption = {
                optionID, optionText ->
                selectedOptionText = optionText
                selectedOptionId = optionID
                openDialog = false
                onOptionChanged(optionID)
            }
        )
    }
}

/**
 * This component displays a text and a button that has the selected color
 */
@Composable
internal fun ColorPickerRow(
    modifier: Modifier = Modifier,
    color: Color = Color.Red,
    onColorChanged: (Long) -> Unit = {},
) {
    var openDialog by remember { mutableStateOf(false) }
    var deckColor by remember { mutableStateOf(color) }
    BaseRow(modifier) {
        MediumText(text = "Select color")
        Spacer(modifier = Modifier.weight(1f))
        Button(
            onClick = { openDialog = !openDialog },
            colors = ButtonColors(
                contentColor = deckColor,
                containerColor = deckColor,
                disabledContentColor = MaterialTheme.colorScheme.secondary,
                disabledContainerColor = MaterialTheme.colorScheme.onSecondary
            ),
            shape = CircleShape,
            modifier = Modifier.padding(end = 10.dp).size(40.dp)
        ) { /* Button without a text */ }
    }

    if (openDialog) {
        ColorPickerDialog(
            selectedColor = deckColor,
            onDismissRequest = { openDialog = false },
            onSaveColorClick = {
                color ->
                deckColor = color
                openDialog = false
                onColorChanged(color.toArgb().toLong())
            }
        )
    }
}

@Composable
fun HeaderWithTextFieldAndButtonRow(
    headerText: String,
    textFieldValue: String,
    textFieldReadOnly: Boolean,
    textFieldIsError: Boolean,
    textFieldKeyboardOptions: KeyboardOptions,
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit = {},
    buttonText: String = "Day",
    onButtonClick: () -> Unit = {},
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.surfaceContainerHigh,
                shape = RoundedCornerShape(12.dp)
            )
            .padding(10.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            LargeText(
                text = headerText,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom =12.dp)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                SingleLineTextFieldWithBorder(
                    value = textFieldValue,
                    readOnly = textFieldReadOnly,
                    isError = textFieldIsError,
                    keyboardOptions = textFieldKeyboardOptions,
                    onValueChange = onValueChange,
                    modifier = Modifier.width(100.dp)
                )

                Spacer(modifier = Modifier.width(12.dp))

                Button(
                    onClick = onButtonClick,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.tertiary,
                        contentColor = MaterialTheme.colorScheme.onTertiary
                    ),
                    shape = RoundedCornerShape(6.dp),
                    modifier = Modifier
                        .width(120.dp)
                        .height(52.dp)
                ) {
                    LargeText(buttonText)
                }
            }
        }
    }
}