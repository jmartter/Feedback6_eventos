package com.example.feedback4_eventos.Widget

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.RemoteViews
import com.example.feedback4_eventos.Base_datos.UserManager
import com.example.feedback4_eventos.R

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
        private const val TAG = "NovelaWidget"

        internal fun updateAppWidget(context: Context, appWidgetManager: AppWidgetManager, appWidgetId: Int) {
            val sharedPreferences = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
            val username = sharedPreferences.getString("username", null)
            val password = sharedPreferences.getString("password", null)

            if (username != null && password != null) {
                val views = RemoteViews(context.packageName, R.layout.novela_widget)

                Log.d(TAG, "Fetching user data for widget update")
                UserManager.getUser(username, password) { user ->
                    if (user != null) {
                        Log.d(TAG, "User data fetched successfully")
                        val favoriteNovelas = user.novelas.filter { it.isFavorite }
                        val novelasText = favoriteNovelas.joinToString("\n") { novela -> novela.titulo }
                        views.setTextViewText(R.id.novela_title, "Novelas Favoritas de $username")
                        views.setTextViewText(R.id.novela_list, novelasText)
                        appWidgetManager.updateAppWidget(appWidgetId, views)
                        Log.d(TAG, "Widget updated with user data")
                    } else {
                        Log.e(TAG, "Failed to fetch user data")
                    }
                }
            } else {
                Log.e(TAG, "No user credentials found")
            }
        }
    }
}