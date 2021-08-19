package com.gtechnofy.funtv.usecase

import com.gtechnofy.funtv.network.Failure
import com.gtechnofy.funtv.network.util.Result

interface UseCase<T> {
    suspend fun execute(): Result<T, Failure>
}





