package repository

import com.prof18.rssparser.RssParser
import model.Feed
import model.FeedItem
import java.text.SimpleDateFormat
import java.time.Instant
import java.util.Date
import java.util.Locale

class FeedRepository(
	private val parser: RssParser,
) {
	@Throws(Throwable::class)
	suspend fun getFeed(url: String): Feed {
		val dateFormat = SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z", Locale.ENGLISH)
		val channel = parser.getRssChannel(url)
		return Feed(
			title = channel.title ?: "",
			items = channel.items.mapNotNull {
				val title = it.title
				val subtitle = it.description
				val pubDate = it.pubDate

				if (title == null || pubDate == null) {
					return@mapNotNull null
				}

				FeedItem(
					title = title,
					subtitle = subtitle,
					content = it.content,
					imageUrl = it.image,
					date = dateFormat.parse(it.pubDate),
					link = it.link ?: "",
				)
			}
		)
	}
}