package com.iceoton.durable.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.iceoton.durable.R;
import com.iceoton.durable.model.Asset;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AssetRecyclerAdapter extends RecyclerView.Adapter<AssetRecyclerAdapter.AssetViewHolder> {

    private ArrayList<Asset> mAssetList;

    public AssetRecyclerAdapter(ArrayList<Asset> mAssetList) {
        this.mAssetList = mAssetList;
    }

    @Override
    public AssetViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.asset_list_row, parent, false);

        return new AssetViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AssetViewHolder holder, int position) {
        Asset asset = mAssetList.get(position);
        holder.txtCode.setText(asset.getCode());
        holder.txtName.setText(asset.getName());
        holder.txtUpdateDate.setText(asset.getUpdateDate());
        holder.txtQuantity.setText(String.valueOf(asset.getQuantity()).concat("\n").concat("หน่วย"));
    }

    @Override
    public int getItemCount() {
        return mAssetList.size();
    }

    public static class AssetViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txtCode) TextView txtCode;
        @BindView(R.id.txtName) TextView txtName;
        @BindView(R.id.txtUpdateDate) TextView txtUpdateDate;
        @BindView(R.id.txtQuantity) TextView txtQuantity;

        public AssetViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
