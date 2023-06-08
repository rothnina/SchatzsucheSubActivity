package de.hsas.inf.schatzsuchesubactivity;

import android.util.Log;
import android.widget.ImageButton;

import com.google.android.material.button.MaterialButton;

import java.time.LocalDateTime;
import java.util.Random;

public class Game {
    private int treasure;
    private int seaMonster;
    private static final int MAX_ISLANDS = 15;
    private int counter = 1;
    private int MAX_TRIES;

    private ScoreItem scoreItem;

    private MainActivity parent;

    public Game(MainActivity parent, int maxTries){
        this.parent = parent;
        this.MAX_TRIES = maxTries;
    }

    public void hideTreasureAndSeamonster(){
        parent.setImageButtonImage();
        treasure = new Random().nextInt(MAX_ISLANDS-1)+1;
        counter = 1;
        scoreItem = null;
        do {
            seaMonster = new Random().nextInt(MAX_ISLANDS-1)+1;
        } while(treasure == seaMonster);
        Log.d("MAIN", "treasure = " + treasure);
        Log.d("MAIN", "seaMonster= " + seaMonster);
    }
    public ScoreItem checkForTreasureAndSeaMonster(ImageButton btn) {
        Log.d("MAIN checkForTreasureAndSeaMonster()", "btn.getId(): " + btn.getId());
        String treasureId = "island_" + treasure;
        Log.d("MAIN checkForTreasureAndSeaMonster()", "TreasureId: " + treasureId);
        String seaMonsterId = "island_" + seaMonster;
        Log.d("MAIN checkForTreasureAndSeaMonster()", "SeaMonsterId: " + seaMonsterId);

        if (btn.getAccessibilityPaneTitle().equals(treasureId)) {
            Log.d("MAIN checkForTreasure", "btn.getAccessibilityPaneTitle: " + btn.getAccessibilityPaneTitle());
            btn.setImageResource(R.mipmap.treasure);
            scoreItem = new ScoreItem(counter, LocalDateTime.now());
            //parent.setImageButtonState(false);
        } else if (btn.getAccessibilityPaneTitle().equals(seaMonsterId)) {
            Log.d("MAIN checkForSeaMonster", "btn.getAccessibilityPaneTitle: " + btn.getAccessibilityPaneTitle());
            btn.setImageResource(R.mipmap.seamonster);
            scoreItem = new ScoreItem(-1, LocalDateTime.now());
            //parent.setImageButtonState(false);
        } else {
            Log.d("MAIN checkForTreasure", "counter = " + counter);
            btn.setImageResource(R.mipmap.wave);
            if (counter < MAX_TRIES) {
                counter++;
            } else {
                scoreItem = new ScoreItem(4, LocalDateTime.now());
                //parent.setImageButtonState(false);
            }

        }
        return scoreItem;
    }


}



