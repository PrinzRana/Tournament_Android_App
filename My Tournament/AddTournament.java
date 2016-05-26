package bughunters.tashfik.flt;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import bughunters.tashfik.mytournament.R;

public class AddTournament extends AppCompatActivity {
    SQLiteDatabase database;
    String sql;
    Cursor c;
    EditText tname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tournament);

    tname= (EditText) findViewById(R.id.tname);
    }

    public void Create(View V){

    String name=tname.getText().toString();
        if(name.equalsIgnoreCase("") || name.length() <1){
            Toast.makeText(AddTournament.this, "Enter Name !", Toast.LENGTH_SHORT).show();
        }else {

            createDatabase(name);
            Intent i=new Intent(AddTournament.this,MainActivity.class);
            startActivity(i);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        }
    }
    void createDatabase(String Tournament){
        database = openOrCreateDatabase("db",MODE_PRIVATE, null);
        sql = "CREATE TABLE IF NOT EXISTS app (Tournament VARCHAR );";
        c = database.rawQuery(sql, null);
        database.execSQL(sql);
        String insertSql = "INSERT INTO app VALUES('"+Tournament+"');";
        database.execSQL(insertSql);
        database.close();
    }
}
