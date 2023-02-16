package com.artemzu.githubapp.network.retrofit.repositories

import com.artemzu.githubapp.network.model.RepositoriesResponse
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query
import javax.inject.Inject

interface RepositoriesNetworkApi {

    @Headers("Accept: application/json")
    @GET("search/repositories")
    suspend fun searchRepositories(
        @Query("q") query: String,
        @Query("sort") sort: String = "star",
        @Query("per_page") perPage: Int = 15,
        @Query("page") page: Int,
    ): RepositoriesResponse
}

class RepositoriesNetworkImpl @Inject constructor(
    private val networkApi: RepositoriesNetworkApi
) : RepositoriesNetwork {

    override suspend fun searchRepositories(searchText: String, page: Int): RepositoriesResponse {
        return networkApi.searchRepositories(query = searchText, page = page)
    }
}