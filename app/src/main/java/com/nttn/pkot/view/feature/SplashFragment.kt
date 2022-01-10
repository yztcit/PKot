package com.nttn.pkot.view.feature

import android.animation.Animator
import android.view.animation.DecelerateInterpolator
import androidx.navigation.fragment.findNavController
import com.nttn.pkot.R
import com.nttn.pkot.SplashFragmentBinding
import com.nttn.pkot.base.BaseVBFragment

class SplashFragment : BaseVBFragment<SplashFragmentBinding>() {

    override fun getLayoutId() = R.layout.fragment_splash

    override fun initView() {
        mBinding.splashLayout.alpha = 0.3f
        mBinding.splashLayout.animate()
            .alpha(1.0f)
            .setDuration(1200)
            .setInterpolator(DecelerateInterpolator())
            .setListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator?) {

            }

            override fun onAnimationEnd(animation: Animator?) {
                findNavController().navigate(NavFragmentDirections.actionToHomeFragment())
            }

            override fun onAnimationCancel(animation: Animator?) {

            }

            override fun onAnimationRepeat(animation: Animator?) {

            }
        }).start()
    }
}