package com.dicoding.picodiploma.githubuserapp.ui.home.favorite

import android.content.Intent
import android.database.ContentObserver
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.picodiploma.githubuserapp.R
import com.dicoding.picodiploma.githubuserapp.data.local.db.DatabaseContract.UserColumns.Companion.CONTENT_URI
import com.dicoding.picodiploma.githubuserapp.data.remote.response.User
import com.dicoding.picodiploma.githubuserapp.data.remote.response.toListUser
import com.dicoding.picodiploma.githubuserapp.databinding.FragmentFavoriteBinding
import com.dicoding.picodiploma.githubuserapp.ui.home.ItemAdapter
import com.dicoding.picodiploma.githubuserapp.ui.home.detail.DetailUserActivity

class FavoriteFragment : Fragment() {

    private var _binding: FragmentFavoriteBinding? = null
    private val binding
        get() = _binding ?: throw Throwable("FragmentFavoriteBinding Not Initialize")
    private val contentResolver by lazy { requireContext().contentResolver }

    private val handlerThread = HandlerThread("DataObserver")

    private val itemAdapter = ItemAdapter(
        blockOnItemClick = { onItemClicked(it) },
    )

    private val myObserver by lazy {
        handlerThread.start()
        object : ContentObserver(Handler(handlerThread.looper)) {
            override fun onChange(self: Boolean) {
                setData()
            }
        }
    }

    private fun setData() {
        val res = contentResolver
            ?.query(CONTENT_URI, null, null, null, null)
            ?.run {
                toListUser().apply { close() }
            } ?: arrayListOf()

        requireActivity().runOnUiThread {
            binding.tvEmpty.isVisible = res.isNullOrEmpty()
            itemAdapter.setList(res)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        val view = LayoutInflater.from(container?.context)
            .inflate(R.layout.fragment_favorite, container, false)
        _binding = FragmentFavoriteBinding.bind(view)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.apply {
            rvUser.layoutManager = LinearLayoutManager(requireContext())
            rvUser.setHasFixedSize(true)
            rvUser.adapter = itemAdapter
        }

        contentResolver.registerContentObserver(CONTENT_URI, true, myObserver)
            .apply { setData() }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        context?.contentResolver?.unregisterContentObserver(myObserver)
        _binding = null
    }

    private fun onItemClicked(data: User) {
        Intent(requireContext(), DetailUserActivity::class.java).also {
            it.putExtra(DetailUserActivity.EXTRA_DATA, data)
            startActivity(it)
        }
    }
}