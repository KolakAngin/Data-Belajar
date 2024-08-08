package com.dcfanbase.storyapp.view.posting

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.dcfanbase.storyapp.data.api.ApiConfig
import com.dcfanbase.storyapp.data.api.FileUploadResponse
import com.dcfanbase.storyapp.data.pref.UserModel
import com.dcfanbase.storyapp.data.repository.UserRepository
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response
import java.io.File

class PostViewModel(private val repository: UserRepository) : ViewModel() {


    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> = _isLoading

    private val _succesResponse : MutableLiveData<Boolean> = MutableLiveData()
    val succesResponse : LiveData<Boolean> = _succesResponse
    fun getSession() : LiveData<UserModel>{
        return repository.getSession().asLiveData()
    }

    fun uploadImageKu(description: String,imgUri: File,token : String){
        _isLoading.value = true
        val requestBody = description.toRequestBody("text/plain".toMediaType())
        val requestImageFIle = imgUri.asRequestBody("image/jpeg".toMediaTypeOrNull())
        val multiPart = MultipartBody.Part.createFormData(
            "photo",
            imgUri.name,
            requestImageFIle
        )
        val client = ApiConfig.getServiceWithOutToken.uploadImageKu(token,multiPart,requestBody)

        client.enqueue(object : Callback<FileUploadResponse>{
            override fun onResponse(
                call: Call<FileUploadResponse>,
                response: Response<FileUploadResponse>
            ) {
                val respBody = response.body()
                Log.d("POST STORY RESPONSE",response.message())
                _succesResponse.value = respBody?.error
                _isLoading.value = false
            }

            override fun onFailure(call: Call<FileUploadResponse>, t: Throwable) {
                Log.d("POST STORY RESPONSE FAILED",t.message.toString())
                _isLoading.value = false
            }

        })
    }

    fun uploadFileImage(description : String, imgUri: File){
        _isLoading.value = true
        val requestBody = description.toRequestBody("text/plain".toMediaType())
        val requestImageFIle = imgUri.asRequestBody("image/jpeg".toMediaType())
        val multiPart = MultipartBody.Part.createFormData(
            "photo",
            imgUri.name,
            requestImageFIle
        )
        viewModelScope.launch {
            try{
                val client = repository.getApiService().uploadImage(multiPart,requestBody)
                _succesResponse.postValue(client.error)
                Log.d("POST STORY", client.message)
                _isLoading.postValue(false)
            }catch (e : HttpException){
                e.printStackTrace()
                _isLoading.postValue(false)
            }
        }
    }
}