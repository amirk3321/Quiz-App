package com.quiz.quizup.model

class QuestionModel(var question : String,
                    var option1 : String,
                    var option2: String,
                    var option3 : String,
                    var option4 : String,
                    var answar : Int) {
    constructor() : this("","","","","",0)
}