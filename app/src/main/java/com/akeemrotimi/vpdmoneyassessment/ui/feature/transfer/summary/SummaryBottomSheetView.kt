package com.akeemrotimi.vpdmoneyassessment.ui.feature.transfer.summary

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.akeemrotimi.vpdmoneyassessment.R
import com.akeemrotimi.vpdmoneyassessment.data.model.Account

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SummaryBottomSheetView(
    sourceAccount: Account?,
    destinationAccount: String?,
    transferAmount: String,
    onDismiss: () -> Unit,
    onConfirmTransfer: () -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = onDismiss
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .height(300.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = stringResource(id = R.string.title_transfer_summary),
                style = MaterialTheme.typography.headlineMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = stringResource(
                    id = R.string.title_source_account,
                    sourceAccount?.name ?: ""
                ),
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = stringResource(
                    id = R.string.title_destination_account,
                    destinationAccount ?: ""
                ),
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = stringResource(id = R.string.title_transfer_amount, transferAmount),
                style = MaterialTheme.typography.bodyLarge
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = onConfirmTransfer) {
                Text(text = stringResource(id = R.string.action_confirm_transfer))
            }
        }
    }
}
