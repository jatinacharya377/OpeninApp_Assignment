package com.assignment.openinapp.data.repository

import com.assignment.openinapp.data.api.ApiBuilder
import com.assignment.openinapp.data.api.OpenInAppApi
import retrofit2.Response

abstract class RepositoryBase {

    private val api by lazy { ApiBuilder().getApi() }

    suspend fun <T> makeApiCall(call: suspend (api: OpenInAppApi) -> Response<T>) : Response<T> {
        return call.invoke(api)
    }

    companion object {
        private val instances = mutableListOf<RepositoryBase>()
        fun <T: RepositoryBase> getInstance(entityClass: Class<T>) : T {
            return instances.firstOrNull {
                entityClass.isInstance(it)
            }?.let {
                it as T
            } ?: run {
                val instance = entityClass.getDeclaredConstructor().newInstance()
                instances.add(instance)
                instance
            }
        }
    }
}