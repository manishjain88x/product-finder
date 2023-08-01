package com.productfinder.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.productfinder.ProductValidationFormState
import com.productfinder.R
import com.productfinder.data.repo.Repository
import com.productfinder.models.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


/***
 * @author Manish Jain
 *
 * **/
@HiltViewModel
class ProductsListViewModel @Inject constructor(
    private val repository: Repository, application: Application
) : BaseViewModel(application) {
    private val _searchForm = MutableLiveData<ProductValidationFormState>()
    val searchFormState: LiveData<ProductValidationFormState> = _searchForm

    private val _recentProductsResult = MutableLiveData<List<Product>>()
    val recentProductsResult: LiveData<List<Product>> get() = _recentProductsResult


    fun getRecentProducts() {
        viewModelScope.launch {
            repository.local.getRecentProducts().collect {
                withContext(Dispatchers.IO) {
                    _recentProductsResult.postValue(it)
                }

            }
        }
    }

    fun checkValidation(searchQuery: String) {
        if (searchQuery.isEmpty()) {
            _searchForm.value =
                ProductValidationFormState(searchError = R.string.search_validation_error_msg)
        } else {
            _searchForm.value = ProductValidationFormState(isDataValid = true)
        }
    }

    fun resetValidation() {
        _searchForm.value = ProductValidationFormState()
    }


}

