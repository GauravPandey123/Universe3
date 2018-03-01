package com.universe.android.resource.Login.CrystalReport;

import com.universe.android.web.BaseService;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by gaurav.pandey on 22-02-2018.
 */

public class CrystalReportService extends BaseService<CrystalReportService.crystalService, CrystalReportRequest, CrystalReportResponse> {


    @Override
    public Call<CrystalReportResponse> onExecute(crystalService api, CrystalReportRequest request) {
        return api.crystalResponseCall(request);
    }

    @Override
    public Class<crystalService> getAPI() {
        return crystalService.class;
    }

    public interface crystalService {
        @POST("/api/cdReport")
        Call<CrystalReportResponse> crystalResponseCall(@Body CrystalReportRequest crystalReportRequest);
    }


}
