package presentation.components


import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import common.components.ErrorView
import common.components.FeedList
import common.components.LoadingProgress
import kotlinx.coroutines.launch
import presentation.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InstalledScreen(viewModel: MainViewModel) {
	MaterialTheme {
		val uiState by viewModel.uiState
		val title = uiState.installedFeed?.title ?: ""
		val scope = rememberCoroutineScope()

		Scaffold(
			topBar = {
				TopAppBar(
					title = {
						Text(
							"Hi!",
							style = MaterialTheme.typography.headlineMedium,
							color = MaterialTheme.colorScheme.onBackground
						)
					},
					actions = {
						IconButton(
							onClick = {
								//onShowCheckerClick()
							}
						) {
							Icon(
								imageVector = Icons.Default.Settings,
								contentDescription = null,
							)
						}
					}
				)
			},
		) { paddingValues ->
			when {
				uiState.isLoading -> {
					LoadingProgress()
				}

				uiState.error != null -> {
					val errorMessage = uiState.error ?: ""
					ErrorView(
						errorMessage = errorMessage,
						onRetryClick = {
							scope.launch {
								viewModel.getFeeds()
							}
						},
					)
				}

				uiState.installedFeed != null -> {
					val items = uiState.installedFeed?.items ?: emptyList()
					FeedList(
						padding = paddingValues,
						feedItems = items,
					)
				}
			}
		}
	}
}