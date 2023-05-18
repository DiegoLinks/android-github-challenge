package com.challenge.github.ui.user

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
import com.challenge.github.gone
import com.challenge.github.isDarkTheme
import com.challenge.github.model.UserDetail
import com.challenge.github.model.UserRepository
import com.challenge.github.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserFragment : Fragment() {

    private val viewModel by viewModels<UserViewModel>()

    private var user: String? = null

    private lateinit var ivAvatar: ImageView
    private lateinit var tvName: TextView
    private lateinit var tvLogin: TextView
    private lateinit var ivLocation: ImageView
    private lateinit var tvLocation: TextView
    private lateinit var ivSite: ImageView
    private lateinit var tvSite: TextView
    private lateinit var ivFollow: ImageView
    private lateinit var tvFollow: TextView
    private lateinit var divisor: View
    private lateinit var recyclerView: RecyclerView
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
        ivAvatar = view.findViewById(R.id.iv_avatar)
        tvName = view.findViewById(R.id.tv_name)
        tvLogin = view.findViewById(R.id.tv_login)
        ivLocation = view.findViewById(R.id.iv_location)
        tvLocation = view.findViewById(R.id.tv_location)
        ivSite = view.findViewById(R.id.iv_site)
        tvSite = view.findViewById(R.id.tv_site)
        ivFollow = view.findViewById(R.id.iv_follow)
        tvFollow = view.findViewById(R.id.tv_follow)
        divisor = view.findViewById(R.id.divisor)
        recyclerView = view.findViewById(R.id.repo_recycler_view)
        recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        progressBar = view.findViewById(R.id.progress_bar)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getUserDetail(user ?: "")

        setObservers()
    }

    private fun setObservers() {
        viewModel.user.observe(viewLifecycleOwner) { user ->
            setLayout(user)
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

    private fun setLayout(user: UserDetail) {
        val context = context ?: return//TODO melhorar

        val locationImage =
            if (isDarkTheme(context)) R.drawable.ic_location_dark else R.drawable.ic_location_light
        val siteImage =
            if (isDarkTheme(context)) R.drawable.ic_link_dark else R.drawable.ic_link_light
        val followImage =
            if (isDarkTheme(context)) R.drawable.ic_follow_dark else R.drawable.ic_follow_light
        val divisorBackground = if (isDarkTheme(context)) R.color.gray else R.color.light_gray

        Glide
            .with(context)
            .load(user.avatarUrl)
            .circleCrop()
            .placeholder(R.drawable.ic_launcher_background)
            .into(ivAvatar)
        tvName.text = user.name
        tvLogin.text = user.login
        tvLocation.text = user.location
        tvSite.text = user.blog
        tvFollow.text = context.getString(
            R.string.followers_and_following_label,
            user.followers,
            user.following
        )
        ivLocation.setImageResource(locationImage)
        ivSite.setImageResource(siteImage)
        divisor.setBackgroundColor(resources.getColor(divisorBackground, null))
        ivFollow.setImageResource(followImage)
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