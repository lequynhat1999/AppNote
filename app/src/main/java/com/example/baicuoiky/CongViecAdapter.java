package com.example.baicuoiky;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class CongViecAdapter extends BaseAdapter {
    private MainActivity context;
    private int layout;
    private List<CongViec> listCV;

    public CongViecAdapter(MainActivity context, int layout, List<CongViec> listCV) {
        this.context = context;
        this.layout = layout;
        this.listCV = listCV;
    }

    @Override
    public int getCount() {
        return listCV.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private class ViewHolder
    {
        TextView textViewTenCV;
        ImageView imageViewDelete,imageViewEdit;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null)
        {
            holder = new ViewHolder();
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(layout,null);
            holder.textViewTenCV = convertView.findViewById(R.id.textViewTenCV);
            holder.imageViewDelete = convertView.findViewById(R.id.imageViewDelete);
            holder.imageViewEdit = convertView.findViewById(R.id.imageViewEdit);
            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }
        CongViec congViec = listCV.get(position);
        holder.textViewTenCV.setText(congViec.getTenCV());

        holder.imageViewEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.DialogEdit(congViec.getTenCV(),congViec.getIdCV());
            }
        });

        holder.imageViewDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.DialogDelete(congViec.getTenCV(),congViec.getIdCV());
            }
        });

        return convertView;
    }
}
