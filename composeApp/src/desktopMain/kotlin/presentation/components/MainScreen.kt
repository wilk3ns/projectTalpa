package presentation.components

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.window.ApplicationScope
import androidx.compose.ui.window.Window
import common.components.ErrorView
import common.components.FeedList
import common.components.LoadingProgress
import kotlinx.coroutines.launch
import presentation.MainViewModel

@Composable
fun ApplicationScope.MainScreen(onShowCheckerClick: () -> Unit) {
	Window(
		onCloseRequest = ::exitApplication,
		title = "Talpa",
	) {
		MaterialTheme {
			val viewModel = remember { MainViewModel() }

			LaunchedEffect(Unit) {
				viewModel.getFeeds()
			}

			val uiState by viewModel.uiState
			val title = uiState.feed?.title ?: ""
			val scope = rememberCoroutineScope()

			Scaffold(
				topBar = {
					TopAppBar(
						backgroundColor = MaterialTheme.colors.background,
						title = {
							Text(
								"Hi!",
								style = MaterialTheme.typography.h5,
								color = MaterialTheme.colors.onBackground
							)
						},
						actions = {
							IconButton(
								onClick = {
									onShowCheckerClick()
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

					uiState.feed != null -> {
						val items = uiState.feed?.items ?: emptyList()
						FeedList(
							padding = paddingValues,
							feedItems = items,
						)
					}
				}
			}
		}
	}
}