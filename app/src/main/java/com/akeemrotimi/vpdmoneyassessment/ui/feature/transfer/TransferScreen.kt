package com.akeemrotimi.vpdmoneyassessment.ui.feature.transfer

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.akeemrotimi.vpdmoneyassessment.R
import com.akeemrotimi.vpdmoneyassessment.data.model.Account

@Preview(showSystemUi = true)
@Composable
private fun TransferScreenPreview() {
    TransferScreen(
        sourceAccount = Account(
            id = 0,
            name = "Miracle Terry",
            bankName = "VPD Bank",
            accountNumber = "2412303012",
            accountBalance = 20000.00
        )
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransferScreen(
    sourceAccount: Account? = null,
    destinationAccounts: List<Account> = emptyList(),
    amount: String = "",
    destinationAccountError: String? = null,
    amountError: String? = null,
    onSourceAccountChange: (Account) -> Unit = {},
    onDestinationAccountChange: (Account) -> Unit = {},
    onAmountChange: (String) -> Unit = {},
    onTransferButtonClick: () -> Unit = {},
    onBackClick: () -> Unit = {}
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { },
                navigationIcon = {
                    IconButton(onClick = { onBackClick() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            var selectedSourceAccount by remember { mutableStateOf<Account?>(null) }

            AccountDropdownMenu(
                accounts = listOfNotNull(sourceAccount),
                onAccountSelected = { selectedAccount ->
                    selectedSourceAccount = selectedAccount
                    onSourceAccountChange(selectedAccount)
                }
            )

            if (selectedSourceAccount != null) {
                Text("Balance: ₦${String.format("%,.2f", selectedSourceAccount!!.accountBalance)}")
            }

            Spacer(modifier = Modifier.height(8.dp))

            DestinationAccountDropdownMenu(
                accounts = destinationAccounts,
                onAccountSelected = { selectedAccount ->
                    onDestinationAccountChange(selectedAccount)
                }
            )

            if (destinationAccountError != null) {
                Text(
                    text = destinationAccountError,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = amount,
                onValueChange = { newValue -> onAmountChange(newValue) },
                label = { Text(text = stringResource(id = R.string.title_enter_amount)) },
                isError = amountError != null
            )

            if (amountError != null) {
                Text(
                    text = amountError,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = onTransferButtonClick) {
                Text(text = stringResource(id = R.string.action_transfer))
            }
        }
    }
}

@Composable
fun AccountDropdownMenu(
    accounts: List<Account>,
    onAccountSelected: (Account) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedAccount by remember { mutableStateOf<Account?>(null) }

    Box {
        Text(
            text = selectedAccount?.name ?: stringResource(id = R.string.title_select_account),
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = true }
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.outline,
                    shape = MaterialTheme.shapes.medium
                )
                .background(MaterialTheme.colorScheme.surface)
                .padding(16.dp)
        )

        androidx.compose.material.DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            accounts.forEach { account ->
                androidx.compose.material.DropdownMenuItem(onClick = {
                    onAccountSelected(account)
                    selectedAccount = account
                    expanded = false
                }) {
                    Text(account.name)
                }
            }
        }
    }
}

@Composable
fun DestinationAccountDropdownMenu(
    accounts: List<Account>,
    onAccountSelected: (Account) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedAccount by remember { mutableStateOf<Account?>(null) }
    Box {
        Text(
            text = selectedAccount?.name ?: stringResource(id = R.string.title_select_destination_account),
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = true }
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.outline,
                    shape = MaterialTheme.shapes.medium
                )
                .background(MaterialTheme.colorScheme.surface)
                .padding(16.dp)
        )
        androidx.compose.material.DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            accounts.forEach { account ->
                androidx.compose.material.DropdownMenuItem(onClick = {
                    onAccountSelected(account)
                    selectedAccount = account
                    expanded = false
                }) {
                    Text(account.name)
                }
            }
        }
    }
}
