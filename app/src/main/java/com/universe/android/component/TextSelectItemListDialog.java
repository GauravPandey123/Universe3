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


import com.universe.android.R;
import com.universe.android.utility.Utility;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * This class contains methods required in creation, opening and closing of Dialog.
 * It displays the gender list or state list in  registration screen
 */
public class TextSelectItemListDialog extends Dialog {
    final ArrayList<String> arrSearlist = new ArrayList<>();
    SelectionItemAdapter adapter;
    ListView listView;
    private String strSelectedItem = null;
    private String strPreSelectedItem = null;
    private String searchText = "";

    public TextSelectItemListDialog(final Context context, String defaultMsg, String selectedChoice, final List<String> questions, int resId, final ItemPickerListner dtp) {
        super(context);
        dismiss();

        ContextThemeWrapper contextThemeWrapper = new ContextThemeWrapper(context, R.style.app_custom_dialog_theme);
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(resId, null);
        SearchView searchView = (SearchView) dialogView.findViewById(R.id.searchView);
        listView = (ListView) dialogView.findViewById(R.id.list_popup);
        ((TextView) dialogView.findViewById(R.id.tvDialogTitle)).setText(defaultMsg);
        (dialogView.findViewById(R.id.tvClose)).setVisibility(View.VISIBLE);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (listView != null) {
            adapter = new SelectionItemAdapter(context, R.layout.dialog_single_row, questions);
            listView.setAdapter(adapter);
            if (questions.size() > 3) {
                ViewGroup.LayoutParams lp = listView.getLayoutParams();
                listView.setLayoutParams(lp);
            }
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Utility.animateView(view);
                    Utility.hideSoftKeyboard((Activity) context);
                    if (searchText.isEmpty()) {
                        strSelectedItem = questions.get(position);
                    } else {
                        strSelectedItem = arrSearlist.get(position);
                    }

                    adapter.setSelectedIndex(position);
                    if (searchText.isEmpty()) {
                        dtp.OnDoneButton(TextSelectItemListDialog.this, strSelectedItem);

                    } else {
                        dtp.OnDoneButton(TextSelectItemListDialog.this, strSelectedItem);

                    }
                    adapter.notifyDataSetChanged();
                }
            });

            if (Utility.validateString(selectedChoice)) {
                for (int i = 0; i < questions.size(); i++) {
                    if (selectedChoice.equalsIgnoreCase(questions.get(i))) {
                        adapter.setSelectedIndex(i);
                        strPreSelectedItem = questions.get(i);
                        strSelectedItem = questions.get(i);
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        }

        (dialogView.findViewById(R.id.tvClose)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utility.animateView(view);
                dismiss();
            }
        });
        setupSearchView(searchView, context, questions);
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

    private void setupSearchView(SearchView searchView, final Context context, final List<String> questions) {
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
    private void filter(String charText, List<String> questions, Context context) {
        if (arrSearlist != null && questions != null) {
            charText = charText.toLowerCase(Locale.getDefault());
            arrSearlist.clear();
            if (charText.length() == 0) {
                arrSearlist.addAll(questions);
            } else {
                for (String wp : questions) {
                    if (wp.toLowerCase(Locale.getDefault()).contains(charText)) {
                        arrSearlist.add(wp);
                    }
                }
            }
        }
        if (charText.length() > 0) {
            adapter = new SelectionItemAdapter(context, R.layout.dialog_single_row, arrSearlist);

        } else {
            adapter = new SelectionItemAdapter(context, R.layout.dialog_single_row, questions);
        }

        listView.setAdapter(adapter);
    }

    //    /**
//     * ItemPickerListner interface used to allow the creator of a dialog to run some code when the dialog is canceled.
//     */
    public interface ItemPickerListner {
        void OnDoneButton(Dialog ansPopup, String strAns);

//        void OnCancelButton(Dialog ansPopup, String strAns);
    }

    /**
     * This adapter being used to show list of gender or state which required in registration
     */
    private class SelectionItemAdapter extends ArrayAdapter<String> {
        private int rowResourceId;
        private List<String> list;
        private int index = -1;

        public SelectionItemAdapter(Context context, int rowResourceId,
                                    List<String> list) {
            super(context, rowResourceId, list);
            this.rowResourceId = rowResourceId;
            this.list = list;
        }

        public void setSelectedIndex(int index) {
            this.index = index;
        }

        @Override
        public String getItem(int position) {
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

            String quest = list.get(position);
            final CheckedTextView txtTitle = viewHolder.title;

            txtTitle.setTag(quest);


            if (index != -1) {
                if (index == position) {
                    txtTitle.setChecked(true);
                } else {
                    txtTitle.setChecked(false);
                }
            }

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
