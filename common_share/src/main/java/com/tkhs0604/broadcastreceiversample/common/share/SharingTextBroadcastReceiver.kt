package com.tkhs0604.broadcastreceiversample.common.share

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import android.widget.Toast
import androidx.core.app.PendingIntentCompat
import androidx.core.content.ContextCompat

class SharingTextBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val selectedComponent = if (VERSION.SDK_INT >= VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(Intent.EXTRA_CHOSEN_COMPONENT, ComponentName::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra(Intent.EXTRA_CHOSEN_COMPONENT)
        }

        Toast.makeText(context, "selected: ${selectedComponent?.packageName}", Toast.LENGTH_SHORT).show()
    }

    fun register(context: Context) {
        ContextCompat.registerReceiver(
            context,
            this,
            IntentFilter(SHARE_ACTION),
            ContextCompat.RECEIVER_NOT_EXPORTED,
        )
    }

    fun unregister(context: Context) {
        context.unregisterReceiver(this)
    }

    companion object {
        private const val SHARE_ACTION = "com.tkhs0604.broadcastreceiversample.SHARE_ACTION"

        fun createChooserIntent(context: Context, text: String): Intent {
            val pendingIntent = PendingIntentCompat.getBroadcast(
                context,
                0,
                Intent(SHARE_ACTION).apply {
                    `package` = context.packageName
                },
                PendingIntent.FLAG_UPDATE_CURRENT,
                true,
            )

            val sendIntent = Intent(Intent.ACTION_SEND).apply {
                putExtra(Intent.EXTRA_TEXT, text)
                type = "text/plain"
            }

            return Intent.createChooser(
                sendIntent,
                null,
                pendingIntent?.intentSender
            )
        }
    }
}