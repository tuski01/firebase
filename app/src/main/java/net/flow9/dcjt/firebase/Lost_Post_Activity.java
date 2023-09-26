package net.flow9.dcjt.firebase;

import static net.flow9.dcjt.firebase.R.array.A_category;
import static net.flow9.dcjt.firebase.R.array.B_category;
import static net.flow9.dcjt.firebase.R.array.Book_category;
import static net.flow9.dcjt.firebase.R.array.C_category;
import static net.flow9.dcjt.firebase.R.array.Card_category;
import static net.flow9.dcjt.firebase.R.array.E_category;
import static net.flow9.dcjt.firebase.R.array.ID_category;
import static net.flow9.dcjt.firebase.R.array.None_category;
import static net.flow9.dcjt.firebase.R.array.W_category;
import static net.flow9.dcjt.firebase.R.array.instrument_category;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.loader.content.CursorLoader;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;


import net.flow9.dcjt.firebase.model.Post;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Lost_Post_Activity extends AppCompatActivity {


    private int REQUEST_IMAGE_CODE = 1001;
    private int REQUEST_EXTERNAL_STORAGE_PERMISSIONS = 1002;
    private TextView date_view;
    private EditText E_title, E_contents, mEditTextFileName;
    private DatePickerDialog dpd;
    private Spinner spinner;
    private ArrayAdapter L_categoryAA, M_categoryAA;
    private Spinner L_categorySp, M_categorySp;
    private String L_category, M_category, E_image, uploadUri, uploadId ;
    private ImageView insert_img_btn, calender_btn;
    private Post post = new Post();

    private Button confirm_btn;

    String imgPath;


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch(requestCode){
            case 10:
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    startToast("외부 메모리 읽기/쓰기 사용 가능");
                } else {
                    startToast("외부 메모리 읽기/쓰기 제한");
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode){
            case 10:
                if(resultCode == RESULT_OK){
                    startToast("RESULT_OK");
                    Uri uri = data.getData();
                    if(uri != null){
                        insert_img_btn.setImageURI(uri);
                        imgPath = getRealPathFromUri(uri);

                        new AlertDialog.Builder(this).setMessage(uri.toString()+ "\n" + imgPath).create().show();
                    }
                } else {
                    startToast("이미지 선택 되지 않음");
                }
                break;
        }
    }

    String getRealPathFromUri(Uri uri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        CursorLoader loader = new CursorLoader(this, uri, proj, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(column_index);
        cursor.close();
        return result;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_lost);

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            int permissionResult = checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if(permissionResult == PackageManager.PERMISSION_DENIED){
                String[] permissions=new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};
                requestPermissions(permissions, 10);
            }
        }

        L_categorySp = findViewById(R.id.spinner);
        M_categorySp = findViewById(R.id.spinner2);
        confirm_btn = findViewById(R.id.confirm_btn);
        calender_btn = findViewById(R.id.calender_btn);
        date_view = findViewById(R.id.date_view);
        E_title = findViewById(R.id.post_title_edit);
        E_contents = findViewById(R.id.post_contents_edit);
        mEditTextFileName = findViewById(R.id.mEditTextFileName);

        L_categoryAA = ArrayAdapter.createFromResource(this, R.array.L_category, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        L_categorySp.setAdapter(L_categoryAA);
        insert_img_btn = findViewById(R.id.insert_image_btn);
        L_categorySp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0:
                        M_categoryAA = ArrayAdapter.createFromResource(Lost_Post_Activity.this, E_category, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        M_categorySp.setAdapter(M_categoryAA);
                        L_category = L_categorySp.getSelectedItem().toString();
                        break;
                    case 1:
                        M_categoryAA = ArrayAdapter.createFromResource(Lost_Post_Activity.this, W_category, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        M_categorySp.setAdapter(M_categoryAA);
                        L_category = L_categorySp.getSelectedItem().toString();
                        break;
                    case 2:
                        M_categoryAA = ArrayAdapter.createFromResource(Lost_Post_Activity.this, C_category, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        M_categorySp.setAdapter(M_categoryAA);
                        L_category = L_categorySp.getSelectedItem().toString();
                        break;
                    case 3:
                        M_categoryAA = ArrayAdapter.createFromResource(Lost_Post_Activity.this, B_category, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        M_categorySp.setAdapter(M_categoryAA);
                        L_category = L_categorySp.getSelectedItem().toString();
                        break;
                    case 4:
                        M_categoryAA = ArrayAdapter.createFromResource(Lost_Post_Activity.this, Card_category, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        M_categorySp.setAdapter(M_categoryAA);
                        L_category = L_categorySp.getSelectedItem().toString();
                        break;
                    case 5:
                        M_categoryAA = ArrayAdapter.createFromResource(Lost_Post_Activity.this, ID_category, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        M_categorySp.setAdapter(M_categoryAA);
                        L_category = L_categorySp.getSelectedItem().toString();
                        break;
                    case 6:
                        M_categoryAA = ArrayAdapter.createFromResource(Lost_Post_Activity.this, A_category, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        M_categorySp.setAdapter(M_categoryAA);
                        L_category = L_categorySp.getSelectedItem().toString();
                        break;
                    case 7:
                        M_categoryAA = ArrayAdapter.createFromResource(Lost_Post_Activity.this, instrument_category, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        M_categorySp.setAdapter(M_categoryAA);
                        L_category = L_categorySp.getSelectedItem().toString();
                        break;
                    case 8:
                        M_categoryAA = ArrayAdapter.createFromResource(Lost_Post_Activity.this, Book_category, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        M_categorySp.setAdapter(M_categoryAA);
                        L_category = L_categorySp.getSelectedItem().toString();
                        break;
                    case 9:
                    default:
                        M_categoryAA = ArrayAdapter.createFromResource(Lost_Post_Activity.this, None_category, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        M_categorySp.setAdapter(M_categoryAA);
                        L_category = L_categorySp.getSelectedItem().toString();
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        M_categorySp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0:
                        M_category = M_categorySp.getSelectedItem().toString();
                        break;
                    case 1:
                        M_category = M_categorySp.getSelectedItem().toString();
                        break;
                    case 2:
                        M_category = M_categorySp.getSelectedItem().toString();
                        break;
                    case 3:
                        M_category = M_categorySp.getSelectedItem().toString();
                        break;
                    case 4:
                        M_category = M_categorySp.getSelectedItem().toString();
                        break;
                    case 5:
                        M_category = M_categorySp.getSelectedItem().toString();
                        break;
                    case 6:
                        M_category = M_categorySp.getSelectedItem().toString();
                        break;
                    case 7:
                        M_category = M_categorySp.getSelectedItem().toString();
                        break;
                    case 8:
                        M_category = M_categorySp.getSelectedItem().toString();
                        break;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        if (ContextCompat.checkSelfPermission(Lost_Post_Activity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {

            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_EXTERNAL_STORAGE_PERMISSIONS);
            }
        } else {

        }

        // 이미지 추가를 위해 ImageView 선택시 일어나는 Event
        insert_img_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 10);
            }
        });

        calender_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar  = Calendar.getInstance();
                int pYear = calendar.get(Calendar.YEAR);
                int pMonth = calendar.get(Calendar.MONTH);
                int pDay = calendar.get(Calendar.DAY_OF_MONTH);

                dpd = new DatePickerDialog(Lost_Post_Activity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        i1 = i1 + 1;
                        String date = ""+ i +  i1  + i2;
                        date_view.setText(date);
                    }
                }, pYear, pMonth, pDay);

            dpd.show();

            }

        });

        // 등록 버튼 눌렀을때 일어나는 event
        confirm_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String L_category = L_categorySp.getSelectedItem().toString();
                String M_category = M_categorySp.getSelectedItem().toString();
                String date = date_view.getText().toString();
                String title = E_title.getText().toString();
                String contents = E_contents.getText().toString();



                Intent intent = new Intent(Lost_Post_Activity.this, indexActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }




    private void startToast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

}
