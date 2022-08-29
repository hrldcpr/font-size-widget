package com.example.fontsizewidget

import android.app.Service
import android.content.Intent
import android.net.Uri
import android.os.IBinder
import android.provider.Settings
import android.util.Log

private const val SCALE = 1.3f

class FontSizeService : Service() {

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        if (Settings.System.canWrite(this)) {
            val scale = Settings.System.getFloat(contentResolver, Settings.System.FONT_SCALE, 1.0f)
            Log.d("FontSizeActivity", "scale=$scale")
            if (scale == SCALE) {
                Log.d("FontSizeActivity", "resetting scale to 1.0")
                Settings.System.putFloat(contentResolver, Settings.System.FONT_SCALE, 1.0f)
            } else {
                Log.d("FontSizeActivity", "setting scale to $SCALE")
                Settings.System.putFloat(contentResolver, Settings.System.FONT_SCALE, SCALE)
            }
        } else {
            Log.d("FontSizeActivity", "can't write")
            startActivity(
                Intent(
                    Settings.ACTION_MANAGE_WRITE_SETTINGS,
                    Uri.parse("package:$packageName")
                ).apply {
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                })
        }
        return START_NOT_STICKY
    }
}
