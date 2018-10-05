package com.quiz.quizup.SqliteUtils

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.quiz.quizup.model.QuestionModel

class Sqlitehelper : SQLiteOpenHelper {
    constructor(context: Context) : super(context, "history.db", null, 1)
    private lateinit var db : SQLiteDatabase
    private val TABLE = "history"
    private val ID = "id"
    private val QUESTION = "question"
    private val OPTION1 = "option1"
    private val OPTION2 = "option2"
    private val OPTION3 = "option3"
    private val OPTION4 = "option4"
    private val ANSWAR = "answer"
    private val CREATE_TABLE = "CREATE  TABLE $TABLE ($ID INTEGER PRIMARY KEY AUTOINCREMENT, $QUESTION TEXT NOT NULL,$OPTION1 TEXT NOT NULL, $OPTION2 TEXT NOT NULL, $OPTION3 TEXT NOT NULL, $OPTION4 TEXT NOT NULL,$ANSWAR INTEGER);"
    private val DROP_TABLE = "DROP TABLE IF EXISTS $TABLE"

    override fun onCreate(db: SQLiteDatabase?) {
        db!!.execSQL(CREATE_TABLE)
       // onCreateQuestion()
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db!!.execSQL(DROP_TABLE)
        onCreate(db)
    }
    fun onInsertData(question : QuestionModel){
        db=this.writableDatabase
        val v=ContentValues()
        v.apply {
            put(QUESTION,question.question)
            put(OPTION1,question.option1)
            put(OPTION2,question.option2)
            put(OPTION3,question.option3)
            put(OPTION4,question.option4)
            put(ANSWAR,question.answar)
        }
        db.insert(TABLE,null,v)
}
    fun ongetQuestion() : MutableList<QuestionModel>{
        db=this.readableDatabase
        val questionList= mutableListOf<QuestionModel>()
        val cursor=db!!.rawQuery("SELECT * FROM $TABLE",null)
        if (cursor.moveToFirst()){
            do {
                val question=QuestionModel()
                question.question=cursor.getString(cursor.getColumnIndex(QUESTION))
                question.option1=cursor.getString(cursor.getColumnIndex(OPTION1))
                question.option2=cursor.getString(cursor.getColumnIndex(OPTION2))
                question.option3=cursor.getString(cursor.getColumnIndex(OPTION3))
                question.option4=cursor.getString(cursor.getColumnIndex(OPTION4))
                question.answar=cursor.getInt(cursor.getColumnIndex(ANSWAR))
                questionList.add(question)
            }while (cursor.moveToNext())
        }
        cursor.close()
        return questionList
    }
    fun onDelete(id : String){
        db=this.writableDatabase
        db.delete(TABLE,null, arrayOf(id))
    }
}
