package com.assignment.openinapp.utils

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun Context?.copyTextToClipboard(text: String) {
    val clipboard: ClipboardManager = this?.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clip = ClipData.newPlainText("Copied link", text)
    clipboard.setPrimaryClip(clip)
    showToast("Link copied")
}

fun String.formatTimestamp(): String {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
    val outputFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
    val date: Date = inputFormat.parse(this)
    return outputFormat.format(date)
}

fun Context?.openWhatsApp(phoneNumber: String) {
    try {
        val intent = this?.packageManager?.getLaunchIntentForPackage("com.whatsapp")
        intent?.let {
            val launcherIntent = Intent(Intent.ACTION_VIEW)
            launcherIntent.data = Uri.parse("https://wa.me/$phoneNumber")
            this?.startActivity(launcherIntent)
        } ?: run {
            showToast("WhatsApp is not installed on your device!")
        }
    } catch (e: PackageManager.NameNotFoundException) {
        showToast("WhatsApp is not installed on your device!")
    }
}

fun Context?.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Int?.toIntOrZero(): String {
    return this?.toString() ?: "0"
}

fun String?.toStringOrNA(): String {
    return when(this) {
        null -> "N/A"
        "" -> "N/A"
        else -> this
    }
}