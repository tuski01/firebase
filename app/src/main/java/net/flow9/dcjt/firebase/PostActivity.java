package net.flow9.dcjt.firebase;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class PostActivity extends AppCompatActivity {
    private Spinner spinner;
    TextView tv_result;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        spinner = findViewById(R.id.spinner);

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
