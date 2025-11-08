package bx.app.studydeck

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import bx.app.presentation.viewmodel.TopBarViewModel
import bx.app.ui.composable.BottomBarComponent
import bx.app.ui.composable.TopBarComponent
import bx.app.ui.navigation.data.NavigationRoutes
import bx.app.ui.navigation.navHostDestinations
import bx.app.ui.theme.StudyDeckTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            StudyDeckTheme {
                StudyDeck(applicationContext)
            }
        }
    }
}

@SuppressLint("ViewModelConstructorInComposable", "UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun StudyDeck(context: Context) {
    val navHostController = rememberNavController()
    val topBarViewModel = TopBarViewModel()
    val title by topBarViewModel.title.collectAsState()

    Scaffold(
        topBar = { TopBarComponent.Manager(title, navHostController) },
        bottomBar = { BottomBarComponent.Manager(navHostController) }
    ) {
        innerPadding ->
            NavHost(
                navController = navHostController,
                startDestination = NavigationRoutes.DECKS,
            ) {
                navHostDestinations(navHostController, context, topBarViewModel)
            }
    }
}
