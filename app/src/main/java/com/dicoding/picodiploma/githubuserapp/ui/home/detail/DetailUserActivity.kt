package com.dicoding.picodiploma.githubuserapp.ui.home.detail

import android.R.drawable.ic_delete
import android.R.drawable.ic_input_add
import android.app.AlertDialog
import android.net.Uri
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import com.dicoding.picodiploma.githubuserapp.data.local.db.DatabaseContract.UserColumns.Companion.CONTENT_URI
import com.dicoding.picodiploma.githubuserapp.data.local.entity.toUserEntity
import com.dicoding.picodiploma.githubuserapp.data.remote.response.User
import com.dicoding.picodiploma.githubuserapp.data.remote.response.toContentValues
import com.dicoding.picodiploma.githubuserapp.databinding.ActivityDetailUserBinding

class DetailUserActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_DATA = "extra_username"
    }

    private val viewModel: DetailUserViewModel by viewModels()

    private lateinit var binding: ActivityDetailUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val dataUser = intent.getParcelableExtra<User>(EXTRA_DATA)
        val username = dataUser?.login

        if (!username.isNullOrBlank()) viewModel.setUserDetail(username)

        viewModel.userDetail.observe(this, {
            binding.data = it
        })

        binding.also {
            it.viewPager.adapter =
                SectionPagerAdapter(this, supportFragmentManager, bundleOf(EXTRA_DATA to username))
            it.tabs.setupWithViewPager(it.viewPager)
        }


        val resource = if (isFavorite(dataUser?.id)) ic_delete else ic_input_add
        binding.fab.setImageResource(resource)
        binding.fab.setOnClickListener {
            showAlertConfirm(dataUser)
        }
    }

    private fun isFavorite(id: Int?): Boolean {
        val uri = Uri.parse("$CONTENT_URI/$id")
        val cursor = contentResolver.query(uri, null, null, null, null)
        val dataEntity = cursor.toUserEntity()
        cursor?.close()
        return dataEntity != null
    }

    private fun showAlertConfirm(user: User?) {
        AlertDialog.Builder(this)
            .setTitle("Confirm")
            .setMessage("do you want to ${if (isFavorite(user?.id)) "delete" else "add"}")
            .setPositiveButton("Yes") { _, _ ->
                if (isFavorite(user?.id)) {
                    contentResolver.delete(Uri.parse("$CONTENT_URI/${user?.id}"), null, null)
                        .apply { if (this > 0) finish() }
                } else {
                    contentResolver.insert(CONTENT_URI, user?.toContentValues())
                        ?.apply { binding.fab.setImageResource(ic_delete) }
                }
            }
            .setNegativeButton("No") { dialog, _ ->
                dialog.dismiss()
            }.show()
    }
}