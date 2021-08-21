package com.noranekoit.bfaausergithub3.data.api

import com.noranekoit.bfaausergithub3.data.model.DetailUserGithubResponse
import com.noranekoit.bfaausergithub3.data.model.RepositoryGithubResponse
import com.noranekoit.bfaausergithub3.data.model.UserGithub
import com.noranekoit.bfaausergithub3.data.model.UserGithubResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {
    @GET("search/users")
    @Headers("Authorization: BuildConfig.GITHUB_TOKEN")
    fun getSearchUsers(
        @Query("q") query: String
    ): Call<UserGithubResponse>


    @GET("users/{username}")
    @Headers("Authorization: BuildConfig.GITHUB_TOKEN")
    fun getUserDetail(
        @Path("username") username : String
    ): Call<DetailUserGithubResponse>

    @GET("users/{username}/following")
    @Headers("Authorization: BuildConfig.GITHUB_TOKEN")
    fun getFollowing(
        @Path("username") username: String
    ): Call<ArrayList<UserGithub>>

    @GET("users/{username}/followers")
    @Headers("Authorization: BuildConfig.GITHUB_TOKEN")
    fun getFollowers(
        @Path("username") username: String
    ): Call<ArrayList<UserGithub>>

    @GET("users/{username}/repos")
    @Headers("Authorization: BuildConfig.GITHUB_TOKEN")
    fun getRepositories(
        @Path("username") username: String
    ):Call<ArrayList<RepositoryGithubResponse>>

}