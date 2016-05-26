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

public class ManageParticipants extends AppCompatActivity {
    Context context = this;
    SQLiteDatabase database;
    String sql;
    Cursor c;
    String clickedTeam;
    String Tname;
    public ListView listCom;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_participants);

        Intent intent = getIntent();
        Tname = intent.getStringExtra("id");

        database = openOrCreateDatabase("db",MODE_PRIVATE, null);

        sql = "CREATE TABLE IF NOT EXISTS team (Tour VARCHAR ,Team VARCHAR );";

        database.execSQL(sql);

        listCom = (ListView) findViewById(R.id.p);
        listCom.setAdapter(new ArrayAdapter<String>(this,R.layout.ltextt,readRecords()));
    }

    public void addp(View v){

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(ManageParticipants.this);
        LayoutInflater inflater = ManageParticipants.this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.customdialogaaaaa, null);
        dialogBuilder.setView(dialogView);

        final EditText h = (EditText) dialogView.findViewById(R.id.head);

        dialogBuilder.setTitle("Add Team Name");

        dialogBuilder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

                addteam(Tname, h.getText().toString());

                Intent intent = getIntent();
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

    ArrayList<String> readRecords(){
        ArrayList<String> record = new ArrayList<String>();

        String	 sqlCur = "SELECT * from team where Tour='" + Tname + "';";
        Cursor c1 = database.rawQuery(sqlCur, null);

        while (c1.moveToNext()){

            String ii ;

            String Tournament = c1.getString(c1.getColumnIndex("Team"));

            ii = ""+Tournament;

            record.add(ii);

        }

        listCom.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> a, View v, int position,
                                    long r) {
                  clickedTeam=((TextView) v).getText().toString();

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        context);

                alertDialogBuilder.setTitle("Details");

                alertDialogBuilder
                        .setMessage("Are you sure you want to remove "+clickedTeam +" ?")
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
                                        sql = "DELETE FROM team WHERE Team='" + clickedTeam + "';";
                                        database.execSQL(sql);
                                        database.close();
                                        Intent i=new Intent(ManageParticipants.this,ManageParticipants.class);
                                        startActivity(i);
                                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                                    }
                                });

                AlertDialog alertDialog = alertDialogBuilder.create();

                alertDialog.show();
            }


        });

        return record;}

    void addteam(String Tournament,String Team){
        database = openOrCreateDatabase("db",MODE_PRIVATE, null);
        sql = "CREATE TABLE IF NOT EXISTS team (Tour VARCHAR ,Team VARCHAR );";
        c = database.rawQuery(sql, null);
        database.execSQL(sql);
        String insertSql = "INSERT INTO team VALUES('"+Tournament+"','"+Team+"');";
        database.execSQL(insertSql);
        database.close();
    }
}
