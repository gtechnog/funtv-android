package com.gtechnofy.funtv.network.repository

import com.gtechnofy.funtv.network.Constants
import com.gtechnofy.funtv.network.model.DiscoverMovies
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface DiscoverNetworkRequest {

    @GET(Constants.DISCOVER_MOVIE)
    suspend fun discoverMovies( @Query("api_key") key: String = Constants.API_KEY) : Response<DiscoverMovies>
}