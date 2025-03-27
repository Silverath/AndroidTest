package com.pabvazzam.test.services

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.pabvazzam.test.appDbName
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class SharedPreferencesManager @Inject constructor(
    @ApplicationContext private val context: Context
) {


    fun getSharedPreferences(): SharedPreferences {
        return context.getSharedPreferences(appDbName, MODE_PRIVATE)
    }
}