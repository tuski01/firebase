package net.flow9.dcjt.firebase;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class PostActivity extends AppCompatActivity {
    private Spinner spinner;
    private ArrayAdapter categoryAA, brandAA;
    private Spinner categorySp, brandSp;
    private String catogory="", brand="";

    TextView tv_result;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        categorySp = findViewById(R.id.spinner);
        brandSp = findViewById(R.id.spinner2);

        categoryAA = ArrayAdapter.createFromResource(this, R.array.category, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        categorySp.setAdapter(categoryAA);

        brandAA = ArrayAdapter.createFromResource(this, R.array.brand, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        brandSp.setAdapter(brandAA);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }
}
