package com.quiz.quizup.makeUpQuiz

import android.app.Activity
import android.os.Bundle
import com.quiz.quizup.AppConstent.toast
import com.quiz.quizup.R
import com.quiz.quizup.SqliteUtils.Sqlitehelper
import com.quiz.quizup.model.QuestionModel
import kotlinx.android.synthetic.main.activity_create_quiz.*

class CreateQuiz : Activity() {
    lateinit var db : Sqlitehelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_quiz)
        db= Sqlitehelper(this)
        create.setOnClickListener {
            if (question.text.isNotBlank()||question1.text.isNotBlank()||
                    question2.text.isNotBlank()||question3.text.isNotBlank()||
                    question4.text.isNotBlank()||answar.text.isNotBlank()){
                db.onInsertData(QuestionModel(question.text.toString(),question1.text.toString(),
                        question2.text.toString(),
                        question3.text.toString(),
                        question4.text.toString(),
                        answar.text.toString().toInt()))
                db.close()
                clearall()
                toast.msg("Question create",this)
            }else
                toast.msg("Fill the Given Fields",this)

        }
    }

    private fun clearall() {
        question.text.clear()
        question1.text.clear()
        question2.text.clear()
        question3.text.clear()
        question4.text.clear()
        answar.text.clear()
    }


}
