package com.dicoding.picodiploma.githubuserapp.ui.home.user

import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.LayoutInflater.from
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.picodiploma.githubuserapp.R
import com.dicoding.picodiploma.githubuserapp.data.remote.response.User
import com.dicoding.picodiploma.githubuserapp.databinding.FragmentUserBinding
import com.dicoding.picodiploma.githubuserapp.ui.home.ItemAdapter
import com.dicoding.picodiploma.githubuserapp.ui.home.detail.DetailUserActivity


class UserFragment : Fragment() {

    companion object {
        private const val KEY_EDIT_TEXT = "KEY_EDIT_TEXT"
        private const val DEFAULT_QUERY = "android kotlin"
    }

    private var _binding: FragmentUserBinding? = null

    private val binding get() = _binding ?: throw Throwable("FragmentUserBinding Not Initialize")

    private val viewModel: UserViewModel by navGraphViewModels(R.id.nav_graph_main)

    private val itemAdapter = ItemAdapter(
        blockOnItemClick = { onItemClicked(it) },
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserBinding
            .bind(from(container?.context).inflate(R.layout.fragment_user, container, false))
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.apply {
            rvUser.layoutManager = LinearLayoutManager(requireContext())
            rvUser.setHasFixedSize(true)
            rvUser.adapter = itemAdapter
        }

        binding.etSearch.setOnKeyListener { _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                searchUser(binding.etSearch.text.toString())
                val imm = getSystemService(requireContext(), InputMethodManager::class.java)
                imm?.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)
                return@setOnKeyListener true
            }
            return@setOnKeyListener false
        }

        binding.etSearch.setText(viewModel.stat[KEY_EDIT_TEXT])
        if (viewModel.stat[KEY_EDIT_TEXT].isNullOrBlank()) searchUser(DEFAULT_QUERY)

        viewModel.user.observe(viewLifecycleOwner, {
            binding.tvEmpty.isVisible = it.isNullOrEmpty()
            itemAdapter.setList(it)
            showLoading(false)
        })
    }

    private fun searchUser(query: String) {
        showLoading(query.isNotBlank())
        viewModel.setSearchUsers(if (query.isNotBlank()) query else DEFAULT_QUERY)
    }


    private fun showLoading(state: Boolean) {
        binding.progressBar.visibility = if (state) VISIBLE else GONE
    }

    private fun onItemClicked(data: User) {
        Intent(requireContext(), DetailUserActivity::class.java).also {
            it.putExtra(DetailUserActivity.EXTRA_DATA, data)
            startActivity(it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.stat[KEY_EDIT_TEXT] = "${binding.etSearch.text}"
        _binding = null
    }
}