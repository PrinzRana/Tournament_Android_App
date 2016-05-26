package bughunters.tashfik.flt;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import bughunters.tashfik.mytournament.R;

public class TournamentDetails extends AppCompatActivity {
    SQLiteDatabase database;
    String sql;
    Cursor c;
    Context context=this;
    String Tname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tournament_details);
        database = openOrCreateDatabase("db",MODE_PRIVATE, null);
        sql = "CREATE TABLE IF NOT EXISTS app (Tournament VARCHAR );";
        database.execSQL(sql);
        Intent intent = getIntent();
          Tname = intent.getStringExtra("id");
    }

    public  void ManageParticipants(View v){
        Intent i=new Intent(TournamentDetails.this,ManageParticipants.class);
        i.putExtra("id", ""+Tname);
        startActivity(i);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    public  void CreateMatches(View v){
        Intent i=new Intent(TournamentDetails.this,CreateMatches.class);
        i.putExtra("id", ""+Tname);
        startActivity(i);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

public void Matches(View v){
    Intent i=new Intent(TournamentDetails.this,Matches.class);
    i.putExtra("id", ""+Tname);
    startActivity(i);
    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
}

    public void RemoveTournament(View v){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context);

        alertDialogBuilder.setTitle("Details");

        alertDialogBuilder
                .setMessage("Are you sure you want to remove ?")
                .setCancelable(false)
                .setNeutralButton("Back",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int id) {
                            }
                        })

                .setPositiveButton("Delete",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int id) {

                                database = openOrCreateDatabase("db", MODE_PRIVATE, null);
                                  sql = "DELETE FROM app WHERE Tournament='" + Tname + "';";
                                database.execSQL(sql);
                                database.close();
                                Intent i=new Intent(TournamentDetails.this,MainActivity.class);
                                startActivity(i);
                                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                            }

                        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}