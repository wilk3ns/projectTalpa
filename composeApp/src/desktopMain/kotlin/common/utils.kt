package common

import java.io.File
import java.net.URL

fun isValidURL(url: String?): Boolean {
	return try {
		URL(url).toURI()
		true
	} catch (e: Exception) {
		false
	}
}

fun getFolderModificationDate(folderPath: String): Long? {
	val file = File(folderPath)
	return if (file.exists()) file.lastModified() else null
}

fun getLocalAppDataPath(): String {
	return System.getenv("LOCALAPPDATA") ?: throw IllegalStateException("LOCALAPPDATA environment variable not found")
}

fun parseOptFile(filePath: String): Map<String, Any> {
	val resultMap = mutableMapOf<String, Any>()
	val file = File(filePath)

	if (!file.exists()) {
		throw IllegalArgumentException("File does not exist: $filePath")
	}

	val lines = file.readLines()
	val iterator = lines.iterator()
	parseLines(iterator, resultMap)
	return resultMap
}

fun parseLines(iterator: Iterator<String>, resultMap: MutableMap<String, Any>, isRoot: Boolean = true) {
	var currentSection: String? = null
	var sectionMap: MutableMap<String, Any>? = null

	while (iterator.hasNext()) {
		val line = iterator.next().trim()

		when {
			// Skip empty lines and comments
			line.isEmpty() || line.startsWith("//") -> { /* Do nothing */ }

			// Section start
			line.startsWith("{") -> {
				val sectionName = line.substring(1).trim()
				sectionMap = mutableMapOf()
				currentSection = sectionName
				parseLines(iterator, sectionMap, isRoot = false)
				resultMap[currentSection] = sectionMap
			}

			// Section end
			line.startsWith("}") -> return

			// Key-value pairs
			else -> {
				val parts = line.split(" ", limit = 2)
				if (parts.size == 2) {
					val key = parts[0].trim()
					val value = parts[1].trim().trim('"')
					if (isRoot) {
						resultMap[key] = value
					} else if (currentSection != null) {
						sectionMap?.put(key, value)
					}
				}
			}
		}
	}
}
