package com.innov.wakasinglebase.data.source



import com.apollographql.apollo3.ApolloClient
import com.innov.wakasinglebase.core.base.BaseResponse
import com.innov.wakasinglebase.data.mapper.toVideoModel
import com.innov.wakasinglebase.data.model.UserModel
import com.innov.wakasinglebase.data.model.VideoModel
import com.wakabase.CreateVideoMutation
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
        author: UserModel,
        description: String
    ): Flow<BaseResponse<Boolean>> {


        return flow {
            emit(BaseResponse.Loading)
            apolloClient.mutation(CreateVideoMutation(data= NewVideoInput(
                description=description,
                title=title,
                category=category,
                author="${author.uid}",
                link="https://d2y4y6koqmb0v7.cloudfront.net/$videoId",
                hasTag = emptyList()
            )

            ))
            emit(BaseResponse.Success(true))

        }.catch {
            emit(BaseResponse.Error("some think want rong"))
        }
    }





    suspend fun fetchVideos(limit:Int): Flow<BaseResponse<List<VideoModel>>> {
        var listData:List<VideoModel>?=null


        return flow {
            emit(BaseResponse.Loading)
       var resp= apolloClient.query(VideosQuery(limit)).execute()

        if(resp.hasErrors()){
            emit(BaseResponse.Error("Error hard to load videos"))
        }
        if (resp.data?.videos!=null){
            var videos=resp.data?.videos?.map { it.toVideoModel() }?: emptyList()
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
                    println()
                }
                BaseResponse.Loading -> {
                    println()
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