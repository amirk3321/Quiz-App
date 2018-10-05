package com.quiz.quizup.PrefUtils

import android.content.Context
import android.preference.PreferenceManager

object preference {
    val SCORE_ID="com.quiz.quizup.SCOURE_ID"

    fun getScore(context: Context) : Int{
        val pref =PreferenceManager.getDefaultSharedPreferences(context)
       return pref.getInt(SCORE_ID,0)
    }
    fun setScore(score :Int,context: Context) {
        val pref=PreferenceManager.getDefaultSharedPreferences(context)
        val edit=pref.edit()
        edit.putInt(SCORE_ID,score).apply()
    }
}