package presentation.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
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
fun NewAddonsScreen(viewModel: MainViewModel) {
		MaterialTheme {
			val uiState by viewModel.uiState
			val title = uiState.newFeed?.title ?: ""
			val scope = rememberCoroutineScope()
			Scaffold(
				topBar = {
					//onShowCheckerClick()
					TopAppBar(
						title = {
							Text(
								"Hi!",
								style = MaterialTheme.typography.headlineMedium,
								color = MaterialTheme.colorScheme.onBackground
							)
						})
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

					uiState.newFeed != null -> {
						val items = uiState.newFeed?.items ?: emptyList()
						FeedList(
							padding = paddingValues,
							feedItems = items,
						)
					}
				}
			}
		}
	}
