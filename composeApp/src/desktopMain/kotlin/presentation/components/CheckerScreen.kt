package presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.ApplicationScope
import androidx.compose.ui.window.Window
import common.DI
import common.isValidURL

@Composable
fun ApplicationScope.CheckerScreen() {
	Window(
		onCloseRequest = ::exitApplication,
		title = "Talpa",
	) {
		MaterialTheme {
			var rssLink by remember { mutableStateOf("") }
			var parsingResult by remember { mutableStateOf("") }

			LaunchedEffect(rssLink) {
				if (rssLink.isNotEmpty() && isValidURL(rssLink)) {
					val feedRepository = DI.feedRepository
					parsingResult = try {
						val result = feedRepository.getFeed(rssLink)
						result.toString()
					} catch (e: Throwable) {
						e.printStackTrace()
						e.toString()
					}
				}
			}

			LazyColumn(
				verticalArrangement = Arrangement.spacedBy(12.dp),
				contentPadding = PaddingValues(vertical = 24.dp)
			) {
				item {
					TextField(
						modifier = Modifier
							.fillMaxWidth()
							.padding(horizontal = 16.dp),
						label = { Text("RSS feed link") },
						value = rssLink,
						onValueChange = { rssLink = it },
						singleLine = true,
						trailingIcon = {
							AnimatedVisibility(
								visible = rssLink.isNotBlank(),
								enter = fadeIn(),
								exit = fadeOut()
							) {
								IconButton(
									onClick = {
										rssLink = ""
										parsingResult = ""
									}
								) {
									Icon(Icons.Outlined.Delete, "Clear")
								}
							}
						},
						keyboardOptions = KeyboardOptions(
							imeAction = ImeAction.Done,
							keyboardType = KeyboardType.Uri,
							autoCorrect = false,
						),
					)
				}

				item {
					Text(
						modifier = Modifier
							.padding(horizontal = 16.dp),
						text = parsingResult
					)
				}
			}
		}
	}
}