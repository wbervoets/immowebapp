package be.wimbervoets.immowebservice.data.datasource

import be.wimbervoets.immowebservice.data.service.ListingService
import be.wimbervoets.immowebservice.data.service.dto.ListingsDTO
import be.wimbervoets.immowebservice.data.service.dto.ListingDTO
import timber.log.Timber
import javax.inject.Inject

class ListingsRemoteDatasource @Inject constructor(private val listingService: ListingService, failureReasonMapper: FailureReasonMapper): BaseDataSource(failureReasonMapper) {

    init {
        Timber.d("Listings remote datasource init")
    }

    suspend fun listings(): ResultOf<ListingsDTO> {
        return mapToResultOf {
            listingService.listings()
        }
    }

    suspend fun listingDetail(id: Long): ResultOf<ListingDTO> {
        return mapToResultOf {
            listingService.listingDetail(id)
        }
    }
}