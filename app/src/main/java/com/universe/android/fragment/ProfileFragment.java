package com.universe.android.fragment;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.cocosw.bottomsheet.BottomSheet;
import com.soundcloud.android.crop.Crop;
import com.universe.android.R;
import com.universe.android.activity.BaseActivity;
import com.universe.android.helper.FontClass;
import com.universe.android.helper.HashTagHelper;
import com.universe.android.realmbean.RealmCustomer;
import com.universe.android.realmbean.RealmUser;
import com.universe.android.resource.Login.Profile.ImageUploadService;
import com.universe.android.resource.Login.Profile.ProfileRequest;
import com.universe.android.resource.Login.Profile.ProfileResponse;
import com.universe.android.resource.Login.Profile.ProfileService;
import com.universe.android.utility.AppConstants;
import com.universe.android.utility.Prefs;
import com.universe.android.utility.Utility;
import com.universe.android.web.BaseApiCallback;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import in.editsoft.api.exception.APIException;
import io.realm.Realm;
import retrofit2.http.Multipart;

import static android.app.Activity.RESULT_OK;

/**
 * Created by gaurav.pandey on 31-01-2018.
 */

public class ProfileFragment extends BaseFragment {
    private View view;
    private TextView textViewUserNameProfile, textViewPhoneNumberProfile, textViewRolesProfile, textViewLob, textViewTLerritor, textViewEmailProfile;
    private EditText editTextPhoneProfile, editTextRolesProfile, editTextLob, editTextTerritory;
    private ArrayList<ProfileResponse.ResponseBean> responseBeans;
    private HashTagHelper hashTagHelper;
    private CircleImageView circleImageViewProfile;
    private boolean isUpdateImage = false;
    private static final int WRITE_EXTERNAL_STORAGE = 1;
    private String mImageId;
    public int CAMERA_REQUEST = 2121;
    public int GALLERY_REQUEST = 2221;
    private String mImageUrl;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.profile_fragement, container, false);
        initialization();
        setUpElements();
        clickListeners();
        setupDetail();
        return view;
    }

    private void clickListeners() {
        circleImageViewProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImageOptions();
            }
        });
    }

    private void setUpElements() {
        textViewUserNameProfile.setTypeface(FontClass.openSansRegular(getActivity()));
        textViewPhoneNumberProfile.setTypeface(FontClass.openSansRegular(getActivity()));
        textViewRolesProfile.setTypeface(FontClass.openSansRegular(getActivity()));
        textViewLob.setTypeface(FontClass.openSansRegular(getActivity()));
        textViewTLerritor.setTypeface(FontClass.openSansRegular(getActivity()));
        editTextPhoneProfile.setTypeface(FontClass.openSansLight(getActivity()));
        editTextRolesProfile.setTypeface(FontClass.openSansLight(getActivity()));
        editTextRolesProfile.setText(new StringBuilder().append(Prefs.getStringPrefs(AppConstants.ROLE).substring(0, 1).toUpperCase()).append(Prefs.getStringPrefs(AppConstants.ROLE).substring(1).toLowerCase()).toString());
        editTextLob.setTypeface(FontClass.openSansLight(getActivity()));
        editTextTerritory.setTypeface(FontClass.openSansLight(getActivity()));
        textViewUserNameProfile.setText(Prefs.getStringPrefs(AppConstants.employee_name));
        editTextPhoneProfile.setText(Prefs.getStringPrefs(AppConstants.phone));
        textViewEmailProfile.setText(Prefs.getStringPrefs(AppConstants.email));
        if (Utility.validateString(Prefs.getStringPrefs(AppConstants.picture)))
            Glide.with(((Activity) mContext)).load(Prefs.getStringPrefs(AppConstants.picture)).into(circleImageViewProfile);
        else {
            circleImageViewProfile.setImageResource(R.drawable.ic_grey_user);
        }


    }

    private void showImageOptions() {
        String title = Utility.getStringRes(R.string.dialog_image_title);
        new BottomSheet.Builder(mActivity)
                .title(Html.fromHtml("<font color=#4A4E55>" + title + "</font>"))
                .sheet(R.menu.image_options)
                .listener(new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case R.id.image_camera:
                                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                                startActivityForResult(cameraIntent, CAMERA_REQUEST);
                                break;
                            case R.id.image_gallery:
                                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                                startActivityForResult(i, GALLERY_REQUEST);
                                break;
                        }
                    }
                }).show();
    }

    private void initialization() {
        mContext = getActivity();
        mActivity = (BaseActivity) mContext;
        responseBeans = new ArrayList<>();
        textViewUserNameProfile = view.findViewById(R.id.textViewUserNameProfile);
        textViewPhoneNumberProfile = view.findViewById(R.id.textViewPhoneNumberProfile);
        textViewEmailProfile = view.findViewById(R.id.textViewEmailProfile);
        textViewRolesProfile = view.findViewById(R.id.textViewRolesProfile);
        textViewLob = view.findViewById(R.id.textViewLob);
        circleImageViewProfile = view.findViewById(R.id.profile_image);
        textViewTLerritor = view.findViewById(R.id.textViewTLerritory);
        editTextPhoneProfile = view.findViewById(R.id.editTextPhoneProfile);
        editTextRolesProfile = view.findViewById(R.id.editTextRolesProfile);
        editTextLob = view.findViewById(R.id.editTextLob);
        editTextTerritory = view.findViewById(R.id.editTextTerritory);


    }

    private void setupDetail() {
        Realm realm = Realm.getDefaultInstance();
        try {

            RealmUser realmUser = realm.where(RealmUser.class).findFirst();
            if (realmUser != null) {
                JSONArray jsonArray = new JSONArray(realmUser.getLob());
                StringBuilder stringBuilder = new StringBuilder();
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject c = jsonArray.getJSONObject(i);
                    stringBuilder.append(c.optString("lob_name"));
                    if (i != jsonArray.length() - 1)
                        stringBuilder.append(", ");
                }
                editTextLob.setText(new StringBuilder().append(stringBuilder.toString().substring(0, 1).toUpperCase()).append(stringBuilder.toString().substring(1).toLowerCase()).toString());
                ;

                JSONArray jsonArray1 = new JSONArray(realmUser.getTerritory());
                StringBuilder stringBuilder1 = new StringBuilder();
                for (int i = 0; i < jsonArray1.length(); i++) {
                    JSONObject c = jsonArray1.getJSONObject(i);
                    stringBuilder1.append(c.optString("territory_name"));
                    if (i != jsonArray1.length() - 1)
                        stringBuilder1.append(", ");
                }
                editTextTerritory.setText(new StringBuilder().append(stringBuilder1.toString().substring(0, 1).toUpperCase()).append(stringBuilder1.toString().substring(1).toLowerCase()).toString());

            }

        } catch (Exception e0) {
            e0.printStackTrace();
            realm.close();
        } finally {
            if (!realm.isClosed()) {
                realm.close();
            }

        }
    }

    public void imageUpload(String path) {
//        ((BaseActivity) getActivity()).showProgress();
        ProfileRequest profileRequest = new ProfileRequest();
        profileRequest.setUserId(Prefs.getStringPrefs(AppConstants.UserId));
        profileRequest.setIsPicture(1);
        profileRequest.setPhoto(path);
        ImageUploadService profileService = new ImageUploadService();
        profileService.executeService(profileRequest, new BaseApiCallback<ProfileResponse>() {
            @Override
            public void onComplete() {
//                ((BaseActivity) getActivity()).dismissProgress();
            }

            @Override
            public void onSuccess(@NonNull ProfileResponse response) {
                super.onSuccess(response);
                mImageId = response.getResponse().get_id();
                Glide.with(mActivity)
                        .load(response.getResponse().getPicture())
                        .into(circleImageViewProfile);
                Prefs.putStringPrefs(AppConstants.picture, response.getResponse().getPicture());
                Prefs.putBooleanPrefs(AppConstants.PROFILE_CHECK, true);
            }

            @Override
            public void onFailure(APIException e) {
                super.onFailure(e);
            }
        });

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent result) {
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            Bitmap photo = (Bitmap) result.getExtras().get("data");
            Uri tempUri = getImageUri(getActivity(), photo);
            beginCrop(tempUri);
        } else if (requestCode == GALLERY_REQUEST && resultCode == RESULT_OK) {
            beginCrop(result.getData());
        } else if (requestCode == Crop.REQUEST_CROP) {
            handleCrop(resultCode, result);
        }
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    private void beginCrop(Uri source) {
        Uri destination = Uri.fromFile(new File(getActivity().getCacheDir(), "cropped"));
        Crop.of(source, destination).asSquare().start(getActivity());
    }

    private void handleCrop(int resultCode, Intent result) {
        if (resultCode == RESULT_OK) {
            mImageUrl = Crop.getOutput(result).getPath();
            if (Crop.getOutput(result).getPath() != null) {
                File file = new File(Crop.getOutput(result).getPath());
                Glide.with(mActivity)
                        .load(file)
                        .into(circleImageViewProfile);
                isUpdateImage = true;
                imageUpload(mImageUrl);
            }
        } else if (resultCode == Crop.RESULT_ERROR) {
            Toast.makeText(getActivity(), Crop.getError(result).getMessage(), Toast.LENGTH_SHORT).show();
        }
    }


}
