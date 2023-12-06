package com.innov.wakasinglebase.data.source



import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.cache.normalized.FetchPolicy
import com.apollographql.apollo3.cache.normalized.fetchPolicy
import com.innov.wakasinglebase.core.base.BaseResponse
import com.innov.wakasinglebase.data.mapper.toVideoModel
import com.innov.wakasinglebase.data.model.VideoModel
import com.wakabase.CreateVideoMutation
import com.wakabase.DeleteVideoMutation
import com.wakabase.LikeVideoMutation
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
            emit(BaseResponse.Error("Error when creating video"))
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
        var listData:List<VideoModel>?=null
//        val client = ApiVideoClient("RtSnicPKEEeYc3KWVNW31UIqKhayYt1KX61gy1XZlfC")
//        val videos=client.videos()
//
//val video=videos.get("vi70Egph6GaUpgPqvVK9uGyt")

       // Log.e("VIDEO",video.videoId+video.title)

        return flow {
            emit(BaseResponse.Loading)
       var resp= apolloClient.query(VideosQuery(limit)).fetchPolicy(FetchPolicy.NetworkFirst).execute()

        if(resp.hasErrors()){
            emit(BaseResponse.Error("Error hard to load videos"))
        }
        if (resp.data?.videos!=null){
            var videos=resp.data?.videos?.map {
                it.toVideoModel() }?: emptyList()

            emit(BaseResponse.Success(videos.shuffled()))

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
                    println(it.error)
                    it.error
                }
                BaseResponse.Loading -> {
                    println(it)
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