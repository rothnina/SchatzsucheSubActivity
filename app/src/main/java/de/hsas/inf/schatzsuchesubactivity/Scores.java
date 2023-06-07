package de.hsas.inf.schatzsuchesubactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class Scores extends AppCompatActivity {

    private LinearLayout ll;
    private Button btnClear;
    private TextView score1, score2, score3, score4, score5;
    private FileIOScores fileIOScores;
    private ArrayList<ScoreItem> scores;
    private static final String fileName = "Scores.txt";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scores);
        ll = findViewById(R.id.linearLayout);
        btnClear = findViewById(R.id.btnClear);
        score1 = findViewById(R.id.score_1);
        score2 = findViewById(R.id.score_2);
        score3 = findViewById(R.id.score_3);
        score4 = findViewById(R.id.score_4);
        score5 = findViewById(R.id.score_5);

        fileIOScores = new FileIOScores(this);
        scores = fileIOScores.readScores(fileName);
        Log.i("Im ScoresView", "vor tv.settext");
        fileIOScores.printFileContent(fileName);

        Log.i("Scores.onCreate()", "scoresize = " + scores.size());
        int size = 5;
        if(scores.size()<size){
            size= scores.size();
        }
        for (int i = 1; i<=size; i++){
            TextView score = (TextView) ll.getChildAt(i);
            score.setText(scores.get(scores.size()-i).toString());
        }

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fileIOScores.eraseContent(fileName);
                for (int i=0; i<5; i++){
                    TextView score = (TextView) ll.getChildAt(i+1);
                    Log.i("Scores btnClear.setOnClickListener()", "score.size()" + scores.size());
                    score.setText("");
                }
            }
        });
    }
}