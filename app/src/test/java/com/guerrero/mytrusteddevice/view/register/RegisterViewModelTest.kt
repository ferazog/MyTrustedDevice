package com.guerrero.mytrusteddevice.view.register

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.guerrero.mytrusteddevice.factorverifier.FactorVerifier
import com.guerrero.mytrusteddevice.repository.RegisterRepository
import com.guerrero.mytrusteddevice.shared.AccessToken
import com.guerrero.mytrusteddevice.shared.RegisterInfo
import com.guerrero.mytrusteddevice.shared.User
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
class RegisterViewModelTest {

    @get: Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private val testDispatcher = TestCoroutineDispatcher()

    private lateinit var viewModel: RegisterViewModel

    private val repository = mockk<RegisterRepository>(relaxed = true)

    private val factorVerifier = mockk<FactorVerifier>(relaxed = true)

    private val viewStateObserver = mockk<Observer<RegisterViewState>>(relaxed = true)

    init {
        Dispatchers.setMain(testDispatcher)
    }

    @Before
    fun setupViewModel() {
        viewModel = RegisterViewModel(repository, factorVerifier)
        viewModel.getViewStateObservable().observeForever(viewStateObserver)
    }

    @Test
    fun `when registerUser should post success`() {
        val user = User("bob", "builder")
        val pushToken = "pushToken"
        val accessToken = AccessToken("token", "serviceSid", "identity", "factorType")
        val registerInfo = RegisterInfo("identity", "factorSid")
        val slot = slot<(RegisterInfo) -> Unit>()

        coEvery {
            repository.getAccessToken(user)
        } returns accessToken
        coEvery {
            factorVerifier.createFactor(accessToken, pushToken, capture(slot), any())
        } answers {
            slot.captured(registerInfo)
        }

        runBlocking {
            viewModel.registerUser(user, pushToken)
        }

        verify(atLeast = 1) {
            viewStateObserver.onChanged(any<RegisterViewState.Loading>())
            viewStateObserver.onChanged(any<RegisterViewState.Success>())
        }
    }

    @Test
    fun `when registerUser should post create factor error`() {
        val errorMessage = "create factor error"
        val user = User("bob", "builder")
        val pushToken = "pushToken"
        val accessToken = AccessToken("token", "serviceSid", "identity", "factorType")
        val onErrorSlot = slot<(Exception) -> Unit>()
        val viewStateSlot = slot<RegisterViewState.Error>()

        coEvery {
            repository.getAccessToken(user)
        } returns accessToken
        coEvery {
            factorVerifier.createFactor(accessToken, pushToken, any(), capture(onErrorSlot))
        } answers {
            onErrorSlot.captured(Exception(errorMessage))
            verify {
                viewStateObserver.onChanged(any<RegisterViewState.Error>())
            }
            Assert.assertEquals(errorMessage, viewStateSlot.captured.message)
        }
        every {
            viewStateObserver.onChanged(capture(viewStateSlot))
        } just runs

        runBlocking {
            viewModel.registerUser(user, pushToken)
        }
    }
}
