package com.aarya.videoplayer.data.remote

import android.util.Log
import com.aarya.videoplayer.model.VideoModel
import io.github.jan.supabase.postgrest.Postgrest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class VideoRepoImpl (
    private val postgrest: Postgrest
) : VideoRepo {
    override suspend fun getAllVideos(): List<VideoModel> {
        return withContext(Dispatchers.IO) {
                try {
                    postgrest.from("videos")
                        .select()
                        .decodeList()
                } catch (e: Exception) {
                    Log.e("VideoRepoImpl", "Error fetching videos: ${e.message}")
                    emptyList() // Return an empty list in case of an error
                }
        }
    }


    override suspend fun getVideoByTitle(): VideoModel {
        TODO("Not yet implemented")
    }

    override suspend fun storeVideos(video: VideoModel): Boolean {
        return try {
            withContext(Dispatchers.IO) {
                postgrest.from("videos").insert(video)
                true
            }
        } catch (e: Exception) {
            Log.e("VideoRepoImpl", "Error storing video: ${e.message}")
            false
        }
    }
}