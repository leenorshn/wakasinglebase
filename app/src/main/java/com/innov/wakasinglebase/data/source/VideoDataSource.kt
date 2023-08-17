package com.innov.wakasinglebase.data.source


import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.snapshots
import com.innov.wakasinglebase.data.model.UserModel
import com.innov.wakasinglebase.data.model.VideoModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

/**
 * Created by innov Victor on 3/18/2023.
 */
object VideoDataSource{



val db=FirebaseFirestore.getInstance()



    suspend fun fetchVideos(): Flow<List<VideoModel>> {
        var listData:List<VideoModel>?=null
       val snap= db.collection("videos").get().await()

         listData=  snap.documents.map {
               // Log.d("ORIO",it.data.toString())
               var authDetails: UserModel? = null
               //uIudd2raZaZwnmN2duKedfTD4U43
               //uIudd2raZaZwnmN2duKedfTD4U43
               //Log.e("Waka", ">>>${it.data?.get("authorDetails")?.toString()}")
               UsersDataSource.fetchSpecificUser(it.data?.get("authorDetails").toString())
                   .collect { d ->
                       if (d != null) {
                           authDetails = UserModel(
                               uid = d.uid,
                               name = d.name,
                               profilePic = d.profilePic,
                               email = d.email,
                               city = d.city,
                               bio = d.bio,
                               uniqueUserName = d.uniqueUserName,
                               phone = d.phone,
                               isVerified = d.isVerified,
                               hasContract = d.hasContract,
                               balance = d.balance
                           )
                       }
                   }


                   VideoModel(
                       videoId = it.data?.get("videoId").toString(),
                       videoLink = it.data?.get("videoLink").toString(),
                       description = it.data?.get("description").toString(),
                       authorDetails = authDetails,
                       videoStats = VideoModel.VideoStats(
                           like = it.data?.get("like").toString().toLong() ,
                           share = it.data?.get("share").toString().toLong(),
                           comment = 0,
                           views =  100
                       )

               )

           }




        return flow {
            listData?.let { emit(it.shuffled()) }
        }
    }



    suspend fun fetchVideosOfParticularUser(userId: String): Flow<List<VideoModel>> {
        val videosList = arrayListOf<VideoModel>()


        fetchVideos().collect{
            videosList.addAll(it)
        }
        return flow {
            val userVideoList = videosList.filter { it.authorDetails?.uid == userId }
            emit(userVideoList)
        }
    }
}