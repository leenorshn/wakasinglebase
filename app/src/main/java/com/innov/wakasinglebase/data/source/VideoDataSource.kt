package com.innov.wakasinglebase.data.source

import com.innov.wakasinglebase.data.model.VideoModel



import com.innov.wakasinglebase.data.source.UsersDataSource.kylieJenner

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * Created by innov Victor on 3/18/2023.
 */
object VideoDataSource {

    object KylieJennerVideos {
        val kylie_vid1 = VideoModel(
            videoId = "vi403lvEZHTXAehMMNSkBQYq",
            authorDetails = kylieJenner,
            videoLink = "https://firebasestorage.googleapis.com/v0/b/wakainnov.appspot.com/o/dua_vid2.mp4?alt=media&token=ba33da5f-f312-476e-97f5-fbaf4cc2bb96",
            videoStats = VideoModel.VideoStats(
                like = 409876,
                comment = 8356,
                share = 3000,
                favourite = 1500
            ),
            description = "Draft video testing  #foryou #fyp #compose #tik",
            audioModel = null, hasTag = listOf(),
        )
        val kylie_vid2 = VideoModel(
            videoId = "kylie_vid2",
            authorDetails = kylieJenner,
            videoLink = "https://firebasestorage.googleapis.com/v0/b/wakainnov.appspot.com/o/daniel_vid5.mp4?alt=media&token=9dcae006-c667-4170-83fa-5bfbeff70960",
            videoStats = VideoModel.VideoStats(
                like = 564572,
                comment = 8790,
                share = 2000,
                favourite = 1546
            ),
            description = "Draft video testing  #foryou #fyp #compose #tik",
            audioModel = null, hasTag = listOf(),
        )
//        val kylie_vid3 = VideoModel(
//            videoId = "kylie_vid3",
//            authorDetails = kylieJenner,
//            videoLink = "kylie_vid3.mp4",
//            videoStats = VideoModel.VideoStats(
//                like = 2415164,
//                comment = 5145,
//                share = 5000,
//                favourite = 2000
//            ),
//            description = "Draft video testing  #foryou #fyp #compose #tik",
//            audioModel = null, hasTag = listOf(),
//        )
        val kylie_vid4 = VideoModel(
            videoId = "kylie_vid4",
            authorDetails = kylieJenner,
            videoLink = "https://firebasestorage.googleapis.com/v0/b/wakainnov.appspot.com/o/dua_vid2.mp4?alt=media&token=ba33da5f-f312-476e-97f5-fbaf4cc2bb96",
            videoStats = VideoModel.VideoStats(
                like = 51626,
                comment = 1434,
                share = 167,
                favourite = 633
            ),
            description = "Draft video testing  #foryou #fyp #compose #tik",
            audioModel = null, hasTag = listOf(),
        )
        val kylie_vid5 = VideoModel(
            videoId = "kylie_vid5",
            authorDetails = kylieJenner,
            videoLink = "https://firebasestorage.googleapis.com/v0/b/wakainnov.appspot.com/o/dua_vid1.mp4?alt=media&token=a3b18d85-56ff-4a62-bfbd-8851988bfed1",
            videoStats = VideoModel.VideoStats(
                like = 547819,
                comment = 79131,
                share = 8921,
                favourite = 2901
            ),
            description = "Draft video testing  #foryou #fyp #compose #tik",
            audioModel = null, hasTag = listOf(),
        )
        val kylie_vid6 = VideoModel(
            videoId = "vi403lvEZHTXAehMMNSkBQYq",
            authorDetails = kylieJenner,
            videoLink = "https://firebasestorage.googleapis.com/v0/b/wakainnov.appspot.com/o/charlieputh_vid1.mp4?alt=media&token=a18b751e-1417-4741-b7a0-0480aa099c2d",
            videoStats = VideoModel.VideoStats(
                like = 4512340,
                comment = 65901,
                share = 8165,
                favourite = 154
            ),
            description = "Draft video testing  #foryou #fyp #compose #tik",
            audioModel = null, hasTag = listOf(),
        )

        val kylie_vid7 = VideoModel(
            videoId = "kylie_vid7",
            authorDetails = kylieJenner,
            videoLink = "https://firebasestorage.googleapis.com/v0/b/wakainnov.appspot.com/o/daniel_vid4.mp4?alt=media&token=18e004a0-0635-415f-a981-70d3164cc8bf",
            videoStats = VideoModel.VideoStats(
                like = 612907,
                comment = 7643,
                share = 1291,
                favourite = 890
            ),
            description = "Draft video testing  #foryou #fyp #compose #tik",
            audioModel = null, hasTag = listOf(),
        )

        val kylieVideosList = listOf(
            kylie_vid1,
            kylie_vid2,
            //kylie_vid3,
            kylie_vid4,
            kylie_vid5,
            kylie_vid6,
            kylie_vid7
        )
    }












    val videosList = arrayListOf<VideoModel>().apply {
        addAll(KylieJennerVideos.kylieVideosList)

    }

    fun fetchVideos(): Flow<List<VideoModel>> {
        return flow {
            emit(videosList.shuffled())
        }
    }

    fun fetchVideosOfParticularUser(userId: String): Flow<List<VideoModel>> {
        return flow {
            val userVideoList = videosList.filter { it.authorDetails.uid == userId }
            emit(userVideoList)
        }
    }
}