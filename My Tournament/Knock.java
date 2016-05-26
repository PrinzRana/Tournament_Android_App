package bughunters.tashfik.flt;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import bughunters.tashfik.mytournament.R;

public class Knock extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Spinner sp;
    String Tname;
    SQLiteDatabase database;
    String sql;
    Cursor c;
    Context con=this;

    List<String> list = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_knock);
        Intent intent = getIntent();
          Tname = intent.getStringExtra("id");

        sp = (Spinner) findViewById(R.id.spinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.spa, android.R.layout.simple_spinner_item);
        sp.setAdapter(adapter);
        sp.setOnItemSelectedListener(this);

        database = openOrCreateDatabase("db",MODE_PRIVATE, null);

        sql = "CREATE TABLE IF NOT EXISTS player (Tour VARCHAR ,Player VARCHAR );";

        database.execSQL(sql);

        String	 sqlCur = "SELECT * from player    WHERE Tour='" + Tname + "';";
        Cursor c1 = database.rawQuery(sqlCur, null);

        while (c1.moveToNext()) {

            String Tournament = c1.getString(c1.getColumnIndex("Player"));
            list.add(Tournament);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    public void knockb(View v){

        int size=list.size()-1;
        for(int i=0;i<size;i++){

            createDatabase(Tname, ""+list.get(i)+"\n"+list.get(i+1));
        }
        Intent i=new Intent(Knock.this,MainActivity.class);
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
