package com.android.ml_challenge.search

import app.cash.turbine.test
import com.android.ml_challenge.MainDispatcherRule
import com.android.ml_challenge.ui.search.SearchViewModel
import com.android.ml_challenge.ui.search.UiSearchState
import com.android.ml_challenge.ui.search.domain.SearchUseCase
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
class SearchViewModelTest {

    private lateinit var searchViewModel: SearchViewModel
    private val dispatcher = StandardTestDispatcher()

    @get:Rule
    val mockkRule = MockKRule(this)

    @get:Rule
    val mainRule = MainDispatcherRule(UnconfinedTestDispatcher())

    @MockK
    private lateinit var searchUseCase: SearchUseCase

    @Before
    fun setUp() {
        searchViewModel = SearchViewModel(searchUseCase)
        Dispatchers.setMain(dispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `search success case`() = runTest {
        every { searchUseCase.isValidText("Success") } returns flowOf(true)

        searchViewModel.uiSearchState.test {
            searchViewModel.search("Success")
            Assert.assertEquals(UiSearchState.Loading, awaitItem())
            Assert.assertEquals(UiSearchState.Success, awaitItem())
        }
    }

    @Test
    fun `search empty case`() = runTest {
        every { searchUseCase.isValidText("") } returns flowOf(false)

        searchViewModel.uiSearchState.test {
            searchViewModel.search("")
            Assert.assertEquals(UiSearchState.Loading, awaitItem())
            Assert.assertEquals(UiSearchState.Empty, awaitItem())
        }
    }
}