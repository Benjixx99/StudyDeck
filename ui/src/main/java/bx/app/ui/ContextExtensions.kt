package bx.app.ui

import android.content.ContentResolver
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.OpenableColumns

// TODO: Maybe add this file to a new module called ui-android-lib
/**
 * This extension function of [Context] delivers the file name from a passed [Uri]
 */
internal fun Context.getFileNameFromUri(uri: Uri?): String {
    if (uri?.scheme == ContentResolver.SCHEME_CONTENT) {
        var cursor: Cursor? = null
        return try {
            cursor = contentResolver.query(uri, arrayOf(OpenableColumns.DISPLAY_NAME), null, null, null)
            if (cursor != null && cursor.moveToFirst()) {
                val index = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                if (index != -1) cursor.getString(index) else ""
            }
            else ""
        }
        finally {
            cursor?.close()
        }
    }
    return (if (uri?.path?.isEmpty() == true) uri.path?.substringAfterLast('/') else "").toString()
}