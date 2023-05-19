package com.challenge.github.ui.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.challenge.github.core.util.Args.USER_ID
import com.challenge.github.R
import com.challenge.github.core.gone
import com.challenge.github.core.isDarkTheme
import com.challenge.github.core.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val viewModel by viewModels<HomeViewModel>()

    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var viewError: View
    private lateinit var ivError: ImageView
    private lateinit var tvErrorTitle: TextView
    private lateinit var tvErrorText: TextView

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
        viewError = view.findViewById(R.id.view_error)
        ivError = view.findViewById(R.id.iv_error)
        tvErrorTitle = view.findViewById(R.id.tv_error_title)
        tvErrorText = view.findViewById(R.id.tv_error_text)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val menuHost: MenuHost = requireActivity()
        setSearchMenu(menuHost)
        setObservers(view)
    }

    private fun setSearchMenu(menuHost: MenuHost) {
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_main, menu)

                val searchItem = menu.findItem(R.id.menu_search)
                val searchView = searchItem.actionView as SearchView

                searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String): Boolean {
                        viewModel.updateListWithSearchText(query)
                        return true
                    }

                    override fun onQueryTextChange(newText: String): Boolean {
                        if (newText.isBlank()) {
                            viewModel.restoreOriginalList()
                        } else {
                            viewModel.updateListWithSearchText(newText)
                        }
                        return true
                    }
                })
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return true
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun setObservers(view: View) {
        viewModel.users.observe(viewLifecycleOwner) { users ->
            recyclerView.adapter = UserAdapter(users) { user ->
                viewModel.restoreOriginalList()
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
            showErrorScreen(view.context)
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

    private fun showErrorScreen(context: Context) {
        val errorImage =
            if (isDarkTheme(context)) R.drawable.ic_error_dark else R.drawable.ic_error_light
        val divisorBackground = if (isDarkTheme(context)) R.color.light_gray else R.color.gray_blue

        ivError.setImageResource(errorImage)
        tvErrorTitle.setTextColor(resources.getColor(divisorBackground, null))
        tvErrorText.setTextColor(resources.getColor(divisorBackground, null))
        viewError.visible()
    }

    private fun showError(errorMessage: String) {
        Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
    }
}