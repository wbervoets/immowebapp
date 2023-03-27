package be.wimbervoets.immowebapp.ui.viewmodel

import be.wimbervoets.immowebapp.data.model.Listing
import be.wimbervoets.immowebapp.data.repository.ListingsRepository
import be.wimbervoets.immowebapp.extensions.LivedataArchTaskExecutorExtension
import be.wimbervoets.immowebapp.extensions.TestCoroutineExtension
import be.wimbervoets.immowebservice.data.datasource.FailureReason
import be.wimbervoets.immowebservice.data.datasource.ResultOf
import be.wimbervoets.immowebservice.data.service.dto.PropertyType
import com.jraska.livedata.test
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.parallel.Isolated
import java.util.*

@OptIn(ExperimentalCoroutinesApi::class)
@ExtendWith(value = [MockKExtension::class, LivedataArchTaskExecutorExtension::class, TestCoroutineExtension::class])
@DisplayName("Listings viewmodel test")
@Isolated // does not run correctly in parallel with other tests
class ListingViewModelTest {

    private lateinit var listingDetailViewModel: ListingDetailViewModel

    @MockK
    private lateinit var listingsRepository: ListingsRepository

    @BeforeEach
    fun setup() {
        listingDetailViewModel = ListingDetailViewModel(
            listingsRepository
        )
    }

    @Test
    fun `test Failure reason and loading state`() = runTest {
        val failureReason = FailureReason("ouch", "an error occured")
        coEvery { listingsRepository.listingDetail(1) } returns ResultOf.Failure(failureReason)

        listingDetailViewModel.fetchListing(1)

        listingDetailViewModel.loading.test().assertValue(true)
        listingDetailViewModel.failureReason.test().assertNullValue()

        advanceUntilIdle() // runs all coroutines
        coVerify(exactly = 1) { listingsRepository.listingDetail(1) }

        listingDetailViewModel.failureReason.test().assertValue(failureReason)
        listingDetailViewModel.loading.test().assertValue(false)

    }

    @Test
    fun `test Success and loading state`() = runTest {
        val listing = Listing(
            id = 2,
            surface = 500.0,
            price = 20000.0,
            currency = Currency.getInstance("EUR"),
            professional = "Luxe property investment group",
            propertyType = PropertyType.VILLA,
            city = "Leuven",
            listingImageURL = "https://v.seloger.com/s/crop/1024x590/visuels/2/a/l/s/2als8bgr8sd2vezcpsj988mse4olspi5rfzpadqok.jpg"
        )

        coEvery { listingsRepository.listingDetail(2) } returns ResultOf.Success(listing)
        listingDetailViewModel.fetchListing(2)

        listingDetailViewModel.loading.test().assertValue(true)
        listingDetailViewModel.failureReason.test().assertNullValue()

        advanceUntilIdle() // runs all coroutines
        coVerify(exactly = 1) { listingsRepository.listingDetail(2) }

        listingDetailViewModel.failureReason.test().assertNullValue()
        listingDetailViewModel.loading.test().assertValue(false)
        listingDetailViewModel.listingDetail.test().assertValue(listing)
    }
}
