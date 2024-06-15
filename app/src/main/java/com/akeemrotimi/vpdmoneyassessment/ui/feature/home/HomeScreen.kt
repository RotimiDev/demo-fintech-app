package com.akeemrotimi.vpdmoneyassessment.ui.feature.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.akeemrotimi.vpdmoneyassessment.R
import com.akeemrotimi.vpdmoneyassessment.data.model.Account
import com.akeemrotimi.vpdmoneyassessment.data.model.Transaction
import com.akeemrotimi.vpdmoneyassessment.ui.feature.account.AccountScreen
import com.akeemrotimi.vpdmoneyassessment.ui.feature.transaction.TransactionScreen
import com.akeemrotimi.vpdmoneyassessment.ui.feature.transfer.TransferScreen
import com.akeemrotimi.vpdmoneyassessment.utils.toFormattedDateTime

@Preview
@Composable
private fun HomeScreenPreview() {
    HomeScreen(
        userName = "Mary",
        balance = 1000.00,
        transactions = listOf(),
        selectedItem = 0,
        searchQuery = "",
        onSearchQueryChanged = {},
        onItemSelected = {},
        onTransferClick = {},
        onTransactionClick = {},
        sourceAccount = Account(
            id = 0,
            name = "",
            bankName = "",
            accountNumber = "",
            accountBalance = 0.00
        ),
        destinationAccounts = listOf()
    )
}


@Composable
fun HomeScreen(
    userName: String,
    balance: Double,
    transactions: List<Transaction>,
    selectedItem: Int,
    searchQuery: String,
    onSearchQueryChanged: (String) -> Unit,
    onItemSelected: (Int) -> Unit,
    onTransferClick: () -> Unit = {},
    onTransactionClick: () -> Unit = {},
    sourceAccount: Account?,
    destinationAccounts: List<Account>
) {
    Scaffold(
        topBar = { HomeAppBar(userName) },
        bottomBar = {
            BottomNavBar(selectedItem) { index ->
                onItemSelected(index)
            }
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            when (selectedItem) {
                0 -> HomeContent(
                    balance,
                    transactions,
                    onTransferClick,
                    onTransactionClick
                )
                1 -> TransferScreen()
                2 -> TransactionScreen(transactions, searchQuery, onSearchQueryChanged)
                3 -> AccountScreen(sourceAccount, destinationAccounts)
            }
        }
    }
}

@Composable
fun HomeContent(
    balance: Double,
    transactions: List<Transaction>,
    onTransferClick: () -> Unit,
    onTransactionClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AccountBalanceCard(balance, onTransferClick, onTransactionClick)
        Spacer(modifier = Modifier.height(32.dp))
        QuickAccessButtons()
        Spacer(modifier = Modifier.height(64.dp))
        RecentTransactions(transactions)
    }
}

@Composable
fun HomeAppBar(userName: String) {
    Box(modifier = Modifier.wrapContentSize()) {
        Column(
            modifier = Modifier
                .background(colorResource(id = R.color.white))
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            ProfileView(userName)
            Spacer(Modifier.height(20.dp))
        }
    }
}

@Composable
fun ProfileView(userName: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = R.drawable.ic_profile),
                contentDescription = "Profile Picture",
                modifier = Modifier
                    .clip(CircleShape)
                    .size(48.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Hi, ${userName.split(" ").first()}",
                style = MaterialTheme.typography.titleMedium
            )
        }
        Image(
            painter = painterResource(id = R.drawable.ic_notification),
            contentDescription = "Notifications",
            modifier = Modifier
                .clip(CircleShape)
                .background(Color.White)
                .size(48.dp)
                .padding(8.dp)
        )
    }
}

@Composable
fun AccountBalanceCard(
    balance: Double,
    onTransferClick: () -> Unit = {},
    onTransactionClick: () -> Unit = {},
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Account Balance",
                color = Color.Black,
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "₦${String.format("%,.2f", balance)}",
                style = MaterialTheme.typography.headlineSmall
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(onClick = { onTransferClick() }) {
                    Text(text = "Transfer")
                }
                Button(onClick = { onTransactionClick() }) {
                    Text(text = "Transactions")
                }
            }
        }
    }
}

@Composable
fun QuickAccessButtons() {
    Column {
        Text(text = "Quick Access", style = MaterialTheme.typography.titleSmall)
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier
                .clip(RoundedCornerShape(6.dp))
                .fillMaxWidth()
                .shadow(1.dp)
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            QuickAccessButton(text = "Electricity", icon = R.drawable.ic_electricity)
            QuickAccessButton(text = "Internet", icon = R.drawable.ic_internet)
            QuickAccessButton(text = "Airtime", icon = R.drawable.ic_airtime)
            QuickAccessButton(text = "Betting", icon = R.drawable.ic_betting)
        }
    }
}

@Composable
fun QuickAccessButton(text: String, icon: Int) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Image(painterResource(id = icon),
            contentDescription = null,
            modifier = Modifier
                .size(20.dp)
                .clickable { })
        Text(text = text, modifier = Modifier.clickable { })
    }
}

@Composable
fun RecentTransactions(transactions: List<Transaction>) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Recent Transactions", style = MaterialTheme.typography.titleSmall)
        }
        Spacer(modifier = Modifier.height(8.dp))

        transactions.forEach { transaction ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_bank),
                        contentDescription = null,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text(
                            text = transaction.sourceAccount,
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Text(
                            text = transaction.timestamp.toFormattedDateTime(),
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.Gray
                        )
                    }
                }
                Text(
                    text = "₦${String.format("%,.2f", transaction.amount)}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
        }
    }
}
