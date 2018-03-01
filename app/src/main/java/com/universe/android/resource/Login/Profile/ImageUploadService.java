package com.universe.android.resource.Login.Profile;

import com.universe.android.web.BaseService;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by gaurav.pandey on 28-02-2018.
 */

public class ImageUploadService extends BaseService<ImageUploadService.ImageUploadApi,ProfileRequest,ProfileResponse>{


    @Override
    public Call<ProfileResponse> onExecute(ImageUploadApi api, ProfileRequest request) {
        File file=new File(request.getPhoto());
        MultipartBody.Part filePart = MultipartBody.Part.createFormData("file", file.getName(), RequestBody.create(MediaType.parse("image/*"), file));
        return api.PROFILE_RESPONSE_CALL(filePart);
    }

    @Override
    public Class<ImageUploadApi> getAPI() {
        return null;
    }

    public interface ImageUploadApi
    {
        @Multipart
        @POST("/api/updateProfile")
        Call<ProfileResponse> PROFILE_RESPONSE_CALL(@Part MultipartBody.Part image);
    }
}
