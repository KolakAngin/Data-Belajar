package com.dcfanbase.storyapp.data.api

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

interface ApiService {

    @FormUrlEncoded
    @POST("register")
    suspend fun register(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): RegisterResponse

    @FormUrlEncoded
    @POST("login")
    suspend fun login(
        @Field("email") name: String,
        @Field("password") email: String,
    ): LoginResponse

    @GET("stories")
    suspend fun getStories(): StoryResponse

    @Multipart
    @POST("stories")
    suspend fun uploadImage(
        @Part file: MultipartBody.Part,
        @Part("description") description: RequestBody,
    ): FileUploadResponse

    @Multipart
    @POST("stories")
    fun uploadImageKu(
        @Header("Authorization") token: String,
        @Part file: MultipartBody.Part,
        @Part("description") description: RequestBody,
    ): retrofit2.Call<FileUploadResponse>


    @Multipart
    @POST("stories")
    fun uploadImageKuWithLocation(
        @Header("Authorization") token: String,
        @Part file: MultipartBody.Part,
        @Part("description") description: RequestBody,
        @Part("lat") lat: Double?,
        @Part("lon") lng: Double?,
    ): retrofit2.Call<FileUploadResponse>

    @GET("stories")
    suspend fun getStoriesKu(
        @Header("Authorization") token: String,
    ) : StoryResponse

    @GET("stories")
    suspend fun getStoriesKuWithPageAndSize(
        @Query("page") page: Int = 1,
        @Query("size") size: Int = 20
    ): StoryResponse


    @GET("stories")
    suspend fun getStoriesKuForMap(
        @Header("Authorization") token: String,
        @Query("location") location : Int = 1
    ) : StoryResponse

}