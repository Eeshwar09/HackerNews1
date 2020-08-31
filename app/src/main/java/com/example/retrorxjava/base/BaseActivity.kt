@file:Suppress("DEPRECATION")

package com.example.retrorxjava.base

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.TextView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.example.retrorxjava.R

@Suppress("DEPRECATION")
abstract  class BaseActivity : AppCompatActivity() {

    lateinit var mTextViewScreenTitle: TextView
    lateinit var mImageButtonBack: ImageButton
    lateinit var mProgressDialog: ProgressDialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mProgressDialog = ProgressDialog(this)
        mProgressDialog.setMessage("Loading")
        mProgressDialog.setCancelable(false)
        mProgressDialog.isIndeterminate = true

    }

    override fun setContentView(layoutResID: Int) {
        val coordinatorLayout: CoordinatorLayout = layoutInflater.inflate(R.layout.activity_base, null) as CoordinatorLayout
        val activityContainer: FrameLayout = coordinatorLayout.findViewById(R.id.layout_container)
        mTextViewScreenTitle = coordinatorLayout.findViewById(R.id.titleName) as TextView
        mImageButtonBack = coordinatorLayout.findViewById(R.id.backButton)

        layoutInflater.inflate(layoutResID, activityContainer, true)
        super.setContentView(coordinatorLayout)
    }
    fun setScreenTitle(resId: Int) {
        mTextViewScreenTitle.text = getString(resId)
    }

    fun setScreenTitle(title: String) {
        mTextViewScreenTitle.text = title
    }

    fun getBackButton(): ImageButton {
        mImageButtonBack.visibility = View.VISIBLE
        return mImageButtonBack
    }
 

}
