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
public class MultiSelectItemListDialog extends Dialog {
    final ArrayList<MultiSpinnerList> arrSearlist = new ArrayList<>();
    SelectionItemAdapter adapter;
    ListView listView;
    private String searchText = "";

    public MultiSelectItemListDialog(final Context context, String defaultMsg, final List<MultiSpinnerList> selectItems, final List<MultiSpinnerList> questions, int resId, final ItemPickerListner dtp) {
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
        adapter = new SelectionItemAdapter(context, R.layout.dialog_checkbox_row, questions);
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
                    Utility.animateView(view);
                    adapter.setSelectedIndex(position);
                    if (searchText.isEmpty())
                        questions.get(position).setChecked(!questions.get(position).isChecked());
                    else
                        arrSearlist.get(position).setChecked(!arrSearlist.get(position).isChecked());
                    adapter.notifyDataSetChanged();
                }
            });

            if (selectItems != null && selectItems.size() > 0) {
                for (int i = 0; i < selectItems.size(); i++) {
                    for (int j = 0; j < questions.size(); j++) {
                        if (selectItems.get(i).getId().equals(questions.get(j).getId())) {
                          //  questions.get(j).setChecked(true);
                            adapter.notifyDataSetChanged();
                            break;
                        }
                    }
                }
            }
        }
        setupSearchView(searchView, context, questions);


        dialogView.findViewById(R.id.tvDone).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<MultiSpinnerList> selectItems = new ArrayList<MultiSpinnerList>();
                Collection<MultiSpinnerList> result = Collections2.filter(questions, new FilterPredicate().filterMultiSpnList);
                if (result != null && result.size() > 0) {
                    for (MultiSpinnerList s : result) {
                        selectItems.add(s);
                    }
                } else {
                    selectItems = null;
                }

                dtp.OnDoneButton(MultiSelectItemListDialog.this, selectItems);
            }
        });
        dialogView.findViewById(R.id.tvCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dtp.OnCancelButton(MultiSelectItemListDialog.this, selectItems);
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
            adapter = new SelectionItemAdapter(context, R.layout.dialog_checkbox_row, arrSearlist);
        } else {
            adapter = new SelectionItemAdapter(context, R.layout.dialog_checkbox_row, questions);
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
        public View getView(int position, View view, ViewGroup parent) {
            View v = view;
            ViewHolder viewHolder;
            if (v == null) {
                LayoutInflater vi = (LayoutInflater) this.getContext()
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = vi.inflate(rowResourceId, null);
                viewHolder = new ViewHolder();
                viewHolder.title = (CheckedTextView) v.findViewById(R.id.tvCheckedItem);
                // store the holder with the view.
                v.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) v.getTag();
            }

            String quest = list.get(position).getName();
            final CheckedTextView txtTitle = viewHolder.title;

            SpinnerList spinnerList = new SpinnerList();
            spinnerList.setName(list.get(position).getName());
            spinnerList.setId(list.get(position).getId());
            if (list.get(position).isChecked()) {
                txtTitle.setChecked(true);
            } else {
                txtTitle.setChecked(false);
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
            CheckedTextView title;
        }

    }
}
