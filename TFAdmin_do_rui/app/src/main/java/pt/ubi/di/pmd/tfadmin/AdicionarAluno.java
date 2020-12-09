package pt.ubi.di.pmd.tfadmin;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

public class AdicionarAluno extends AppCompatActivity {
    Spinner dropdown;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addaluno);
        dropdown = (Spinner) findViewById(R.id.inputSexo);


        String[] items = new String[]{"Masculino", "Feminino"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(AdicionarAluno.this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);


    }
}
