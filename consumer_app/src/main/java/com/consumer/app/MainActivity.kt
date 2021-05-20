package com.consumer.app

import android.app.AlertDialog
import android.database.ContentObserver
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.consumer.app.DatabaseContract.UserColumns.Companion.CONTENT_URI
import com.consumer.app.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val itemAdapter by lazy { ItemAdapter(this::showAlertConfirmDelete) }

    private val handlerThread = HandlerThread("DATA_OBSERVER")

    private val myObserver by lazy {
        handlerThread.start()
        object : ContentObserver(Handler(handlerThread.looper)) {
            override fun onChange(self: Boolean) = setData()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        contentResolver.registerContentObserver(CONTENT_URI, true, myObserver)
            .apply { setData() }

        binding.rv.apply {
            setHasFixedSize(true)
            adapter = itemAdapter
        }
    }

    private fun setData() {
        contentResolver?.query(CONTENT_URI, null, null, null, null)
            ?.apply {
                toListModel().also { data ->
                    runOnUiThread {
                        binding.tvEmpty.isVisible = data.isNullOrEmpty()
                        itemAdapter.setList(data)
                    }
                }
            }?.close()
    }

    private fun showAlertConfirmDelete(id: Int) {
        AlertDialog.Builder(this)
            .setTitle("Confirm")
            .setMessage("are you sure want to delete this item")
            .setPositiveButton("Yes") { _, _ ->
                val res = contentResolver.delete(Uri.parse("$CONTENT_URI/$id"), null, null)
                val message = if (res > 0) "delete is success" else "filed delete data"
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("No") { dialog, _ ->
                dialog.dismiss()
            }.show()
    }

    private fun Cursor?.toListModel() = arrayListOf<Model>()
        .apply {
            while (this@toListModel != null && moveToNext()) {
                val model = Model(
                    id = getInt(Model.COLUMN_INDEX_ID),
                    login = getString(Model.COLUMN_INDEX_LOGIN),
                    avatar_url = getString(Model.COLUMN_INDEX_AVATAR_URL)
                )
                add(model)
            }
        }
}