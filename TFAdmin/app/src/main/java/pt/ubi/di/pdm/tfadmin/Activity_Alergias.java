package pt.ubi.di.pdm.tfadmin;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

public class Activity_Alergias extends AppCompatActivity {

    LinearLayout oLL;
    Cursor oCursor;
    DBHelper dbHelper;
    SQLiteDatabase base;
    Button submeter,cancelar,add;
    String tk,i;
    int e_id,id;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alergias);

        dbHelper = new DBHelper(this);
        base = dbHelper.getWritableDatabase();

        SharedPreferences shp = getApplicationContext().getSharedPreferences("important_variables",0);
        id = shp.getInt("id",999);


        Intent Cheguei = getIntent();
        i = Cheguei.getStringExtra("id");
        e_id = Integer.parseInt(i);

        Log.d("tag","1");

        submeter = (Button)findViewById(R.id.btnSubmeter);
        cancelar = (Button)findViewById(R.id.btnCancelar);
        add = (Button)findViewById(R.id.btnAddAlergia);

    }

    protected void onResume() {
        super.onResume();
        Cursor cursor = base.query(DBHelper.TEDUCADOR,new String[]{DBHelper.COL7_TEDUCADOR},DBHelper.COL1_TEDUCADOR+"=?",new String[]{String.valueOf(e_id)},null,null,null);
        cursor.moveToFirst();
        tk = cursor.getString(0);
        cursor.close();


        int aux = dbHelper.updateAlergia(base,id,e_id,tk);

        Log.d("tag","3");
        displayAlergias();
    }

    public void displayAlergias(){
        oLL = (LinearLayout) findViewById(R.id.listaAlergias);
        oLL.removeAllViews();
        oCursor = base.query(DBHelper.TALERGIA, new String[]{"*"}, null, null, null, null, null, null);
        boolean bCarryOn = oCursor.moveToFirst();
        while (bCarryOn) {
            LinearLayout oLL1 = (LinearLayout) getLayoutInflater().inflate(R.layout.linha_visualizar, null);
            oLL1.setId(oCursor.getInt(0) * 10 + 2);

            TextView E1 = (TextView) oLL1.findViewById(R.id.nomeAluno);
            E1.setId(oCursor.getInt(0) * 10 );
            E1.setText(oCursor.getString(1));

            ImageButton B1 = (ImageButton) oLL1.findViewById(R.id.btnApagar);
            B1.setId(oCursor.getInt(0) * 10 +1);


            oLL.addView(oLL1);
            bCarryOn = oCursor.moveToNext();
        }

    }

}
