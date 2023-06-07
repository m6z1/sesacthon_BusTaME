package com.sesac.bustame

import android.content.Context
import com.google.gson.Gson

object RideBellDataManager {
    private const val PREFERENCES_KEY = "RideBellDataPref"
    private const val RIDE_BELL_DATA_KEY = "RideBellDataKey"

    fun saveRideBellData(rideBellData: RideBellData, context: Context) {
        val rideBellDataJson = Gson().toJson(rideBellData)
        val sharedPreferences = context.getSharedPreferences(PREFERENCES_KEY, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString(RIDE_BELL_DATA_KEY, rideBellDataJson)
        editor.apply()
    }

    fun getRideBellData(context: Context): RideBellData? {
        val sharedPreferences = context.getSharedPreferences(PREFERENCES_KEY, Context.MODE_PRIVATE)
        val rideBellDataJson = sharedPreferences.getString(RIDE_BELL_DATA_KEY, null)
        return Gson().fromJson(rideBellDataJson, RideBellData::class.java)
    }
}