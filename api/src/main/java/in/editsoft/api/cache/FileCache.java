package in.editsoft.api.cache;

import android.content.Context;

import com.google.gson.Gson;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import in.editsoft.api.cache.type.DataType;
import in.editsoft.api.response.IResponse;

public class FileCache implements DataCache {

    private static FileCache fileCache;
    private final Gson gson = new Gson();

    @Deprecated
    public static FileCache getInstanse() {
        return FileCache.getInstance();
    }

    public static FileCache getInstance() {
        if (fileCache != null) {
            return fileCache;
        }
        return fileCache = new FileCache();
    }

    private FileCache() {
    }

    @Override
    public <T extends IResponse> void save(Context context, T response, DataType type) throws DBException {
        if (context == null) {
            throw new DBException(DBException.Kind.NULL, "Context can't be null");
        }
        if (response == null) {
            throw new DBException(DBException.Kind.NULL, "DBData can't be null");
        }
        if (type == null) {
            throw new DBException(DBException.Kind.NULL, "DB_DATA_TYPES can't be null");
        }
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = context.openFileOutput(type.getName(), Context.MODE_PRIVATE);
            fileOutputStream.write(gson.toJson(response).getBytes());
        } catch (IOException e) {
            e.printStackTrace();
            throw new DBException(DBException.Kind.NETWORK, e.getMessage());
        } finally {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public boolean remove(Context context, DataType type) throws DBException {
        if (context == null) {
            throw new DBException(DBException.Kind.NULL, "Context can't be null");
        }
        if (type == null) {
            throw new DBException(DBException.Kind.NULL, "DB_DATA_TYPES can't be null");
        }
        return context.deleteFile(type.getName());
    }

    @Override
    public <T extends IResponse> T load(Context context, DataType<T> type) throws DBException {
        if (context == null) {
            throw new DBException(DBException.Kind.NULL, "Context can't be null");
        }
        if (type == null) {
            throw new DBException(DBException.Kind.NULL, "DB_DATA_TYPES can't be null");
        }

        FileInputStream fileInputStream = null;
        try {
            fileInputStream = context.openFileInput(type.getName());
            byte[] buffer = new byte[fileInputStream.available()];
            int read = fileInputStream.read(buffer);
            if (read != -1) {
                String json = new String(buffer);
                return gson.fromJson(json, type.getType());
            } else {
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new DBException(DBException.Kind.NETWORK, e.getMessage());
        } catch (ClassCastException e) {
            e.printStackTrace();
            throw new DBException(DBException.Kind.CLASS_CAST_EXCEPTION, e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            throw new DBException(DBException.Kind.UNEXPECTED, e.getMessage());
        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
