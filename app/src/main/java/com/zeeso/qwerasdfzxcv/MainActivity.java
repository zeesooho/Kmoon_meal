package com.zeeso.qwerasdfzxcv;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    TextView txt1 = null;
    Document doc = null;
    Button btn1 = null;
    DatePicker datePicker = null;
    int year1,month,day;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txt1 = (TextView)findViewById(R.id.txt1);
        btn1 = (Button)findViewById(R.id.button);
        btn1.setOnClickListener((View.OnClickListener) this);
        datePicker = (DatePicker)findViewById(R.id.datePicker);
        datePicker.init(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth(), new DatePicker.OnDateChangedListener(){
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                year1 = year;
                month = monthOfYear+1;
                day = dayOfMonth;
            }
        });
    }

    @Override
    public void onClick(View v) {
        new T().execute(null,null,null);
    }


    class T extends AsyncTask<Void, Void, Void>{
        String title;
        protected Void doInBackground(Void... params) {
            try {
                doc = Jsoup.connect("http://www.kmoon.hs.kr/main.php?menugrp=020301&master=meal2&act=list&SearchYear="+year1+"&SearchMonth="+month+"&SearchDay="+day+"#diary_list").get();
                Elements asd = doc.select("div.meal_table");
                for(Element asdf: asd){
                        title += (asdf.text() + "\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            String match = "[^\uAC00-\uD7A3xfe0-9a-zA-Z\\s]";
            title =title.replaceAll(match, " ");
            title = title.replaceAll(" +"," ");
            String[] des;
            String dess ="";
            des = title.split("\\s");
            if(des[5].equals("등록된")){
                for (int i = 5; i < des.length; i++){
                    if (i < des.length - 1) {
                        dess = dess + des[i] + " ";
                    } else {
                        dess = dess + des[i] + ".";
                    }
                }
            }
            else {
                for (int i = 5; i < des.length; i++) {
                    if (i < des.length - 1) {
                        dess = dess + des[i] + "\n";
                    } else {
                        dess = dess + des[i];
                    }
                }
            }
            txt1.setText(dess);
        }
    }

}
