// ThemeManager.kt
package com.example.feedback4_eventos.Tema

import android.content.Context
import android.content.SharedPreferences
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

object ThemeManager {
    private const val PREFS_NAME = "theme_prefs"
    private const val KEY_IS_DARK_THEME = "is_dark_theme"

    private val _isDarkTheme = MutableStateFlow(false)
    val isDarkTheme: StateFlow<Boolean> get() = _isDarkTheme

    private fun getPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    fun loadTheme(context: Context) {
        _isDarkTheme.value = getPreferences(context).getBoolean(KEY_IS_DARK_THEME, false)
    }

    fun setDarkTheme(context: Context, isDarkTheme: Boolean) {
        getPreferences(context).edit().putBoolean(KEY_IS_DARK_THEME, isDarkTheme).apply()
        _isDarkTheme.value = isDarkTheme
    }
}