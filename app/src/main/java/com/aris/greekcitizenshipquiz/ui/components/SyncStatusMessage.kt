package com.aris.greekcitizenshipquiz.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Sync
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.aris.greekcitizenshipquiz.ui.state.QuizUiState

@Composable
fun SyncButtonContent(
    state: QuizUiState
) {

    when (state) {

        QuizUiState.Loading -> {

            CircularProgressIndicator(
                modifier = Modifier.size(22.dp),
                color = MaterialTheme.colorScheme.onPrimary,
                strokeWidth = 2.dp
            )
        }


        QuizUiState.Success -> {

            Text(
                text = "✓ Μόλις Ενημερώθηκε"
            )
        }


        QuizUiState.NoUpdates -> {

            Text(
                text = "✓ Ήδη ενημερωμένο"
            )
        }


        QuizUiState.Idle -> {

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                Icon(
                    imageVector = Icons.Default.Sync,
                    contentDescription = null
                )

                Spacer(
                    modifier = Modifier.width(8.dp)
                )

                Text(
                    text = "Λήψη ενημέρωσης"
                )
            }
        }


        is QuizUiState.Error -> {

            Text(
                text = "Αδυναμία"
            )
        }
    }
}