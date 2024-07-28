package model

import java.util.Date

data class FeedItem(
	val title: String,
	val subtitle: String?,
	val content: String?,
	val imageUrl: String?,
	val date: Date,
	val link: String,
)
