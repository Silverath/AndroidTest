package com.pabvazzam.test.repository

import android.content.SharedPreferences
import androidx.core.content.edit
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.pabvazzam.test.FAV_CHARACTER_SAVED_LIST
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

    override fun addFavCharacter(character: Character) {
        val characters = getFavCharacters()
        var listToSave = emptyList<Character>()
        if (characters.isEmpty())
            listToSave = listOf(character)
        else
            if (!characters.contains(character)) {
                listToSave = listOf(character).plus(characters)
            }
        val json = gson.toJson(listToSave)
        sharedPreferences.edit { putString(FAV_CHARACTER_SAVED_LIST, json) }
    }

    override fun getFavCharacters(): List<Character> {
        return gson.fromJson<List<Character>?>(
            sharedPreferences.getString(FAV_CHARACTER_SAVED_LIST, null),
            object : TypeToken<List<Character>?>() {}.type
        ) ?: emptyList()
    }
}