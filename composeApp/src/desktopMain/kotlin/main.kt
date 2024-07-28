import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.window.application
import common.getLocalAppDataPath
import common.parseOptFile
import presentation.components.CheckerScreen
import presentation.components.MainScreen

fun main() = application {

	val localAppDataPath = getLocalAppDataPath()
	val filePath = "$localAppDataPath\\Packages\\Microsoft.FlightSimulator_8wekyb3d8bbwe\\LocalCache\\UserCfg.opt"
	val configMap = parseOptFile(filePath)

	println("${configMap["InstalledPackagesPath"]}\\Community)")



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