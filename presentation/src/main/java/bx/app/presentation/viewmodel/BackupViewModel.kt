package bx.app.presentation.viewmodel

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import bx.app.data.backup.JsonManager
import bx.app.data.repository.BackupRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.SerializationException
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

class BackupViewModel(private val repo: BackupRepository) : ViewModel() {

    fun exportAll(uri: Uri, contentResolver: ContentResolver, context: Context) = viewModelScope.launch {
        try {
            val backupBundle = withContext(Dispatchers.IO) { repo.exportAll() }
            val jsonString = withContext(Dispatchers.Default) { JsonManager.export(backupBundle) }

            withContext(Dispatchers.IO) {
                contentResolver.openOutputStream(uri)?.use {
                    output -> output.write(jsonString.toByteArray(Charsets.UTF_8))
                }
            }
        }
        catch (e: Exception) {
            when (e) {
                is IOException -> {
                    Toast.makeText(context, "Export failed!", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    fun importAll(uri: Uri, contentResolver: ContentResolver, context: Context) = viewModelScope.launch {
        try {
            withContext(Dispatchers.IO) {
                contentResolver.openInputStream(uri)?.use { input ->
                    val jsonString = BufferedReader(InputStreamReader(input)).use { it.readText() }
                    repo.importAll(JsonManager.import(jsonString))
                }
            }
        }
        catch (e: Exception) {
            when (e) {
                is IOException -> {
                    Toast.makeText(context, "Open file failed!", Toast.LENGTH_LONG).show()
                }
                is SerializationException -> {
                    Toast.makeText(context, "Wrong JSON schema!", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}