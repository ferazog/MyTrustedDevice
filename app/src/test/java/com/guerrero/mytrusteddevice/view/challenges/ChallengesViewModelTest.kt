package com.guerrero.mytrusteddevice.view.challenges

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.guerrero.mytrusteddevice.factorverifier.FactorVerifier
import com.guerrero.mytrusteddevice.repository.ChallengesRepository
import com.guerrero.mytrusteddevice.shared.ChallengeWrapper
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

@ExperimentalCoroutinesApi
class ChallengesViewModelTest {

    @get: Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private val testDispatcher = TestCoroutineDispatcher()

    private lateinit var viewModel: ChallengesViewModel

    private val repository = mockk<ChallengesRepository>(relaxed = true)

    private val factorVerifier = mockk<FactorVerifier>(relaxed = true)

    private val viewStateObserver = mockk<Observer<ChallengesViewState>>(relaxed = true)

    init {
        Dispatchers.setMain(testDispatcher)
    }

    @Before
    fun setupViewModel() {
        viewModel = ChallengesViewModel(repository, factorVerifier)
        viewModel.getChallengesViewStateObservable().observeForever(viewStateObserver)
    }

    @Test
    fun `when getting pending challenges should return the challenges successfully`() {
        val factorSid = "factorSid"
        val onSuccessSlot = slot<(List<ChallengeWrapper>) -> Unit>()
        val viewStateSlot = slot<ChallengesViewState.Success>()

        coEvery {
            repository.getFactorSid()
        } returns factorSid
        every {
            viewStateObserver.onChanged(capture(viewStateSlot))
        } just runs
        every {
            factorVerifier.getPendingChallenges(factorSid, capture(onSuccessSlot), any())
        } answers {
            onSuccessSlot.captured(ChallengeTestObjects.CHALLENGES)
            verify {
                viewStateObserver.onChanged(any<ChallengesViewState.Success>())
            }
            Assert.assertEquals(ChallengeTestObjects.CHALLENGES, viewStateSlot.captured)
        }

        runBlocking {
            viewModel.getPendingChallenges()
        }

        verify {
            viewStateObserver.onChanged(any<ChallengesViewState.Loading>())
        }
    }

    @Test
    fun `when getting pending challenges should return the empty state`() {
        val factorSid = "factorSid"
        val onSuccessSlot = slot<(List<ChallengeWrapper>) -> Unit>()

        coEvery {
            repository.getFactorSid()
        } returns factorSid
        every {
            factorVerifier.getPendingChallenges(factorSid, capture(onSuccessSlot), any())
        } answers {
            onSuccessSlot.captured(emptyList())
            verify {
                viewStateObserver.onChanged(any<ChallengesViewState.EmptyState>())
            }
        }

        runBlocking {
            viewModel.getPendingChallenges()
        }

        verify {
            viewStateObserver.onChanged(any<ChallengesViewState.Loading>())
        }
    }

    @Test
    fun `when getting pending challenges should return error`() {
        val factorSid = "factorSid"
        val errorMessage = "error message"
        val onErrorSlot = slot<(Exception) -> Unit>()
        val viewStateSlot = slot<ChallengesViewState.Error>()

        coEvery {
            repository.getFactorSid()
        } returns factorSid
        every {
            viewStateObserver.onChanged(capture(viewStateSlot))
        } just runs
        every {
            factorVerifier.getPendingChallenges(factorSid, any(), capture(onErrorSlot))
        } answers {
            onErrorSlot.captured(Exception(errorMessage))
            verify {
                viewStateObserver.onChanged(any<ChallengesViewState.Error>())
            }
            Assert.assertEquals(errorMessage, viewStateSlot.captured.message)
        }

        runBlocking {
            viewModel.getPendingChallenges()
        }

        verify {
            viewStateObserver.onChanged(any<ChallengesViewState.Loading>())
        }
    }
}
