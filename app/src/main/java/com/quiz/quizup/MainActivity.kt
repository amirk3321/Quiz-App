package com.quiz.quizup

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.quiz.quizup.AppConstent.Constent
import com.quiz.quizup.AppConstent.toast
import com.quiz.quizup.PrefUtils.preference
import com.quiz.quizup.makeUpQuiz.CreateQuiz
import com.quiz.quizup.makeUpQuiz.StartQuiz
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val RC_QUIZ_SCORE=123
    private var mHighScore=0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        onLoadScore()
        start_quiz.setOnClickListener {
            toast.msg("start",this)
            val intent=Intent(this,StartQuiz::class.java)
            startActivityForResult(intent,RC_QUIZ_SCORE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode==RC_QUIZ_SCORE && resultCode== Activity.RESULT_OK){
            val newscore=data!!.getIntExtra(Constent.SCORE_EXTRA,0)
            toast.msg("new score $newscore",this)
            if (newscore>mHighScore)
                onUpdateScoreUI(newscore)
        }
    }
    fun onLoadScore(){
        val score=preference.getScore(this)
        highscore.text="High Score :$score"
    }
    private fun onUpdateScoreUI(newScore: Int) {
       mHighScore=newScore
        highscore.text="High Score :$mHighScore"
        preference.setScore(mHighScore,this)
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val id =item!!.itemId
        when(id){
            R.id.action_create ->{
                startActivity(Intent(applicationContext, CreateQuiz::class.java))
                toast.msg("Create quiz click",this)
            }
            R.id.action_setting ->{
                toast.msg("Setting Click",this)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
