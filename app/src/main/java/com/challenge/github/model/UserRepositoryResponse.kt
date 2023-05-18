package com.challenge.github.model

import kotlinx.serialization.SerialName

data class UserRepositoryResponse(
    @SerialName("id") val id: Int,
    @SerialName("node_id") val node_id: String,
    @SerialName("name") val name: String,
    @SerialName("full_name") val full_name: String,
    @SerialName("private") val private: Boolean,
    @SerialName("owner") val owner: UserResponse,
    @SerialName("html_url") val html_url: String,
    @SerialName("description") val description: String?,
    @SerialName("fork") val fork: Boolean,
    @SerialName("url") val url: String,
    @SerialName("forks_url") val forks_url: String,
    @SerialName("keys_url") val keys_url: String,
    @SerialName("collaborators_url") val collaborators_url: String,
    @SerialName("teams_url") val teams_url: String?,
    @SerialName("hooks_url") val hooks_url: String?,
    @SerialName("issue_events_url") val issue_events_url: String?,
    @SerialName("events_url") val events_url: String?,
    @SerialName("assignees_url") val assignees_url: String,
    @SerialName("branches_url") val branches_url: String,
    @SerialName("tags_url") val tags_url: String,
    @SerialName("blobs_url") val blobs_url: String?,
    @SerialName("git_tags_url") val git_tags_url: String?,
    @SerialName("git_refs_url") val git_refs_url: String?,
    @SerialName("trees_url") val trees_url: String,
    @SerialName("statuses_url") val statuses_url: String,
    @SerialName("languages_url") val languages_url: String?,
    @SerialName("stargazers_url") val stargazers_url: String,
    @SerialName("contributors_url") val contributors_url: String,
    @SerialName("subscribers_url") val subscribers_url: String,
    @SerialName("subscription_url") val subscription_url: String,
    @SerialName("commits_url") val commits_url: String,
    @SerialName("git_commits_url") val git_commits_url: String,
    @SerialName("comments_url") val comments_url: String,
    @SerialName("issue_comment_url") val issue_comment_url: String,
    @SerialName("contents_url") val contents_url: String,
    @SerialName("compare_url") val compare_url: String,
    @SerialName("merges_url") val merges_url: String,
    @SerialName("archive_url") val archive_url: String,
    @SerialName("downloads_url") val downloads_url: String,
    @SerialName("issues_url") val issues_url: String,
    @SerialName("pulls_url") val pulls_url: String,
    @SerialName("milestones_url") val milestones_url: String,
    @SerialName("notifications_url") val notifications_url: String,
    @SerialName("labels_url") val labels_url: String,
    @SerialName("releases_url") val releases_url: String,
    @SerialName("deployments_url") val deployments_url: String,
    @SerialName("created_at") val created_at: String?,
    @SerialName("updated_at") val updated_at: String?,
    @SerialName("pushed_at") val pushed_at: String?,
    @SerialName("git_url") val git_url: String,
    @SerialName("ssh_url") val ssh_url: String,
    @SerialName("clone_url") val clone_url: String,
    @SerialName("svn_url") val svn_url: String,
    @SerialName("homepage") val homepage: String?,
    @SerialName("size") val size: Int,
    @SerialName("stargazers_count") val stargazers_count: Int,
    @SerialName("watchers_count") val watchers_count: Int,
    @SerialName("language") val language: String?,
    @SerialName("has_issues") val has_issues: Boolean,
    @SerialName("has_projects") val has_projects: Boolean,
    @SerialName("has_downloads") val has_downloads: Boolean,
    @SerialName("has_wiki") val has_wiki: Boolean,
    @SerialName("has_pages") val has_pages: Boolean,
    @SerialName("has_discussions") val has_discussions: Boolean,
    @SerialName("forks_count") val forks_count: Int,
    @SerialName("mirror_url") val mirror_url: String?,
    @SerialName("archived") val archived: Boolean,
    @SerialName("disabled") val disabled: Boolean,
    @SerialName("open_issues_count") val open_issues_count: Int,
    @SerialName("license") val license: LicenseResponse?,
    @SerialName("allow_forking") val allow_forking: Boolean,
    @SerialName("is_template") val is_template: Boolean,
    @SerialName("web_commit_sign_off_required") val web_commit_sign_off_required: Boolean,
    @SerialName("topics") val topics: List<String>,
    @SerialName("visibility") val visibility: String,
    @SerialName("forks") val forks: Int,
    @SerialName("open_issues") val open_issues: Int,
    @SerialName("watchers") val watchers: Int,
    @SerialName("default_branch") val default_branch: String
) {
    fun toUserRepository(): UserRepository {
        return UserRepository(
            id = id,
            nodeId = node_id,
            name = name,
            fullName = full_name,
            private = private,
            owner = owner.toUser(),
            htmlUrl = html_url,
            description = description,
            fork = fork,
            url = url,
            forksUrl = forks_url,
            keysUrl = keys_url,
            collaboratorsUrl = collaborators_url,
            teamsUrl = teams_url,
            hooksUrl = hooks_url,
            issueEventsUrl = issue_events_url,
            eventsUrl = events_url,
            assigneesUrl = assignees_url,
            branchesUrl = branches_url,
            tagsUrl = tags_url,
            blobsUrl = blobs_url,
            gitTagsUrl = git_tags_url,
            gitRefsUrl = git_refs_url,
            treesUrl = trees_url,
            statusesUrl = statuses_url,
            languagesUrl = languages_url,
            stargazersUrl = stargazers_url,
            contributorsUrl = contributors_url,
            subscribersUrl = subscribers_url,
            subscriptionUrl = subscription_url,
            commitsUrl = commits_url,
            gitCommitsUrl = git_commits_url,
            commentsUrl = comments_url,
            issueCommentUrl = issue_comment_url,
            contentsUrl = contents_url,
            compareUrl = compare_url,
            mergesUrl = merges_url,
            archiveUrl = archive_url,
            downloadsUrl = downloads_url,
            issuesUrl = issues_url,
            pullsUrl = pulls_url,
            milestonesUrl = milestones_url,
            notificationsUrl = notifications_url,
            labelsUrl = labels_url,
            releasesUrl = releases_url,
            deploymentsUrl = deployments_url,
            createdAt = created_at,
            updatedAt = updated_at,
            pushedAt = pushed_at,
            gitUrl = git_url,
            sshUrl = ssh_url,
            cloneUrl = clone_url,
            svnUrl = svn_url,
            homepage = homepage,
            size = size,
            stargazersCount = stargazers_count,
            watchersCount = watchers_count,
            language = language,
            hasIssues = has_issues,
            hasProjects = has_projects,
            hasDownloads = has_downloads,
            hasWiki = has_wiki,
            hasPages = has_pages,
            hasDiscussions = has_discussions,
            forksCount = forks_count,
            mirrorUrl = mirror_url,
            archived = archived,
            disabled = disabled,
            openIssuesCount = open_issues_count,
            license = license?.toLicense(),
            allowForking = allow_forking,
            isTemplate = is_template,
            webCommitSignOffRequired = web_commit_sign_off_required,
            topics = topics,
            visibility = visibility,
            forks = forks,
            openIssues = open_issues,
            watchers = watchers,
            defaultBranch = default_branch
        )
    }
}
