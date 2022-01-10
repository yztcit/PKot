package com.nttn.pkot.view.feature

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.core.os.bundleOf
import androidx.navigation.NavDirections
import com.nttn.pkot.R
import com.nttn.pkot.data.room.Note

class NavFragmentDirections private constructor() {
    private open class NavDirectionImpl(@IdRes val id: Int) : NavDirections {
        override fun getActionId(): Int {
            return id
        }

        override fun getArguments(): Bundle {
            return Bundle.EMPTY
        }
    }

    private data class ActionToCategory(val title: String) :
        NavDirectionImpl(R.id.action_test_navigate) {
        override fun getArguments(): Bundle = bundleOf("title" to title)
    }

    private data class ActionToCreateNote(val noteId: Int) :
        NavDirectionImpl(R.id.action_to_create_note) {
        override fun getArguments(): Bundle = bundleOf("noteId" to noteId)
    }

    companion object {
        fun actionToCategoryFragment(title: String): NavDirections = ActionToCategory(title)

        fun actionToHomeFragment(): NavDirections = NavDirectionImpl(R.id.action_splash_to_main)

        fun actionToCreateNote(): NavDirections = NavDirectionImpl(R.id.action_to_create_note)

        fun actionToCreateNote(noteId: Int): NavDirections = ActionToCreateNote(noteId)
    }
}