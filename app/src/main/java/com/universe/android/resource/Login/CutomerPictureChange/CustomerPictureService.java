package com.universe.android.resource.Login.CutomerPictureChange;

import com.universe.android.web.BaseService;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;

/**
 * Created by gaurav.pandey on 06-03-2018.
 */

public class CustomerPictureService extends BaseService<CustomerPictureService.customerPicture, CustomerPictureRequest, CustomerPictureResponse> {


    @Override
    public Call<CustomerPictureResponse> onExecute(customerPicture api, CustomerPictureRequest request) {
        File file = new File(request.getPhoto());
        MultipartBody.Part filePart = MultipartBody.Part.createFormData("photo", file.getName(), RequestBody.create(MediaType.parse("image/*"), file));
        RequestBody customerId = toRequestBody(request.getCustomerId());
        RequestBody pictureId = toRequestBody(String.valueOf(request.getIsPicture()));
        Map<String, RequestBody> map = new HashMap<>();
        map.put("customerId", customerId);
        map.put("isPicture", pictureId);
        return api.customerPictureResponseCall(map, filePart);

    }

    @Override
    public Class<customerPicture> getAPI() {
        return customerPicture.class;
    }

    public interface customerPicture {
        @Multipart
        @POST("api/customerProfile")
        Call<CustomerPictureResponse> customerPictureResponseCall(@PartMap Map<String, RequestBody> data, @Part MultipartBody.Part image);
    }

    public static RequestBody toRequestBody(String value) {
        RequestBody body = RequestBody.create(MediaType.parse("text/plain"), value);
        return body;
    }
}
