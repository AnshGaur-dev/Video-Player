package com.aarya.videoplayer.data.remote

import com.aarya.videoplayer.model.VideoModel

interface  VideoRepo{
    suspend fun getAllVideos(): List<VideoModel>
    suspend fun getVideoByTitle(): VideoModel
    suspend fun storeVideos(video: VideoModel): Boolean

}