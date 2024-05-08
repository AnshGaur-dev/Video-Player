package com.aarya.videoplayer.model
import kotlinx.serialization.Serializable
import java.util.Date // Import Date for timestamp mapping

@Serializable
data class VideoModel(
    val id: Long, // Change Int to Long for int8 mapping
    val title: String,
    val url: String,
    val channelName: String,
    val description: String,
    val likes: Long?, // Change Int to Long for int8 mapping
    val timestamp: Long
)


