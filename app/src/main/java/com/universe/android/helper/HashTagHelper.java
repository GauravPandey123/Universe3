package com.universe.android.helper;

import android.graphics.Color;
import android.text.Html;
import android.text.Spannable;
import android.text.method.LinkMovementMethod;
import android.text.style.CharacterStyle;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;


import com.universe.android.utility.Utility;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gaurav on 07/2/18.
 */

public class HashTagHelper implements ClickableColorSpan.OnHashTagClickListener {

    /**
     * If this is not null then  all of the symbols in the List will be considered as valid symbols of hashtag
     * For example :
     * mAdditionalHashTagChars = {'$','_','-'}
     * it means that hashtag: "#this_is_hashtag-with$dollar-sign" will be highlighted.
     * <p>
     * Note: if mAdditionalHashTagChars would be "null" only "#this" would be highlighted
     */
    private final List<Character> mAdditionalHashTagChars;
    private TextView mTextView;
    private int mHashTagWordColor;
    private static int mCharLength = 0;

    private HashTagHelper.OnHashTagClickListener mOnHashTagClickListener;

    public static final class Creator {

        private Creator() {
        }

        public static HashTagHelper create(int length, int color, HashTagHelper.OnHashTagClickListener listener) {
            mCharLength = length;
            return new HashTagHelper(color, listener, null);
        }

        public static HashTagHelper create(int color, HashTagHelper.OnHashTagClickListener listener, char... additionalHashTagChars) {
            return new HashTagHelper(color, listener, additionalHashTagChars);
        }
    }

    public interface OnHashTagClickListener {
        void onHashTagClicked(String hashTag);
    }

    private HashTagHelper(int color, HashTagHelper.OnHashTagClickListener listener, char... additionalHashTagCharacters) {
        mHashTagWordColor = color;
        mOnHashTagClickListener = listener;
        mAdditionalHashTagChars = new ArrayList<>();

        if (additionalHashTagCharacters != null) {
            for (char additionalChar : additionalHashTagCharacters) {
                mAdditionalHashTagChars.add(additionalChar);
            }
        }
    }

    public void handlecomment(TextView textView) {
        if (mTextView == null) {
            mTextView = textView;
            // in order to use spannable we have to set buffer type
            //mTextView.setText(mTextView.getText(), TextView.BufferType.SPANNABLE);
            String text = mTextView.getText().toString();
            if (!Utility.validateString(text)) {
                mTextView.setText("");
            } else if (text.length() < mCharLength) {
                mTextView.setText(text, TextView.BufferType.SPANNABLE);
            } else {
                String s1 = text.substring(0, mCharLength);
                String s2 = text.substring(mCharLength);
                mTextView.setText(Html.fromHtml("<b>" + s1 + "</b>" + s2), TextView.BufferType.SPANNABLE);
            }
            if (mOnHashTagClickListener != null) {
                // we need to set this in order to get onClick event
                mTextView.setMovementMethod(LinkMovementMethod.getInstance());
                // after onClick clicked text become highlighted
                mTextView.setHighlightColor(Color.TRANSPARENT);
            } else {
                // hash tags are not clickable, no need to change these parameters
            }
            setColorsToAllHashTags(mTextView.getText());
        } else {
            throw new RuntimeException("TextView is not null. You need to create a unique HashTagHelper for every TextView");
        }

    }


    private void eraseAndColorizeAllText(CharSequence text) {
        Spannable spannable = ((Spannable) mTextView.getText());
        CharacterStyle[] spans = spannable.getSpans(0, text.length(), CharacterStyle.class);
        for (CharacterStyle span : spans) {
            spannable.removeSpan(span);
        }
        setColorsToAllHashTags(text);
    }

    private void setColorsToAllHashTags(CharSequence text) {
        int index = 0;
        int startIndexOfNextHashSign;
        while (index < text.length() - 1) {
            char sign = text.charAt(index);
            int nextNotLetterDigitCharIndex = index + 1; // we assume it is next. if if was not changed by findNextValidHashTagChar then index will be incremented by 1
            if (sign == '#') {
                startIndexOfNextHashSign = index;
                nextNotLetterDigitCharIndex = findNextValidHashTagChar(text, startIndexOfNextHashSign);
                setColorForHashTagToTheEnd(startIndexOfNextHashSign, nextNotLetterDigitCharIndex);
            }

            index = nextNotLetterDigitCharIndex;
        }
    }

    private int findNextValidHashTagChar(CharSequence text, int start) {
        int nonLetterDigitCharIndex = -1; // skip first sign '#"
        for (int index = start + 1; index < text.length(); index++) {
            char sign = text.charAt(index);
            boolean isValidSign = Character.isLetterOrDigit(sign) || mAdditionalHashTagChars.contains(sign);
            if (!isValidSign) {
                nonLetterDigitCharIndex = index;
                break;
            }
        }
        if (nonLetterDigitCharIndex == -1) {
            // we didn't find non-letter. We are at the end of text
            nonLetterDigitCharIndex = text.length();
        }
        return nonLetterDigitCharIndex;
    }

    private void setColorForHashTagToTheEnd(int startIndex, int nextNotLetterDigitCharIndex) {
        CharacterStyle span;
        Spannable s = (Spannable) mTextView.getText();
        if (mOnHashTagClickListener != null) {
            span = new ClickableColorSpan(mHashTagWordColor, this);
        } else {
            // no need for clickable span because it is messing with selection when click
            span = new ForegroundColorSpan(mHashTagWordColor);
        }
        s.setSpan(span, startIndex, nextNotLetterDigitCharIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
    }

    @Override
    public void onHashTagClicked(String hashTag) {
        mOnHashTagClickListener.onHashTagClicked(hashTag);
    }
}
