package model

import com.google.gson.annotations.SerializedName

data class AddonNeutral(
	@SerializedName("LastUpdate")
	val lastUpdate: String?,
	@SerializedName("OlderHistory")
	val olderHistory: String?,
)
