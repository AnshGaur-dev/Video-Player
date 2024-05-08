package com.aarya.videoplayer.views


import android.media.browse.MediaBrowser
import android.net.Uri
import android.util.Log
import android.view.Display.Mode
import android.widget.VideoView
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.aarya.videoplayer.data.remote.VideoRepo
import com.aarya.videoplayer.model.VideoModel
import com.aarya.videoplayer.module.supabase
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.ui.PlayerView
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun HomeScreen(remoteRepo: VideoRepo) {
    var searchQuery by remember { mutableStateOf("") }
    var videos by remember { mutableStateOf(emptyList<VideoModel>()) }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            try {
                val fetchedVideos = remoteRepo.getAllVideos()
                videos = fetchedVideos
            } catch (e: Exception) {
                // Handle error
            }
        }
    }

    Column(
        modifier = Modifier
            .padding(
                start = 16.dp,
                end = 16.dp,
                top = 4.dp,
                bottom = 8.dp
                )
            .fillMaxSize()
    ) {
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            label = { Text("Search videos") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {

                items(videos.size) { index ->
                    val video = videos[index]
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .padding(bottom = 8.dp)
                    ){
                        VideoItem(video)
                    }

                }


        }
    }
}


@Composable
fun VideoPlayer(
    videoUrl: String,
    modifier: Modifier = Modifier
) {
    Log.d("videoUrl", videoUrl)
    val context = LocalContext.current
    val exoPlayer = remember {
//        ExoPlayer.Builder(context).build().apply {
//            val mediaItem = MediaBrowser.MediaItem.fromUri(videoUrl)
//            setMediaItem(mediaItem)
//            prepare()
//        }
        ExoPlayer.Builder(context)
            .build()
            .apply {
                val mediaItem = MediaItem.fromUri(videoUrl)
                setMediaItem(mediaItem)
                prepare()
//                play()
            }
    }

    DisposableEffect(Unit) {
        onDispose {
            exoPlayer.release()
        }
    }

    AndroidView(
        factory = { context ->
            PlayerView(context).apply {
                player = exoPlayer
            }
        },
        modifier = modifier
    )
}

@Composable
fun VideoItem(video: VideoModel) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Log.d("videoUrl from video item", video.url)
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(12.dp))
        ) {
            VideoPlayer(
                videoUrl = video.url,
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(200.dp)
                    .clip(RoundedCornerShape(12.dp)) // Apply clip to VideoPlayer
            )
        }
        Text(
            text = video.title,
            style = MaterialTheme.typography.titleLarge
        )
        Text(
            text = "Channel: ${video.channelName}",
            style = MaterialTheme.typography.bodySmall
        )
        Text(
            text = "Likes: ${video.likes}",
            style = MaterialTheme.typography.bodySmall
        )
        Text(
            text = "Timestamp: ${formatTimestamp(video.timestamp)}",
            style = MaterialTheme.typography.bodySmall
        )
        Text(
            text = video.description,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}

@Composable
fun formatTimestamp(timestamp: Long): String {
    val date = Date(timestamp)
    val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
    return format.format(date)
}