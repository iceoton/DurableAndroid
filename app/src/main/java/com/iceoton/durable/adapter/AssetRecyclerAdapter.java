package com.iceoton.durable.adapter;

import android.content.Context;
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

/**
 * Adapter สำหรับจัดการการแสดง list of assets (รายการครุภัณฑ์)
 */
public class AssetRecyclerAdapter extends RecyclerView.Adapter<AssetRecyclerAdapter.AssetViewHolder> {
    /**
     * context ของหน้าที่จะแสดง list of assets
     */
    private Context mContext;
    /**
     * รายการครุภัณฑ์ที่ต้องการแสดง
     */
    private ArrayList<Asset> mAssetList;

    public AssetRecyclerAdapter(ArrayList<Asset> mAssetList, Context context) {
        this.mAssetList = mAssetList;
        this.mContext = context;
    }

    @Override
    public AssetViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.asset_list_row, parent, false);

        return new AssetViewHolder(view, mContext);
    }

    @Override
    public void onBindViewHolder(AssetViewHolder holder, int position) {
        Asset asset = mAssetList.get(position);
        holder.bind(asset);
    }

    @Override
    public int getItemCount() {
        return mAssetList.size();
    }

    @Override
    public long getItemId(int position) {
        return mAssetList.get(position).getId();
    }

    /**
     * class holder สำหรับแสดงแต่ละ item ใน list ในที่นี้คือครุภัณฑ์ในรายการครุภัณฑ์
     */
    public static class AssetViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txtCode) TextView txtCode;
        @BindView(R.id.txtName) TextView txtName;
        @BindView(R.id.txtUpdateDate) TextView txtUpdateDate;
        @BindView(R.id.txtQuantity) TextView txtQuantity;

        public AssetViewHolder(View itemView, final Context context) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        /**
         * ทำการตั้งค่าข้อมูลครุภัณฑ์ลงบน view ของ item นี้
         * @param asset ครุภัณฑ์ที่จะแสดงใน item นี้
         */
        public void bind(Asset asset){
            txtCode.setText(asset.getCode());
            txtName.setText(asset.getName());
            txtUpdateDate.setText(asset.getUpdateDate());
            txtQuantity.setText(String.valueOf(asset.getQuantity()).concat("\n").concat("หน่วย"));
        }
    }
}
