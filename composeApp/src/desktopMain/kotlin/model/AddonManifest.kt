package model

import com.google.gson.annotations.SerializedName
import java.util.Date


data class AddonManifest(
	val dependencies: List<Any?>,
	@SerializedName("content_type")
	val contentType: String,
	val title: String,
	val manufacturer: String,
	val creator: String,
	@SerializedName("package_version")
	val packageVersion: String,
	@SerializedName("minimum_game_version")
	val minimumGameVersion: String,
	@SerializedName("release_notes")
	val releaseNotes: AddonReleaseNotes,
	val fileModified: Date?,
	val normalizedName: String?
)

