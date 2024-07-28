package presentation

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import common.DI

import model.UIState

class MainViewModel {

	private val url = "https://simplaza.org/feed/"

	private val feedRepository = DI.feedRepository

	private val _uiState: MutableState<UIState> = mutableStateOf(UIState())
	val uiState: State<UIState> = _uiState

	suspend fun getFeeds() {
		_uiState.value = UIState(
			isLoading = true,
		)
		try {
			val feed = feedRepository.getFeed(url)
			_uiState.value = UIState(
				isLoading = false,
				feed = feed
			)
		} catch (e: Exception) {
			println("Something wrong while getting the feeds")
			e.printStackTrace()
			_uiState.value = UIState(
				isLoading = false,
				error = "Something wrong"
			)
		}
	}
}