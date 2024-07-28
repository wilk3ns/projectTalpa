package common

import com.prof18.rssparser.RssParserBuilder
import repository.FeedRepository

object DI {
	val feedRepository = FeedRepository(
		parser = RssParserBuilder().build()
	)
}