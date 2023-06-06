package de.hsas.inf.schatzsuchesubactivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.button.MaterialButton;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.navigation.ui.AppBarConfiguration;

import de.hsas.inf.schatzsuchesubactivity.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;

    private ConstraintLayout cl;
    private MaterialButton btnHideTreasure;
    private int treasure;
    private int seaMonster;
    private static final int MAX_ISLANDS = 15;
    private int counter = 0;
    private static final int MAX_TRIES = 3;

    private ActivityResultLauncher<Intent> resultLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        cl = findViewById(R.id.constrainLayout);
        for(int i=0; i<MAX_ISLANDS; i++) {
            ImageButton btn = (ImageButton) cl.getChildAt(i);
            btn.setEnabled(false);
            btn.setOnClickListener(v -> {
                checkForTreasureAndSeaMonster(btn);
            });
        }
        btnHideTreasure = findViewById(R.id.btn_hideTreasure);
        btnHideTreasure.setOnClickListener(v ->{
            btnHideTreasure.setEnabled(false);
            treasure = new Random().nextInt(MAX_ISLANDS-1)+1;
            do {
                seaMonster = new Random().nextInt(MAX_ISLANDS-1)+1;
            } while(treasure == seaMonster);
            Log.d("MAIN", "treasure = " + treasure);
            Log.d("MAIN", "seaMonster= " + seaMonster);
            setImageButtonState(true);
        });
        resultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
            }
        });

    }
    public void checkForTreasureAndSeaMonster(ImageButton btn){
        Log.d("MAIN checkForTreasureAndSeaMonster()", "btn.getId(): " + btn.getId());
        String treasureId = "island_" + treasure;
        Log.d("MAIN checkForTreasureAndSeaMonster()", "TreasureId: " + treasureId);
        String seaMonsterId = "island_" + seaMonster;
        Log.d("MAIN checkForTreasureAndSeaMonster()", "SeaMonsterId: " + seaMonsterId);
        if (btn.getAccessibilityPaneTitle().equals(treasureId)){
            Log.d("MAIN checkForTreasure", "btn.getAccessibilityPaneTitle: " + btn.getAccessibilityPaneTitle());
            btn.setImageResource(R.mipmap.treasure);
            setImageButtonState(false);
        }
        else if (btn.getAccessibilityPaneTitle().equals(seaMonsterId)) {
            Log.d("MAIN checkForSeaMonster", "btn.getAccessibilityPaneTitle: " + btn.getAccessibilityPaneTitle());
            btn.setImageResource(R.mipmap.seamonster);
            setImageButtonState(false);
        }
        else{
            if (counter < MAX_TRIES) {
                counter++;
                Log.d("MAIN checkForTreasure", "counter = " + counter);
                btn.setImageResource(R.mipmap.wave);
            }
            else{
                setImageButtonState(false);
            }

        }

    }
    public void setImageButtonState(boolean bool){
        for(int j=0; j<MAX_ISLANDS; j++){
            ImageButton i_btn = (ImageButton) cl.getChildAt(j);
            i_btn.setEnabled(bool);
        };
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.scores) {
            Intent startScores = new Intent(MainActivity.this, Scores.class);
            resultLauncher.launch(startScores);
        }

        return super.onOptionsItemSelected(item);
    }


}