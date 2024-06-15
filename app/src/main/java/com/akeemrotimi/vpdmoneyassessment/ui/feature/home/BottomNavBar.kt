package com.akeemrotimi.vpdmoneyassessment.ui.feature.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.akeemrotimi.vpdmoneyassessment.R

@Composable
fun BottomNavBar(selectedItem: Int, onMenuItemClicked: (Int) -> Unit) {
    val items = listOf(
        Pair(R.drawable.ic_home, R.string.title_home),
        Pair(R.drawable.ic_transfer, R.string.title_transfer),
        Pair(R.drawable.ic_transaction_history, R.string.title_transactions),
        Pair(R.drawable.ic_account, R.string.title_account)
    )

    NavigationBar(
        modifier = Modifier.fillMaxWidth(),
        containerColor = colorResource(id = R.color.white)
    ) {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                icon = {
                    Image(
                        painter = painterResource(id = item.first),
                        contentDescription = "",
                        modifier = Modifier.size(24.dp)
                    )
                },
                label = { Text(stringResource(id = item.second)) },
                selected = selectedItem == index,
                onClick = { onMenuItemClicked(index) }
            )
        }
    }
}
