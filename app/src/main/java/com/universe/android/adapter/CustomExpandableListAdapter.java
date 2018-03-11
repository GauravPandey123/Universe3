package com.universe.android.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.universe.android.R;
import com.universe.android.model.CategoryModal;
import com.universe.android.model.Questions;
import com.universe.android.utility.AppConstants;
import com.universe.android.utility.Prefs;
import com.universe.android.utility.Utility;

import java.util.HashMap;
import java.util.List;

/**
 * The type Custom expandable list adapter.
 */
public class CustomExpandableListAdapter extends BaseExpandableListAdapter {

    private Context _context;
    private List<CategoryModal> _listDataHeader; // header titles
    // child data in format of header title, child title

    private HashMap<CategoryModal, List<Questions>> _listDataChild;


    /**
     * Instantiates a new Custom expandable list adapter.
     *
     * @param context        the context
     * @param listDataHeader the list data header
     * @param listChildData  the list child data
     */
    public CustomExpandableListAdapter(Context context, List<CategoryModal> listDataHeader,
                                       HashMap<CategoryModal, List<Questions>> listChildData) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listChildData;

    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        final String productName = _listDataChild.get(_listDataHeader.get(groupPosition)).get(childPosition).getTitle();
        final String required = _listDataChild.get(_listDataHeader.get(groupPosition)).get(childPosition).getStatus();
        final String answer = _listDataChild.get(_listDataHeader.get(groupPosition)).get(childPosition).getAnswer();


        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.child_list, null);
        }

        TextView tvQuestionName = (TextView) convertView
                .findViewById(R.id.tvQuestionName);
        ImageView imgStatus = (ImageView) convertView
                .findViewById(R.id.imgStatus);
        ImageView imgRequired = (ImageView) convertView
                .findViewById(R.id.imgRequired);

        if (Utility.validateString(answer)) {
            imgStatus.setBackgroundResource(R.drawable.done);
        } else {
            imgStatus.setBackgroundResource(R.drawable.wrong);
        }
        if (required.equalsIgnoreCase("Yes")) {
            imgRequired.setBackgroundResource(R.drawable.required);
            imgRequired.setVisibility(View.VISIBLE);
        } else {
            imgRequired.setVisibility(View.GONE);
        }
        tvQuestionName.setText(productName);


        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this._listDataHeader.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this._listDataHeader.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String headerTitle = _listDataHeader.get(groupPosition).getCategoryName();
        String count = _listDataHeader.get(groupPosition).getQuestionCount();
        String categoryAnsweredd = _listDataHeader.get(groupPosition).getCategoryAnswered();
        String status = _listDataHeader.get(groupPosition).getStatus();
        String isViewByZM = _listDataHeader.get(groupPosition).getIsViewByZM();

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.header_item, null);
        }

        TextView lblListHeader = (TextView) convertView
                .findViewById(R.id.tvTitle);
        TextView tvCount = (TextView) convertView
                .findViewById(R.id.tvCount);
        ImageView imgStatus = (ImageView) convertView
                .findViewById(R.id.imgStatus);


        if (categoryAnsweredd.equalsIgnoreCase("Yes")) {
            imgStatus.setBackgroundResource(R.drawable.done);
        } else {
            imgStatus.setBackgroundResource(R.drawable.wrong);
        }

        String designation = Prefs.getStringPrefs(AppConstants.TYPE);

        if (designation.equalsIgnoreCase("cd")) {
            if (categoryAnsweredd.equalsIgnoreCase("Yes") && status.equalsIgnoreCase("2")) {
                imgStatus.setBackgroundResource(R.drawable.done);
            }
        } else if (designation.equalsIgnoreCase("rm")) {
            if (categoryAnsweredd.equalsIgnoreCase("Yes") && status.equalsIgnoreCase("1")) {
                imgStatus.setBackgroundResource(R.drawable.double_done);
            }
        } else {
            if (categoryAnsweredd.equalsIgnoreCase("Yes") && isViewByZM.equalsIgnoreCase("1")) {
                imgStatus.setBackgroundResource(R.drawable.double_done);
            }
        }


        tvCount.setText(count);
        //  lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);
        if (isExpanded) {
            lblListHeader.setTypeface(null, Typeface.NORMAL);
            lblListHeader.setCompoundDrawablesWithIntrinsicBounds(R.drawable.arrow_up, 0,
                    0, 0);
        } else {
            // If group is not expanded then change the text back into normal
            // and change the icon

            lblListHeader.setTypeface(null, Typeface.NORMAL);
            lblListHeader.setCompoundDrawablesWithIntrinsicBounds(R.drawable.arrow_down, 0,
                    0, 0);
        }
        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }


}