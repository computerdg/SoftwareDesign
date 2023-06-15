package com.example.termproject;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class PictureRegister extends AppCompatActivity {

    Button takePicture, takeGall, btnBack, btn_complete;
    ImageView userImage;
    String imgPath;
    int checkPhoto;
    Bitmap bitmap;


    // 카메라로 이동시켜주는 함수
    private void callCamera() {
        Intent i = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        try {
            PackageManager pm = getPackageManager();

            final ResolveInfo mInfo = pm.resolveActivity(i, 0);

            Intent intent = new Intent();
            intent.setComponent(new ComponentName(mInfo.activityInfo.packageName, mInfo.activityInfo.name));
            intent.setAction(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);

            startActivity(intent);
        } catch (Exception e) {
            Log.i("TAG", "Unable to launch camera : " + e);
        }
    }

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.picture_register);


        takePicture = (Button) findViewById(R.id.takePicture); // 1. 사진촬영
        takeGall = (Button) findViewById(R.id.takeGall); // 2. 사진 등록
        btnBack = (Button) findViewById(R.id.btnBack); // 왼쪽 상단에 뒤로가기 버튼
        btn_complete = (Button) findViewById(R.id.btn_complete); // 3. 등록 완료
        userImage = (ImageView) findViewById(R.id.userImage); // 등록한 사진 이미지뷰
        checkPhoto = 0;

        Intent second_intent = new Intent(getApplicationContext(), Info_Register.class);
        imgPath = getIntent().getStringExtra("imgPath");
        Log.i("imgPath", imgPath);

        // 뒤로가기
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // 갤러리에서 등록하는 버튼 눌렀을때 이벤트 설정
        takeGall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityResult.launch(intent); // 밑에 함수랑 연결
            }
        });

        // 사진 촬영 버튼 클릭 시에 카메라로 이동시켜줌
        takePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callCamera();
            }
        });

        // 등록 완료 버튼 클릭
        btn_complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    userImage.setDrawingCacheEnabled(true);

                    File file = new File(imgPath);
                    FileOutputStream fileOutputStream = new FileOutputStream(file);
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
                    fileOutputStream.flush();
                    fileOutputStream.close();

                    checkPhoto = 1;
                    second_intent.putExtra("checkPhoto", checkPhoto);
                    Toast.makeText(getApplicationContext(), "사진등록 완료!", Toast.LENGTH_SHORT).show();
                    finish();

                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "사진등록 실패", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }

            }
        });
    }

    Uri uri;

    // 연결되는 함수
    ActivityResultLauncher<Intent> startActivityResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                uri = result.getData().getData();

                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                    userImage.setImageBitmap(bitmap);
                    userImage.setRotation(90);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    });
}


