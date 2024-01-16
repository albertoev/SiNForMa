package com.pimcd2014.appsinforma;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.pimcd2014.appsinforma.R;

/**
 * Created by Alberto on 18/11/2015.
 */
public class DifficultyLevels extends Activity implements OnClickListener {
    private boolean isWord = true;
    private String game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
        readParameters();
        setContentView(R.layout.activity_difficulty_levels);
        initOnClick();
    }

    // Reads the parameters
    private void readParameters() {
        Intent intent = getIntent(); // gets the previously created intent
        isWord = intent.getBooleanExtra("isWord", true);
        game   = intent.getStringExtra("game");
    }

    // Initializes OnClick events
    private void initOnClick() {
        findViewById(R.id.butEasy).setOnClickListener(this);
        findViewById(R.id.butMedium).setOnClickListener(this);
        findViewById(R.id.butHard).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int butPressID = ((Button) v).getId();
        int level = 0;
        boolean isWords = false;

        switch (butPressID){
            case R.id.butEasy:   level = 0; break;
            case R.id.butMedium: level = 1; break;
            case R.id.butHard:   level = 2; break;
        }

        try {
            Class<?> clazz = Class.forName("com.pimcd2014.appsinforma." + game);
            Intent intent = new Intent(this, clazz);
            intent
                .putExtra("level", level)
                .putExtra("isWord", isWord);
            startActivity(intent);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
