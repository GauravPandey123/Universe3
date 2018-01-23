package in.editsoft.api.request;


import in.editsoft.api.util.APIConstants;

/**
 * APIRequest
 */
public abstract class APIRequest implements IRequest, APIConstants {

    private int mVersion;
    private String mScenario;
    private String mAuth;

    @Override
    public int getVersion() {
        return this.mVersion;
    }

    @Override
    public void setVersion(int mVersion) {
        this.mVersion = mVersion;
    }

    @Override
    public String getScenario() {
        return mScenario;
    }

    @Override
    public void setScenario(String mScenario) {
        this.mScenario = mScenario;
    }

    @Override
    public String getId() {
        return null;
    }

    @Override
    public String getAuthToken() {
        return this.mAuth;
    }

    @Override
    public void setAuthToken(String authToken) {
        this.mAuth = authToken;
    }

}
