package com.pabvazzam.test.repository

import com.pabvazzam.test.data.Character

interface CharacterRepository {

    suspend fun getCharactersFromApi(page: Int): List<Character>?
}