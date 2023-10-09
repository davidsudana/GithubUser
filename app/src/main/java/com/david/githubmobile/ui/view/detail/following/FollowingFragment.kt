package com.david.githubmobile.ui.view.detail.following

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.david.githubmobile.R
import com.david.githubmobile.data.Result
import com.david.githubmobile.data.local.entity.UserEntity
import com.david.githubmobile.databinding.FollowingFragmentBinding
import com.david.githubmobile.ui.adapter.UserAdapter
import com.david.githubmobile.ui.view.detail.DetailViewModel
import com.david.githubmobile.utils.Constant.EXTRA_USERNAME
import com.david.githubmobile.utils.StateApp

class FollowingFragment : Fragment(), StateApp<List<UserEntity>> {
    private var _binding: FollowingFragmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel: DetailViewModel by viewModels()
    private lateinit var userAdapter: UserAdapter
    private var username: String? = null

    companion object {
        fun getInstance(username: String): Fragment = FollowingFragment().apply {
            arguments = Bundle().apply {
                putString(EXTRA_USERNAME, username)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            username = it.getString(EXTRA_USERNAME)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FollowingFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userAdapter = UserAdapter()
        binding.rvListUser.apply {
            adapter = userAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }

        viewModel.getFollowing(username.toString()).observe(viewLifecycleOwner) {
            when (it) {
                is Result.Error -> onFailed(it.message)
                is Result.Loading -> onLoading()
                is Result.Success -> it.data?.let { it1 -> onSuccess(it1) }
            }
        }
    }

    override fun onLoading() {
        binding.apply {
            tvMessage.visibility = gone
            progressBar.visibility = visible
            rvListUser.visibility = gone
        }
    }

    override fun onSuccess(data: List<UserEntity>) {
        userAdapter.setAllData(data)
        binding.apply {
            tvMessage.visibility = gone
            progressBar.visibility = gone
            rvListUser.visibility = visible
        }
    }

    override fun onFailed(message: String?) {
        binding.apply {
            if (message == null) {
                tvMessage.text = resources.getString(R.string.following_not_found, username)
                tvMessage.visibility = visible
            } else {
                tvMessage.text = resources.getString(R.string.no_internet)
                tvMessage.visibility = visible
            }
            progressBar.visibility = gone
            rvListUser.visibility = gone
        }
    }
}