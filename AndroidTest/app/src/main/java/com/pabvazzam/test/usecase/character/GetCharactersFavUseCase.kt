package com.pabvazzam.test.usecase.character

import com.pabvazzam.test.data.Character
import com.pabvazzam.test.repository.CharacterRepositoryImpl
import javax.inject.Inject

class GetCharactersFavUseCase @Inject constructor(
    private val characterRepositoryImpl: CharacterRepositoryImpl
) {

    operator fun invoke(): Result<List<Character>> {
        val characters = characterRepositoryImpl.getFavCharacters()
        return Result.success(characters)
    }
}