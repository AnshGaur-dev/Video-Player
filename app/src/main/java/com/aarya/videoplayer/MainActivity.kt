package com.aarya.videoplayer

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import com.aarya.videoplayer.data.remote.VideoRepoImpl
import com.aarya.videoplayer.model.VideoModel
import com.aarya.videoplayer.module.supabase
import com.aarya.videoplayer.ui.theme.VideoPlayerTheme
import com.aarya.videoplayer.views.HomeScreen
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MainActivity : ComponentActivity() {
    private val videoRepo = VideoRepoImpl(postgrest = supabase.postgrest)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            VideoPlayerTheme {
                HomeScreen(remoteRepo = videoRepo)
            }
        }
//        fetchVideos()
//        storeVideo()
    }

    private fun fetchVideos() {
        Log.d("MainActivity", "fetchVideos() called") // Add this line for debugging
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val videos = videoRepo.getAllVideos()
                Log.d("VideoRepo", "Videos: $videos")
                Log.d("MainActivity", "Videos fetched successfully") // Add this line for debugging
            } catch (e: Exception) {
                Log.e("VideoRepo", "Error fetching videos: ${e.message}")
                Log.e("MainActivity", "Error fetching videos: ${e.message}") // Add this line for debugging
            }
        }
    }
    private fun storeVideo() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val video = VideoModel(
                    id = 8,
                    title = "Hello Supabase",
                    description = "hello supabase users",
                    channelName = "Aarya",
                    likes = 0,
                    url = "abcd",
                    timestamp = System.currentTimeMillis()
                )
                val success = videoRepo.storeVideos(video)
                if (success) {
                    Log.d("VideoRepo", "Video stored successfully")
                    Log.d("MainActivity", "Video stored successfully")
                } else {
                    Log.e("VideoRepo", "Failed to store video")
                    Log.e("MainActivity", "Error storing video: Unknown Error ")
                }
            } catch (e: Exception) {
                Log.e("VideoRepo", "Error storing video: ${e.message}")
                Log.e("MainActivity", "Error storing video: ${e.message}")
            }
        }
    }
}