package be.wimbervoets.immowebservice.data.datasource

import android.content.Context
import be.wimbervoets.immowebservice.R
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CancellationException
import retrofit2.HttpException
import timber.log.Timber
import java.net.UnknownHostException
import javax.inject.Inject

class FailureReasonMapper @Inject constructor(@ApplicationContext private val context: Context) {
    fun mapToFailureReason(throwable: Throwable): FailureReason {
        Timber.d(throwable)
        return when (throwable) {
            is HttpException -> mapToFailureReason(throwable.code())
            is UnknownHostException -> FailureReason(
                context.getString(R.string.no_internet_title),
                context.getString(R.string.no_internet_description)
            )
            is CancellationException -> FailureReason(
                "Request canceled",
                throwable.message ?: "The request was canceled"
            )
            else -> FailureReason(
                context.getString(R.string.generic_error_title),
                context.getString(R.string.generic_error_description)
            )
        }
    }

    fun mapToFailureReason(statusCode: Int): FailureReason {
        return when (statusCode) {
            404 -> FailureReason(
                context.getString(R.string.wrong_listing_title),
                context.getString(R.string.wrong_listing_description)
            )
            else -> FailureReason(
                context.getString(R.string.generic_error_title),
                context.getString(R.string.generic_error_description)
            )
        }
    }
}
