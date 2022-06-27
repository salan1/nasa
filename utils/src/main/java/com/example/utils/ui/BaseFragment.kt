package com.example.utils.ui

import android.content.Context
import android.view.View
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.google.android.material.snackbar.Snackbar

abstract class BaseFragment : Fragment {

    constructor() : super()
    constructor(@LayoutRes contentLayoutId: Int) : super(contentLayoutId)

    protected var root: View? = null
    var viewModelClass: Class<out ViewModel>? = null
    var navController: NavController? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onDestroy() {
        root = null
        navController = null
        super.onDestroy()
    }

    open fun showErrorMessage(@StringRes messageId: Int, vararg templates: String) {
        root?.apply {
            Snackbar.make(
                this,
                resources.getString(messageId, templates),
                Snackbar.LENGTH_LONG
            )
                .setBackgroundTint(ContextCompat.getColor(requireContext(), -0xbbcca))
                .show()
        }
    }
}