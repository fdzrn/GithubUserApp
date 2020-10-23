package com.codelab.githubuser.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.codelab.githubuser.R
import com.codelab.githubuser.adapter.FollowAdapter
import com.codelab.githubuser.viewmodel.FragmentViewModel
import kotlinx.android.synthetic.main.fragment_following.*

class FollowingFragment : Fragment() {
    private  lateinit var followAdapter: FollowAdapter
    private lateinit var fragmentViewModel: FragmentViewModel

    companion object {

        private const val ARG_USERNAME = "username"

        fun newInstance(username: String): FollowingFragment {
            val fragment = FollowingFragment()
            val bundle = Bundle()
            bundle.putString(ARG_USERNAME, username)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_following, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val username = arguments?.getString(ARG_USERNAME)
        fragmentViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(FragmentViewModel::class.java)

        showLoading(true)
        inputDataFollowing(username)
        showRecyclerView()


    }

    private fun inputDataFollowing(username: String?) {
        showLoading(true)
        fragmentViewModel.setDataListFollowing(requireContext(),username)
        observerFragmentViewModel()
    }

    private fun observerFragmentViewModel() {
        fragmentViewModel.getDataFollowUser().observe(viewLifecycleOwner, Observer { usersItems ->
            if (usersItems != null){
                followAdapter.setData(usersItems)
                showLoading(false)
            }
        })
    }

    private fun showLoading(state: Boolean) {
        if (state) progressBar.visibility = View.VISIBLE
        else progressBar.visibility = View.INVISIBLE
    }

    private fun showRecyclerView() {
        followAdapter = FollowAdapter()
        followAdapter.notifyDataSetChanged()
        recyclerview_following.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = followAdapter
            setHasFixedSize(true)
        }
    }
}