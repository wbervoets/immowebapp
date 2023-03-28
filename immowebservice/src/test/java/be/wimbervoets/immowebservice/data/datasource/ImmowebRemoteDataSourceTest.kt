package be.wimbervoets.immowebservice.data.datasource

import android.content.Context
import be.wimbervoets.immowebservice.R
import be.wimbervoets.immowebservice.data.service.ListingService
import be.wimbervoets.immowebservice.data.service.dto.ListingsDTO
import be.wimbervoets.immowebservice.mothers.ListingMother.listing1
import be.wimbervoets.immowebservice.mothers.ListingMother.listing2
import be.wimbervoets.immowebservice.mothers.ListingMother.listingsDTO
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import retrofit2.Response
import java.util.*

@ExtendWith(value = [MockKExtension::class])
@DisplayName("Immoweb Remote data source test")
@OptIn(ExperimentalCoroutinesApi::class)
class ImmowebRemoteDataSourceTest {

    private lateinit var listingsRemoteDatasource: ListingsRemoteDatasource

    @MockK
    private lateinit var listingService: ListingService

    @MockK
    private lateinit var mockContext: Context

    @BeforeEach
    fun setup() {
        listingsRemoteDatasource = ListingsRemoteDatasource(
            listingService,
            FailureReasonMapper(mockContext)
        )
    }

    @Nested
    inner class SuccessApiResponses {

        @Test
        fun `given a success immoweb service API response, a Result Success object with current listings is returned`() = runTest {
            coEvery {
                listingService.listings()
            } returns Response.success(
                listingsDTO
            )

            val result: ResultOf<ListingsDTO> = listingsRemoteDatasource.listings()
            assertTrue(result is ResultOf.Success)

            val listings = (result as ResultOf.Success).data
            assertEquals(listingsDTO, listings)
            assertEquals(listing1, listings.items[0])
            assertEquals(listing2, listings.items[1])
        }
    }

    @Nested
    inner class FailedApiResponses {

        @BeforeEach
        fun setup() {
            every { mockContext.getString(R.string.generic_error_title) } returns "Generic error title"
            every { mockContext.getString(R.string.generic_error_description) } returns "Generic error description"
        }

        @Test
        fun `given an error 500 immoweb service response, a Result Error object is returned`() = runTest {
            coEvery { listingService.listings()  } returns Response.error(
                500,
                "error".toResponseBody()
            )

            val result: ResultOf<ListingsDTO> = listingsRemoteDatasource.listings()
            assertTrue(result is ResultOf.Failure)
            val failureResult = result as ResultOf.Failure
            assertEquals("Generic error title", failureResult.failureReason.title)
            assertEquals("Generic error description", failureResult.failureReason.description)
        }
    }
}
