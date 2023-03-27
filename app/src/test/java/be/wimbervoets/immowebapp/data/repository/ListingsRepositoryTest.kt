package be.wimbervoets.immowebapp.data.repository

import be.wimbervoets.immowebapp.data.model.Listing
import be.wimbervoets.immowebservice.data.datasource.ListingsRemoteDatasource
import be.wimbervoets.immowebservice.data.datasource.ResultOf
import be.wimbervoets.immowebservice.data.service.dto.ListingDTO
import be.wimbervoets.immowebservice.data.service.dto.PropertyType
import be.wimbervoets.junit5.extensions.JsonParserExtension
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.util.*

@OptIn(ExperimentalCoroutinesApi::class)
@ExtendWith(value = [MockKExtension::class, JsonParserExtension::class])
@DisplayName("Listings Repository test")
class ListingsRepositoryTest {

    private lateinit var listingsRepository: ListingsRepository

    @MockK
    private lateinit var listingsRemoteDatasource: ListingsRemoteDatasource

    @BeforeEach
    fun setup() {
        listingsRepository = ListingsRepository(
            listingsRemoteDatasource
        )
    }

    @Test
    fun `when datasource returns a listing detail dto the repository returns a listing detail`() = runTest {
        coEvery {
            listingsRemoteDatasource.listingDetail(2)
        } returns ResultOf.Success(
            ListingDTO(
                id = 2,
                surface = 500.0,
                _price = 20000.0,
                professional = "Luxe property investment group",
                propertyType = PropertyType.VILLA,
                offerType = 1,
                city = "Leuven",
                imageURL = "https://v.seloger.com/s/crop/1024x590/visuels/2/a/l/s/2als8bgr8sd2vezcpsj988mse4olspi5rfzpadqok.jpg"
            )
        )

        val result: ResultOf<Listing> = listingsRepository.listingDetail(2)
        assertTrue(result is ResultOf.Success)

        (result as ResultOf.Success).let {
            val listing = result.data
            assertEquals(2, listing.id)
            assertEquals(500.0, listing.surface)
            assertEquals(20000.0, listing.price)
            assertEquals("Luxe property investment group", listing.professional)
            assertEquals(PropertyType.VILLA, listing.propertyType)
            assertEquals("Leuven", listing.city)
            assertEquals("https://v.seloger.com/s/crop/1024x590/visuels/2/a/l/s/2als8bgr8sd2vezcpsj988mse4olspi5rfzpadqok.jpg", listing.listingImageURL)

        }
    }

    // we could also add a test for ResultOf.Error and for repository.listings()
}
