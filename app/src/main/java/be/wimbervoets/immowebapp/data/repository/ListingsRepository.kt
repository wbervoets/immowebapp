package be.wimbervoets.immowebapp.data.repository

import be.wimbervoets.immowebapp.data.model.Listing
import be.wimbervoets.immowebapp.data.model.Listings
import be.wimbervoets.immowebservice.data.datasource.ListingsRemoteDatasource
import be.wimbervoets.immowebservice.data.datasource.ResultOf
import be.wimbervoets.immowebservice.data.service.dto.ListingDTO
import timber.log.Timber
import javax.inject.Inject

class ListingsRepository @Inject constructor(private val listingsRemoteDatasource: ListingsRemoteDatasource) {

    init {
        Timber.d("Listings repository init")
    }

    suspend fun listings(): ResultOf<Listings> {
        return when (val result = listingsRemoteDatasource.listings()) {
            is ResultOf.Success -> {
                ResultOf.Success(
                    Listings(
                        result.data.items.map { convertListingDTO(it) }
                    )
                )
            }
            is ResultOf.Failure -> {
                ResultOf.Failure(result.failureReason)
            }
        }
    }

    private fun convertListingDTO(it: ListingDTO) =
        Listing(
            id = it.id,
            city = it.city,
            price = it.price.first,
            currency = it.price.second,
            surface = it.surface,
            propertyType = it.propertyType,
            professional = it.professional,
            nrOfBedrooms = it.nrOfBedrooms,
            nrOfRooms = it.nrOfRooms,
            listingImageURL = it.imageURL
        )

    suspend fun listingDetail(id: Long): ResultOf<Listing> {
        return when (val result = listingsRemoteDatasource.listingDetail(id)) {
            is ResultOf.Success -> {
                ResultOf.Success(convertListingDTO(result.data))
            }
            is ResultOf.Failure -> {
                ResultOf.Failure(result.failureReason)
            }
        }
    }
}