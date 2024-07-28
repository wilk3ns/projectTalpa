package presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.ApplicationScope
import androidx.compose.ui.window.Window
import presentation.MainViewModel

@Composable
fun ApplicationScope.TabScreen(onShowCheckerClick: () -> Unit) {
	Window(
		onCloseRequest = ::exitApplication,
		title = "Talpa",
	) {
		val viewModel = remember { MainViewModel() }
		var tabIndex by remember { mutableStateOf(0) }

		val tabs = listOf("New packages", "Installed packages", "Settings")

		LaunchedEffect(Unit) {
			viewModel.getFeeds()
		}

		Column(modifier = Modifier.fillMaxWidth()) {
			TabRow(selectedTabIndex = tabIndex) {
				tabs.forEachIndexed { index, title ->
					Tab(text = { Text(title) },
						selected = tabIndex == index,
						onClick = { tabIndex = index }
					)
				}
			}
			when (tabIndex) {
				0 -> NewAddonsScreen(viewModel)
				1 -> InstalledScreen(viewModel)
				2 -> CheckerScreen()
			}
		}
	}
}