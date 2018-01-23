package com.universe.android.web;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;

import in.editsoft.api.cache.type.DataType;
import in.editsoft.api.callback.APICallback;
import in.editsoft.api.exception.APIError;
import in.editsoft.api.exception.APIException;
import in.editsoft.api.util.App;


/**
 * BaseApiCallback
 */
public abstract class BaseApiCallback<T extends BaseResponse<T>> extends APICallback<T> {

    // Todo can change later according to server response
    public static final int ERROR_CODE_MINOR_UPDATE = 307;
    public static final int ERROR_CODE_MAJOR_UPDATE = 308;
    private static final String TAG = BaseApiCallback.class.getSimpleName();

    private Activity mActivity;

    public BaseApiCallback() {
        super(App.context);
    }

    public BaseApiCallback(@NonNull Activity activity) {
        super(activity);
        mActivity = activity;
    }

    @Override
    public void onSuccess(@NonNull T response) {

    }

    @Override
    public void onFailure(APIException e) {
        Log.e(TAG, "onFailure: ");
        int errorCode = e.getCode();
        APIError error = e.getError();
        if (error == null) {
            return;
        }
        String errorMsg = "Update";
        if (TextUtils.isEmpty(error.getMessage())) {
            errorMsg = error.getMessage();
        }

        switch (errorCode) {
            case ERROR_CODE_MINOR_UPDATE:
                showAlertDialog("Minor Update", errorMsg, "UPDATE", "CANCEL", true);
                break;
            case ERROR_CODE_MAJOR_UPDATE:
                showAlertDialog("Major Update", errorMsg, "UPDATE", "", false);
                break;
        }
    }

    protected void showAlertDialog(String title, String msg, String positiveBtnText, String negativeBtnText, boolean cancelable) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
        builder.setTitle(title == null ? "title" : title);
        builder.setMessage(msg == null ? "message" : msg);
        if (positiveBtnText != null && !positiveBtnText.isEmpty()) {
            builder.setPositiveButton(positiveBtnText, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // Todo launch app store from here

                }
            });
        }
        if (negativeBtnText != null && !negativeBtnText.isEmpty()) {
            builder.setNegativeButton(negativeBtnText, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
        }
        builder.setCancelable(cancelable)
                .create()
                .show();
    }

    @Override
    public DataType<T> getDBDataType() {
        return null;
    }

}
