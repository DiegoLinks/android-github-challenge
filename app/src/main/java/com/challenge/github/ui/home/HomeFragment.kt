package com.challenge.github.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.challenge.github.core.util.Args.USER_ID
import com.challenge.github.R
import com.challenge.github.core.gone
import com.challenge.github.core.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val viewModel by viewModels<HomeViewModel>()

    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.getUserList()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        progressBar = view.findViewById(R.id.progress_bar)
        recyclerView = view.findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setObservers(view)
    }

    private fun setObservers(view: View) {
        viewModel.users.observe(viewLifecycleOwner) { users ->
            recyclerView.adapter = UserAdapter(users) { user ->
                val bundle = bundleOf(USER_ID to user.login)
                Navigation.findNavController(view)
                    .navigate(R.id.action_homeFragment_to_userFragment, bundle)
            }

            setViewState(false)
        }

        viewModel.isLoading.observe(viewLifecycleOwner) {
            setViewState(it)
        }

        viewModel.error.observe(viewLifecycleOwner) {
            showError(it)
            setViewState(false)
        }
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
        recyclerView.visible()
    }

    private fun loadingContent() {
        progressBar.visible()
        recyclerView.gone()
    }

    private fun showError(errorMessage: String) {
        Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
    }
}