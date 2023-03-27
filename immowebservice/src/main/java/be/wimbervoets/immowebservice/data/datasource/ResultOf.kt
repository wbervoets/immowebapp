package be.wimbervoets.immowebservice.data.datasource

sealed class ResultOf<out T> {
    class Failure<out T>(val failureReason: FailureReason) : ResultOf<T>()
    class Success<out T>(val data: T) : ResultOf<T>()
}