package be.wimbervoets.immowebservice.data.service

import be.wimbervoets.junit5.extensions.JsonFileSource
import be.wimbervoets.junit5.extensions.JsonParserExtension
import be.wimbervoets.junit5.extensions.MockWebServerExtension
import be.wimbervoets.immowebservice.data.service.di.retrofit.RetrofitModule
import be.wimbervoets.immowebservice.data.service.di.retrofit.RetrofitSerializationConverterFactoryModule
import be.wimbervoets.immowebservice.data.service.dto.ListingsDTO
import be.wimbervoets.immowebservice.data.service.dto.PropertyType
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.fail
import org.junit.jupiter.params.ParameterizedTest
import java.util.*

@ExtendWith(
    value = [
        MockWebServerExtension::class,
        JsonParserExtension::class
    ]
)
@DisplayName("Immoweb Service")
@OptIn(ExperimentalCoroutinesApi::class)
class ListingServiceTest {

    private lateinit var listingService: ListingService
    private val EUR = Currency.getInstance("EUR")

    @BeforeEach
    fun createRetrofitInterfaceForOpenWeatherService(mockWebServerBaseURL: HttpUrl, json: Json) {
        val converterFactory = RetrofitSerializationConverterFactoryModule.jsonConverterFactory(
            json
        )
        val retrofit = RetrofitModule.retrofit(
            okHttpClient = OkHttpClient(),
            httpConverterFactory = converterFactory,
            listingAPIBaseUrl = mockWebServerBaseURL.toString()
        )

        listingService = retrofit.create(ListingService::class.java)
    }

    @ParameterizedTest
    @JsonFileSource(jsonFile = "/json/listing_1.json")
    fun `returns a listing detail object for id 1 after querying immoweb API endpoint`(
        listing1DetailJson: JsonObject,
        mockWebServer: MockWebServer
    ) = runTest {
        mockWebServer.enqueue(MockResponse().setBody(listing1DetailJson.toString()))


        val listingDetailResponse = listingService.listingDetail(1)
        assertTrue(listingDetailResponse.isSuccessful)

        val listingDetail = listingDetailResponse.body() ?: fail { "listing_1 json could not be serialized" }

        assertEquals(1, listingDetail.id)
        assertEquals("Villers-sur-Mer", listingDetail.city)
        assertEquals(4, listingDetail.nrOfBedrooms)
        assertEquals(8, listingDetail.nrOfRooms)
        assertEquals(250.0, listingDetail.surface)
        assertEquals("https://v.seloger.com/s/crop/590x330/visuels/1/7/t/3/17t3fitclms3bzwv8qshbyzh9dw32e9l0p0udr80k.jpg", listingDetail.imageURL)
        assertEquals(PropertyType.VILLA, listingDetail.propertyType)
        assertEquals(1500000.0, listingDetail.price.first)
        assertEquals(EUR, listingDetail.price.second)
        assertEquals("GSL EXPLORE", listingDetail.professional)
        assertEquals(1, listingDetail.offerType)
    }

    @ParameterizedTest
    @JsonFileSource(jsonFile = "/json/listing_4.json")
    fun `returns a listing detail object for id 4 after querying immoweb API endpoint`(
        listing4DetailJson: JsonObject,
        mockWebServer: MockWebServer
    ) = runTest {
        mockWebServer.enqueue(MockResponse().setBody(listing4DetailJson.toString()))


        val listingDetailResponse = listingService.listingDetail(4)
        assertTrue(listingDetailResponse.isSuccessful)

        val listingDetail = listingDetailResponse.body() ?: fail { "listing_4 json could not be serialized" }

        assertEquals(4, listingDetail.id)
        assertEquals("Nice", listingDetail.city)
        assertNull(listingDetail.nrOfBedrooms)
        assertNull(listingDetail.nrOfRooms)
        assertEquals(250.0, listingDetail.surface)
        assertEquals("https://v.seloger.com/s/crop/590x330/visuels/1/9/f/x/19fx7n4og970dhf186925d7lrxv0djttlj5k9dbv8.jpg", listingDetail.imageURL)
        assertEquals(PropertyType.VILLA, listingDetail.propertyType)
        assertEquals(5000000.0, listingDetail.price.first)
        assertEquals(EUR, listingDetail.price.second)
        assertEquals("GSL CONTACTING", listingDetail.professional)
        assertEquals(4, listingDetail.offerType)
    }


    @ParameterizedTest
    @JsonFileSource(jsonFile = "/json/listings.json")
    fun `returns a List of listings after querying the immoweb API endpoint`(
        listingsJson: JsonObject,
        mockWebServer: MockWebServer
    ) = runTest {
        mockWebServer.enqueue(MockResponse().setBody(listingsJson.toString()))

        val response = listingService.listings()
        assertTrue(response.isSuccessful)

        val listingsDTO: ListingsDTO = response.body() ?: fail { "Failed to parse listings json"}
        assertEquals(4, listingsDTO.items.count())

        val firstListing = listingsDTO.items.first()
        assertEquals(1, firstListing.id)
        assertEquals("Villers-sur-Mer", firstListing.city)
    }

    // Possible other tests: 40x / 50x error
}
