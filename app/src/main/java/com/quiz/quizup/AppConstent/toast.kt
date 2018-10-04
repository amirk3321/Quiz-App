package com.quiz.quizup.AppConstent

import android.content.Context
import android.widget.Toast

object toast {
    fun msg(msg : String, ctx : Context){
        Toast.makeText(ctx,msg,Toast.LENGTH_SHORT).show()
    }
}