@file:Suppress("UNCHECKED_CAST")

package com.akeemrotimi.vpdmoneyassessment

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.akeemrotimi.vpdmoneyassessment.data.model.Account
import com.akeemrotimi.vpdmoneyassessment.ui.feature.account.AccountViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class AccountViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var firebaseAuth: FirebaseAuth

    @Mock
    private lateinit var firebaseUser: FirebaseUser

    private lateinit var viewModel: AccountViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        `when`(firebaseAuth.currentUser).thenReturn(firebaseUser)
        `when`(firebaseUser.displayName).thenReturn("John Doe")
        viewModel = AccountViewModel(firebaseAuth)
    }

    @Test
    fun init_shouldInitializeSourceAccount() {
        val observer = mock(Observer::class.java) as Observer<Account?>

        viewModel.sourceAccount.observeForever(observer)
        val expectedAccount = Account(
            id = 0,
            name = "John Doe",
            bankName = "VPD Money",
            accountNumber = "2412403012",
            accountBalance = 6000.00
        )

        verify(observer).onChanged(expectedAccount)
        viewModel.sourceAccount.removeObserver(observer)
    }

    @Test
    fun init_shouldInitializeAccounts() {
        val observer = mock(Observer::class.java) as Observer<List<Account>>

        viewModel.accounts.observeForever(observer)
        val expectedAccounts = listOf(
            Account(
                id = 1,
                name = "Akeem Terry",
                bankName = "Sterling Bank",
                accountNumber = "2412403012",
                accountBalance = 100000.00
            ),
            Account(
                id = 2,
                name = "Joseph Parker",
                bankName = "First Bank",
                accountNumber = "4651240301",
                accountBalance = 30000.00
            ),
            Account(
                id = 3,
                name = "Happiness Julius",
                bankName = "VPD Money",
                accountNumber = "0012403012",
                accountBalance = 30000.00
            )
        )

        verify(observer).onChanged(expectedAccounts)
        viewModel.accounts.removeObserver(observer)
    }

    @Test
    fun transfer_shouldUpdateBalancesWhenTransferIsSuccessful() {
        val observer = mock(Observer::class.java) as Observer<Account?>
        viewModel.sourceAccount.observeForever(observer)

        val initialSourceAccount = viewModel.sourceAccount.value
        val destinationAccountId = 1
        val transferAmount = 500.0

        val transferResult = viewModel.transfer(destinationAccountId, transferAmount)

        assertTrue(transferResult)
        assertEquals(
            initialSourceAccount?.accountBalance?.minus(transferAmount),
            viewModel.sourceAccount.value?.accountBalance
        )

        viewModel.sourceAccount.removeObserver(observer)
    }

    @Test
    fun transfer_shouldNotUpdateBalancesWhenTransferFails() {
        val observer = mock(Observer::class.java) as Observer<Account?>
        viewModel.sourceAccount.observeForever(observer)

        val initialSourceAccount = viewModel.sourceAccount.value
        val destinationAccountId = 1
        val transferAmount = 7000.0 // Exceeds the source account balance

        val transferResult = viewModel.transfer(destinationAccountId, transferAmount)

        assertFalse(transferResult)
        assertEquals(
            initialSourceAccount?.accountBalance,
            viewModel.sourceAccount.value?.accountBalance
        )

        viewModel.sourceAccount.removeObserver(observer)
    }
}
