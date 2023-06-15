package com.example.termproject;


import android.app.Activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoField;
import java.util.Calendar;

public class Info_Register extends Activity {

    Button date, btn_startDate, btn_finDate, setPicture, setDate, save;
    ImageButton home;
    EditText name;
    TextView dateText;
    TextView start_Date, fin_Date; // 유통기한 시작일, 종료일 변수

    String productName; // 제품명 저장 변수
    LocalDate buyDate;
    LocalDate startDate;
    LocalDate finishDate;
    LocalDate noticeDate;
    String imgPath;
    int checkPhoto;

    @Override
    protected void onRestart() {
        super.onRestart();
        checkPhoto = getIntent().getIntExtra("checkPhoto", 1);
        Log.i("checkPhoto",String.valueOf(checkPhoto));
    }

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_register);
        setTitle("Information Register");
        checkPhoto = 0;


        home = (ImageButton) findViewById(R.id.home); // 첫화면으로 돌아가는 버튼
        date = (Button) findViewById(R.id.date); // 구매한 날짜를 선택하는 이벤트를 만드는 버튼
        btn_startDate = (Button) findViewById(R.id.btn_startDate); // 유통기한 시작일 선택 -> calendar를 이용해 startDate 변수에 저장
        btn_finDate = (Button) findViewById(R.id.btn_finDate); // 유통기한 종료일 선택 -> calendar를 이용해 finishDate 변수에 저장
        setPicture = (Button) findViewById(R.id.setPicture); // 사진 등록을 하는 버튼 -> Intent 를 이용해 PictureRegister 로 넘겨줌
        setDate = (Button) findViewById(R.id.setDate); // 알림을 보낼 날짜 설정 -> 마감일 몇일 전에 보낼지 설정 -> noticeDate에 설정해둔 날짜값 저장 (나중에 유통기한 종료일에서 빼줘서 몇일 남았는지 계산)
        save = (Button) findViewById(R.id.save); // 저장 버튼 -> 제품명 입력한것을 productName에 저장 -> Third 페이지에서 제품명 출력할때 해당 변수 사용
        name = (EditText) findViewById(R.id.name); // 제품명을 입력하고 저장하는 EditText
        dateText = (TextView) findViewById(R.id.dateText); // Calendar 에서 선택한 날짜를 출력하는 TextView
        start_Date = (TextView) findViewById(R.id.start_Date); // 시작일 표시
        fin_Date = (TextView) findViewById(R.id.fin_Date); // 종료일 표시


        //home 버튼
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // 구매날짜 등록 버튼 눌렀을때 DatePickerDialog로 설정
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int pYear = calendar.get(Calendar.YEAR);
                int pMonth = calendar.get(Calendar.MONTH);
                int pDay = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(Info_Register.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month = month + 1;
                        buyDate = LocalDate.of(year,month,dayOfMonth);
                        dateText.setText(year + "-" + month + "-" + dayOfMonth);
                    }
                }, pYear, pMonth, pDay);
                datePickerDialog.show();
            }
        });


//         유통기한 설정 -> 시작일 / 종료일 버튼 두개로 설정
        btn_startDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int pYear = calendar.get(Calendar.YEAR);
                int pMonth = calendar.get(Calendar.MONTH);
                int pDay = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(Info_Register.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month = month + 1;
                        startDate = LocalDate.of(year,month,dayOfMonth);
                        start_Date.setText(year + "-" + month + "-" + dayOfMonth);
                    }
                }, pYear, pMonth, pDay);
                datePickerDialog.show();
            }
        });

        btn_finDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int pYear = calendar.get(Calendar.YEAR);
                int pMonth = calendar.get(Calendar.MONTH);
                int pDay = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(Info_Register.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month = month + 1;
                        finishDate = LocalDate.of(year, month, dayOfMonth);
                        fin_Date.setText(year + "-" + month + "-" + dayOfMonth);
                    }
                }, pYear, pMonth, pDay);

                // 종료일 클릭 시 최소 날짜를 시작일로 설정
                long minDate = startDate.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli();
                datePickerDialog.getDatePicker().setMinDate(minDate);

                datePickerDialog.show();
            }
        });


        // 알림날짜 설정 버튼 setDate
        setDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int pYear = calendar.get(Calendar.YEAR);
                int pMonth = calendar.get(Calendar.MONTH);
                int pDay = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(Info_Register.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month = month + 1;
                        noticeDate = LocalDate.of(year, month, dayOfMonth);
                        String date = year + "-" + month + "-" + dayOfMonth;
                        Toast.makeText(getApplicationContext(), date + " 로 알림설정 완료", Toast.LENGTH_SHORT).show();
                    }
                }, pYear, pMonth, pDay);

                //알림날짜 선택도 최소 날짜를 시작일로 선택가능하도록 제한
                long minDate = startDate.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli();
                datePickerDialog.getDatePicker().setMinDate(minDate);

                datePickerDialog.show();
            }
        });

        //사진 등록 버튼 setPicture
        setPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(nullCheck(name)){ // nullcheck -> 사진 등록 안했을 때
                    Toast.makeText(getApplicationContext(), "제품명을 입력해 주세요.", Toast.LENGTH_SHORT).show();
                }
                else{
                    Intent intent = new Intent(getApplicationContext(), PictureRegister.class);
                    intent.putExtra("imgPath", getCacheDir().getAbsolutePath() + "/" + name.getText().toString() + "_img");
                    startActivity(intent);
                }
            }
        });

        // 저장 버튼
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if((checkPhoto == 0) || nullCheck(name) || (noticeDate == null) || (finishDate == null) || (startDate == null) || (buyDate == null)) {
                    Toast.makeText(getApplicationContext(), "모든 정보를 입력해 주세요.", Toast.LENGTH_SHORT).show();
                }
                else {
                    productName = name.getText().toString(); // 제품명 string으로 저장
                    imgPath = getCacheDir().getAbsolutePath() + "/" + productName + "_img";
                    Product product = new Product(productName, buyDate, startDate, noticeDate,finishDate, imgPath);

                    addProduct(product, productName + "_product");
                    Toast.makeText(getApplicationContext(), "등록을 완료 했습니다.", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
    }

    public void addProduct(Product product, String filePath) {
        try {
            FileOutputStream fileOutputStream = openFileOutput(filePath, MODE_PRIVATE);
            PrintWriter printWriter = new PrintWriter(fileOutputStream);
            printWriter.println(product.toString());
            printWriter.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public boolean nullCheck(TextView textView){
        if(textView.getText().toString().replace(" ","").equals("")){
            return true;
        }
        else return false;
    }
}

