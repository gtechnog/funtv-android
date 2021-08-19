package com.gtechnofy.funtv.network.util

import com.gtechnofy.funtv.network.ExceptionFailure
import com.gtechnofy.funtv.network.Failure
import com.gtechnofy.funtv.network.NetworkConnectionFailure
import retrofit2.HttpException
import retrofit2.Response
import java.util.concurrent.TimeoutException

/**
 * Provides common handling to make a retrofit request and convert it to a [Result]
 *
 * @param request A lambda that makes the request, returning a [Response]
 * @param onSuccess Converts from the [Response] type to the return data type
 * @param onFailure Converts from the [Response] type to the [Failure] type
 */
inline fun <In, Out> handleRetrofitResponse(
    request: () -> Response<In>,
    onSuccess: (Response<In>) -> Result<Out, Failure>,
    onFailure: (Response<In>) -> Failure
): Result<Out, Failure> {
    return try {
        val response = request()
        when {
            response.isSuccessful -> onSuccess(response)
            else -> Result.failure(onFailure(response))
        }
    } catch (throwable: Throwable) {
        Result.failure(
            when (throwable) {
                is HttpException, is TimeoutException -> NetworkConnectionFailure(throwable)
                else -> ExceptionFailure(throwable)
            }
        )
    }
}

sealed class Result<out T, out E> {
    companion object {
        fun <T, E> success(value: T): Result<T, E> = Success(value)
        fun <T, E> failure(error: E): Result<T, E> = Failure(error)
    }

    /**
     * Returns `true` if this instance represents a successful outcome.
     * In this case [isFailure] returns `false`.
     */
    abstract val isSuccess: Boolean

    /**
     * Returns `true` if this instance represents a failed outcome.
     * In this case [isSuccess] returns `false`.
     */
    abstract val isFailure: Boolean

    /**
     * Returns the encapsulated value if this instance represents [success][Result.isSuccess] or `null`
     * if it is [failure][Result.isFailure].
     *
     * This function is a shorthand for `getOrElse { null }` (see [getOrElse]) or
     * `fold(onSuccess = { it }, onFailure = { null })` (see [fold]).
     */
    abstract fun getOrNull(): T?

    /**
     * Returns the encapsulated error if this instance represents [failure][isFailure] or `null`
     * if it is [success][isSuccess].
     *
     * This function is a shorthand for `fold(onSuccess = { null }, onFailure = { it })` (see [fold]).
     */
    abstract fun errorOrNull(): E?

    private class Success<T, E>(private val value: T) : Result<T, E>() {
        override fun equals(other: Any?): Boolean =
            this === other || other is Success<*, *> && this.value == other.value

        override fun hashCode(): Int = 7 + 31 * value.hashCode()

        override fun toString(): String = "Success($value)"

        override val isSuccess: Boolean get() = true
        override val isFailure: Boolean get() = false

        override fun getOrNull(): T? = value
        override fun errorOrNull(): E? = null
    }

    private class Failure<T, E>(private val error: E) : Result<T, E>() {
        override fun equals(other: Any?): Boolean =
            this === other || other is Failure<*, *> && this.error == other.error

        override fun hashCode(): Int = 11 + 31 * error.hashCode()

        override fun toString(): String = "Failure($error)"

        override val isSuccess: Boolean get() = false
        override val isFailure: Boolean get() = true

        override fun getOrNull(): T? = null
        override fun errorOrNull(): E? = error
    }
}

/**
 * Performs the given [action] on the encapsulated value if this instance represents [success][Result.isSuccess].
 * Returns the original `Result` unchanged.
 */
inline fun <T, E> Result<T, E>.onSuccess(action: (value: T) -> Unit): Result<T, E> {
    @Suppress("UNCHECKED_CAST")
    if (isSuccess) action(getOrNull() as T)
    return this
}

/**
 * Performs the given [action] on the encapsulated error if this instance represents [failure][Result.isFailure].
 * Returns the original `Result` unchanged.
 */
inline fun <T, E> Result<T, E>.onFailure(action: (error: E) -> Unit): Result<T, E> {
    @Suppress("UNCHECKED_CAST")
    if (isFailure) action(errorOrNull() as E)
    return this
}