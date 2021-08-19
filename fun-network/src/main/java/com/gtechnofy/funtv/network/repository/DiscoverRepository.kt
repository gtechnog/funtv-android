package com.gtechnofy.funtv.network.repository

import com.gtechnofy.funtv.network.NetworkManager
import com.gtechnofy.funtv.network.model.DiscoverMovies
import retrofit2.Response

interface DiscoverRepository {
    suspend fun discoverMovies() : Response<DiscoverMovies>
}

class DiscoverRepositoryImpl : DiscoverRepository {
    override suspend fun discoverMovies(): Response<DiscoverMovies> {
        return NetworkManager.getNetworkClient()
            .create(DiscoverNetworkRequest::class.java)
            .discoverMovies()
    }
}