package com.joseangelmaneiro.movies.platform.views

import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.joseangelmaneiro.movies.R
import com.joseangelmaneiro.movies.presentation.BaseView

abstract class BaseActivity : AppCompatActivity(), BaseView {

  override fun showErrorMessage(message: String) {
    val builder = AlertDialog.Builder(
      this,
      R.style.Theme_AppCompat_Light_Dialog_Alert
    )
    builder.setMessage(message)
    builder.setPositiveButton(android.R.string.ok) { dialog, _ ->
      dialog.dismiss()
    }
    val dialog: AlertDialog = builder.create()
    dialog.show()
  }
}
