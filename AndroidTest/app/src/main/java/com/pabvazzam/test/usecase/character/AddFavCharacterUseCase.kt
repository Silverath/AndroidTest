package com.pabvazzam.test.usecase.character

import com.pabvazzam.test.data.Character
import com.pabvazzam.test.repository.CharacterRepositoryImpl
import javax.inject.Inject

class AddFavCharacterUseCase @Inject constructor(
    private val characterRepositoryImpl: CharacterRepositoryImpl
) {

    operator fun invoke(character: Character): Result<List<Character>> {
        return try {

            Result.success(characterRepositoryImpl.addFavCharacter(character))
        } catch (error: Error) {
            Result.failure(exception = NullPointerException())
        }
    }
}