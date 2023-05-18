package com.challenge.github.model

import kotlinx.serialization.SerialName

data class LicenseResponse(
    @SerialName("key") val key: String?,
    @SerialName("name") val name: String?,
    @SerialName("spdx_id") val spdx_id: String?,
    @SerialName("url") val url: String?,
    @SerialName("node_id") val node_id: String?
)

fun LicenseResponse.toLicense(): License {
    return License(
        key = key,
        name = name,
        spdxId = spdx_id,
        url = url,
        nodeId = node_id
    )
}