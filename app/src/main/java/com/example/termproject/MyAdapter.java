package com.example.termproject;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class MyAdapter extends BaseAdapter {

    ArrayList<Product> productArrayList;
    LayoutInflater layoutInflater = null;
    Context context = null;


    public MyAdapter(Context context, ArrayList<Product> productArrayList){
        this.context = context;
        this.productArrayList = productArrayList;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return productArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    //리스트에 있는거 삭제하는 메소드
    public void removeItem(int position) {
        if(position >= 0 && position < getCount()) {
            productArrayList.remove(getItem(position));
            notifyDataSetChanged();
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = layoutInflater.inflate(R.layout.listlayout, null);

        ImageView image = (ImageView)view.findViewById(R.id.image);
        TextView listName = (TextView)view.findViewById(R.id.listName);
        TextView eatablePeriod = (TextView)view.findViewById(R.id.eatablePeriod);
        TextView set_Date = (TextView)view.findViewById(R.id.set_Date);

        String imgPath = productArrayList.get(position).getImgPath();
        Bitmap bitmap = BitmapFactory.decodeFile(imgPath);
        image.setImageBitmap(bitmap);

        listName.setText(productArrayList.get(position).getProductName());

        long d_day = ChronoUnit.DAYS.between(LocalDate.now(), productArrayList.get(position).getFinishDate());
        String eatableMsg = "유통기한이 " + Long.valueOf(d_day).intValue() + "일 남았습니다.";
        eatablePeriod.setText(eatableMsg);

        long alarm_day = ChronoUnit.DAYS.between(productArrayList.get(position).getNoticeDate(), productArrayList.get(position).getFinishDate());
        String alarmMsg = "알람 설정일로부터 " + Long.valueOf(alarm_day).intValue() + "일 남았습니다.";
        set_Date.setText(alarmMsg);

        return view;
    }
}
