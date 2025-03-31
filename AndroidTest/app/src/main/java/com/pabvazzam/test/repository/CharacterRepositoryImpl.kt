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

    override fun addFavCharacter(character: Character): List<Character> {
        val characters = getFavCharacters().toMutableList()
        characters.add(character)
        val listToSave = characters.toList()
        val json = gson.toJson(listToSave)
        sharedPreferences.edit { putString(FAV_CHARACTER_SAVED_LIST, json) }
        return listToSave
    }

    override fun getFavCharacters(): List<Character> {
        return gson.fromJson<List<Character>?>(
            sharedPreferences.getString(FAV_CHARACTER_SAVED_LIST, null),
            object : TypeToken<List<Character>?>() {}.type
        ) ?: emptyList()
    }

    override fun removeFavCharacter(character: Character): List<Character> {
        val characters = getFavCharacters().toMutableList()
        characters.remove(character)
        val listToSave = characters.toList()
        val json = gson.toJson(listToSave)
        sharedPreferences.edit { putString(FAV_CHARACTER_SAVED_LIST, json) }
        return listToSave
    }
}