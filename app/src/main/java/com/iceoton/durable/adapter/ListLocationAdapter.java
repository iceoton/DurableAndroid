package com.iceoton.durable.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.iceoton.durable.model.AssetDetail;

import java.util.List;

public class ListLocationAdapter extends ArrayAdapter<AssetDetail.Location>{
    private static class ViewHolder {
        TextView textName;
    }


    public ListLocationAdapter(@NonNull Context context, @NonNull List<AssetDetail.Location> objects) {
        super(context, android.R.layout.simple_dropdown_item_1line, objects);
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).getId();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        AssetDetail.Location location = getItem(position);
        ViewHolder viewHolder;

        if(convertView == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(android.R.layout.simple_dropdown_item_1line, parent, false);
            viewHolder.textName = (TextView) convertView.findViewById(android.R.id.text1);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.textName.setText(location.toString());

        return convertView;
    }
}
