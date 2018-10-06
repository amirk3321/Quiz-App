package com.quiz.quizup.makeUpQuiz

import android.app.Activity
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import com.quiz.quizup.AppConstent.Constent
import com.quiz.quizup.AppConstent.toast
import com.quiz.quizup.MainActivity
import com.quiz.quizup.R
import com.quiz.quizup.SqliteUtils.Sqlitehelper
import com.quiz.quizup.model.QuestionModel
import kotlinx.android.synthetic.main.activity_start_quiz.*
import java.util.*

class StartQuiz : Activity() {
    private var mTimeMillieSecund = 30000L
    private var mTimeSecondLeft = 0L
    private lateinit var mTimer: CountDownTimer
    private lateinit var mTimerDefaultColor: ColorStateList
    private var mListColorState: ColorStateList? = null
    private lateinit var mQuestionList: MutableList<QuestionModel>
    private var mQuestionCurrent = QuestionModel()
    private var mScoure = 0
    private var mCounterQuestion = 0
    private var mTotalQuestion = 0
    private var mAnswer = true
    private var mBackPressTime = 0L


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start_quiz)
        val mDb = Sqlitehelper(this)
        mQuestionList = mutableListOf()
        mQuestionList = mDb.ongetQuestion()
        mTotalQuestion = mQuestionList.size
        mListColorState = option1.textColors
        Collections.shuffle(mQuestionList)

        mTimerDefaultColor = counttime.textColors


        onShowNextQuestion()
        finish.setOnClickListener {

            if (!mAnswer) {
                if (option1.isChecked || option2.isChecked || option3.isChecked || option4.isChecked)
                    onCheckAns()
                else
                    toast.msg("Please Select One Option", this)
            } else
                onShowNextQuestion()
        }

    }

    fun onStartCountDownTimer() {
        mTimer = object : CountDownTimer(mTimeSecondLeft, 1000) {
            override fun onFinish() {
                mTimeSecondLeft = 0
                onUpdateTimerTextUI()
                onCheckAns()
            }

            override fun onTick(MinUntilFinsih: Long) {
                mTimeSecondLeft = MinUntilFinsih
                onUpdateTimerTextUI()
            }
        }.start()
    }

    fun onUpdateTimerTextUI() {
        val minte = (mTimeSecondLeft / 1000) / 60
        val second = (mTimeSecondLeft / 1000) % 60
        val format = String.format(Locale.getDefault(), "%02d:%02d", minte, second)
        counttime.text = format
        if (mTimeSecondLeft >= 10000 && mTimeSecondLeft < 20000)
            counttime.setTextColor(Color.rgb(9, 196, 0))
        else if (mTimeSecondLeft > 5000 && mTimeSecondLeft < 10000)
            counttime.setTextColor(Color.rgb(255, 67, 0))
        else if (mTimeSecondLeft <= 5000)
            counttime.setTextColor(Color.rgb(211, 0, 7))
        else
            counttime.setTextColor(mTimerDefaultColor)
    }

    private fun onCheckAns() {
        mAnswer = true
        mTimer.cancel()
        val radiobtn = findViewById<View>(optionGrp.checkedRadioButtonId)
        var answare = optionGrp.indexOfChild(radiobtn) + 1
        if (answare == mQuestionCurrent.answar) {
            mScoure++
            socure_tv.text = "Scoure :$mScoure"
        }
        onShowSulotion()

    }

    private fun onShowSulotion() {
        option1.setTextColor(Color.RED)
        option2.setTextColor(Color.RED)
        option3.setTextColor(Color.RED)
        option4.setTextColor(Color.RED)
        when (mQuestionCurrent.answar) {
            1 -> {
                option1.setTextColor(Color.GREEN)
                toast.msg("question 1 is Right", this)
            }
            2 -> {
                option2.setTextColor(Color.GREEN)
                toast.msg("question 2 is Right", this)
            }
            3 -> {
                option3.setTextColor(Color.GREEN)
                toast.msg("question 3 is Right", this)
            }
            4 -> {
                option4.setTextColor(Color.GREEN)
                toast.msg("question 4 is Right", this)
            }
        }
        if (mCounterQuestion < mTotalQuestion)
            finish.text = "next"
        else
            finish.text = "finish"
    }

    private fun onShowNextQuestion() {
        option1.setTextColor(mListColorState)
        option2.setTextColor(mListColorState)
        option3.setTextColor(mListColorState)
        option4.setTextColor(mListColorState)
        optionGrp.clearCheck()
        if (mCounterQuestion < mTotalQuestion) {
            mQuestionCurrent = mQuestionList[mCounterQuestion]
            question.text = mQuestionCurrent.question
            option1.text = mQuestionCurrent.option1
            option2.text = mQuestionCurrent.option2
            option3.text = mQuestionCurrent.option3
            option4.text = mQuestionCurrent.option4
            mCounterQuestion++
            total_question_tv.text = "Question $mCounterQuestion / $mTotalQuestion"
            mAnswer = false
            finish.text = "Confirm"
            mTimeSecondLeft = mTimeMillieSecund
            onStartCountDownTimer()
        } else
            onFinish()

    }

    private fun onFinish() {
        val scoreIntent = Intent()
        scoreIntent.putExtra(Constent.SCORE_EXTRA, mScoure)
        setResult(RESULT_OK, scoreIntent)
        finish()
    }

    override fun onBackPressed() {
        if (mBackPressTime + 2000 > System.currentTimeMillis())
            onFinish()
        else
            toast.msg("Press back simultaneously ", this)

        mBackPressTime = System.currentTimeMillis()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (mTimer != null)
            mTimer.cancel()
    }
}
