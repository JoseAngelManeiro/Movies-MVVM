package com.joseangelmaneiro.movies.platform.features

import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.joseangelmaneiro.movies.R

abstract class BaseActivity : AppCompatActivity() {

  fun showErrorMessage(message: String) {
    val builder = AlertDialog.Builder(this,
      R.style.Theme_AppCompat_Light_Dialog_Alert)
    builder.setMessage(message)
    builder.setPositiveButton(android.R.string.ok) { dialog, _ ->
      dialog.dismiss()
    }
    val dialog: AlertDialog = builder.create()
    dialog.show()
  }
}
