package be.wimbervoets.immowebservice.data.datasource

import retrofit2.Response

abstract class BaseDataSource(private val failureReasonMapper: FailureReasonMapper) {
    protected suspend fun <T> mapToResultOf(methodCall: suspend () -> Response<T>): ResultOf<T> {
        // method call is a coroutine function defined in a retrofit interface, returning a retrofit response
        // here we map this to a ResultOf object containing either Data or error
        try {
            val response: Response<T> = methodCall()
            return if (response.isSuccessful) {
                val data: T? = response.body()
                if (data != null) {
                    ResultOf.Success(data)
                } else {
                    @Suppress("UNCHECKED_CAST")
                    ResultOf.Success(Unit as T)
                }
            } else {
                ResultOf.Failure(failureReasonMapper.mapToFailureReason(response.code()))
            }
        } catch (@Suppress("TooGenericExceptionCaught") t: Throwable) {
            return ResultOf.Failure(failureReasonMapper.mapToFailureReason(t))
        }
    }
}
