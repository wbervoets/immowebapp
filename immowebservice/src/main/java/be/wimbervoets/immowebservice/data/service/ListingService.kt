package be.wimbervoets.immowebservice.data.service

import be.wimbervoets.immowebservice.data.service.dto.ListingsDTO
import be.wimbervoets.immowebservice.data.service.dto.ListingDTO
import retrofit2.Response
import retrofit2.http.*
import java.util.*

interface ListingService {

    // GET https://gsl-apps-technical-test.dignp.com/listings.json
    // GET https://gsl-apps-technical-test.dignp.com/listings/{listingId}.json


    @GET("listings.json")
    suspend fun listings(): Response<ListingsDTO>

    @GET("listings/{listingId}.json")
    suspend fun listingDetail(@Path("listingId") id: Long): Response<ListingDTO>
}