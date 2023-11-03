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
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import net.flow9.dcjt.firebase.adapters.lost_PostAdapter;
import net.flow9.dcjt.firebase.model.Post;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Find_Post_Activity extends AppCompatActivity {

    private int REQUEST_IMAGE_CODE = 1001;
    private int REQUEST_EXTERNAL_STORAGE_PERMISSIONS = 1002;
    String encodeImageString;

    Bitmap bitmap;
    private TextView date_view;
    private EditText E_title, E_contents, mEditTextFileName;
    private DatePickerDialog dpd;
    private List<Post> mDatas;
    private Post postdata;
    private int documentNum = 0;



    private Spinner spinner;
    private ArrayAdapter L_categoryAA, M_categoryAA;
    private Spinner L_categorySp, M_categorySp;
    private String L_category, M_category, E_image, uploadUri, uploadId ;
    private ImageView insert_img_btn, calender_btn;
    private Uri image;

    private static final String url = "http://49.50.175.166/findpost.php";

    private Button confirm_btn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_find);

        SharedPreferences sharedPre = this.getSharedPreferences("shared", Context.MODE_PRIVATE);
        String strEmail = sharedPre.getString("email", "");


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
                        M_categoryAA = ArrayAdapter.createFromResource(Find_Post_Activity.this, E_category, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        M_categorySp.setAdapter(M_categoryAA);
                        L_category = L_categorySp.getSelectedItem().toString();
                        break;
                    case 1:
                        M_categoryAA = ArrayAdapter.createFromResource(Find_Post_Activity.this, W_category, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        M_categorySp.setAdapter(M_categoryAA);
                        L_category = L_categorySp.getSelectedItem().toString();
                        break;
                    case 2:
                        M_categoryAA = ArrayAdapter.createFromResource(Find_Post_Activity.this, C_category, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        M_categorySp.setAdapter(M_categoryAA);
                        L_category = L_categorySp.getSelectedItem().toString();
                        break;
                    case 3:
                        M_categoryAA = ArrayAdapter.createFromResource(Find_Post_Activity.this, B_category, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        M_categorySp.setAdapter(M_categoryAA);
                        L_category = L_categorySp.getSelectedItem().toString();
                        break;
                    case 4:
                        M_categoryAA = ArrayAdapter.createFromResource(Find_Post_Activity.this, Card_category, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        M_categorySp.setAdapter(M_categoryAA);
                        L_category = L_categorySp.getSelectedItem().toString();
                        break;
                    case 5:
                        M_categoryAA = ArrayAdapter.createFromResource(Find_Post_Activity.this, ID_category, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        M_categorySp.setAdapter(M_categoryAA);
                        L_category = L_categorySp.getSelectedItem().toString();
                        break;
                    case 6:
                        M_categoryAA = ArrayAdapter.createFromResource(Find_Post_Activity.this, A_category, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        M_categorySp.setAdapter(M_categoryAA);
                        L_category = L_categorySp.getSelectedItem().toString();
                        break;
                    case 7:
                        M_categoryAA = ArrayAdapter.createFromResource(Find_Post_Activity.this, instrument_category, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        M_categorySp.setAdapter(M_categoryAA);
                        L_category = L_categorySp.getSelectedItem().toString();
                        break;
                    case 8:
                        M_categoryAA = ArrayAdapter.createFromResource(Find_Post_Activity.this, Book_category, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        M_categorySp.setAdapter(M_categoryAA);
                        L_category = L_categorySp.getSelectedItem().toString();
                        break;
                    case 9:
                    default:
                        M_categoryAA = ArrayAdapter.createFromResource(Find_Post_Activity.this, None_category, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
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
        if (ContextCompat.checkSelfPermission(Find_Post_Activity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {

            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_EXTERNAL_STORAGE_PERMISSIONS);
            }
        } else {

        }

        // 이미지 추가를 위해 ImageView 선택시 일어나는 Event
        insert_img_btn.setOnClickListener(view -> {
            Dexter.withActivity(Find_Post_Activity.this)
                    .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                    .withListener(new PermissionListener() {
                        @Override
                        public void onPermissionGranted(PermissionGrantedResponse response)
                        {
                            Intent intent = new Intent(Intent.ACTION_PICK);
                            intent.setType("image/*");
                            startActivityForResult(Intent.createChooser(intent, "Browse Image"), 1);
                        }

                        @Override
                        public void onPermissionDenied(PermissionDeniedResponse response)
                        {

                        }

                        @Override
                        public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token)
                        {
                            token.continuePermissionRequest();
                        }
                    }).check();
        });

        calender_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar  = Calendar.getInstance();
                int pYear = calendar.get(Calendar.YEAR);
                int pMonth = calendar.get(Calendar.MONTH);
                int pDay = calendar.get(Calendar.DAY_OF_MONTH);

                dpd = new DatePickerDialog(Find_Post_Activity.this, new DatePickerDialog.OnDateSetListener() {
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
        confirm_btn.setOnClickListener(view ->{
            uploadDataToDB();

        });
    }

    private void uploadDataToDB()
    {
        String L_category = L_categorySp.getSelectedItem().toString();
        String M_category = M_categorySp.getSelectedItem().toString();
        String date = date_view.getText().toString();
        String title = E_title.getText().toString();
        String contents = E_contents.getText().toString().trim();
        String userID = indexActivity.userID;


        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_SHORT).show();
                finish();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError
            {
                Map<String, String> map = new HashMap<>();
                // 1번 인자는 PHP 파일의 $_POST['']; 부분과 똑같이 해줘야 한다
                map.put("img", encodeImageString);
                map.put("L_category", L_category);
                map.put("M_category", M_category);
                map.put("date", date);
                map.put("title", title);
                map.put("contents", contents);
                map.put("userID", userID);
                return map;


            }
        };
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(request);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        if (requestCode == 1 && resultCode == RESULT_OK && data != null)
        {
            Uri filePath = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(filePath);
                bitmap = BitmapFactory.decodeStream(inputStream);
                insert_img_btn.setImageBitmap(bitmap);
                encodeBitmapImage(bitmap);
            }   catch (Exception e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void encodeBitmapImage(Bitmap bitmap)
    {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);

        byte[] bytesOfImage = byteArrayOutputStream.toByteArray();
        encodeImageString = android.util.Base64.encodeToString(bytesOfImage, Base64.DEFAULT);
        System.out.println(encodeImageString );
    }


    private void startToast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

}
