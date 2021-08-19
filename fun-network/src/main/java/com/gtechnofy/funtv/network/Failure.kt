package com.gtechnofy.funtv.network

interface Failure

/**
 * [NetworkConnectionFailure] used to represent network timeouts or other failure to use the network
 *
 * @param errorData any error data in the response
 */
open class NetworkConnectionFailure<ErrorData>(val errorData: ErrorData? = null) : Failure

/**
 * Subclass of [NetworkConnectionFailure] with no errorData
 */
object GenericNetworkConnectionFailure : NetworkConnectionFailure<Unit>(null)

/**
 * [ServiceFailure] represents an error on the service-side of a network request. Typical usage is whenever an HTTP 4XX
 * or 5XX status code is encountered
 *
 * @param errorData any error data in the response
 */
open class ServiceFailure<ErrorData>(val errorData: ErrorData? = null) : Failure

/**
 * Subclass of [ServiceFailure] with no errorData
 */
object GenericServiceFailure : ServiceFailure<Unit>(null)

/**
 * [ExceptionFailure] wraps an exception that resulted in a [Failure]
 *
 * @property e - the exception
 */
open class ExceptionFailure(val e: Throwable) : Failure {
    override fun toString(): String {
        return e.toString()
    }
}