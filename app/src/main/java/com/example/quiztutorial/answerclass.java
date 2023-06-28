package com.example.quiztutorial;

public class answerclass {
//
//    private int optionA,optionB,optionC,optionD,questionid,answerid;
//
//    public answerclass(int questionide,int optiona,int optionb,int optionc,int optiond,int answeride)
//    {
//        questionid=questionide;
//        optionA=optiona;
//        optionB=optionb;
//        optionC=optionc;
//        optionD=optiond;
//        answerid=answeride;
//
//
//
//    }
//
//
//    public int getOptionA() {
//        return optionA;
//    }
//
//    public int getOptionB() {
//        return optionB;
//    }
//
//    public int getOptionC() {
//        return optionC;
//    }
//
//    public int getOptionD() {
//        return optionD;
//    }
//
//    public int getQuestionid() {
//        return questionid;
//    }
//
//    public int getAnswerid() {
//        return answerid;
//    }
private String question;
    private String optionA;
    private String optionB;
    private String optionC;
    private String optionD;
    private String answer;

    public answerclass() {
        // Default constructor required for Firebase
    }

    public answerclass(String question, String optionA, String optionB, String optionC, String optionD, String answer) {
        this.question = question;
        this.optionA = optionA;
        this.optionB = optionB;
        this.optionC = optionC;
        this.optionD = optionD;
        this.answer = answer;
    }

    public String getQuestion() {
        return question;
    }

    public String getOptionA() {
        return optionA;
    }

    public String getOptionB() {
        return optionB;
    }

    public String getOptionC() {
        return optionC;
    }

    public String getOptionD() {
        return optionD;
    }

    public String getAnswer() {
        return answer;
    }
}
