package common

import com.google.gson.GsonBuilder
import model.AddonManifest
import model.SemVer
import java.io.File
import java.net.URL
import java.nio.file.Files
import java.nio.file.Path
import java.util.Date

fun isValidURL(url: String?): Boolean {
	return try {
		URL(url).toURI()
		true
	} catch (e: Exception) {
		false
	}
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

fun searchManifests(directory: Path, manifestFiles: MutableList<AddonManifest>) {
	Files.walk(directory).use { stream ->
		stream.filter { Files.isRegularFile(it) && it.fileName.toString() == "manifest.json" }
			.forEach { file ->
				val manifest = parseManifest(file)
				if (manifest != null) {
					manifestFiles.add(manifest)
				}
			}
	}
}

fun parseManifest(file: Path): AddonManifest? {
	return try {
		val jsonContent = Files.readString(file)
		val gson = GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").create()
		val manifest = gson.fromJson(jsonContent, AddonManifest::class.java)

		// Get the last modified date and set it in the manifest
		val lastModified = Date(Files.getLastModifiedTime(file).toMillis())
		val normalizedName = generateNormalizedName(manifest.title, manifest.manufacturer, manifest.packageVersion)

		// Create a new Manifest object with the fileModified date set
		AddonManifest(
			dependencies = manifest.dependencies,
			minimumGameVersion = manifest.minimumGameVersion,
			title = manifest.title,
			packageVersion = manifest.packageVersion,
			creator = manifest.creator,
			manufacturer = manifest.manufacturer,
			releaseNotes = manifest.releaseNotes,
			contentType = manifest.contentType,
			fileModified = lastModified,
			normalizedName = normalizedName.lowercase()
		)
	} catch (e: Exception) {
		e.printStackTrace()
		null
	}
}

fun String.toSemanticVersion(): SemVer {
	val parts = this.split(".")
	require(parts.size == 3) { "Invalid version format" }
	val (major, minor, patch) = parts.map { it.toInt() }
	return SemVer(major, minor, patch)
}

fun String.extractVersion(): SemVer {
	val versionRegex = Regex("""v(\d+)\.(\d+)\.(\d+)$""")
	val matchResult = versionRegex.find(this)
	require(matchResult != null) { "Version not found in the text" }

	val (major, minor, patch) = matchResult.destructured
	return SemVer(major.toInt(), minor.toInt(), patch.toInt())
}

fun generateNormalizedName(title: String, manufacturer: String, packageVersion: String): String {
	val regex = Regex("\\b${Regex.escape(manufacturer)}\\b")
	val restOfTitle = regex.split(title, limit = 2).getOrElse(1) { "" }.trim()
	return "$manufacturer - $restOfTitle v$packageVersion"
}
