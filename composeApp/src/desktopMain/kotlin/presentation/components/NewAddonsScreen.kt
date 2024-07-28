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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import common.components.ErrorView
import common.components.FeedList
import common.components.LoadingProgress
import kotlinx.coroutines.launch
import presentation.MainViewModel

@Composable
fun NewAddonsScreen(viewModel: MainViewModel) {
		MaterialTheme {
			val uiState by viewModel.uiState
			val title = uiState.newFeed?.title ?: ""
			val scope = rememberCoroutineScope()
			Scaffold(
				topBar = {
					TopAppBar(
						backgroundColor = MaterialTheme.colors.secondary,
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
