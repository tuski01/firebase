package net.flow9.dcjt.firebase;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class SearchAddressActivity extends AppCompatActivity {

    private EditText mEtAddress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_address);

        mEtAddress = findViewById(R.id.et_address);
        // blodck touch
        mEtAddress.setFocusable(false);
        mEtAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 주소 검색 웹뷰 화면으로 이동
                Intent intent = new Intent(SearchAddressActivity.this, SearchActivity.class);
                getSearchResult.launch(intent);

            }
        });
    }

    private final ActivityResultLauncher<Intent> getSearchResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                // Search Activity 로 부터의 결과 값이 이곳으로 전달 된다 (setResult에 의해)
                if (result.getResultCode() == RESULT_OK) {
                    if (result.getData() != null) {
                        String data = result.getData().getStringExtra("data");
                        mEtAddress.setText(data);
                    }
                }
            }
    );
}