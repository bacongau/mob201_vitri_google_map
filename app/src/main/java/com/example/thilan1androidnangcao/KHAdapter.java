package com.example.thilan1androidnangcao;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class KHAdapter extends BaseAdapter {
    Context context;
    int layout;
    ArrayList<KhachHang> khachHangList;

    public KHAdapter(Context context, int layout, ArrayList<KhachHang> khachHangList) {
        this.context = context;
        this.layout = layout;
        this.khachHangList = khachHangList;
    }

    @Override
    public int getCount() {
        return khachHangList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public class ViewHolder{
        TextView textView_id,textView_ten,textView_kinhdo,textView_vido;
        Button button_xoa,button_xem;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout,null);

            viewHolder.textView_id = convertView.findViewById(R.id.textView_id);
            viewHolder.textView_ten = convertView.findViewById(R.id.textView_ten);
            viewHolder.textView_kinhdo = convertView.findViewById(R.id.textView_kinhdo);
            viewHolder.textView_vido = convertView.findViewById(R.id.textView_vido);
            viewHolder.button_xoa = convertView.findViewById(R.id.button_xoa);
            viewHolder.button_xem = convertView.findViewById(R.id.button_xemdiachi);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        KhachHang kh = khachHangList.get(position);
        viewHolder.textView_id.setText("Id: " + kh.id);
        viewHolder.textView_ten.setText("Ten: " + kh.ten);
        viewHolder.textView_kinhdo.setText("Kinh do: " + kh.kinhdo);
        viewHolder.textView_vido.setText("Vi do: " + kh.vido);

        viewHolder.button_xoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(context);
                alert.setMessage("Bạn có muốn xóa thông tin khach hang ?");
                alert.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                alert.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String id = kh.id;
                        String DATABASE_NAME = "db diachiKH.db";
                        SQLiteDatabase database = Database.initDatabase((Activity) context, DATABASE_NAME);
                        database.delete("KH", "id=?", new String[]{id});
                        ((MainActivity)context).docDuLieu();
                        Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                    }
                });
                alert.show();

            }
        });

        viewHolder.button_xem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();

                bundle.putFloat("kinhdo",khachHangList.get(position).kinhdo);
                bundle.putFloat("vido",khachHangList.get(position).vido);

                Intent intent = new Intent(context,MapsActivity.class);
                intent.putExtra("diachi",bundle);
                context.startActivity(intent);
            }
        });

        return convertView;
    }
}
