package com.challenge.github

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.challenge.github.Args.USER_ID

class HomeFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        recyclerView = view.findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val users = listOf(
            User("Usuário 1"),
            User("Usuário 2"),
            User("Usuário 3"),
            User("Usuário 4"),
            User("Usuário 5"),
            User("Usuário 6")
        )

        recyclerView.adapter = UserAdapter(users) { user ->
            val bundle = bundleOf(USER_ID to user.name)
            Navigation.findNavController(view)
                .navigate(R.id.action_homeFragment_to_userFragment, bundle)
        }

        return view
    }
}