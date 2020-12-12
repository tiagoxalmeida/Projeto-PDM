package pt.ubi.di.pdm.tfadmin;

import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class Activity_AdicionarEducadores extends AppCompatActivity {
    DBHelper db_helper;
    SQLiteDatabase educ_db;
    LinearLayout visualizer;

    int id, aux, sexo_dropdown_selection;

    EditText educador, idade_educ, morada, email;

    Spinner dropdown;

    ArrayList<String> sexList = new ArrayList<>();

    Button registo, btn_submeter, btn_cancelar;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addaluno);

        db_helper = new DBHelper(this);
        educ_db = db_helper.getWritableDatabase();

        SharedPreferences oSP = getApplicationContext().getSharedPreferences("important_variables", 0);
        id = oSP.getInt("id", 999);

        registo = (Button) findViewById(R.id.btnCriar);

        educador = (EditText) findViewById(R.id.inputUser);
        idade_educ = (EditText) findViewById(R.id.inputIdade);
        morada = (EditText) findViewById(R.id.inputMorada);
        email = (EditText) findViewById((R.id.inputEmail));

        dropdown = (Spinner) findViewById(R.id.inputSexo);

        sexList.add("");
        String[] items = new String[]{"Feminino", "Masculino"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(Activity_AdicionarEducadores.this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);
        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sexo_dropdown_selection = dropdown.getSelectedItemPosition();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        registo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String educador_nome = educador.getText().toString();
                int idade = Integer.parseInt(idade_educ.getText().toString());
                String mor = morada.getText().toString();
                String em = email.getText().toString();
                if(!em.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")){
                    email.setText("");
                    Toast.makeText(Activity_AdicionarEducadores.this,"Email inválido!",Toast.LENGTH_SHORT).show();
                    email.requestFocus();
                    return;
                }

                int sexo = Integer.valueOf(sexo_dropdown_selection);

                if(db_helper.addEducador(educ_db, id, educador_nome, idade, mor, sexo, em, "pwd") == 1){
                    Toast.makeText(Activity_AdicionarEducadores.this, "Feito!", Toast.LENGTH_SHORT).show();
                } else{
                    Toast.makeText(Activity_AdicionarEducadores.this, "erro!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        aux = db_helper.updateEducador(educ_db, id);
        if(aux == 0 || aux == -1){
            Toast.makeText(Activity_AdicionarEducadores.this, "Erro a atualizar a base de dades Educador", Toast.LENGTH_SHORT).show();
        }
    }
}