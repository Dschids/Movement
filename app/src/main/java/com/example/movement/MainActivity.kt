package com.example.movement

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import android.view.animation.LinearInterpolator
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView
import com.example.movement.databinding.ActivityMainBinding

private lateinit var _maBinding: ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var bike : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _maBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(_maBinding.root)

        // kind of defeats the purpose of the binding but i'm lazy
        bike = _maBinding.ivBike

        _maBinding.button.setOnClickListener {
            weeee()
        }

    }

    private fun weeee() {
        val container = bike.parent as ViewGroup

        // get the width and height of the frame layout that holds the star
        val containerW = container.width
        val containerH = container.height

        // get the width and height of the star image
        var bikeW: Float = bike.width.toFloat()
        var bikeH: Float = bike.height.toFloat()

        // create a new View to hold the star graphic
        val newBike = AppCompatImageView(this)
        // set the image contained in newStar
        newBike.setImageResource(R.drawable.pedal_bike_24)
        newBike.layoutParams = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.WRAP_CONTENT,
            FrameLayout.LayoutParams.WRAP_CONTENT)
        container.addView(newBike)

        // set the starting scale of newBike, scaleX is part of the View library
        newBike.scaleX = 1.5f
        newBike.scaleY = 1.5f

        bikeW = newBike.scaleX
        bikeH = newBike.scaleY

        // set the starting location
        newBike.translationX = bikeW/2
        newBike.translationY = (Math.random().toFloat() * (containerH/3) + containerH/4)

        // animators to move bike
        val xMover = ObjectAnimator.ofFloat(newBike, View.TRANSLATION_X, -bikeW, containerW + bikeW)
        // accelerates the bike from left to right
        xMover.interpolator = AccelerateInterpolator(.5f)

        // animator to rotate the star a random amount
        val rotator = ObjectAnimator.ofFloat(newBike, View.ROTATION,
            (Math.random() * -20).toFloat())
        // rotate the stars
        rotator.interpolator = LinearInterpolator()
        // AnimatorSet lets you play multiple animations together
        val set = AnimatorSet()
        // what animations to play
        set.playTogether(xMover, rotator)
        // how long to play them
        set.duration = (Math.random() * 1500 + 500).toLong()

        // add a listener to detect the end of the animation then delete the created newStar view
        set.addListener(object: AnimatorListenerAdapter(){
            override fun onAnimationEnd(animation: Animator) {
                container.removeView(newBike)
            }
        })
        set.start()

    }

}