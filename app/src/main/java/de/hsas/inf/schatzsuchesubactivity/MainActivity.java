package de.hsas.inf.schatzsuchesubactivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.button.MaterialButton;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;


import androidx.constraintlayout.widget.ConstraintLayout;

import de.hsas.inf.schatzsuchesubactivity.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;


public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    private ConstraintLayout cl;
    private MaterialButton btnHideTreasure;
    private static final int MAX_ISLANDS = 15;
    private static final int MAX_TRIES = 3;
    private static final String fileName = "Scores.txt";

    private ActivityResultLauncher<Intent> resultLauncher;

    private FileIOScores fileIOScores;
    private ScoreItem scoreItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        cl = findViewById(R.id.constrainLayout);
        Game game = new Game(MAX_TRIES);
        fileIOScores = new FileIOScores(this);

        for(int i=0; i<MAX_ISLANDS; i++) {
            ImageButton btn = (ImageButton) cl.getChildAt(i);
            btn.setEnabled(false);
            btn.setOnClickListener(v -> {

                scoreItem = game.checkForTreasureAndSeaMonster(btn);
                if (scoreItem != null){
                    fileIOScores.writeFile(fileName, scoreItem.toString());
                    fileIOScores.printFileContent(fileName);
                    setImageButtonState(false);
                    setMaterialButtonState();
                }

            });
        }
        btnHideTreasure = findViewById(R.id.btn_hideTreasure);
        btnHideTreasure.setOnClickListener(v ->{
            btnHideTreasure.setEnabled(false);
            btnHideTreasure.setText(R.string.searchTreasure);
            game.hideTreasureAndSeamonster();
            setImageButtonImage();
            setImageButtonState(true);
        });
        resultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
            }
        });

    }

    public void setImageButtonState(boolean bool){
        for(int j=0; j<MAX_ISLANDS; j++){
            ImageButton i_btn = (ImageButton) cl.getChildAt(j);
            i_btn.setEnabled(bool);
        };
    }
    public void setMaterialButtonState(){
        btnHideTreasure.setEnabled(true);
        btnHideTreasure.setText(R.string.hideTreasure);
    }
    public void setImageButtonImage(){
        for(int j=0; j<MAX_ISLANDS; j++){
            ImageButton i_btn = (ImageButton) cl.getChildAt(j);
            i_btn.setImageResource(R.mipmap.island);
        };
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.scores) {
            Intent startScores = new Intent(MainActivity.this, Scores.class);
            resultLauncher.launch(startScores);
        }

        return super.onOptionsItemSelected(item);
    }


}