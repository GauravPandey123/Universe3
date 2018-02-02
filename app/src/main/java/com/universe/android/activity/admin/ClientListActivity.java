package com.universe.android.activity.admin;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.universe.android.R;
import com.universe.android.model.ClientModal;
import com.universe.android.parent.ParentSaveActivity;
import com.universe.android.realmbean.RealmClient;
import com.universe.android.realmbean.RealmCustomer;
import com.universe.android.utility.AppConstants;
import com.universe.android.utility.Utility;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

public class ClientListActivity extends ParentSaveActivity {


    private static final int ID_UPDATE_CAMP = 1000;
    private List<ClientModal> clientsModals = new ArrayList<>();
    private ClientListAdapter adapter;
    private String formId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_list_view);
        setHeader();
        Intent intent=getIntent();
        if (intent!=null){
            formId=intent.getStringExtra(AppConstants.FORM_ID);
        }
        prepareList();
        ((TextView) findViewById(R.id.tvName)).setText(getString(R.string.client));

        FloatingActionButton fabAdd=(FloatingActionButton)findViewById(R.id.fabAdd);
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(ClientListActivity.this,FormQuestionActivity.class);
                i.putExtra(AppConstants.FORM_ID,formId);
                startActivityForResult(i, ID_UPDATE_CAMP);
            }
        });
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ClientListAdapter(this, clientsModals);
        recyclerView.setAdapter(adapter);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ID_UPDATE_CAMP && resultCode == RESULT_OK) {
            prepareList();
        }
    }

    private void prepareList() {
        if (clientsModals == null) clientsModals = new ArrayList<>();
        clientsModals.clear();
        Realm realm = Realm.getDefaultInstance();

        try {

            RealmResults<RealmClient> realmClients = realm.where(RealmClient.class).findAllSorted(AppConstants.CREATEDAT, Sort.DESCENDING);


            if (realmClients != null && realmClients.size() > 0) {
                for (int i = 0; i < realmClients.size(); i++) {
                    ClientModal modal = new ClientModal();
                    modal.setId(realmClients.get(i).getId());
                    modal.setName(realmClients.get(i).getName());
                    modal.setStatus(realmClients.get(i).getStatus());
                 //   String date=AppConstants.format2.format(realmSurveys.get(i).getExpiryDate());
                    modal.setExpiryDate(realmClients.get(i).getExpiryDate());
                    clientsModals.add(modal);
                }
            }
        } catch (Exception e) {
            realm.close();
            e.printStackTrace();
        } finally {
            realm.close();
        }

        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }

    private void deleteCamp(final ClientModal clientModal) {
        Realm realm = Realm.getDefaultInstance();
        if (!realm.isInTransaction())
        realm.beginTransaction();
        try {

                List<RealmClient> realmClients = realm.where(RealmClient.class).equalTo(AppConstants.ID, clientModal.getId()).findAll();
                if (realmClients != null && realmClients.size() > 0) {
                    realmClients.get(0).deleteFromRealm();
                }

        } catch (Exception e) {
            if (realm.isInTransaction())
            realm.cancelTransaction();

        } finally {
            if (realm.isInTransaction())
            realm.commitTransaction();
            if (!realm.isClosed())
            realm.close();
        }
    }


    private void showConfirmationDialog(final ClientModal clientModal, int size) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog);

        TextView text = (TextView) dialog.findViewById(R.id.text_dialog);
        Button dialogButton = (Button) dialog.findViewById(R.id.btnYes);
        Button dialogNo = (Button) dialog.findViewById(R.id.btnNo);
        if (size == 0) {
            text.setText(getString(R.string.delete_msg));
            dialogButton.setVisibility(View.VISIBLE);
            dialogNo.setText(getString(R.string.no));
        } else {
            text.setText(getString(R.string.cannot_delete));
            dialogButton.setVisibility(View.GONE);
            dialogNo.setText(getString(R.string.ok));
        }
        dialogButton.setText(getString(R.string.yes));
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Utility.animateView(v);
                dialog.dismiss();
                deleteCamp(clientModal);

            }
        });

        dialogNo.setVisibility(View.VISIBLE);
        dialogNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Utility.animateView(v);
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    /**
     * The type Indicator list adapter.
     */
    class ClientListAdapter extends RecyclerView.Adapter<ClientListAdapter.CustomViewHolder> {

        private final Context context;
        private List<ClientModal> surveysModals;

        /**
         * Instantiates a new Camp list adapter.
         *
         * @param context       the context
         * @param indicatorList the feed item list
         */
        public ClientListAdapter(Context context, List<ClientModal> indicatorList) {
            this.context = context;
            surveysModals = indicatorList;
        }


        @Override
        public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_row, null);
            return new CustomViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final CustomViewHolder holder, int position) {
            holder.imgEdit.setTag(surveysModals.get(position));


            if (Utility.validateString(surveysModals.get(position).getName())) {
                holder.tvName.setVisibility(View.VISIBLE);
                holder.tvName.setText( surveysModals.get(position).getName());
            } else {
                holder.tvName.setVisibility(View.GONE);
            }



            holder.imgEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Utility.animateView(view);

                    if (view != null && view.getTag() instanceof ClientModal) {
                        ClientModal camp = (ClientModal) view.getTag();



                        Intent intent = new Intent(context, FormQuestionActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.putExtra(AppConstants.STR_TITLE,getString(R.string.client));
                        intent.putExtra(AppConstants.FORM_ID,formId);
                        intent.putExtra(AppConstants.FORM_ANS_ID, camp.getId());
                        startActivityForResult(intent, ID_UPDATE_CAMP);

                    }
                }
            });

            holder.imgDelete.setTag(surveysModals.get(position));

            holder.imgDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    Utility.animateView(view);


                    if (view != null && view.getTag() instanceof ClientModal) {
                        ClientModal camp = (ClientModal) view.getTag();
                        Realm realm = Realm.getDefaultInstance();
                        ;
                        long count = 0;
                        try {
                            count = realm.where(RealmCustomer.class).equalTo(AppConstants.CLIENTID, camp.getId()).count();
                        } catch (Exception e) {
                            realm.close();
                            e.printStackTrace();
                        } finally {
                            realm.close();
                        }

                        if (count == 0) {
                            showConfirmationDialog(camp, 0);
                        } else {
                            if (Utility.isConnected()) {
                                //   showConfirmationDialog(camp, 1);
                            } else {
                                showConfirmationDialog(camp, 1);
                                //   showToastMessage(getString(R.string.no_network));
                            }
                        }
                    }
                }
            });
        }


        @Override
        public int getItemCount() {
            return (null != surveysModals ? surveysModals.size() : 0);
        }

        /**
         * The type Custom view holder.
         */
        public class CustomViewHolder extends RecyclerView.ViewHolder {
            private TextView tvStatus;
            private LinearLayout relRow;
            private TextView imgDelete;
            private TextView imgEdit;
            private TextView tvName;

            /**
             * Instantiates a new Custom view holder.
             *
             * @param view the view
             */
            private CustomViewHolder(View view) {
                super(view);
                tvName = (TextView) view.findViewById(R.id.tvName);
                tvStatus = (TextView) view.findViewById(R.id.tvStatus);
                imgDelete = (TextView) view.findViewById(R.id.imgDelete);
                imgEdit = (TextView) view.findViewById(R.id.imgEdit);
                relRow = (LinearLayout) view.findViewById(R.id.llRow);
            }
        }
    }


}
