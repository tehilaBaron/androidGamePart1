package com.example.firstex;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private static final long DELAY = 1000L;
    private ExtendedFloatingActionButton main_FAB_left;
    private ExtendedFloatingActionButton main_FAB_right;
    private AppCompatImageView[] main_IMG_dwarfs;
    private AppCompatImageView[][] main_IMG_hammers;
    private AppCompatImageView[] main_IMG_hearts;
    private boolean timerOn = false;
    private long startTime;
    private Timer timer;
    private GameManeger gameManeger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        initViews();
        gameManeger = new GameManeger(main_IMG_hearts.length, main_IMG_hammers.length, main_IMG_hammers[0].length);
    }

    @Override
    protected void onStart() {
        super.onStart();
        startTimer();
    }

    @Override
    protected void onResume() {
        super.onResume();
        startTimer();
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopTimer();
    }

    private void initViews() {
        main_FAB_left.setOnClickListener(v -> moveDwarfClicked(1));
        main_FAB_right.setOnClickListener(v -> moveDwarfClicked(-1));
    }

    private void moveDwarfClicked(int number) {
        gameManeger.moveDwarf(number);
        refreshUI();
    }

    private void moveDwarfUI() {
        for (int i = 0; i < main_IMG_dwarfs.length; i++) {
            if (gameManeger.dwarfArray[i]) {
                main_IMG_dwarfs[i].setVisibility(View.VISIBLE);
            } else {
                main_IMG_dwarfs[i].setVisibility(View.INVISIBLE);
            }
        }

    }

    private void showLifeUI() {
        boolean hasCollition = gameManeger.checkCollition();
        if (hasCollition) {
            toastAndVibrate("Oops! You lost a life");
            main_IMG_hearts[gameManeger.getLife()].setVisibility(View.INVISIBLE);
        }
    }

    private void updateHummerMatrix() {
        gameManeger.updateMatrix();
        refreshUI();
    }


    private void startTimer() {
        if (!timerOn) {
            Log.d("startTimer", "startTimer: Timer Started");
            startTime = System.currentTimeMillis();
            timerOn = true;
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    runOnUiThread(() -> updateHummerMatrix());
                }
            }, 0L, DELAY);
        }
    }

    private void updateHammersUI() {
        for (int i = 0; i < gameManeger.getRowSize(); i++) {
            for (int j = 0; j < gameManeger.getColSize(); j++) {
                if (gameManeger.getHammerMatrix()[i][j]) {
                    main_IMG_hammers[i][j].setVisibility(View.VISIBLE);
                } else {
                    main_IMG_hammers[i][j].setVisibility(View.INVISIBLE);

                }
            }
        }

    }

    private void refreshUI() {
        moveDwarfUI();
        showLifeUI();
        updateHammersUI();
    }


    private void stopTimer() {
        timerOn = false;
        Log.d("stopTimer", "stopTimer: Timer Stopped");
        timer.cancel();
    }

    private void toastAndVibrate(String text) {
        vibrate();
        toast(text);
    }

    private void toast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_LONG).show();
    }

    private void vibrate() {
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        // Vibrate for 500 milliseconds
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            //deprecated in API 26
            v.vibrate(500);
        }
    }

    private void findViews() {
        main_FAB_left = findViewById(R.id.main_FAB_left);
        main_FAB_right = findViewById(R.id.main_FAB_right);
        main_IMG_hearts = new AppCompatImageView[]{
                findViewById(R.id.main_IMG_heart1),
                findViewById(R.id.main_IMG_heart2),
                findViewById(R.id.main_IMG_heart3)
        };
        main_IMG_dwarfs = new AppCompatImageView[]{
                findViewById(R.id.main_IMG_dwarf1),
                findViewById(R.id.main_IMG_dwarf2),
                findViewById(R.id.main_IMG_dwarf3)
        };
        main_IMG_hammers = new AppCompatImageView[][]{
                {findViewById(R.id.main_IMG_hammer11),
                        findViewById(R.id.main_IMG_hammer12),
                        findViewById(R.id.main_IMG_hammer13)},
                {findViewById(R.id.main_IMG_hammer21),
                        findViewById(R.id.main_IMG_hammer22),
                        findViewById(R.id.main_IMG_hammer23)},
                {findViewById(R.id.main_IMG_hammer31),
                        findViewById(R.id.main_IMG_hammer32),
                        findViewById(R.id.main_IMG_hammer33)},
                {findViewById(R.id.main_IMG_hammer41),
                        findViewById(R.id.main_IMG_hammer42),
                        findViewById(R.id.main_IMG_hammer43)},
                {findViewById(R.id.main_IMG_hammer51),
                        findViewById(R.id.main_IMG_hammer52),
                        findViewById(R.id.main_IMG_hammer53)},
                {findViewById(R.id.main_IMG_hammer61),
                        findViewById(R.id.main_IMG_hammer62),
                        findViewById(R.id.main_IMG_hammer63)},
                {findViewById(R.id.main_IMG_hammer71),
                        findViewById(R.id.main_IMG_hammer72),
                        findViewById(R.id.main_IMG_hammer73)},
                {findViewById(R.id.main_IMG_hammer81),
                        findViewById(R.id.main_IMG_hammer82),
                        findViewById(R.id.main_IMG_hammer83)}

        };
    }
}
