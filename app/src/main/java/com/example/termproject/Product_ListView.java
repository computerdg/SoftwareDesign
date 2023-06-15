package com.example.termproject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.ArrayList;

public class Product_ListView extends Activity {

    ImageButton home;
    ArrayList<Product> productArrayList;
    ListView list_item;
//    Button btn_delete;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_listview);
        setTitle("Product_List");

        list_item = (ListView) findViewById(R.id.list_item);
        home = (ImageButton) findViewById(R.id.home);
//        btn_delete = (Button) findViewById(R.id.btn_delete);

        productArrayList = new ArrayList<>();
        productArrayList.add(new Product("제품명", LocalDate.of(2022,10,10),LocalDate.of(2022,10,10),LocalDate.of(2022,10,10),LocalDate.of(2022,10,10),"imagePath"));

        File file = new File(getFilesDir().getPath());
        File[] files = file.listFiles();

        if(files != null){
            for(int i=0; i<files.length; i++){
                try {
                    //Log.i("file path",files[i].getName());

                    FileInputStream fileInputStream = openFileInput(files[i].getName());
                    InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
                    BufferedReader reader = new BufferedReader(inputStreamReader);

                    String productName = reader.readLine();
                    String buyDate = reader.readLine();
                    String startDate = reader.readLine();
                    String finishDate = reader.readLine();
                    String noticeDate = reader.readLine();
                    String imgPath = reader.readLine();

                    fileInputStream.close();

                    LocalDate buyDateLocalDate = LocalDate.parse(buyDate);
                    LocalDate startDateLocalDate = LocalDate.parse(startDate);
                    LocalDate finishDateLocalDate = LocalDate.parse(finishDate);
                    LocalDate noticeDateLocalDate = LocalDate.parse(noticeDate);

                    Product product = new Product(productName, buyDateLocalDate, startDateLocalDate, noticeDateLocalDate, finishDateLocalDate, imgPath);
                    productArrayList.add(product);


                }
                catch(FileNotFoundException e){
                    e.printStackTrace();
                }
                catch(IOException e){ // readLine 예외문 처리
                    e.printStackTrace();
                }
            }
        }
        else{
            Toast.makeText(getApplicationContext(), "등록된 정보가 없습니다.", Toast.LENGTH_SHORT).show();
        }

        final MyAdapter myAdapter = new MyAdapter(this, productArrayList);
        list_item.setAdapter(myAdapter);

//        // long 클릭 시 삭제 기능
        list_item.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Product_ListView.this);
                builder.setTitle("삭제")
                        .setMessage("정말로 삭제하시겠습니까?")
                        .setPositiveButton("삭제", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                MyAdapter adapter = (MyAdapter) list_item.getAdapter();
                                productArrayList.remove(position);
                                adapter.notifyDataSetChanged();
                                adapter.removeItem(position);
                            }
                        })
                        .setNegativeButton("취소", null)
                        .show();
                return true;
            }
        });

//        // 삭제 버튼 클릭 시 삭제
//        btn_delete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int count, checked;
//                count = myAdapter.getCount();
//
//                if(count > 0) {
//                    checked = list_item.getCheckedItemPosition();
//                    if(checked > -1 && checked < count) {
//                        productArrayList.remove(checked);
//                        productArrayList.clear();
//                        myAdapter.notifyDataSetChanged();
//                    }
//                }
//            }
//        });

        // home btn
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
