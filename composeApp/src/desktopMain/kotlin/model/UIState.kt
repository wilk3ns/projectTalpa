package model

data class UIState(
	val isLoading: Boolean = true,
	val newFeed: Feed? = null,
	val installedFeed: Feed? = null,
	val error: String? = null
)
