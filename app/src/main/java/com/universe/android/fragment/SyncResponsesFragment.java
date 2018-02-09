package com.universe.android.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.universe.android.R;
import com.universe.android.okkhttp.APIClient;
import com.universe.android.okkhttp.UniverseAPI;
import com.universe.android.realmbean.RealmController;
import com.universe.android.realmbean.RealmSurveys;
import com.universe.android.utility.AppConstants;
import com.universe.android.utility.Prefs;
import com.universe.android.utility.Utility;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import io.realm.Realm;
import io.realm.RealmResults;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class SyncResponsesFragment extends BaseFragment {
    private ProgressDialog progressDialog;
    private TextView tvLastSyncDate;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sync_responses, container, false);


        tvLastSyncDate = (TextView) view.findViewById(R.id.tvLastSyncDate);

        setSyncDate();

        view.findViewById(R.id.llSync).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (new RealmController().isAnySyncPending()) {
                    if (Utility.isConnected()) {
                        progressDialog = new ProgressDialog(getActivity());
                        progressDialog.setMessage(getString(R.string.syncing_data));
                        progressDialog.show();
                        progressDialog.setCancelable(false);
                        progressDialog.setCanceledOnTouchOutside(false);
                        syncSurveyFormsRequest();
                    } else {

                        Utility.showToast(getString(R.string.no_network));
                    }
                } else {

                    Utility.showToast(getString(R.string.no_data_to_sync));
                           /* if (Utility.isConnected()) {
                                progressDialog = new ProgressDialog(getActivity());
                                progressDialog.setMessage(getString(R.string.syncing_data));
                                progressDialog.show();
                                progressDialog.setCancelable(false);
                                progressDialog.setCanceledOnTouchOutside(false);

                            }*/
                }


            }
        });


        return view;
    }


    private void syncSurveyFormsRequest() {
        final Realm realm = Realm.getDefaultInstance();
        JSONArray jsonArray = new JSONArray();
        try {
            RealmResults<RealmSurveys> realmSurveys = realm.where(RealmSurveys.class).equalTo(AppConstants.ISSYNC, false).findAll();
            if (realmSurveys != null && realmSurveys.size() > 0) {
                for (RealmSurveys realmSurveys1 : realmSurveys) {
                    if (realmSurveys1 != null) {
                        String strRequest = new Gson().toJson(realm.copyFromRealm(realmSurveys1));
                        if (Utility.validateString(strRequest)) {
                            JSONObject jsonRequest = new JSONObject(strRequest);
                            if (jsonRequest != null) {
                                jsonRequest = Utility.formatDates(jsonRequest);
                                jsonRequest.remove(AppConstants.ID);
                                jsonRequest.remove(AppConstants.ISSYNC);
                                jsonRequest.remove(AppConstants.ISUPDATE);
                                jsonArray.put(jsonRequest);
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            realm.close();
            e.printStackTrace();
        } finally {
            realm.close();
        }
        if (jsonArray != null && jsonArray.length() > 0) {
            syncSurveyFormsServer(jsonArray);
        } else {
            syncUpdateSurveyRequest();
        }
    }


    private void setSyncDate() {
        String strDate = "";
        SimpleDateFormat format = new SimpleDateFormat(AppConstants.utc_format1);
        Date newDate = null;
        try {
            newDate = format.parse(Prefs.getStringPrefs(AppConstants.LAST_SYNC_DATE));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (newDate != null) {
            String date = AppConstants.format10.format(newDate);
            strDate = getString(R.string.last_sync_date) + " - " + date;
        }

        tvLastSyncDate.setText(strDate);
    }

    private void syncSurveyFormsServer(JSONArray jsonRequest) {
        OkHttpClient okHttpClient = APIClient.getHttpClient();
        if (okHttpClient != null) {
            RequestBody requestBody = RequestBody.create(UniverseAPI.JSON, jsonRequest.toString());
            String url = UniverseAPI.WEB_SERVICE_CREATE_SURVEY_METHOD;
            Request request = APIClient.getPostRequest(getActivity(), url, requestBody);
            okHttpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    showFailedToastMsg();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    try {
                        if (response != null && response.isSuccessful()) {
                            String responseData = response.body().string();
                            if (responseData != null) {
                                new RealmController().saveSyncFormInputResponse(responseData, false);
                            }
                        }
                        syncUpdateSurveyRequest();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }


    private void showFailedToastMsg() {
        if (progressDialog != null) progressDialog.dismiss();
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Utility.showToast(getString(R.string.no_network_error));
            }
        });
    }

    private void syncCompleted() {
        if (progressDialog != null) progressDialog.dismiss();
        if (getActivity() != null) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Utility.showToast(getString(R.string.sync_completed));
                    Utility.saveLastSyncDate(getActivity());
                    setSyncDate();

                }
            });
        }
    }


    private void catchException(Exception e) {
        if (progressDialog != null) progressDialog.dismiss();
        e.printStackTrace();
    }


    private void syncUpdateSurveyRequest() {
        final Realm realm = Realm.getDefaultInstance();
        JSONArray jsonArray = new JSONArray();
        try {
            RealmResults<RealmSurveys> realmSurveys = realm.where(RealmSurveys.class).equalTo(AppConstants.ISSYNC, true).equalTo(AppConstants.ISUPDATE, false).findAll();
            if (realmSurveys != null && realmSurveys.size() > 0) {
                for (RealmSurveys realmSurveys1 : realmSurveys) {
                    if (realmSurveys1 != null) {
                        String strRequest = new Gson().toJson(realm.copyFromRealm(realmSurveys1));
                        if (Utility.validateString(strRequest)) {
                            JSONObject jsonRequest = new JSONObject(strRequest);
                            if (jsonRequest != null) {
                                jsonRequest = Utility.formatDates(jsonRequest);
                                jsonRequest.remove(AppConstants.ISSYNC);
                                jsonRequest.remove(AppConstants.ISUPDATE);
                                jsonArray.put(jsonRequest);
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            realm.close();
            e.printStackTrace();
        } finally {
            realm.close();
        }

        if (jsonArray != null && jsonArray.length() > 0) {
            syncUpdateSurveyToServer(jsonArray);
        } else {
            syncCompleted();
        }

    }


    private void syncUpdateSurveyToServer(JSONArray jsonRequest) {
        OkHttpClient okHttpClient = APIClient.getHttpClient();
        if (okHttpClient != null) {
            Log.d("Syncrequest", jsonRequest.toString());
            RequestBody requestBody = RequestBody.create(UniverseAPI.JSON, jsonRequest.toString());
            String url = UniverseAPI.WEB_SERVICE_CREATE_SURVEY_METHOD;
            Request request = APIClient.getPostRequest(getActivity(), url, requestBody);
            okHttpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                    showFailedToastMsg();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    try {
                        if (response != null && response.isSuccessful()) {
                            String responseData = response.body().string();
                            if (responseData != null) {
                                new RealmController().saveSyncFormInputResponse(responseData, true);
                            }
                        }


                    } catch (Exception e) {
                        catchException(e);
                    }
                }
            });
        }
    }

}