package com.example.niek.speelveld;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Manual extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual);

        Button mainmenu = (Button) findViewById(R.id.mainMenu);
    }

    public void onClickManual(View view){
        finish();
    }
}
