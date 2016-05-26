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
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import bughunters.tashfik.mytournament.R;

public class MainActivity extends AppCompatActivity {

    Context context = this;
    SQLiteDatabase database;
    String sql;
    Cursor c;
    public	ListView listCom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database = openOrCreateDatabase("db",MODE_PRIVATE, null);

        sql = "CREATE TABLE IF NOT EXISTS app (Tournament VARCHAR );";

        database.execSQL(sql);

        listCom = (ListView) findViewById(R.id.tournametlist);
        listCom.setAdapter(new ArrayAdapter<String>(this,R.layout.ltextt,readRecords()));
    }

    public void addtournament(View v){
        Intent i=new Intent(MainActivity.this,AddTournament.class);
        startActivity(i);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    ArrayList<String> readRecords(){
        ArrayList<String> record = new ArrayList<String>();

        String	 sqlCur = "SELECT * from app;";
        Cursor c1 = database.rawQuery(sqlCur, null);

        while (c1.moveToNext()){

            String ii ;

            String Tournament = c1.getString(c1.getColumnIndex("Tournament"));

            ii = ""+Tournament;

            record.add(ii);
        }

        listCom.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> a, View v, int position,
                                    long r) {
                String st=((TextView) v).getText().toString();

                Intent i=new Intent(MainActivity.this,TournamentDetails.class);
                i.putExtra("id", ""+st);
                startActivity(i);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }


        });

        return record;}
}
