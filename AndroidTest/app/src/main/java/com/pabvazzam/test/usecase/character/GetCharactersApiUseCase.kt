package com.pabvazzam.test.usecase.character

import com.pabvazzam.test.data.Character
import com.pabvazzam.test.repository.CharacterRepositoryImpl
import javax.inject.Inject

class GetCharactersApiUseCase @Inject constructor(
    private val characterRepositoryImpl: CharacterRepositoryImpl
) {

    suspend operator fun invoke(page: Int): Result<List<Character>?> {
        val characters = characterRepositoryImpl.getCharactersFromApi(page)
        return Result.success(characters)
    }
}