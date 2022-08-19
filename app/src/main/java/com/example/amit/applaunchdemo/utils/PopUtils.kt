package com.example.amit.applaunchdemo.utils

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.view.WindowManager
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import com.example.amit.applaunchdemo.R

fun showMultiPurposeDialog(
    context: Context,
    title: String,
    isImageVisible: Boolean,
    msg: String,
    onClickListener: View.OnClickListener,
    onCancelListener: View.OnClickListener,
    textOkay: String,
    textCancel: String
) {
    val dialog = Dialog(context)
    dialog.setContentView(R.layout.dialog_multi_purpose)
    val windowManagerLayoutParams = WindowManager.LayoutParams()
    val window = dialog.window

    if (window != null) {
        windowManagerLayoutParams.copyFrom(window.attributes)
        window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        //This makes the dialog take up the full width
        windowManagerLayoutParams.width = WindowManager.LayoutParams.MATCH_PARENT
        windowManagerLayoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT
        window.attributes = windowManagerLayoutParams
    }
    dialog.setCancelable(false)

    val tvTitleMessage: AppCompatTextView = dialog.findViewById(R.id.tvTitleMessage)
    val tvMessage: AppCompatTextView = dialog.findViewById(R.id.tvMessage)
    val tvCancel: AppCompatTextView = dialog.findViewById(R.id.tvCancel)
    val tvOkay: AppCompatTextView = dialog.findViewById(R.id.tvOkay)
    val ivTitle: AppCompatImageView = dialog.findViewById(R.id.ivTitle)

    tvTitleMessage.text = title
    tvMessage.text = msg
    tvOkay.text = textOkay
    tvCancel.text = textCancel

    if (isImageVisible) {
        ivTitle.visibility = View.VISIBLE
    } else {
        ivTitle.visibility = View.GONE
    }

    tvOkay.setOnClickListener { v: View? ->
        onClickListener.onClick(v)
        dialog.dismiss()
    }

    tvCancel.setOnClickListener { v: View? ->
        onCancelListener.onClick(v)
        dialog.dismiss()
    }
    dialog.show()
}