package com.mockitotest;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by bykj003 on 2017/8/17.
 */

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.Holder> {

    private Context mContext;
    private List<String> mLanguageList;
    private LayoutInflater mLayoutInflater;

    public CustomAdapter(Context context, List<String> languageList) {
        this.mContext = context;
        this.mLanguageList = languageList;
        this.mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder(mLayoutInflater.inflate(R.layout.item_language, null));
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        holder.mTvName.setText(mLanguageList.get(position));
    }

    @Override
    public int getItemCount() {
        return mLanguageList == null ? 0 : mLanguageList.size();
    }

    static class Holder extends RecyclerView.ViewHolder {

        public TextView mTvName;

        public Holder(View itemView) {
            super(itemView);
            mTvName = (TextView) itemView.findViewById(R.id.tv_name);
        }
    }
}
