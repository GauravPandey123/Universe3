package com.universe.android.resource.Login.Profile;

import com.universe.android.web.BaseService;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;

/**
 * Created by gaurav.pandey on 28-02-2018.
 */

public class ImageUploadService extends BaseService<ImageUploadService.ImageUploadApi, ProfileRequest, ProfileResponse> {


    @Override
    public Call<ProfileResponse> onExecute(ImageUploadApi api, ProfileRequest request) {
        File file = new File(request.getPhoto());
        MultipartBody.Part filePart = MultipartBody.Part.createFormData("photo", file.getName(), RequestBody.create(MediaType.parse("image/*"), file));
        RequestBody userId = toRequestBody(request.getUserId());
        RequestBody pictureId = toRequestBody(String.valueOf(request.getIsPicture()));
        Map<String, RequestBody> map = new HashMap<>();
        map.put("userId", userId);
        map.put("isPicture", pictureId);
        return api.PROFILE_RESPONSE_CALL(map, filePart);
    }

    @Override
    public Class<ImageUploadApi> getAPI() {
        return ImageUploadApi.class;
    }

    public interface ImageUploadApi {
        @Multipart
        @POST("/api/updateProfile")
        Call<ProfileResponse> PROFILE_RESPONSE_CALL(@PartMap Map<String, RequestBody> data, @Part MultipartBody.Part image);
    }

    public static RequestBody toRequestBody(String value) {
        RequestBody body = RequestBody.create(MediaType.parse("text/plain"), value);
        return body;
    }

}
