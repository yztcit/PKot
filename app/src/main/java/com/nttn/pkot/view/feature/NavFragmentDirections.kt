package com.nttn.pkot.view.feature

import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.navigation.NavDirections
import com.nttn.pkot.R

class NavFragmentDirections private constructor() {
    private data class ActionToCategory(val title: String) : NavDirections {
        override fun getActionId(): Int = R.id.action_test_navigate

        override fun getArguments(): Bundle = bundleOf("title" to title)
    }

    companion object {
        fun actionToCategoryFragment(title: String): NavDirections = ActionToCategory(title)
    }
}