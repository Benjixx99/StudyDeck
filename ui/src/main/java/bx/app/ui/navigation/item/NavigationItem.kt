package bx.app.ui.navigation.item

import androidx.compose.ui.graphics.vector.ImageVector

data class NavigationItem(
    val text: String,
    val route: String,
    val backStackRoute: String,
    val icon: ImageVector? = null,
)