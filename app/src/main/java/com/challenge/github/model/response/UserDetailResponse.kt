package com.challenge.github.model.response

import com.challenge.github.model.UserDetail
import kotlinx.serialization.SerialName

data class UserDetailResponse(
    @SerialName("id") val id: Int,
    @SerialName("login") val login: String,
    @SerialName("node_id") val node_id: String,
    @SerialName("avatar_url") val avatar_url: String,
    @SerialName("gravatar_id") val gravatar_id: String,
    @SerialName("url") val url: String,
    @SerialName("html_url") val html_url: String?,
    @SerialName("followers_url") val followers_url: String,
    @SerialName("following_url") val following_url: String,
    @SerialName("gists_url") val gists_url: String,
    @SerialName("starred_url") val starred_url: String,
    @SerialName("subscriptions_url") val subscriptions_url: String,
    @SerialName("organizations_url") val organizations_url: String,
    @SerialName("repos_url") val repos_url: String,
    @SerialName("events_url") val events_url: String?,
    @SerialName("received_events_url") val received_events_url: String?,
    @SerialName("type") val type: String,
    @SerialName("site_admin") val site_admin: Boolean,
    @SerialName("name") val name: String?,
    @SerialName("company") val company: String?,
    @SerialName("blog") val blog: String?,
    @SerialName("location") val location: String?,
    @SerialName("email") val email: String?,
    @SerialName("hireable") val hireable: Boolean?,
    @SerialName("bio") val bio: String?,
    @SerialName("twitter_username") val twitter_username: String?,
    @SerialName("public_repos") val public_repos: Int,
    @SerialName("public_gists") val public_gists: Int,
    @SerialName("followers") val followers: Int,
    @SerialName("following") val following: Int,
    @SerialName("created_at") val created_at: String?,
    @SerialName("updated_at") val updated_at: String?,
)

fun UserDetailResponse.toUserDetail(): UserDetail {
    return UserDetail(
        id = id,
        login = login,
        nodeId = node_id,
        avatarUrl = avatar_url,
        gravatarId = gravatar_id,
        url = url,
        htmlUrl = html_url,
        followersUrl = followers_url,
        followingUrl = following_url,
        gistsUrl = gists_url,
        starredUrl = starred_url,
        subscriptionsUrl = subscriptions_url,
        organizationsUrl = organizations_url,
        reposUrl = repos_url,
        eventsUrl = events_url,
        receivedEventsUrl = received_events_url,
        type = type,
        siteAdmin = site_admin,
        name = name,
        company = company,
        blog = blog,
        location = location,
        email = email,
        hireAble = hireable,
        bio = bio,
        twitterUsername = twitter_username,
        publicRepos = public_repos,
        publicGists = public_gists,
        followers = followers,
        following = following,
        createdAt = created_at,
        updatedAt = updated_at,
    )
}