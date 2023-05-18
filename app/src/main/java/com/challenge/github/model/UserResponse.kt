package com.challenge.github.model

import kotlinx.serialization.SerialName

data class UserResponse(
    @SerialName("id") val id: Int,
    @SerialName("login") val login: String,
    @SerialName("node_id") val node_id: String,
    @SerialName("avatar_url") val avatar_url: String,
    @SerialName("gravatar_id") val gravatar_id: String,
    @SerialName("url") val url: String,
    @SerialName("html_url") val html_url: String,
    @SerialName("followers_url") val followers_url: String,
    @SerialName("following_url") val following_url: String,
    @SerialName("gists_url") val gists_url: String,
    @SerialName("starred_url") val starred_url: String,
    @SerialName("subscriptions_url") val subscriptions_url: String,
    @SerialName("organizations_url") val organizations_url: String,
    @SerialName("repos_url") val repos_url: String,
    @SerialName("events_url") val events_url: String,
    @SerialName("received_events_url") val received_events_url: String,
    @SerialName("type") val type: String,
    @SerialName("site_admin") val site_admin: Boolean,
)

//O app funcionaria usando a classe UserResponse diretamente na apresentação dos dados.
//Porém, a utilização de models diferentes (UserResponse e User) é uma boa prática.
//Por que proporciona separação de responsabilidades, encapsulamento da lógica de negócio, flexibilidade e evolução independente das classes.
//Isso torna o código mais modular e fácil de manter.

//The app would work using the UserResponse class directly in the data presentation.
//However, the use of different models (UserResponse and User) is considered a best practice.
//It provides separation of concerns, encapsulation of business logic, flexibility, and independent evolution of the classes.
//This makes the code more modular and easier to maintain.
fun UserResponse.toUser(): User {
    return User(
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
        siteAdmin = site_admin
    )
}