package com.example.feedback4_eventos

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.RemoteViews
import com.example.feedback4_eventos.Base_datos.UserManager

class NovelaWidget : AppWidgetProvider() {
    private val handler = Handler(Looper.getMainLooper())
    private val updateInterval = 3000L // 3 seconds

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
        scheduleNextUpdate(context, appWidgetManager, appWidgetIds)
    }

    private fun scheduleNextUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        handler.postDelayed({
            onUpdate(context, appWidgetManager, appWidgetIds)
        }, updateInterval)
    }

    companion object {
        private const val USERNAME = "1234"
        private const val PASSWORD = "1234"
        private const val TAG = "NovelaWidget"

        internal fun updateAppWidget(context: Context, appWidgetManager: AppWidgetManager, appWidgetId: Int) {
            val views = RemoteViews(context.packageName, R.layout.novela_widget)

            Log.d(TAG, "Fetching user data for widget update")
            UserManager.getUser(USERNAME, PASSWORD) { user ->
                if (user != null) {
                    Log.d(TAG, "User data fetched successfully")
                    val novelas = user.novelas.joinToString("\n") { novela -> novela.titulo }
                    views.setTextViewText(R.id.novela_title, "Novelas de $USERNAME")
                    views.setTextViewText(R.id.novela_list, novelas)
                    appWidgetManager.updateAppWidget(appWidgetId, views)
                    Log.d(TAG, "Widget updated with user data")
                } else {
                    Log.e(TAG, "Failed to fetch user data")
                }
            }
        }
    }
}