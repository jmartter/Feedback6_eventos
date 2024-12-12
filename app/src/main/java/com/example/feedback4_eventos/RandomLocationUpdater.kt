package com.example.feedback4_eventos

import android.content.Context
import android.location.Location
import android.os.Handler
import android.os.Looper
import kotlin.random.Random

class RandomLocationUpdater(private val context: Context, private val callback: (Location) -> Unit) {
    private val handler = Handler(Looper.getMainLooper())
    private val updateInterval = 20000L // 20 seconds

    private val random = Random.Default

    private val updateLocationRunnable = object : Runnable {
        override fun run() {
            val location = generateRandomLocation()
            callback(location)
            handler.postDelayed(this, updateInterval)
        }
    }

    fun start() {
        handler.post(updateLocationRunnable)
    }

    fun stop() {
        handler.removeCallbacks(updateLocationRunnable)
    }

    private fun generateRandomLocation(): Location {
        val location = Location("RandomProvider")
        location.latitude = random.nextDouble(-90.0, 90.0)
        location.longitude = random.nextDouble(-180.0, 180.0)
        return location
    }
}