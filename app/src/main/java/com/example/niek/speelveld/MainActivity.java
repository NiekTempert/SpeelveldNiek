package com.example.niek.speelveld;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity implements View.OnClickListener{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        Button startGame = (Button) findViewById(R.id.startGame);
        startGame.setOnClickListener(this);
        Button manual = (Button) findViewById(R.id.manual);
        manual.setOnClickListener(this);
        Button exit = (Button) findViewById(R.id.exit);
        exit.setOnClickListener(this);

        //Zet naar fullscreen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);



    }

    public void onClick(View view){
        switch (view.getId()){
            case R.id.startGame: {
                setContentView(new GamePanel(this));
                break;
            }
            case R.id.manual: {
                Intent intent = new Intent(getApplicationContext(),Manual.class);
                startActivity(intent);
                break;
            }
            case R.id.exit: {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;
            }
        }
    }
}
