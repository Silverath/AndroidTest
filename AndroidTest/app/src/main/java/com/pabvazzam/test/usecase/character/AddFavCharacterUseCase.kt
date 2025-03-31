package com.pabvazzam.test.usecase.character

import com.pabvazzam.test.data.Character
import com.pabvazzam.test.repository.CharacterRepositoryImpl
import javax.inject.Inject

class AddFavCharacterUseCase @Inject constructor(
    private val characterRepositoryImpl: CharacterRepositoryImpl
) {

    operator fun invoke(character: Character): Result<Boolean> {
        try {
            characterRepositoryImpl.addFavCharacter(character)
            return Result.success(true)
        } catch (error: Error) {
            return Result.failure(exception = NullPointerException())
        }
    }
}