package de.hsas.inf.schatzsuchesubactivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class Scores extends AppCompatActivity {

    private LinearLayout ll;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private MiniAdapter adapter;
    private Button btnClear;
    private TextView score1, score2, score3, score4, score5;
    private FileIOScores fileIOScores;
    private ArrayList<ScoreItem> scores;
    private ArrayList<String> scoreStrings = new ArrayList<String>();
    private static final String fileName = "Scores.txt";
    private ImageView seamonster;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scores);
        ll = findViewById(R.id.linearLayout);
        btnClear = findViewById(R.id.btnClear);
        recyclerView = findViewById(R.id.recyclerView);
        seamonster = findViewById(R.id.iv_anim);

        Animation anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_rotate);
        seamonster.startAnimation(anim);



        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        fileIOScores = new FileIOScores(this);
        scores = fileIOScores.readScores(fileName);
        Log.i("Im ScoresView", "vor tv.settext");
        fileIOScores.printFileContent(fileName);

        adapter = new MiniAdapter(scoreStrings);
        recyclerView.setAdapter(adapter);
        for (ScoreItem si : scores){
            Log.d("ScoreItem", si.toString());
            adapter.add(si.toString());
        }
        Log.i("ScoreItemAmount", scoreStrings.toString());


        Log.i("Scores.onCreate()", "scoresize = " + scores.size());

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fileIOScores.eraseContent(fileName);
                adapter.clear();
            }
        });
    }
}