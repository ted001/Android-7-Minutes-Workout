package com.ted.a7minutesworkout

import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ted.a7minutesworkout.databinding.ActivityExerciseBinding

class ExerciseActivity : AppCompatActivity() {

    // (Step 1 - Adding a variables for the 10 seconds REST timer.)
    // Variable for Rest Timer and later on we will initialize it.
    private var restTimer: CountDownTimer? = null

    // Variable for timer progress. As initial value the rest progress is set to 0.
    // As we are about to start.
    private var restProgress = 0

    // create a binding variable
    private var binding: ActivityExerciseBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //inflate the layout
        binding = ActivityExerciseBinding.inflate(layoutInflater)
        // pass in binding?.root in the content view
        setContentView(binding?.root)
        // then set support action bar and get toolBarExcerciser using the binding variable
        setSupportActionBar(binding?.toolbarExercise)

        if (supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        binding?.toolbarExercise?.setNavigationOnClickListener {
            onBackPressed()
        }

        //(Step 4 - Calling the function to make it visible on screen.)-->
        // REST View is set in this function
        setupRestView()
    }


    //(Step 3 - Setting up the Get Ready View with 10 seconds of timer.)-->
    /**
     * Function is used to set the timer for REST.
     */
    private fun setupRestView() {
        /**
         * Here firstly we will check if the timer is running the and
         * it is not null then cancel the running timer and start the new one.
         * And set the progress to initial which is 0.
         */
        if (restTimer != null) {
            restTimer!!.cancel()
            restProgress = 0
        }

        // This function is used to set the progress details.
        setRestProgressBar()
    }

    //(Step 2 - Setting up the 10 seconds timer for rest view and updating it continuously.)-->
    /**
     * Function is used to set the progress of timer using the progress
     */
    private fun setRestProgressBar() {
        // Sets the current progress to the specified value.
        binding?.progressBar?.progress = restProgress

        /**
         * @param millisInFuture The number of millis in the future from the call
         *   to {#start()} until the countdown is done and {#onFinish()}
         *   is called.
         * @param countDownInterval The interval along the way to receive
         *   {#onTick(long)} callbacks.
         */
        // Here we have started a timer of 10 seconds so the 10000 is milliseconds is 10 seconds
        // and the countdown interval is 1 second so it 1000.
        restTimer = object : CountDownTimer(10000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                // It is increased by 1
                restProgress++
                // Indicates progress bar progress
                binding?.progressBar?.progress = 10 - restProgress
                // Current progress is set to text view in terms of seconds.
                binding?.tvTimer?.text = (10 - restProgress).toString()
            }

            override fun onFinish() {
                // When the 10 seconds will complete this will be executed.
                Toast.makeText(
                    this@ExerciseActivity,
                    "Here now we will start the exercise.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }.start()
    }

    //(Step 5 - Destroying the timer when closing the activity or app.)-->
    /**
     * Here in the Destroy function we will reset the rest timer if it is running.
     */
    public override fun onDestroy() {
        if (restTimer != null) {
            restTimer?.cancel()
            restProgress = 0
        }
        super.onDestroy()
        binding = null
    }
}