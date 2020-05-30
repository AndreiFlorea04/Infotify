package com.thecode.infotify.activities

import android.animation.Animator
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.DisplayMetrics
import android.view.animation.DecelerateInterpolator
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.dynamicanimation.animation.DynamicAnimation
import androidx.dynamicanimation.animation.SpringAnimation
import androidx.dynamicanimation.animation.SpringForce
import com.thecode.infotify.R
import com.thecode.infotify.utils.SharedPreferenceUtils
import io.github.inflationx.viewpump.ViewPumpContextWrapper
import kotlinx.android.synthetic.main.activity_splash.*


class SplashActivity : AppCompatActivity() {

    private lateinit var springForce: SpringForce


    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_splash)
        Handler().postDelayed({
            //do stuff
            //Like your Background calls and all
            springForce = SpringForce(0f)
            relative_layout.pivotX = 0f
            relative_layout.pivotY = 0f
            val springAnim = SpringAnimation(relative_layout, DynamicAnimation.ROTATION).apply {
                springForce.dampingRatio = SpringForce.DAMPING_RATIO_HIGH_BOUNCY
                springForce.stiffness = SpringForce.STIFFNESS_VERY_LOW
            }
            springAnim.spring = springForce
            springAnim.setStartValue(80f)
            springAnim.addEndListener { _, _, _, _ ->
                val displayMetrics = DisplayMetrics()
                windowManager.defaultDisplay.getMetrics(displayMetrics)
                val height = displayMetrics.heightPixels.toFloat()
                val width = displayMetrics.widthPixels
                relative_layout.animate()
                    .setStartDelay(1)
                    .translationXBy(width.toFloat() / 2)
                    .translationYBy(height)
                    .setListener(object : Animator.AnimatorListener {
                        override fun onAnimationRepeat(p0: Animator?) {

                        }

                        @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
                        override fun onAnimationEnd(p0: Animator?) {
                            lateinit var intent : Intent
                            if(SharedPreferenceUtils.getIsOnboardingCompleted()){
                                 intent = Intent(applicationContext, MainActivity::class.java)
                            }else{
                                intent = Intent(applicationContext, OnboardingActivity::class.java)
                            }
                            finish()
                            startActivity(intent)

                            overridePendingTransition(0, 0)
                        }

                        override fun onAnimationCancel(p0: Animator?) {

                        }

                        override fun onAnimationStart(p0: Animator?) {

                        }

                    })
                    .setInterpolator(DecelerateInterpolator(1f))
                    .start()
            }
            springAnim.start()
        }, 4000)

    }
}