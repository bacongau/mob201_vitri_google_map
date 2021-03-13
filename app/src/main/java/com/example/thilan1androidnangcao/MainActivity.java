package com.example.thilan1androidnangcao;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    String DATABASE_NAME = "db diachiKH.db";
    SQLiteDatabase database;

    FloatingActionButton floatingActionButton;
    ListView listView;
    ArrayList<KhachHang> khachHangList = new ArrayList<>();;
    KHAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        anhxa();
        docDuLieu();
        suKien();
    }

    private void anhxa() {
        floatingActionButton = findViewById(R.id.floatingActionButton_add);
        listView = findViewById(R.id.listView_danhsach);

        adapter = new KHAdapter(MainActivity.this, R.layout.item_khachhang, khachHangList);
        listView.setAdapter(adapter);

    }

    public void docDuLieu() {
//        database = Database.initDatabase(MainActivity.this, DATABASE_NAME);
//        Cursor cursor = database.rawQuery("SELECT * FROM KH", null);
//        khachHangList.clear();
//        for (int i = 0; i < cursor.getCount(); i++) {
//            cursor.moveToPosition(i);
//            String id = cursor.getString(0);
//            String Ten = cursor.getString(1);
//            float kinhdo = cursor.getFloat(2);
//            float vido = cursor.getFloat(3);
//            khachHangList.add(new KhachHang(id, Ten, kinhdo, vido));
//        }
//        adapter.notifyDataSetChanged();

        database = Database.initDatabase(MainActivity.this, DATABASE_NAME);
        Cursor cursor = database.rawQuery("SELECT * FROM KH", null);
        khachHangList.clear();
        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);
            String id = cursor.getString(0);
            String ten = cursor.getString(1);
            float kinhdo = cursor.getFloat(2);
            float vido = cursor.getFloat(3);
            khachHangList.add(new KhachHang(id, ten, kinhdo, vido));
        }
        adapter.notifyDataSetChanged();
    }

    private void suKien() {
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                


                Dialog dialog = new Dialog(MainActivity.this);
                dialog.setContentView(R.layout.dialog_them_kh);

                EditText editText_id = dialog.findViewById(R.id.editText_id_dialog);
                EditText editText_ten = dialog.findViewById(R.id.editText_ten_dialog);
                EditText editText_kinhdo = dialog.findViewById(R.id.editText_kinhdo_dialog);
                EditText editText_vido = dialog.findViewById(R.id.editText_vido_dialog);
                Button button_ok = dialog.findViewById(R.id.button_ok_dialog);
                Button button_huy = dialog.findViewById(R.id.button_huy_dialog);

                button_ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String id = editText_id.getText().toString();
                        String ten = editText_ten.getText().toString();
                        float kinhdo = 0,vido = 0;
                        try {
                             kinhdo = Float.parseFloat(editText_kinhdo.getText().toString());
                             vido = Float.parseFloat(editText_vido.getText().toString());
                        }catch (Exception e){
                            
                        }

                        if (checkRong(id, ten, kinhdo, vido) == false) {
                            ContentValues values = new ContentValues();
                            values.put("id",id);
                            values.put("ten", ten);
                            values.put("kinhdo", kinhdo);
                            values.put("vido", vido);

                            SQLiteDatabase database = Database.initDatabase(MainActivity.this, DATABASE_NAME);
                            database.insert("KH", null, values);
                            Toast.makeText(MainActivity.this, "Them moi thanh cong", Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(MainActivity.this, "Khong de trong thong tin", Toast.LENGTH_SHORT).show();
                        }

                        docDuLieu();
                        dialog.dismiss();
                    }
                });

                button_huy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });
    }

    private boolean checkRong(String id, String ten, float kinhdo, float vido) {
        if (id.isEmpty() || ten.isEmpty() || "".equals(kinhdo) || "".equals(vido)) {
            return true;
        } else {
            return false;
        }
    }


    public void loadListView() {
    }


}