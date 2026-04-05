package bx.app.studydeck

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import bx.app.data.local.DatabaseBuilder
import bx.app.data.repository.BackupRepository
import bx.app.presentation.viewmodel.BackupViewModel
import bx.app.presentation.viewmodel.HideNavigationBarViewModel
import bx.app.presentation.viewmodel.TopBarViewModel
import bx.app.ui.composable.BottomBarComponent
import bx.app.ui.composable.TopBarComponent
import bx.app.ui.navigation.data.NavigationRoute
import bx.app.ui.navigation.navHostDestinations
import bx.app.ui.theme.StudyDeckTheme
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class MainActivity : ComponentActivity() {
    private val topBarViewModel = TopBarViewModel()
    private val hideNavigationBarViewModel = HideNavigationBarViewModel()
    private val backupViewModel by lazy {
        BackupViewModel(BackupRepository(DatabaseBuilder.getInstance(applicationContext)))
    }

    private val createDocumentLauncher = registerForActivityResult(
        ActivityResultContracts.CreateDocument("application/json")
    ) {
        uri -> uri?.let { backupViewModel.exportAll(it, application.contentResolver, applicationContext) }
    }

    private val openDocumentLauncher = registerForActivityResult(
        ActivityResultContracts.OpenDocument()
    ) {
        uri -> uri?.let { backupViewModel.importAll(it, application.contentResolver, applicationContext) }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            StudyDeckTheme {
                StudyDeck()
            }
        }
    }

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @Composable
    fun StudyDeck() {
        val navHostController = rememberNavController()
        val title by topBarViewModel.title.collectAsStateWithLifecycle()
        val hide by hideNavigationBarViewModel.hide.collectAsStateWithLifecycle()

        Scaffold(
            topBar = {
                TopBarComponent.Manager(
                    title = title,
                    navHostController = navHostController,
                    onImportClick = { openDocumentLauncher.launch(arrayOf("application/json")) },
                    onExportClick = { createDocumentLauncher.launch("study_deck_export_${LocalDateTime.now().format(
                        DateTimeFormatter.ofPattern("yyyy-MM-dd_hh:mm:ss"))}.json") },
                    isDebug = BuildConfig.DEBUG
                )
            },
            bottomBar = { BottomBarComponent.Manager(hide, navHostController) }
        ) {
            innerPadding ->
            NavHost(
                navController = navHostController,
                startDestination = NavigationRoute.Decks,
            ) {
                navHostDestinations(
                    navHostController = navHostController,
                    context = applicationContext,
                    topBarViewModel = topBarViewModel,
                    hideNavigationBarViewModel = hideNavigationBarViewModel
                )
            }
        }
    }
}