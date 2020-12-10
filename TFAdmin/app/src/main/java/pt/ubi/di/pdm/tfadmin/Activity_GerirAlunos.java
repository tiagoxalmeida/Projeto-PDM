package pt.ubi.di.pdm.tfadmin;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Activity_GerirAlunos extends AppCompatActivity {

    DBHelper dbHelper;
    SQLiteDatabase base;
    LinearLayout oLL;
    Cursor oCursor,cursor2;
    String tk,i;
    int e_id,id;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_geriralunos);

        dbHelper = new DBHelper(this);
        base = dbHelper.getWritableDatabase();


        Intent Cheguei = getIntent();

        i = Cheguei.getStringExtra("id");

        e_id = Integer.parseInt(i);

        SharedPreferences shp = getApplicationContext().getSharedPreferences("important_variables",0);
        id = shp.getInt("id",999);


        Cursor cursor = base.query(DBHelper.TEDUCADOR,new String[]{"*"},null,null,null,null,null);
        while (cursor.moveToNext()){
            String c1 =cursor.getString(cursor.getColumnIndex(DBHelper.COL1_TEDUCADOR));
            if (c1.equals(e_id)){
                tk = cursor.getString(cursor.getColumnIndex(DBHelper.COL7_TEDUCADOR));
                break;
            }
        }
        cursor.close();
        Log.d("tag","3");

        dbHelper = new DBHelper(this);
        base = dbHelper.getWritableDatabase();

        displayAlunos();
    }

    protected void onResume() {
        super.onResume();
        dbHelper.updateEducando(base,id,e_id,tk);
    }

    public void displayAlunos() {
        oLL = (LinearLayout) findViewById(R.id.visualizar);
        oCursor = base.query(DBHelper.TEDUCANDO, new String[]{"*"}, null, null, null, null, null, null);

        boolean bCarryOn = oCursor.moveToFirst();
        while (bCarryOn) {
            LinearLayout oLL1 = (LinearLayout) getLayoutInflater().inflate(R.layout.linha_visualizar, null);
            oLL1.setId(oCursor.getInt(0) * 10 + 3);

            TextView T1 = (TextView) oLL1.findViewById(R.id.nomeAluno);
            T1.setId(oCursor.getInt(0) * 10 + 2);
            T1.setText(oCursor.getString(1));

            TextView T2 = (TextView) oLL1.findViewById(R.id.idAluno);
            T2.setId(oCursor.getInt(0) * 10 + 1);
            T2.setText(oCursor.getString(0));

            ImageButton B1 = (ImageButton)oLL1.findViewById(R.id.btnVerAluno);
            B1.setId(oCursor.getInt(0) * 10);
            B1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(Activity_GerirAlunos.this,Activity_PerfAluno.class);
                    i.putExtra("id",(v.getId())/10);
                    startActivity(i);
                }
            });

            oLL.addView(oLL1);
            bCarryOn = oCursor.moveToNext();
        }

    }
}
