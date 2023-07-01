package com.example.quiztutorial;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView optionA,optionB,optionC,optionD;
    private TextView questionnumber,question,score;
    private TextView chechkout1,checkout2;
    int currentIndex;
    int mscore=0;
    int qn=1;
    ProgressBar progressBar;

    String CurrentQuestion,CurrentOptionA,CurrentOptionB,CurrentOptionC,CurrentOptionD;
    List<answerclass> questionBank = new ArrayList<>();



//    private answerclass[] questionBank= new answerclass[]
//            {
//                new answerclass(R.string.question_1,R.string.question1_A,R.string.question1_B,R.string.question1_C,R.string.question1_D,R.string.answer_1),
//                new answerclass(R.string.question_2,R.string.question_2A,R.string.question_2B,R.string.question_2C,R.string.question_2D,R.string.answer_2),
//                    new answerclass(R.string.question_3,R.string.question_3A,R.string.question_3B,R.string.question_3C,R.string.question_3D,R.string.answer_3),
//                    new answerclass(R.string.question_4,R.string.question_4A,R.string.question_4B,R.string.question_4C,R.string.question_4D,R.string.answer_4),
//                    new answerclass(R.string.question_5,R.string.question_5A,R.string.question_5B,R.string.question_5C,R.string.question_5D,R.string.answer_5),
//                    new answerclass(R.string.question_6,R.string.question_6A,R.string.question_6B,R.string.question_6C,R.string.question_6D,R.string.answer_6),
//                    new answerclass(R.string.question_7,R.string.question_7A,R.string.question_7B,R.string.question_7C,R.string.question_7D,R.string.answer_7),
//                    new answerclass(R.string.question_8,R.string.question_8A,R.string.question_8B,R.string.question_8C,R.string.question_8D,R.string.answer_8),
//
//
//
//
//            };

    int PROGRESS_BAR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("questionBank");


        optionA=findViewById(R.id.optionA);
        optionB=findViewById(R.id.optionB);
        optionC=findViewById(R.id.optionC);
        optionD=findViewById(R.id.optionD);

        question = findViewById(R.id.question);
        score=findViewById(R.id.score);
        questionnumber=findViewById(R.id.QuestionNumber);

        chechkout1=findViewById(R.id.selectoption);
        checkout2=findViewById(R.id.CorrectAnswer);



        question.setText(CurrentQuestion);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    answerclass answer = snapshot.getValue(answerclass.class);
                    questionBank.add(answer);
                }

                // Call the method to initialize the first question
                initializeQuestion(questionBank);
            }




            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle any error that occurs while fetching data from Firebase
            }
        });




        optionA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(CurrentOptionA);
                updateQuestion();

            }
        });

        optionB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(CurrentOptionB);
                updateQuestion();


            }
        });
        optionC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(CurrentOptionC);
                updateQuestion();


            }
        });
        optionD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                checkAnswer(CurrentOptionD);
                updateQuestion();

            }
        });
        











    }

    private void initializeQuestion(List<answerclass> questionBank) {



            CurrentQuestion = questionBank.get(currentIndex).getQuestion();
            question.setText(CurrentQuestion);
            CurrentOptionA = questionBank.get(currentIndex).getOptionA();
            optionA.setText(CurrentOptionA);
            CurrentOptionB = questionBank.get(currentIndex).getOptionB();
            optionB.setText(CurrentOptionB);
            CurrentOptionC = questionBank.get(currentIndex).getOptionC();
            optionC.setText(CurrentOptionC);
            CurrentOptionD = questionBank.get(currentIndex).getOptionD();
            optionD.setText(CurrentOptionD);

        // Add click listeners and updateQuestion() method as before
        // ...

        // Update the UI as needed
        progressBar.setMax(questionBank.size() * PROGRESS_BAR);
        score.setText("Score: " + mscore + "/" + questionBank.size());
        questionnumber.setText(qn + "/" + questionBank.size() + " Question");
    }

    private void checkAnswer(String userSelection) {
        String tag = "MyApp"; // Tag for identifying the log message


        Log.i(tag,currentIndex+" "+questionBank.size());

        String correctanswer= questionBank.get(currentIndex).getAnswer();

        chechkout1.setText(userSelection);
        checkout2.setText(correctanswer);

        String m= chechkout1.getText().toString().trim();
        String n=checkout2.getText().toString().trim();

        if(m.equals(n))
        {
            Toast.makeText(getApplicationContext(),"Right",Toast.LENGTH_SHORT).show();
            mscore=mscore+1;
            progressBar.incrementProgressBy(PROGRESS_BAR);
        }
        else
        {
            Toast.makeText(getApplicationContext(),"Wrong",Toast.LENGTH_SHORT).show();
        }




    }

    @SuppressLint("SetTextI18n")
    private void updateQuestion() {
        PROGRESS_BAR = (int) Math.ceil(100/questionBank.size());
// Get the reference to the ProgressBar


// Set the progress color
        progressBar.getProgressDrawable().setColorFilter(getResources().getColor(R.color.colorPrimary), android.graphics.PorterDuff.Mode.SRC_IN);

        currentIndex=(currentIndex+1)%questionBank.size();

        if(currentIndex==0)
        {

            AlertDialog.Builder alert=new AlertDialog.Builder(this);
            alert.setTitle("Game Over");
            alert.setCancelable(false);
            alert.setMessage("Your Score" + mscore +"points");
            alert.setPositiveButton("Close Application", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });

            alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    mscore=0;
                    qn=1;
                    progressBar.setProgress(0);
                    score.setText("Score" + mscore +"/" +questionBank.size());
                    questionnumber.setText(qn + "/" + questionBank.size() +"Question");
                }
            });

         alert.show();

        }



        CurrentQuestion= questionBank.get(currentIndex).getQuestion();
        question.setText(CurrentQuestion);
        CurrentOptionA= questionBank.get(currentIndex).getOptionA();
        optionA.setText(CurrentOptionA);
        CurrentOptionB= questionBank.get(currentIndex).getOptionB();
        optionB.setText(CurrentOptionB);
        CurrentOptionC= questionBank.get(currentIndex).getOptionC();
        optionC.setText(CurrentOptionC);
        CurrentOptionD= questionBank.get(currentIndex).getOptionD();
        optionD.setText(CurrentOptionD);

        qn=qn+1;

        if(qn<=questionBank.size())

        {
            questionnumber.setText(qn + "/" + questionBank.size()+"Question");
        }
        score.setText("Score" + mscore +"/" +questionBank.size());



    }


}