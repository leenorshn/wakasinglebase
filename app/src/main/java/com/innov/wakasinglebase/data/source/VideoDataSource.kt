package com.innov.wakasinglebase.data.source



import android.util.Log
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.cache.normalized.FetchPolicy
import com.apollographql.apollo3.cache.normalized.fetchPolicy
import com.innov.wakasinglebase.core.base.BaseResponse
import com.innov.wakasinglebase.data.mapper.toVideoModel
import com.innov.wakasinglebase.data.model.VideoModel
import com.wakabase.CreateVideoMutation
import com.wakabase.DeleteVideoMutation
import com.wakabase.LikeVideoMutation
import com.wakabase.VideoQuery
import com.wakabase.VideosQuery
import com.wakabase.type.NewVideoInput
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * Created by innov Victor on 3/18/2023.
 */
class VideoDataSource @Inject constructor(
  private val apolloClient: ApolloClient
){
    suspend fun createVideo(
        videoId: String,
        category: String,
        title: String,
        // author: UserModel,
        thumbnail:String,
        description: String
    ): Flow<BaseResponse<Boolean>> {


        return flow {
            emit(BaseResponse.Loading)

            val res = apolloClient.mutation(
                CreateVideoMutation(data= NewVideoInput(
                    description=description,
                    title=title,
                    category=category,
                    thumbnail=thumbnail,
                    // author="${author.uid}",
                    link=videoId,
                    hasTag = emptyList()
                ))).execute()

            if (res.hasErrors()){
                emit(BaseResponse.Error("Error when creating video"))
            }else{
                emit(BaseResponse.Success(true))
            }


        }.catch {
            emit(BaseResponse.Error("Error when creating video"))
        }
    }
    suspend fun likeVideo(
        videoId: String,
    ): Flow<BaseResponse<Boolean>> {


        return flow {
            emit(BaseResponse.Loading)

          val res = apolloClient.mutation(LikeVideoMutation(videoId)).execute()

            if (res.hasErrors()){
                emit(BaseResponse.Error("Error when liking a video"))
            }else{
                emit(BaseResponse.Success(true))
            }


        }.catch {
            emit(BaseResponse.Error("Error when liking video"))
        }
    }

    suspend fun deleteVideo(
        videoId: String,
    ): Flow<BaseResponse<Boolean>> {


        return flow {
            emit(BaseResponse.Loading)

            val res = apolloClient.mutation(DeleteVideoMutation(videoId)).execute()

            if (res.hasErrors()){
                emit(BaseResponse.Error("Error when liking a video"))
            }else{
                emit(BaseResponse.Success(true))
            }


        }.catch {
            emit(BaseResponse.Error("Error when creating video"))
        }
    }





    suspend fun fetchVideos(limit:Int): Flow<BaseResponse<List<VideoModel>>> {

        return flow {
            emit(BaseResponse.Loading)
       val resp= apolloClient.query(VideosQuery(limit)).fetchPolicy(FetchPolicy.NetworkFirst).execute()
           // Log.d("Manatane","${resp.data}")
        if(resp.hasErrors()){
            Log.d("Manatane",resp.errors.toString())
            emit(BaseResponse.Error("Error hard to load videos"))
        }
        if (resp.data?.videos!=null){
            val videos=resp.data?.videos?.map {
                //Log.d("Manatane",it.id)
                it.toVideoModel()

            }?: emptyList()


            emit(BaseResponse.Success(videos.shuffled()))

        }
        }.catch {
            emit(BaseResponse.Error("Error hard to load videos"))
        }
    }

    suspend fun fetchVideo(id:String): Flow<BaseResponse<VideoModel>> {

        return flow {
            emit(BaseResponse.Loading)
            val resp= apolloClient.query(VideoQuery(id)).fetchPolicy(FetchPolicy.NetworkFirst).execute()
            //Log.d("Manatane","${resp.data}")
            if(resp.hasErrors()){
               // Log.d("Manatane",resp.errors.toString())
                emit(BaseResponse.Error("Error hard to load videos"))
            }
            if (resp.data?.video!=null){
                val video=resp.data?.video?.toVideoModel()

video?.let {
    emit(BaseResponse.Success(it))
}


            }
        }.catch {
            emit(BaseResponse.Error("Error hard to load videos"))
        }
    }




    suspend fun fetchVideosOfParticularUser(userId: String): Flow<BaseResponse<List<VideoModel>>> {
        val videosList = arrayListOf<VideoModel>()


        fetchVideos(100).collect{
            when(it){
                is BaseResponse.Error -> {
                   // println(it.error)
                    "Error When loading videos"
                }
                BaseResponse.Loading -> {
                   // println(it)
                    true
                }
                is BaseResponse.Success -> {
                    videosList.addAll(it.data)
                }

            }
        }
        return flow {
            val userVideoList = videosList.filter { it.authorDetails?.uid == userId }
            emit(BaseResponse.Success(userVideoList))
        }
    }


}