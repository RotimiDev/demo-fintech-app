package com.akeemrotimi.vpdmoneyassessment.ui.feature.account

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.akeemrotimi.vpdmoneyassessment.data.model.Account

@Preview(showSystemUi = true)
@Composable
private fun AccountPreview() {
    AccountScreen(
        destinationAccounts = listOf(
            Account(
                id = 0,
                name = "Mary Joseph",
                bankName = "VPD Money",
                accountNumber = "2412403012",
                accountBalance = 6000.00
            ),
        ),
        sourceAccount = null
    )
}

@Composable
fun AccountScreen(
    sourceAccount: Account?,
    destinationAccounts: List<Account>
) {
    Column {
        sourceAccount?.let {
            Text("Source Account:")
            AccountItem(it)
        }
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(destinationAccounts) { account ->
                AccountItem(account)
            }
        }
    }
}

@Composable
fun AccountItem(account: Account) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Account Holder: ${account.name}",
                style = MaterialTheme.typography.titleMedium,
                color = Color.Black,
            )
            Text(
                text = "Bank: ${account.bankName}",
                style = MaterialTheme.typography.titleSmall
            )
            Text(
                text = "Account Number: ${account.accountNumber}",
                style = MaterialTheme.typography.titleSmall
            )
            Text(
                text = "Balance: ₦${account.accountBalance}",
                style = MaterialTheme.typography.titleMedium,
                color = Color.Black
            )
        }
    }
}
