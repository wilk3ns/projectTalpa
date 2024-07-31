package presentation

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import common.Constants.CONFIG_FILE_NAME
import common.DI
import common.Constants.MSFS_PACKAGE_PATH
import common.extractVersion
import common.getLocalAppDataPath
import common.parseOptFile
import common.searchManifests
import common.toSemanticVersion
import model.AddonManifest
import model.Feed
import model.FeedItem
import model.UIState
import java.nio.file.Path
import java.nio.file.Paths


class MainViewModel {

	private val url = "https://flightsim.to/rss"
	private val feedRepository = DI.feedRepository
	private val configFilePath =
		Paths.get(getLocalAppDataPath(), MSFS_PACKAGE_PATH, CONFIG_FILE_NAME).toString()
	private val configMap = parseOptFile(configFilePath)
	private val communityFolderPath =
		Path.of(configMap["InstalledPackagesPath"].toString(), "Community")
	private val manifestFiles = mutableListOf<AddonManifest>()
	private val _uiState: MutableState<UIState> = mutableStateOf(UIState())
	val uiState: State<UIState> = _uiState

	suspend fun getFeeds() {
		_uiState.value = UIState(
			isLoading = true,
		)
		try {
			val feed = feedRepository.getFeed(url)
			searchManifests(communityFolderPath, manifestFiles)
			var installedFeed: Feed? = null
			val feedItemsList: MutableList<FeedItem> = emptyList<FeedItem>().toMutableList()
			feed.items.forEach { feedItem ->
				manifestFiles.forEach { manifest ->
					if (manifest.normalizedName == feedItem.title.lowercase()) {
						feedItemsList.add(feedItem)
						if (feedItem.title.extractVersion() == manifest.packageVersion.toSemanticVersion()) {
							println("Package is up to date!")
						}
					}
				}
				installedFeed = Feed(title = feed.title, items = feedItemsList.toList())
			}
			_uiState.value = UIState(
				isLoading = false,
				newFeed = feed,
				installedFeed = installedFeed
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