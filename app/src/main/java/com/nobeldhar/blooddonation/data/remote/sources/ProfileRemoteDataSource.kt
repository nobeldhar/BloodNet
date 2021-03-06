package com.nobeldhar.blooddonation.data.remote.sources

import com.nobeldhar.blooddonation.data.remote.requests.CreatePostRequest
import com.nobeldhar.blooddonation.data.remote.services.ProfileService
import okhttp3.MultipartBody
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProfileRemoteDataSource @Inject constructor(
    private val profileService: ProfileService
): BaseDataSource() {

    suspend fun getBloodList() =
        getResult { profileService.getBloodList() }

    suspend fun getDistricts()  =
        getResult { profileService.getDistricts() }

    suspend fun getThanas(i: Int)  =
        getResult { profileService.getThanas(i) }

    suspend fun getCities(i: Int)  =
        getResult { profileService.getCities(i) }

    suspend fun getTimeLinePosts()  =
            getResult { profileService.getTimeLinePosts() }

    suspend fun createPost(createPostRequest: CreatePostRequest) =
        getResult { profileService.createPost(createPostRequest) }

    suspend fun uploadProfileImage(part: MultipartBody.Part) =
        getResult { profileService.uploadProfileImage(part) }

    suspend fun getProfileImage() = getResult { profileService.getProfileImage() }
    suspend fun likePost(id: Int) = getResult { profileService.likePost(id) }

}