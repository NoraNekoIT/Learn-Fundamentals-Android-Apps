package com.noranekoit.bfaausergithub3.data.model

data class DetailUserGithubResponse(
    val login : String,
    val id : Int,
    val avatar_url: String,
    val followers_url : String,
    val following_url : String,
    val name : String,
    val following : Int,
    val followers : Int,
    val company : String,
    val location: String,
    val public_repos: String,
    val repos_url: String


)
