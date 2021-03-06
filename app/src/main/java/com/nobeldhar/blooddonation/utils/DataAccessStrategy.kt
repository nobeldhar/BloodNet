package com.nobeldhar.blooddonation.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.nobeldhar.blooddonation.data.remote.responses.AuthResponse
import kotlinx.coroutines.Dispatchers

fun <T, A> performGetOperation(
        databaseQuery: () -> LiveData<T>,
        networkCall: suspend () -> Resource<A>,
        saveCallResult: suspend (A) -> Unit
): LiveData<Resource<T>> =
        liveData(Dispatchers.IO) {
            emit(Resource.loading())
            val source = databaseQuery.invoke().map { Resource.success(it) }
            emitSource(source)

            val responseStatus = networkCall.invoke()
            if (responseStatus.status == Resource.Status.SUCCESS) {
                responseStatus
                saveCallResult(responseStatus.data!!)
            } else if (responseStatus.status == Resource.Status.ERROR) {
                emit(Resource.error(responseStatus.message!!, isNetworkError = responseStatus.isNetworkError))
                emitSource(source)
            }
        }

fun performAuthOperation(
        networkCall: suspend () -> Resource<AuthResponse>,
        saveAuthInfo: (AuthResponse) -> Unit
): LiveData<Resource<AuthResponse>> =
        liveData(Dispatchers.IO) {
            emit(Resource.loading())

            val responseStatus  = networkCall.invoke()
            if (responseStatus.status == Resource.Status.SUCCESS) {
                emit(responseStatus)
                if (responseStatus.data?.status!!) {
                    saveAuthInfo(responseStatus.data)
                }
            } else if (responseStatus.status == Resource.Status.ERROR) {
                emit(responseStatus)
            }


        }

fun <T> performAuthOperation(
    networkCall: suspend () -> Resource<T>
): LiveData<Resource<T>> =
    liveData(Dispatchers.IO) {
        emit(Resource.loading())

        val responseStatus  = networkCall.invoke()
        if (responseStatus.status == Resource.Status.SUCCESS) {
            emit(responseStatus)

        } else if (responseStatus.status == Resource.Status.ERROR) {
            emit(responseStatus)
        }
    }