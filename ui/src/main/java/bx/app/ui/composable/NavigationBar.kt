package bx.app.ui.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import bx.app.ui.navigation.item.NavigationItem

private val barHeight = 80.dp

@Composable
internal fun NavigationBar(
    items: List<NavigationItem>,
    onItemSelected: (NavigationItem) -> Unit,
    currentItem: NavigationItem
) {
    Surface(
        Modifier
            .height(barHeight)
            .fillMaxWidth()
    ) {
        NavigationRow(items, onItemSelected, currentItem)
    }
}

@Composable
internal fun NavigationRow(
    items: List<NavigationItem>,
    onItemSelected: (NavigationItem) -> Unit,
    currentItem: NavigationItem
) {
    Row(
        modifier = Modifier.background(MaterialTheme.colorScheme.secondary).selectableGroup(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        items.forEach {
            item ->
            NavigationTab(
                item = item,
                onSelected = { onItemSelected(item) },
                selected = (currentItem == item)
            )
        }
    }
}

@Composable
internal fun NavigationTab(
    item: NavigationItem,
    onSelected: () -> Unit,
    selected: Boolean
) {
    Column(
        modifier = Modifier
            .padding(top = 15.dp, bottom = 30.dp, start = 10.dp, end = 10.dp)
            //.height(barHeight)
            .selectable(
                selected = selected,
                onClick = onSelected
            )
            .clip(RoundedCornerShape(5.dp))
            .background(color = if (selected) MaterialTheme.colorScheme.onSecondary else Color.Transparent)
            .size(90.dp, 60.dp)
            .padding(all = 5.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        MediumText(item.text.uppercase(), textAlign = TextAlign.Center)
    }
}