package bx.app.ui.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.RadioButton
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

/**
 * A dialog with a radio button group from that a user can select an option
 */
@Composable
internal fun RadioButtonGroupDialog(
    headerText: String,
    selectedOption: Int,
    optionList: List<String>,
    onDismissRequest: () -> Unit,
    onSelectOption: (Int, String) -> Unit,
    modifier: Modifier = Modifier
) {
    Dialog(onDismissRequest = onDismissRequest) {
        Card {
            val options = optionList
            val selectedOption by remember { mutableStateOf(options[selectedOption]) }

            Column(modifier = modifier.selectableGroup(), horizontalAlignment = Alignment.CenterHorizontally) {
                LargeText(text = headerText, modifier = Modifier.padding(vertical = 10.dp))
                options.forEach { text ->
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                            .selectable(
                                selected = (text == selectedOption),
                                onClick = { onSelectOption(optionList.indexOf(text), text) },
                                role = Role.RadioButton
                            )
                            .padding(horizontal = 20.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(selected = (text == selectedOption), onClick = null)
                        MediumText(text = text, modifier = Modifier.padding(start = 16.dp))
                    }
                }
            }
        }
    }
}

/**
 * A dialog with a color picker from that the user can select a color
 * components/dialogs.kt
 */
@Composable
internal fun ColorPickerDialog(
    selectedColor: Color,
    onDismissRequest: () -> Unit,
    onSaveColorClick: (Color) -> Unit,
    modifier: Modifier = Modifier
) {
    Dialog(onDismissRequest = { onDismissRequest() }) {
        Card(
            modifier = modifier.fillMaxWidth().height(400.dp),
            shape = RoundedCornerShape(16.dp),
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                MediumText(
                    text = "Color picker",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(30.dp)
                        .wrapContentSize(Alignment.Center)
                        .padding(top = 5.dp),
                    textAlign = TextAlign.Center,
                )
                var color = ColorPicker(
                    modifier = Modifier.size(250.dp).padding(top = 20.dp),
                    initialColor = selectedColor
                )
                MediumText(
                    text = Integer.toHexString(color.toArgb()),
                    modifier = Modifier.padding(top = 5.dp)
                )

                Row {
                    TextButton(onClick = onDismissRequest) { MediumText("Cancel") }
                    Spacer(Modifier.weight(1.0f))
                    TextButton(onClick = { onSaveColorClick(color) }) { MediumText("Save") }
                }
            }
        }
    }
}

/**
 * A dialog to let the user confirm something
 */
@Composable
fun ConfirmationDialog(
    isVisible: Boolean,
    message: String,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
    confirmText: String = "Yes",
    dismissText: String = "No"
) {
    if (!isVisible) return

    AlertDialog(
        onDismissRequest = onDismiss,
        title = null,
        text = { LargeText(message) },
        confirmButton = {
            TextButton(onClick = { onConfirm() }) {
                MediumText(confirmText)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                MediumText(dismissText)
            }
        }
    )
}
