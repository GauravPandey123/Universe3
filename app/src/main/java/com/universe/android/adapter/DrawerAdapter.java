package com.universe.android.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.universe.android.R;
import com.universe.android.helper.FontClass;
import com.universe.android.model.DrawerItem;
import com.universe.android.utility.AppConstants;
import com.universe.android.utility.Prefs;

import java.util.ArrayList;

/**
 * Created by darshanz on 7/6/15.
 */
public class DrawerAdapter extends RecyclerView.Adapter<DrawerAdapter.DrawerViewHolder> {

    private final static int TYPE_HEADER = 0;
    private final static int TYPE_MENU = 1;

    private ArrayList<DrawerItem> drawerMenuList;
    private OnItemSelecteListener mListener;

    Context mContext;

    public DrawerAdapter(ArrayList<DrawerItem> drawerMenuList, Context mContext) {
        this.drawerMenuList = drawerMenuList;
        this.mContext = mContext;
    }

    @Override
    public DrawerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == TYPE_HEADER) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_drawer_header, parent, false);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_menu_item, parent, false);
        }


        return new DrawerViewHolder(view, viewType);
    }


    @Override
    public void onBindViewHolder(DrawerViewHolder holder, int position) {
        if (position == 0) {
            holder.textViewStatus.setTypeface(FontClass.openSansLight(mContext));
            holder.textViewMobileNo.setTypeface(FontClass.openSansRegular(mContext));
            holder.textViewName.setTypeface(FontClass.openSansBold(mContext));
            if (Prefs.getStringPrefs(AppConstants.isActive).equals("1")) {
                holder.textViewStatus.setText("ONLINE");
            } else {
                holder.textViewStatus.setText("OFFLINE");
            }
          //  if (Prefs.getLongPrefs(AppConstants.phone)!=0)
          //  holder.textViewMobileNo.setText(""+Prefs.getLongPrefs(AppConstants.phone));
            holder.textViewName.setText(Prefs.getStringPrefs(AppConstants.name));
        } else {
            holder.title.setText(drawerMenuList.get(position - 1).getTitle());
            holder.icon.setImageResource(drawerMenuList.get(position - 1).getIcon());
            holder.title.setTypeface(FontClass.openSansRegular(mContext));
        }

    }

    @Override
    public int getItemCount() {
        return drawerMenuList.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_HEADER;
        }
        return TYPE_MENU;

    }

    class DrawerViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView textViewName, textViewMobileNo, textViewStatus;
        ImageView icon;

        public DrawerViewHolder(View itemView, int viewType) {
            super(itemView);
            if (viewType == 0) {
                textViewName = itemView.findViewById(R.id.textViewUserName);
                textViewMobileNo = itemView.findViewById(R.id.textViewPhoneNo);
                textViewStatus = itemView.findViewById(R.id.textViewOnline);
            } else {
                title = itemView.findViewById(R.id.title);
                icon = itemView.findViewById(R.id.icon);
            }
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.onItemSelected(view, getAdapterPosition());

                }
            });
        }

    }

    public void setOnItemClickLister(OnItemSelecteListener mListener) {
        this.mListener = mListener;
    }

    public interface OnItemSelecteListener {
        public void onItemSelected(View v, int position);
    }

}