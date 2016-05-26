package bughunters.tashfik.flt;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import bughunters.tashfik.mytournament.R;

public class Matches extends AppCompatActivity {
    Context context = this;
    SQLiteDatabase database;
    String sql,Tname,st;
    Cursor c;
    String team1,team2;
    int br;
    public ListView listCom;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matches);

        Intent intent = getIntent();
        Tname = intent.getStringExtra("id");
        database = openOrCreateDatabase("db",MODE_PRIVATE, null);

        sql = "CREATE TABLE IF NOT EXISTS matches (Tournament VARCHAR,Matches VARCHAR );";

        database.execSQL(sql);

        listCom = (ListView) findViewById(R.id.matches);
        listCom.setAdapter(new ArrayAdapter<String>(this,R.layout.ltextt,readRecords()));
    }
    ArrayList<String> readRecords(){
        ArrayList<String> record = new ArrayList<String>();

        String	 sqlCur = "SELECT * from Matches where Tournament='" + Tname + "';";
        Cursor c1 = database.rawQuery(sqlCur, null);

        while (c1.moveToNext()){

            String ii ;

            String Matches = c1.getString(c1.getColumnIndex("Matches"));

            ii = ""+Matches;

            record.add(ii);
        }

        listCom.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> a, View v, int position,
                                    long r) {
                st=((TextView) v).getText().toString();

                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(Matches.this);
                LayoutInflater inflater = Matches.this.getLayoutInflater();
                final View dialogView = inflater.inflate(R.layout.customdialog, null);
                dialogBuilder.setView(dialogView);

                final EditText h = (EditText) dialogView.findViewById(R.id.head);
                final EditText d = (EditText) dialogView.findViewById(R.id.des);

                dialogBuilder.setTitle("Add");

                dialogBuilder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                        database = openOrCreateDatabase("db", MODE_PRIVATE, null);

                        sql = "DELETE FROM matches WHERE Matches='" + st + "';";
                        database.execSQL(sql);
                        database.close();

                        for(int i=0;i<st.length();i++){

                       if(st.charAt(i)=='\n' ||  st.charAt(i)==':')
                            br=i;
                        }

                        team1=st.substring(0,br);
                        team2= st.substring(br,st.length());

                        createDatabase(Tname,team1+"\t\t:"+h.getText().toString()+"\n"+team2+"\t\t:"+d.getText().toString());

                        Intent intent = new Intent(Matches.this,MainActivity.class);
                        startActivity(intent);
                        overridePendingTransition(0, 0);
                        finish();

                    }
                });
                dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        //pass
                    }
                });
                AlertDialog b = dialogBuilder.create();
                b.show();
            }


        });

        return record;}

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
