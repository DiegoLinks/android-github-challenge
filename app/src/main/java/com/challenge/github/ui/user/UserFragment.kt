package com.challenge.github.ui.user

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.challenge.github.core.util.Args.USER_ID
import com.challenge.github.R
import com.challenge.github.core.gone
import com.challenge.github.core.groupVisibility
import com.challenge.github.core.isDarkTheme
import com.challenge.github.model.UserDetail
import com.challenge.github.model.UserRepository
import com.challenge.github.core.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserFragment : Fragment() {

    private val viewModel by viewModels<UserViewModel>()

    private var user: String? = null

    private lateinit var ivAvatar: ImageView
    private lateinit var tvName: TextView
    private lateinit var tvLogin: TextView
    private lateinit var ivCompany: ImageView
    private lateinit var tvCompany: TextView
    private lateinit var ivLocation: ImageView
    private lateinit var tvLocation: TextView
    private lateinit var ivSite: ImageView
    private lateinit var tvSite: TextView
    private lateinit var ivEmail: ImageView
    private lateinit var tvEmail: TextView
    private lateinit var ivTwitter: ImageView
    private lateinit var tvTwitter: TextView
    private lateinit var ivFollow: ImageView
    private lateinit var tvFollow: TextView
    private lateinit var topDivisor: View
    private lateinit var middleDivisor: View
    private lateinit var bottomDivisor: View
    private lateinit var recyclerView: RecyclerView
    private lateinit var tvRepositoryNumber: TextView
    private lateinit var tvGistsNumber: TextView
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            user = it.getString(USER_ID)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_user, container, false)
        bindViews(view)
        return view
    }

    private fun bindViews(view: View) {
        ivAvatar = view.findViewById(R.id.iv_avatar)
        tvName = view.findViewById(R.id.tv_name)
        tvLogin = view.findViewById(R.id.tv_login)
        ivCompany = view.findViewById(R.id.iv_company)
        tvCompany = view.findViewById(R.id.tv_company)
        ivLocation = view.findViewById(R.id.iv_location)
        tvLocation = view.findViewById(R.id.tv_location)
        ivSite = view.findViewById(R.id.iv_site)
        tvSite = view.findViewById(R.id.tv_site)
        ivEmail = view.findViewById(R.id.iv_email)
        tvEmail = view.findViewById(R.id.tv_email)
        ivTwitter = view.findViewById(R.id.iv_twitter)
        tvTwitter = view.findViewById(R.id.tv_twitter)
        ivFollow = view.findViewById(R.id.iv_follow)
        tvFollow = view.findViewById(R.id.tv_follow)
        topDivisor = view.findViewById(R.id.top_divisor)
        middleDivisor = view.findViewById(R.id.middle_divisor)
        bottomDivisor = view.findViewById(R.id.bottom_divisor)
        recyclerView = view.findViewById(R.id.repo_recycler_view)
        recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        tvRepositoryNumber = view.findViewById(R.id.tv_repository_number)
        tvGistsNumber = view.findViewById(R.id.tv_gists_number)
        progressBar = view.findViewById(R.id.progress_bar)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getUserDetail(user ?: "")
        setObservers(view.context)
    }

    private fun setObservers(context: Context) {
        viewModel.user.observe(viewLifecycleOwner) { user ->
            setLayout(user, context)
            setViewState(false)
        }

        viewModel.repositories.observe(viewLifecycleOwner) { repos ->
            setRepositoriesLayout(repos)
        }

        viewModel.isLoading.observe(viewLifecycleOwner) {
            setViewState(it)
        }

        viewModel.error.observe(viewLifecycleOwner) {
            showError(it)
            setViewState(false)
        }
    }

    private fun setLayout(user: UserDetail, context: Context) {
        val companyImage =
            if (isDarkTheme(context)) R.drawable.ic_company_dark else R.drawable.ic_company_light
        val locationImage =
            if (isDarkTheme(context)) R.drawable.ic_location_dark else R.drawable.ic_location_light
        val siteImage =
            if (isDarkTheme(context)) R.drawable.ic_link_dark else R.drawable.ic_link_light
        val emailImage =
            if (isDarkTheme(context)) R.drawable.ic_email_dark else R.drawable.ic_email_light
        val twitterImage =
            if (isDarkTheme(context)) R.drawable.ic_twitter_dark else R.drawable.ic_twitter_light
        val followImage =
            if (isDarkTheme(context)) R.drawable.ic_follow_dark else R.drawable.ic_follow_light
        val divisorBackground = if (isDarkTheme(context)) R.color.gray else R.color.light_gray

        Glide
            .with(context)
            .load(user.avatarUrl)
            .circleCrop()
            .placeholder(R.color.black)
            .into(ivAvatar)
        tvName.text = user.name
        tvLogin.text = user.login

        ivCompany.setImageResource(companyImage)
        tvCompany.text = user.company
        groupVisibility(user.company, ivCompany, tvCompany)

        ivLocation.setImageResource(locationImage)
        tvLocation.text = user.location
        groupVisibility(user.location, ivLocation, tvLocation)

        ivSite.setImageResource(siteImage)
        tvSite.text = user.blog
        groupVisibility(user.blog, ivSite, tvSite)

        ivEmail.setImageResource(emailImage)
        tvEmail.text = user.email
        groupVisibility(user.email, ivEmail, tvEmail)

        ivTwitter.setImageResource(twitterImage)
        tvTwitter.text = user.twitterUsername
        groupVisibility(user.twitterUsername, ivTwitter, tvTwitter)

        ivFollow.setImageResource(followImage)
        tvFollow.text = context.getString(
            R.string.followers_and_following_label,
            user.followers,
            user.following
        )

        tvRepositoryNumber.text = user.publicRepos.toString()
        tvGistsNumber.text = user.publicGists.toString()

        topDivisor.setBackgroundColor(resources.getColor(divisorBackground, null))
        middleDivisor.setBackgroundColor(resources.getColor(divisorBackground, null))
        bottomDivisor.setBackgroundColor(resources.getColor(divisorBackground, null))
    }

    private fun setRepositoriesLayout(repositories: List<UserRepository>) {
        recyclerView.adapter = UserRepositoryAdapter(repositories)
    }

    private fun setViewState(isLoading: Boolean) {
        if (isLoading) {
            loadingContent()
        } else {
            showContent()
        }
    }

    private fun showContent() {
        progressBar.gone()
        tvName.visible()
    }

    private fun loadingContent() {
        progressBar.visible()
        tvName.gone()
    }

    private fun showError(errorMessage: String) {
        Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
    }
}