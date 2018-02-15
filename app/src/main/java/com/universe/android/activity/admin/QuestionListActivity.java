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
import com.universe.android.activity.BaseActivity;
import com.universe.android.model.QuestionModal;
import com.universe.android.parent.ParentSaveActivity;
import com.universe.android.realmbean.RealmQuestion;
import com.universe.android.utility.AppConstants;
import com.universe.android.utility.Utility;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

public class QuestionListActivity extends BaseActivity {


    private static final int ID_UPDATE_CAMP = 1000;
    private List<QuestionModal> questionModals = new ArrayList<>();
    private QuestionListAdapter adapter;
    private String formId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_list_view);

        Intent intent=getIntent();
        if (intent!=null){
            formId=intent.getStringExtra(AppConstants.FORM_ID);
        }
        prepareList();
        ((TextView) findViewById(R.id.textViewHeader)).setText(getString(R.string.question));

        FloatingActionButton fabAdd=(FloatingActionButton)findViewById(R.id.fabAdd);
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(QuestionListActivity.this,FormQuestionActivity.class);
                i.putExtra(AppConstants.FORM_ID,formId);
                i.putExtra(AppConstants.STR_TITLE,getString(R.string.question));
                startActivityForResult(i, ID_UPDATE_CAMP);
            }
        });
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new QuestionListAdapter(this, questionModals);
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
        if (questionModals == null) questionModals = new ArrayList<>();
        questionModals.clear();
        Realm realm = Realm.getDefaultInstance();

        try {

            RealmResults<RealmQuestion> realmQuestions = realm.where(RealmQuestion.class).findAllSorted(AppConstants.CREATEDAT, Sort.DESCENDING);


            if (realmQuestions != null && realmQuestions.size() > 0) {
                for (int i = 0; i < realmQuestions.size(); i++) {
                    QuestionModal modal = new QuestionModal();
                    modal.setId(realmQuestions.get(i).getId());
                    modal.setTitle(realmQuestions.get(i).getTitle());
                    modal.setDisplayOrder(realmQuestions.get(i).getDisplayOrder());
                 //   String date=AppConstants.format2.format(realmSurveys.get(i).getExpiryDate());
                    modal.setRequired(realmQuestions.get(i).isRequired());
                    questionModals.add(modal);
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

    private void deleteCamp(final QuestionModal QuestionModal) {
        Realm realm = Realm.getDefaultInstance();
        if (!realm.isInTransaction())
        realm.beginTransaction();
        try {

                List<RealmQuestion> realmQuestions = realm.where(RealmQuestion.class).equalTo(AppConstants.ID, QuestionModal.getId()).findAll();
                if (realmQuestions != null && realmQuestions.size() > 0) {
                    realmQuestions.get(0).deleteFromRealm();
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


    private void showConfirmationDialog(final QuestionModal QuestionModal, int size) {
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
                deleteCamp(QuestionModal);
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
    class QuestionListAdapter extends RecyclerView.Adapter<QuestionListAdapter.CustomViewHolder> {

        private final Context context;
        private List<QuestionModal> surveysModals;

        /**
         * Instantiates a new Camp list adapter.
         *
         * @param context       the context
         * @param indicatorList the feed item list
         */
        public QuestionListAdapter(Context context, List<QuestionModal> indicatorList) {
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
        //    holder.tvCampDate.setText(surveysModals.get(position).getCampDate());

            holder.imgEdit.setTag(surveysModals.get(position));


            if (Utility.validateString(surveysModals.get(position).getTitle())) {
                holder.tvName.setVisibility(View.VISIBLE);
                holder.tvName.setText( surveysModals.get(position).getTitle());
            } else {
                holder.tvName.setVisibility(View.GONE);
            }



            holder.imgEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Utility.animateView(view);

                    if (view != null && view.getTag() instanceof QuestionModal) {
                        QuestionModal camp = (QuestionModal) view.getTag();


                            Intent intent = new Intent(context, FormQuestionActivity.class);
                          //  intent.putExtra(AppConstants.STR_TITLE, getString(R.string.camp_master));
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.putExtra(AppConstants.STR_TITLE,getString(R.string.question));
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


                    if (view != null && view.getTag() instanceof QuestionModal) {
                        QuestionModal questionModal = (QuestionModal) view.getTag();


                            if (Utility.isConnected()) {
                              //  showConfirmationDialog(questionModal, 0);
                            } else {
                                showConfirmationDialog(questionModal, 0);

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
