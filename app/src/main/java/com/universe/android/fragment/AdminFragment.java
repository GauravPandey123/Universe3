package com.universe.android.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.universe.android.R;
import com.universe.android.activity.admin.CategoryListActivity;
import com.universe.android.activity.admin.ClientListActivity;
import com.universe.android.activity.admin.CustomerListActivity;
import com.universe.android.activity.admin.QuestionListActivity;
import com.universe.android.activity.admin.SurveyListActivity;
import com.universe.android.model.DrawerItem;
import com.universe.android.utility.AppConstants;
import com.universe.android.utility.SpacesItemDecoration;

import java.util.ArrayList;


public class AdminFragment extends BaseFragment {
    private TextView tvMaster;

    private RecyclerView recyclerView;
    private HomeListAdapter adapter;
    private ArrayList<DrawerItem> responseList=new ArrayList<>();


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.admin_home_selection, container, false);
        initialization(view);
        return view;
    }


    private void initialization(View view) {
        tvMaster =(TextView) view.findViewById(R.id.tvMaster);
        getHomeData();

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        GridLayoutManager mLayoutManager = new GridLayoutManager(getActivity(),2);
     //   recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new SpacesItemDecoration(10));
        adapter = new HomeListAdapter(getActivity(), responseList);
        recyclerView.setAdapter(adapter);



    }

    private void getHomeData(){
        DrawerItem drawerItem2=new DrawerItem();
        drawerItem2.setTitle(getString(R.string.survey));
        responseList.add(drawerItem2);
        DrawerItem drawerItem1=new DrawerItem();
        drawerItem1.setTitle(getString(R.string.customer));
        responseList.add(drawerItem1);
        DrawerItem drawerItem3=new DrawerItem();
        drawerItem3.setTitle(getString(R.string.client));
        responseList.add(drawerItem3);
        DrawerItem drawerItem4=new DrawerItem();
        drawerItem4.setTitle(getString(R.string.category));
        responseList.add(drawerItem4);
        DrawerItem drawerItem5=new DrawerItem();
        drawerItem5.setTitle(getString(R.string.question));
        responseList.add(drawerItem5);



    }


    class HomeListAdapter extends RecyclerView.Adapter<HomeListAdapter.CustomViewHolder> {

        private final Context context;
        private ArrayList<DrawerItem> DrawerItemList;


        /**
         * Instantiates a new Indicator list adapter.
         *
         * @param context       the context
         * @param indicatorList the feed item list

         */
        public HomeListAdapter(Context context, ArrayList<DrawerItem> indicatorList) {
            this.context = context;
            DrawerItemList = indicatorList;

        }


        @Override
        public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.admin_list_item, null);
            return new CustomViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final CustomViewHolder holder, final int position) {

                final DrawerItem drawerItem = DrawerItemList.get(position);

                if (position==0){
                    holder.icon.setImageDrawable(getResources().getDrawable(R.drawable.ic_survey));
                }else if (position==1){
                    holder.icon.setImageDrawable(getResources().getDrawable(R.drawable.ic_client));
                }else if (position==2){
                    holder.icon.setImageDrawable(getResources().getDrawable(R.drawable.ic_customer));
                }else if (position==3){
                    holder.icon.setImageDrawable(getResources().getDrawable(R.drawable.ic_category));
                }else if (position==4){
                    holder.icon.setImageDrawable(getResources().getDrawable(R.drawable.ic_question));
                }
                holder.tvTitle.setText(drawerItem.getTitle());

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i=new Intent(getActivity(),SurveyListActivity.class);
                        i.putExtra(AppConstants.FORM_ID,"survey");
                        i.putExtra(AppConstants.STR_TITLE,getString(R.string.survey));

                        if (drawerItem.getTitle().equalsIgnoreCase(getString(R.string.client))){
                            i=new Intent(getActivity(),ClientListActivity.class);
                            i.putExtra(AppConstants.FORM_ID,"client");
                            i.putExtra(AppConstants.STR_TITLE,getString(R.string.client));

                        }else if (drawerItem.getTitle().equalsIgnoreCase(getString(R.string.customer))){
                            i=new Intent(getActivity(),CustomerListActivity.class);
                            i.putExtra(AppConstants.FORM_ID,"customer");
                            i.putExtra(AppConstants.STR_TITLE,getString(R.string.customer));

                        }else if (drawerItem.getTitle().equalsIgnoreCase(getString(R.string.category))){
                            i=new Intent(getActivity(),CategoryListActivity.class);
                            i.putExtra(AppConstants.FORM_ID,"category");
                            i.putExtra(AppConstants.STR_TITLE,getString(R.string.category));

                        }
                        else if (drawerItem.getTitle().equalsIgnoreCase(getString(R.string.question))){
                            i=new Intent(getActivity(),QuestionListActivity.class);
                            i.putExtra(AppConstants.FORM_ID,"question");
                            i.putExtra(AppConstants.STR_TITLE,getString(R.string.question));

                        }
                        startActivity(i);
                        getActivity().overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                    }
                });



        }


        @Override
        public int getItemCount() {
            return (null != DrawerItemList ? DrawerItemList.size() : 0);
        }

        /**
         * The type Custom view holder.
         */
        public class CustomViewHolder extends RecyclerView.ViewHolder {
            private TextView tvTitle;
            private ImageView icon;
            final private View itemView;


            /**
             * Instantiates a new Custom view holder.
             *
             * @param view the view
             */
            private CustomViewHolder(View view) {
                super(view);
                tvTitle = (TextView) view.findViewById(R.id.tvTtitle);
                icon = (ImageView) view.findViewById(R.id.icon);
                 itemView=view;
            }
        }
    }

}
