package bughunters.tashfik.flt;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import bughunters.tashfik.mytournament.R;

public class Round extends AppCompatActivity {

    String Tname;
    SQLiteDatabase database;
    String sql;
    Cursor c;
    List<String> list = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_round);

        Intent intent = getIntent();
         Tname = intent.getStringExtra("id");

        database = openOrCreateDatabase("db",MODE_PRIVATE, null);

        sql = "CREATE TABLE IF NOT EXISTS team (Tour VARCHAR ,Team VARCHAR );";

        database.execSQL(sql);

        String	 sqlCur = "SELECT * from team    WHERE Tour='" + Tname + "';";
        Cursor c1 = database.rawQuery(sqlCur, null);

        while (c1.moveToNext()) {

            String Tournament = c1.getString(c1.getColumnIndex("Team"));
            list.add(Tournament);
        }
    }

  public void  gen(View v){

      int size=list.size()-1;
      for(int i=0;i<size;i++){

          createDatabase(Tname, ""+list.get(i)+"\n"+list.get(i+1));
      }
      Intent i=new Intent(Round.this,MainActivity.class);
      startActivity(i);
      overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
  }
    void createDatabase(String Tournament,String Matches){
        database = openOrCreateDatabase("db",MODE_PRIVATE, null);
        sql = "CREATE TABLE IF NOT EXISTS matches (Tournament VARCHAR,Matches VARCHAR );";
        c = database.rawQuery(sql, null);
        database.execSQL(sql);
        String insertSql = "INSERT INTO matches VALUES('"+Tournament+"','"+Matches+"');";
        database.execSQL(insertSql);
        database.close();
    }
}