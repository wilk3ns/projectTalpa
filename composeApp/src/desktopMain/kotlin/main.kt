import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.window.application
import presentation.components.CheckerScreen
import presentation.components.TabScreen
import ui.theme.TalpaTheme

fun main() = application {
	var isMainScreenShow by remember { mutableStateOf(true) }

	if (isMainScreenShow) {
		TalpaTheme{
			TabScreen(
				onShowCheckerClick = {
					isMainScreenShow = false
				}
			)
		}
	} else {
		CheckerScreen()
	}
}