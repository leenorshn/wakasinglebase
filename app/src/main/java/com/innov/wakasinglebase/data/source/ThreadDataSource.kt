package com.innov.wakasinglebase.data.source

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.cache.normalized.FetchPolicy
import com.apollographql.apollo3.cache.normalized.fetchPolicy
import com.innov.wakasinglebase.core.base.BaseResponse
import com.innov.wakasinglebase.data.mapper.toThreadModel
import com.innov.wakasinglebase.data.model.ProductModel
import com.wakabase.AttacheVideoToProductMutation
import com.wakabase.CreateNewProductMutation
import com.wakabase.MyProductsQuery
import com.wakabase.ProductQuery
import com.wakabase.type.NewProduct

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ThreadDataSource @Inject constructor(
    private val apolloClient: ApolloClient,

){


    fun fetchMyProducts(): Flow<BaseResponse<List<ProductModel>>> {
        return flow {
            emit(BaseResponse.Loading)
            val resp= apolloClient.query(MyProductsQuery()).fetchPolicy(FetchPolicy.CacheAndNetwork).execute()
            if (resp.hasErrors()){
                emit(BaseResponse.Error("Error of loading ticket check your network"))
            }
            val threads=resp.data?.myProducts?.map {
                it.toThreadModel()
            }?: emptyList()
            emit(BaseResponse.Success(threads))
        }
    }

    fun fetchProduct(id:String): Flow<BaseResponse<ProductModel>> {
        return flow {
            emit(BaseResponse.Loading)
            val resp= apolloClient.query(ProductQuery(id)).fetchPolicy(FetchPolicy.CacheAndNetwork).execute()
            if (resp.hasErrors()){
                emit(BaseResponse.Error("Error of loading thread check your network"))
            }
            val thread=resp.data?.product?.toThreadModel()
           if (thread!=null){
               emit(BaseResponse.Success(thread))
           }else{
               emit(BaseResponse.Error("Error thread not found"))
           }

        }
    }
    fun createProduct(data:NewProduct): Flow<BaseResponse<Boolean>> {

        return flow {
            emit(BaseResponse.Loading)
            val resp= apolloClient.mutation(CreateNewProductMutation(data)).fetchPolicy(FetchPolicy.CacheAndNetwork).execute()
            if (resp.hasErrors()){
                emit(BaseResponse.Error("Error of loading ticket check your network"))
            }

            emit(BaseResponse.Success(true))
        }
    }

    fun attacheProduct(videoId:String, threadId:String): Flow<BaseResponse<Boolean>> {

        return flow {
            emit(BaseResponse.Loading)
            val resp= apolloClient.mutation(AttacheVideoToProductMutation(videoId,threadId)).execute()
            if (resp.hasErrors()){
                emit(BaseResponse.Error("Error of loading ticket check your network"))
            }
            emit(BaseResponse.Success(resp.data?.linkWithProduct==true))
        }
    }
}