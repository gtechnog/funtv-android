package com.gtechnofy.funtv.usecase

import com.gtechnofy.funtv.network.*
import com.gtechnofy.funtv.network.model.DiscoverMovies
import com.gtechnofy.funtv.network.repository.DiscoverRepository
import com.gtechnofy.funtv.network.repository.DiscoverRepositoryImpl
import com.gtechnofy.funtv.network.util.Result
import com.gtechnofy.funtv.network.util.handleRetrofitResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DiscoverMovieUseCase(
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val repository: DiscoverRepository = DiscoverRepositoryImpl()
) : UseCase<DiscoverMovies> {

    override suspend fun execute(): Result<DiscoverMovies, Failure> {
        return withContext(ioDispatcher) {
            handleRetrofitResponse(
                request = {
                    repository.discoverMovies()
                },
                onSuccess = { response ->
                    response.body()?.let {
                        Result.success(it)
                    } ?: Result.failure(GenericNetworkConnectionFailure)
                },
                onFailure = {
                    ServiceFailure(it.errorBody().toString())
                }
            )
        }
    }
}