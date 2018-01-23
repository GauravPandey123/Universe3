package in.editsoft.api.response;

import com.google.gson.annotations.SerializedName;

/**
 * APIResponse
 */
public abstract class APIResponse<T extends IResponse<T>> implements IResponse<T> {

    @SerializedName("_v")
    protected int version = 0;

    @Override
    public int getVersion() {
        return this.version;
    }

    @Override
    public void update(T response) {
    }

    @Override
    public boolean isValid(String condition, boolean isStrict) {
        return false;
    }

    @Override
    public boolean isValid(String condition, boolean isStrict, boolean partial) {
        return false;
    }
}
