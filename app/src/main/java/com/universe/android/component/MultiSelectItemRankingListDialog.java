package com.universe.android.component;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.Context;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.google.common.collect.Collections2;
import com.universe.android.R;
import com.universe.android.model.MultiSpinnerList;
import com.universe.android.model.SpinnerList;
import com.universe.android.utility.Utility;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

/**
 * This class contains methods required in creation, opening and closing of Dialog.
 * It displays the gender list or state list in  registration screen
 */
public class MultiSelectItemRankingListDialog extends Dialog {
    final ArrayList<MultiSpinnerList> arrSearlist = new ArrayList<>();
    SelectionItemAdapter adapter;
    ListView listView;
    private String searchText = "";

    public MultiSelectItemRankingListDialog(final Context context, String defaultMsg, final List<MultiSpinnerList> selectItems, final List<MultiSpinnerList> questions, int resId, final ItemPickerListner dtp) {
        super(context);
        dismiss();
        ContextThemeWrapper contextThemeWrapper = new ContextThemeWrapper(context, R.style.app_custom_dialog_theme);
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(resId, null);
        dialogView.findViewById(R.id.llDone).setVisibility(View.VISIBLE);


        SearchView searchView = (SearchView) dialogView.findViewById(R.id.searchView);
        listView = (ListView) dialogView.findViewById(R.id.list_popup);

        if (questions.size() > 10) {
            ViewGroup.LayoutParams lp = listView.getLayoutParams();
            lp.height = Utility.dpToPx(context, 350);
            listView.setLayoutParams(lp);
        } else {
            ViewGroup.LayoutParams lp = listView.getLayoutParams();
            lp.height = Utility.dpToPx(context, 300);
            listView.setLayoutParams(lp);
        }
        ((TextView) dialogView.findViewById(R.id.tvDialogTitle)).setText(defaultMsg);
        adapter = new SelectionItemAdapter(context, R.layout.dialog_single_row_ranking, questions);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (listView != null) {
            listView.setAdapter(adapter);
            if (questions.size() > 3) {
                ViewGroup.LayoutParams lp = listView.getLayoutParams();
                listView.setLayoutParams(lp);
            }
            listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                   /* Utility.animateView(view);
                    adapter.setSelectedIndex(position);
                    if (searchText.isEmpty())
                        questions.get(position).setChecked(!questions.get(position).isChecked());
                    else
                        arrSearlist.get(position).setChecked(!arrSearlist.get(position).isChecked());
                    adapter.notifyDataSetChanged();*/
                }
            });


        }
        setupSearchView(searchView, context, questions);


        dialogView.findViewById(R.id.tvDone).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<MultiSpinnerList> selectItems = new ArrayList<MultiSpinnerList>();
                List<String> selectText = new ArrayList<String>();
                Collection<MultiSpinnerList> result = Collections2.filter(questions, new FilterPredicate().filterMultiSpnListEdittext);
                if (questions != null && questions.size() > 0) {
                    for (MultiSpinnerList s : questions) {
                        if (!s.getAmount().isEmpty()) {
                            if (!selectText.contains(s.getAmount()))
                                selectText.add(s.getAmount());
                        }


                    }

                    if (questions.size()!=selectText.size()){
                       // Utility.showToast("Please fill all options");
                    }else {
                        for (int k = 0; k < selectText.size(); k++) {
                            MultiSpinnerList multiSpinnerList = new MultiSpinnerList();
                            multiSpinnerList.setAmount(selectText.get(k));

                            multiSpinnerList.setName(questions.get(k).getName());
                            multiSpinnerList.setChecked(true);
                            selectItems.add(multiSpinnerList);
                        }
                    }
                } else {
                    selectItems = null;
                }

                if (selectItems!=null) {
                    if (selectItems.size() != questions.size()) {
                        Utility.showToast("Same rank not acceptable");
                    } else {
                        dtp.OnDoneButton(MultiSelectItemRankingListDialog.this, selectItems);
                    }
                }else {
                    dtp.OnDoneButton(MultiSelectItemRankingListDialog.this, selectItems);
                }

              //  dtp.OnDoneButton(MultiSelectItemRankingListDialog.this, selectItems);
            }
        });
        dialogView.findViewById(R.id.tvCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dtp.OnCancelButton(MultiSelectItemRankingListDialog.this, selectItems);
            }
        });

        setContentView(dialogView);
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(contextThemeWrapper);
        alertDialog.setView(dialogView);

        alertDialog.create();
        setCanceledOnTouchOutside(false);


        if (isShowing()) {
            dismiss();
        } else {
            show();
        }

    }

    private void setupSearchView(SearchView searchView, final Context context, final List<MultiSpinnerList> questions) {
        SearchManager searchManager = (SearchManager) context.getSystemService(Context.SEARCH_SERVICE);
        SearchableInfo searchableInfo = searchManager.getSearchableInfo(((Activity) context).getComponentName());
        if (searchView != null) {
            searchView.setSearchableInfo(searchableInfo);
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    searchText = newText;
                    filter(newText, questions, context);
                    return false;
                }
            });
        }

    }

    /**
     * Filter.
     *
     * @param charText the char text
     */
    private void filter(String charText, List<MultiSpinnerList> questions, Context context) {
        if (arrSearlist != null && questions != null) {
            charText = charText.toLowerCase(Locale.getDefault());
            arrSearlist.clear();
            if (charText.length() == 0) {
                arrSearlist.addAll(questions);
            } else {
                for (MultiSpinnerList wp : questions) {
                    if (wp.getName().toLowerCase(Locale.getDefault()).contains(charText)) {
                        arrSearlist.add(wp);
                    }
                }
            }
        }
        if (charText.length() > 0) {
            adapter = new SelectionItemAdapter(context, R.layout.dialog_single_row_ranking, arrSearlist);
        } else {
            adapter = new SelectionItemAdapter(context, R.layout.dialog_single_row_ranking, questions);
        }
        listView.setAdapter(adapter);
    }

    //    /**
//     * ItemPickerListner interface used to allow the creator of a dialog to run some code when the dialog is canceled.
//     */
    public interface ItemPickerListner {
        void OnDoneButton(Dialog ansPopup, List<MultiSpinnerList> selctedItems);

        void OnCancelButton(Dialog ansPopup, List<MultiSpinnerList> selctedItems);
    }

    /**
     * This adapter being used to show list of gender or state which required in registration
     */
    private class SelectionItemAdapter extends ArrayAdapter<MultiSpinnerList> {
        private int rowResourceId;
        private List<MultiSpinnerList> list;
        private int index = -1;

        public SelectionItemAdapter(Context context, int rowResourceId,
                                    List<MultiSpinnerList> list) {
            super(context, rowResourceId, list);
            this.rowResourceId = rowResourceId;
            this.list = list;
        }

        public void setSelectedIndex(int index) {
            this.index = index;
        }

        @Override
        public MultiSpinnerList getItem(int position) {
            return list.get(position);
        }

        @Override
        public View getView(final int position, View view, ViewGroup parent) {
            View v = view;
            ViewHolder viewHolder;
            if (v == null) {
                LayoutInflater vi = (LayoutInflater) this.getContext()
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = vi.inflate(rowResourceId, null);
                viewHolder = new ViewHolder();
                viewHolder.radio1 = (CheckedTextView) v.findViewById(R.id.radio1);
                viewHolder.radio2 = (CheckedTextView) v.findViewById(R.id.radio2);
                viewHolder.radio3 = (CheckedTextView) v.findViewById(R.id.radio3);
                viewHolder.radio4 = (CheckedTextView) v.findViewById(R.id.radio4);
                viewHolder.radio5 = (CheckedTextView) v.findViewById(R.id.radio5);
                viewHolder.title = (TextView) v.findViewById(R.id.title);
                // store the holder with the view.
                v.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) v.getTag();
            }

            String quest = list.get(position).getName();
            final TextView txtTitle = viewHolder.title;
            final CheckedTextView radio1 = viewHolder.radio1;
            final CheckedTextView radio2 = viewHolder.radio2;
            final CheckedTextView radio3 = viewHolder.radio3;
            final CheckedTextView radio4 = viewHolder.radio4;
            final CheckedTextView radio5 = viewHolder.radio5;



            if (Utility.validateString(list.get(position).getAmount())){

                if (list.get(position).getAmount().equalsIgnoreCase("1")){
                    radio1.setChecked(true);
                }else if (list.get(position).getAmount().equalsIgnoreCase("2")){
                    radio2.setChecked(true);
                }else if (list.get(position).getAmount().equalsIgnoreCase("3")){
                    radio3.setChecked(true);
                }else if (list.get(position).getAmount().equalsIgnoreCase("4")){
                    radio4.setChecked(true);
                }else if (list.get(position).getAmount().equalsIgnoreCase("5")){
                    radio5.setChecked(true);
                }
            }
            radio1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    radio1.setChecked(true);
                    radio2.setChecked(false);

                    radio3.setChecked(false);

                    radio4.setChecked(false);
                    radio5.setChecked(false);

                    /*radio1.setEnabled(true);
                    radio2.setEnabled(false);
                    radio3.setEnabled(false);
                    radio4.setEnabled(false);
                    radio5.setEnabled(false);*/
                    list.get(position).setAmount("1");
                    list.get(position).setChecked(true);

                }
            });

            radio2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    radio2.setChecked(true);
                    radio1.setChecked(false);

                    radio3.setChecked(false);

                    radio4.setChecked(false);
                    radio5.setChecked(false);

                   /* radio2.setEnabled(true);
                    radio1.setEnabled(false);
                    radio3.setEnabled(false);
                    radio4.setEnabled(false);
                    radio5.setEnabled(false);*/

                    list.get(position).setChecked(true);
                    list.get(position).setAmount("2");

                }
            });
            radio3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    radio3.setChecked(true);
                    radio2.setChecked(false);

                    radio1.setChecked(false);

                    radio4.setChecked(false);
                    radio5.setChecked(false);

                   /* radio1.setEnabled(true);
                    radio2.setEnabled(false);
                    radio3.setEnabled(false);
                    radio4.setEnabled(false);
                    radio5.setEnabled(false);*/

                    list.get(position).setChecked(true);
                    list.get(position).setAmount("3");

                }
            });
            radio4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    radio4.setChecked(true);
                    radio2.setChecked(false);

                    radio3.setChecked(false);

                    radio1.setChecked(false);
                    radio5.setChecked(false);

                   /* radio1.setEnabled(true);
                    radio2.setEnabled(false);
                    radio3.setEnabled(false);
                    radio4.setEnabled(false);
                    radio5.setEnabled(false);*/

                    list.get(position).setChecked(true);
                    list.get(position).setAmount("4");
                }
            });
            radio5.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    radio5.setChecked(true);
                    radio2.setChecked(false);

                    radio3.setChecked(false);

                    radio4.setChecked(false);
                    radio1.setChecked(false);

                  /*  radio1.setEnabled(true);
                    radio2.setEnabled(false);
                    radio3.setEnabled(false);
                    radio4.setEnabled(false);
                    radio5.setEnabled(false);*/

                    list.get(position).setChecked(true);
                    list.get(position).setAmount("5");
                }
            });

            SpinnerList spinnerList = new SpinnerList();
            spinnerList.setName(list.get(position).getName());
            spinnerList.setId(list.get(position).getId());
            if (Utility.validateString(list.get(position).getName())) {
                txtTitle.setText(list.get(position).getName());
            } else {
                txtTitle.setText(list.get(position).getName());
            }

            txtTitle.setTag(quest);


            txtTitle.setText(quest);
            return v;
        }

        /**
         * A ViewHolder object stores each of the component views inside the tag field of the Layout,
         * so can immediately access them without the need to look them up repeatedly.
         */
        private class ViewHolder {
            CheckedTextView radio1,radio2,radio3,radio4,radio5;
            TextView title;
        }

    }
}
