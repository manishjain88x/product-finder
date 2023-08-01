package com.productfinder.viewmodel

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.productfinder.ProductValidationFormState
import com.productfinder.R
import com.productfinder.data.LocalDataSource
import com.productfinder.data.RemoteDataSource
import com.productfinder.data.repo.Repository
import com.productfinder.getOrAwaitValue
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class ProductsListViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()


    @Mock
    private lateinit var mockRemoteDataSource: RemoteDataSource

    @Mock
    private lateinit var mockLocalDataSource: LocalDataSource

    @InjectMocks
    private lateinit var repository: Repository

    lateinit var viewModel: ProductsListViewModel

    @Mock
    private lateinit var application: Application


    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        viewModel = ProductsListViewModel(repository, application)
    }


    @Test
    fun `test checkValidation sets error message when search query is empty`() {
        // Arrange
        val searchQuery = ""
        val expectedFormState =
            ProductValidationFormState(searchError = R.string.search_validation_error_msg)

        // Act
        viewModel.checkValidation(searchQuery)

        // Assert
        val actualFormState = viewModel.searchFormState.getOrAwaitValue()
        assertEquals(expectedFormState, actualFormState)
    }

    @Test
    fun `test checkValidation sets isDataValid true when search query is valid`() {
        // Arrange
        val searchQuery = "iPhone"
        val expectedFormState = ProductValidationFormState(isDataValid = true)

        // Act
        viewModel.checkValidation(searchQuery)

        // Assert
        val actualFormState = viewModel.searchFormState.getOrAwaitValue()
        assertEquals(expectedFormState, actualFormState)
    }

}
