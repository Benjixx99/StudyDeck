package bx.app.data.backup

import kotlinx.serialization.json.Json
import kotlinx.serialization.encodeToString

object JsonManager {
    private val json = Json {
        prettyPrint = true
        ignoreUnknownKeys = true
    }

    fun export(backupBundle: BackupBundle): String {
        return json.encodeToString<BackupBundle>(backupBundle)
    }

    fun import(jsonString: String): BackupBundle {
        return json.decodeFromString(jsonString)
    }
}