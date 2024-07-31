package presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.PermanentDrawerSheet
import androidx.compose.material3.PermanentNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.window.ApplicationScope
import androidx.compose.ui.window.Window
import presentation.MainViewModel

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun ApplicationScope.TabScreen(onShowCheckerClick: () -> Unit) {
	Window(
		onCloseRequest = ::exitApplication,
		title = "Talpa",
	) {
		val viewModel = remember { MainViewModel() }
		val windowSizeClass = calculateWindowSizeClass()

		LaunchedEffect(Unit) {
			viewModel.getFeeds()
		}

		val selectedItem = mutableStateOf(0)
		val items = listOf("New", "Installed", "Settings")
		val icons = listOf(Icons.Filled.Home, Icons.Filled.Search, Icons.Filled.Settings)

		when (windowSizeClass.widthSizeClass) {
			WindowWidthSizeClass.Compact -> {
				CompactScreen(selectedItem, items, icons, viewModel)
			}

			WindowWidthSizeClass.Medium -> {
				MediumScreen(selectedItem, items, icons, viewModel)
			}

			WindowWidthSizeClass.Expanded -> {
				ExpandedScreen(selectedItem, items, icons, viewModel)
			}
		}

	}
}

@Composable
fun CompactScreen(
	selectedItem: MutableState<Int>,
	items: List<String>,
	icons: List<ImageVector>,
	viewModel: MainViewModel
) {
	Scaffold(
		bottomBar = {
			NavigationBar {
				items.forEachIndexed { index, item ->
					NavigationBarItem(
						icon = { Icon(icons[index], contentDescription = item) },
						label = { Text(item) },
						selected = selectedItem.value == index,
						onClick = {
							selectedItem.value = index
						}
					)
				}
			}
		}
	) {
		Box(modifier = Modifier.fillMaxSize()) {
			when (selectedItem.value) {
				0 -> NewAddonsScreen(viewModel)
				1 -> InstalledScreen(viewModel)
				2 -> CheckerScreen()
			}
		}

	}
}

@Composable
fun MediumScreen(
	selectedItem: MutableState<Int>,
	items: List<String>,
	icons: List<ImageVector>,
	viewModel: MainViewModel
) {
	Row {
		NavigationRail {
			items.forEachIndexed { index, item ->
				NavigationRailItem(
					icon = { Icon(icons[index], contentDescription = item) },
					label = { Text(item) },
					selected = selectedItem.value == index,
					onClick = {
						selectedItem.value = index
					}
				)
			}
		}
		Box(modifier = Modifier.fillMaxSize()) {
			when (selectedItem.value) {
				0 -> NewAddonsScreen(viewModel)
				1 -> InstalledScreen(viewModel)
				2 -> CheckerScreen()
			}
		}
	}
}

@Composable
fun ExpandedScreen(
	selectedItem: MutableState<Int>,
	items: List<String>,
	icons: List<ImageVector>,
	viewModel: MainViewModel
) {
	PermanentNavigationDrawer(
		drawerContent = {
			PermanentDrawerSheet {
				items.forEachIndexed { index, item ->
					NavigationDrawerItem(
						icon = { Icon(icons[index], contentDescription = item) },
						label = { Text(item) },
						selected = selectedItem.value == index,
						onClick = {
							selectedItem.value = index
						}
					)
				}
			}
		},
		content = {
			when (selectedItem.value) {
				0 -> NewAddonsScreen(viewModel)
				1 -> InstalledScreen(viewModel)
				2 -> CheckerScreen()
				else -> {}
			}
		}
	)
}