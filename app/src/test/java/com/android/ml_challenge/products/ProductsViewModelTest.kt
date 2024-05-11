package com.android.ml_challenge.products

import app.cash.turbine.test
import com.android.ml_challenge.MainDispatcherRule
import com.android.ml_challenge.model.Product
import com.android.ml_challenge.model.SearchResponse
import com.android.ml_challenge.ui.products.ProductsViewModel
import com.android.ml_challenge.ui.products.UiProductsState
import com.android.ml_challenge.ui.products.domain.ProductsUseCase
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit4.MockKRule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ProductsViewModelTest {

    private lateinit var productsViewModel: ProductsViewModel
    private val dispatcher = StandardTestDispatcher()

    @get:Rule
    val mockkRule = MockKRule(this)

    @get:Rule
    val mainRule = MainDispatcherRule(UnconfinedTestDispatcher())

    @MockK
    private lateinit var productsUseCase: ProductsUseCase

    @Before
    fun setUp() {
        productsViewModel = ProductsViewModel(productsUseCase)
        productsViewModel.setProductName("Cabo")
        Dispatchers.setMain(dispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `search success case`() = runTest {
        every { productsUseCase.products("Cabo") } returns flowOf(searchSuccessSearchResponse())

        productsViewModel.uiProductsState.test {
            productsViewModel.fetchProducts()
            Assert.assertEquals(UiProductsState.Loading, awaitItem())
            Assert.assertEquals(
                UiProductsState.Success(searchSuccessSearchResponse().results),
                awaitItem()
            )
        }
    }

    @Test
    fun `search empty case`() = runTest {
        every { productsUseCase.products("Cabo") } returns flowOf(searchEmptySearchResponse())

        productsViewModel.uiProductsState.test {
            productsViewModel.fetchProducts()
            Assert.assertEquals(UiProductsState.Loading, awaitItem())
            Assert.assertEquals(UiProductsState.Empty, awaitItem())
        }
    }

    @Test
    fun `search more products success case`() = runTest {
        every { productsUseCase.products("Cabo") } returns flowOf(searchSuccessSearchResponse())

        productsViewModel.uiProductsState.test {
            productsViewModel.fetchProducts()
            Assert.assertEquals(UiProductsState.Loading, awaitItem())
            Assert.assertEquals(
                UiProductsState.Success(searchSuccessSearchResponse().results),
                awaitItem()
            )
        }

        productsViewModel.uiProductsState.test {
            productsViewModel.fetchMoreProducts()
            Assert.assertEquals(
                UiProductsState.Success(searchSuccessSearchResponse().results),
                awaitItem()
            )
        }
    }

    private fun searchSuccessSearchResponse(): SearchResponse {
        val attributes = mutableListOf<Product.Attributes>()
        attributes.add(Product.Attributes("id", "name"))
        val product = Product(
            "0",
            "title",
            "condition",
            "permalink",
            "thumb",
            100.0,
            "quantity",
            Product.Seller("id", "nickname"),
            attributes
        )

        val results = mutableListOf<Product>()
        results.add(product)

        return SearchResponse(
            paging = SearchResponse.Paging(0, 0, 0, 0),
            results
        )
    }

    private fun searchEmptySearchResponse(): SearchResponse {
        val attributes = mutableListOf<Product.Attributes>()
        attributes.add(Product.Attributes("id", "name"))

        val results = mutableListOf<Product>()

        return SearchResponse(
            paging = SearchResponse.Paging(0, 0, 0, 0),
            results
        )
    }
}