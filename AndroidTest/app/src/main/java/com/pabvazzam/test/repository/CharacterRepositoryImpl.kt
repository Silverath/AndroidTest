package com.pabvazzam.test.repository

import android.content.SharedPreferences
import com.google.gson.Gson
import com.pabvazzam.test.api.ApiService
import com.pabvazzam.test.data.Character
import javax.inject.Inject

class CharacterRepositoryImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences,
    private val gson: Gson,
    private val apiService: ApiService
) : CharacterRepository {

    override suspend fun getCharactersFromApi(page: Int): List<Character>? {
        return apiService.getAllCharacters(page).body()?.results
    }
}