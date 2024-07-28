import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.window.application
import presentation.components.CheckerScreen
import presentation.components.MainScreen

fun main() = application {
	var isMainScreenShow by remember { mutableStateOf(true) }

	if (isMainScreenShow) {
		MainScreen(
			onShowCheckerClick = {
				isMainScreenShow = false
			}
		)
	} else {
		CheckerScreen()
	}
}