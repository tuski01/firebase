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
import android.content.ClipData;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import net.flow9.dcjt.firebase.adapters.PostAdapter;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;

public class PostActivity extends AppCompatActivity {
    private int REQUEST_IMAGE_CODE = 1001;
    private int REQUEST_EXTERNAL_STORAGE_PERMISSIONS = 1002;
    private StorageReference mStorageRef;
    private Spinner spinner;
    private ArrayAdapter L_categoryAA, M_categoryAA;
    private Spinner L_categorySp, M_categorySp;
    private String L_category, M_category;
    private ImageView insert_img_btn;
    private Uri image;
    private Button confirm_btn;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_CODE) {
            image = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), image);
                insert_img_btn.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        mStorageRef = FirebaseStorage.getInstance().getReference();

        L_categorySp = findViewById(R.id.spinner);
        M_categorySp = findViewById(R.id.spinner2);
        confirm_btn = findViewById(R.id.confirm_btn);


        L_categoryAA = ArrayAdapter.createFromResource(this, R.array.L_category, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        L_categorySp.setAdapter(L_categoryAA);

        insert_img_btn = findViewById(R.id.insert_image_btn);

        L_categorySp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0:
                        M_categoryAA = ArrayAdapter.createFromResource(PostActivity.this, E_category, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        M_categorySp.setAdapter(M_categoryAA);
                        L_category = L_categorySp.getSelectedItem().toString();
                        break;
                    case 1:
                        M_categoryAA = ArrayAdapter.createFromResource(PostActivity.this, W_category, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        M_categorySp.setAdapter(M_categoryAA);
                        L_category = L_categorySp.getSelectedItem().toString();
                        break;
                    case 2:
                        M_categoryAA = ArrayAdapter.createFromResource(PostActivity.this, C_category, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        M_categorySp.setAdapter(M_categoryAA);
                        L_category = L_categorySp.getSelectedItem().toString();
                        break;
                    case 3:
                        M_categoryAA = ArrayAdapter.createFromResource(PostActivity.this, B_category, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        M_categorySp.setAdapter(M_categoryAA);
                        L_category = L_categorySp.getSelectedItem().toString();
                        break;
                    case 4:
                        M_categoryAA = ArrayAdapter.createFromResource(PostActivity.this, Card_category, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        M_categorySp.setAdapter(M_categoryAA);
                        L_category = L_categorySp.getSelectedItem().toString();
                        break;
                    case 5:
                        M_categoryAA = ArrayAdapter.createFromResource(PostActivity.this, ID_category, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        M_categorySp.setAdapter(M_categoryAA);
                        L_category = L_categorySp.getSelectedItem().toString();
                        break;
                    case 6:
                        M_categoryAA = ArrayAdapter.createFromResource(PostActivity.this, A_category, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        M_categorySp.setAdapter(M_categoryAA);
                        L_category = L_categorySp.getSelectedItem().toString();
                        break;
                    case 7:
                        M_categoryAA = ArrayAdapter.createFromResource(PostActivity.this, instrument_category, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        M_categorySp.setAdapter(M_categoryAA);
                        L_category = L_categorySp.getSelectedItem().toString();
                        break;
                    case 8:
                        M_categoryAA = ArrayAdapter.createFromResource(PostActivity.this, Book_category, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        M_categorySp.setAdapter(M_categoryAA);
                        L_category = L_categorySp.getSelectedItem().toString();
                        break;
                    case 9:
                    default:
                        M_categoryAA = ArrayAdapter.createFromResource(PostActivity.this, None_category, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
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
        if (ContextCompat.checkSelfPermission(PostActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
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
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, REQUEST_IMAGE_CODE);
            }
        });


        // 등록 버튼 눌렀을때 일어나는 event
        confirm_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                post_upload();
                finish();
            }
        });
    }

    // 사진 업로드 함수
    private void post_upload(){
        StorageReference riversRef = mStorageRef.child("images" + image.getLastPathSegment());
        riversRef.putFile(image).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                    }
                });
    }

}
