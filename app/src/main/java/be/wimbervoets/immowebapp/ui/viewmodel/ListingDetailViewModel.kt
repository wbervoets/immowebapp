package be.wimbervoets.immowebapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import be.wimbervoets.immowebapp.data.model.Listing
import be.wimbervoets.immowebapp.data.repository.ListingsRepository
import be.wimbervoets.immowebservice.data.datasource.FailureReason
import be.wimbervoets.immowebservice.data.datasource.ResultOf
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ListingDetailViewModel @Inject constructor(private val listingsRepository: ListingsRepository) : ViewModel() {

    init {
        Timber.d("ListingsViewModel init")
    }

    private val _listingDetail: MutableLiveData<Listing> = MutableLiveData<Listing>()
    val listingDetail: LiveData<Listing> = _listingDetail

    private val _failureReason: MutableLiveData<FailureReason?> = MutableLiveData(null)
    val failureReason: LiveData<FailureReason?> = _failureReason

    private val _loading: MutableLiveData<Boolean?> = MutableLiveData(null)
    val loading: LiveData<Boolean?> = _loading

    fun fetchListing(id: Long) {
        _loading.value = true
        _failureReason.value = null
        viewModelScope.launch {
            fetchListingDetail(id)
            _loading.value = false
        }
    }

    private suspend fun fetchListingDetail(id: Long) {
        when (val listingDetail = listingsRepository.listingDetail(id)) {
            is ResultOf.Success -> {
                Timber.d("Succesfully fetched listing detail")
                _listingDetail.postValue(listingDetail.data)
            }
            is ResultOf.Failure -> {
                _failureReason.postValue(listingDetail.failureReason)
            }
        }
    }

}