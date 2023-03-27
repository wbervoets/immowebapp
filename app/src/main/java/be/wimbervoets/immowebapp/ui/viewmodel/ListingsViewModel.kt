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
class ListingsViewModel @Inject constructor(private val listingsRepository: ListingsRepository) : ViewModel() {

    init {
        Timber.d("ListingsViewModel init")
    }

    private val _listings: MutableLiveData<List<Listing>> = MutableLiveData<List<Listing>>()
    val listings: LiveData<List<Listing>> = _listings

    private val _failureReason: MutableLiveData<FailureReason?> = MutableLiveData(null)
    val failureReason: LiveData<FailureReason?> = _failureReason

    private val _loading: MutableLiveData<Boolean?> = MutableLiveData(null)
    val loading: LiveData<Boolean?> = _loading

    fun fetchCurrentListings() {
        _loading.value = true
        _failureReason.value = null
        viewModelScope.launch {
            fetchListings()
            _loading.value = false
        }
    }


    private suspend fun fetchListings() {
        when (val listingsResult = listingsRepository.listings()) {
            is ResultOf.Success -> {
                Timber.d("Succesfully fetched listings")
                _listings.value = listingsResult.data.items
            }
            is ResultOf.Failure -> {
                _failureReason.value = listingsResult.failureReason
            }
        }
    }
}