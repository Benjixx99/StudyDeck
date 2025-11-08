package bx.app.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

internal object ModifierManager {
    val paddingMostTopModifier = Modifier.Companion.padding(top = 140.dp)
    val paddingTopModifier = Modifier.Companion.padding(top = 25.dp)
    val paddingListItemRowModifier = Modifier.Companion.padding(6.dp)
    val listColumnModifier = Modifier.Companion.padding(bottom = 10.dp).padding(all = 5.dp).height(70.dp).fillMaxWidth()
}