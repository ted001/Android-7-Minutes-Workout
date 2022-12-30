package com.ted.a7minutesworkout

import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ted.a7minutesworkout.databinding.ActivityExerciseBinding

class ExerciseActivity : AppCompatActivity() {

    // Adding a variables for the 10 seconds REST timer.
    // Variable for Rest Timer and later on we will initialize it.
    private var restTimer: CountDownTimer? = null

    // Variable for timer progress. As initial value the rest progress is set to 0.
    // As we are about to start.
    private var restProgress = 0

    // Adding a variables for the 30 seconds Exercise timer.
    // Variable for Exercise Timer and later on we will initialize it.
    private var exerciseTimer: CountDownTimer? = null
    // Variable for the exercise timer progress. As initial value the exercise progress is set to 0.
    // As we are about to start.
    private var exerciseProgress = 0

    // We will initialize the list later.
    private var exerciseList: ArrayList<ExerciseModel>? = null
    // Current Position of Exercise.
    private var currentExercisePosition = -1


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

        exerciseList = Constants.defaultExerciseList()
        setupRestView()
    }


    //(Step 3 - Setting up the Get Ready View with 10 seconds of timer.)-->
    /**
     * Function is used to set the timer for REST.
     */
    private fun setupRestView() {
        binding?.flRestView?.visibility = View.VISIBLE
        binding?.tvTitle?.visibility = View.VISIBLE
        binding?.upcomingLabel?.visibility = View.VISIBLE
        binding?.tvUpcomingExerciseName?.visibility = View.VISIBLE
        binding?.tvExerciseName?.visibility = View.INVISIBLE
        binding?.flExerciseView?.visibility = View.INVISIBLE
        binding?.ivImage?.visibility = View.INVISIBLE

        /**
         * Here firstly we will check if the timer is running the and
         * it is not null then cancel the running timer and start the new one.
         * And set the progress to initial which is 0.
         */
        if (restTimer != null) {
            restTimer!!.cancel()
            restProgress = 0
        }

        binding?.tvUpcomingExerciseName?.text = exerciseList!![currentExercisePosition + 1].name
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
                currentExercisePosition++
                // When the 10 seconds will complete this will be executed.
                setupExerciseView()
            }
        }.start()
    }

    // Step 4 - Setting up the Exercise View with a 30 seconds timer.
    /**
     * Function is used to set the progress of the timer using the progress for Exercise View.
     */
    private fun setupExerciseView() {
        binding?.flRestView?.visibility = View.INVISIBLE
        binding?.tvTitle?.visibility = View.INVISIBLE
        binding?.tvUpcomingExerciseName?.visibility = View.INVISIBLE
        binding?.upcomingLabel?.visibility = View.INVISIBLE
        binding?.tvExerciseName?.visibility = View.VISIBLE
        binding?.flExerciseView?.visibility = View.VISIBLE
        binding?.ivImage?.visibility = View.VISIBLE

        /**
         * Here firstly we will check if the timer is running
         * and it is not null then cancel the running timer and start the new one.
         * And set the progress to the initial value which is 0.
         */
        if (exerciseTimer != null) {
            exerciseTimer?.cancel()
            exerciseProgress = 0
        }

        binding?.ivImage?.setImageResource(exerciseList!![currentExercisePosition].image)
        binding?.tvExerciseName?.text = exerciseList!![currentExercisePosition].name

        setExerciseProgressBar()
    }

    // After REST View Setting up the 30 seconds timer for the Exercise view and updating it continuously.
    /**
     * Function is used to set the progress of the timer using the progress for Exercise View for 30 Seconds
     */
    private fun setExerciseProgressBar() {

        binding?.progressBarExercise?.progress = exerciseProgress

        exerciseTimer = object : CountDownTimer(30000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                exerciseProgress++
                binding?.progressBarExercise?.progress = 30 - exerciseProgress
                binding?.tvTimerExercise?.text = (30 - exerciseProgress).toString()
            }

            override fun onFinish() {
                if (currentExercisePosition < exerciseList?.size!! - 1) {
                    setupRestView()
                } else {
                    Toast.makeText(
                        this@ExerciseActivity,
                        "Congratulations! You have completed the 7 minutes workout.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }.start()
    }

    // Destroying the timer when closing the activity or app
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