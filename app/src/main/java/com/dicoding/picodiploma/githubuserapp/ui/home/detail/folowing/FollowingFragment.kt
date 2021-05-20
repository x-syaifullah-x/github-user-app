package com.dicoding.picodiploma.githubuserapp.ui.home.detail.folowing

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.picodiploma.githubuserapp.R
import com.dicoding.picodiploma.githubuserapp.databinding.FragmentFollowBinding
import com.dicoding.picodiploma.githubuserapp.ui.home.ItemAdapter
import com.dicoding.picodiploma.githubuserapp.ui.home.detail.DetailUserActivity

class FollowingFragment : Fragment(R.layout.fragment_follow) {

    private var _binding: FragmentFollowBinding? = null

    private val binding get() = _binding ?: throw Throwable("FragmentFollowBinding Not Initialize")

    private val viewModel: FollowingViewModel by viewModels()

    private val itemAdapter by lazy { ItemAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentFollowBinding.bind(view)

        binding.apply {
            rvUser.setHasFixedSize(true)
            rvUser.layoutManager = LinearLayoutManager(activity)
            rvUser.adapter = itemAdapter
        }

        showLoading(true)
        val username = "${arguments?.getString(DetailUserActivity.EXTRA_DATA)}"
        viewModel.setListFollowing(username)
        viewModel.listFollowing.observe(viewLifecycleOwner, {
            binding.tvEmpty.isVisible = it.isNullOrEmpty()
            itemAdapter.setList(it)
            showLoading(false)
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showLoading(state: Boolean) {
        binding.progressBar.visibility = if (state) View.VISIBLE else View.GONE
    }
}