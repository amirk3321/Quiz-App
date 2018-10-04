package com.quiz.quizup

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.quiz.quizup.AppConstent.toast
import com.quiz.quizup.makeUpQuiz.CreateQuiz
import com.quiz.quizup.makeUpQuiz.StartQuiz
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        start_quiz.setOnClickListener {
            toast.msg("start",this)
            val intent=Intent(this,StartQuiz::class.java)
            startActivity(intent)
        }
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
