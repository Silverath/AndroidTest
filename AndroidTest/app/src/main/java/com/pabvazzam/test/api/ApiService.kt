package com.pabvazzam.test.api

import com.pabvazzam.test.CHARACTER_END_POINT
import com.pabvazzam.test.data.ResponseApi
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET(CHARACTER_END_POINT)
    suspend fun getAllCharacters(
        @Query("page") page: Int

    ): Response<ResponseApi>
}