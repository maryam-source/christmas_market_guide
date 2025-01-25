package com.iqbalmaryam.christmasmarketguide;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class VendorAdapter extends BaseAdapter {

    private Context context;
    private List<Vendor> vendorList;

    public VendorAdapter(Context context, List<Vendor> vendorList) {
        this.context = context;
        this.vendorList = vendorList;
    }

    @Override
    public int getCount() {
        return vendorList.size();
    }

    @Override
    public Object getItem(int position) {
        return vendorList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.vendor_item, parent, false);
        }

        TextView vendorName = convertView.findViewById(R.id.vendorName);
        TextView vendorCategory = convertView.findViewById(R.id.vendorCategory);
        TextView vendorAddress = convertView.findViewById(R.id.vendorAddress);

        Vendor vendor = vendorList.get(position);

        vendorName.setText(vendor.getName());
        vendorCategory.setText(vendor.getCategory());
        vendorAddress.setText(vendor.getAddress());

        return convertView;
    }
}
