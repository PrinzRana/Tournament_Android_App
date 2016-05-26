package bughunters.tashfik.flt;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import bughunters.tashfik.mytournament.R;

public class CreateMatches extends AppCompatActivity {
    String Tname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_matches);

        Intent intent = getIntent();
        Tname = intent.getStringExtra("id");
    }
        public void round(View v){

            Intent i=new Intent(CreateMatches.this,Round.class);
            i.putExtra("id", ""+Tname);
            startActivity(i);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        }

    public void knock(View v){
        Intent i=new Intent(CreateMatches.this,Knock.class);
        i.putExtra("id", ""+Tname);
        startActivity(i);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

    }
}