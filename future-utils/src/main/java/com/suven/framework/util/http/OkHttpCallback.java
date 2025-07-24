package com.suven.framework.util.http;

import com.suven.framework.util.json.JsonUtils;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by joven on 2017/9/9.
 */
public class OkHttpCallback<T> implements Callback {

    private Class<T> clazz;
    private T data;

    public OkHttpCallback(Class<T> clazz){
        this.clazz = clazz;
    }

    public T get(){
        return data;
    }

    @Override
    public void onFailure(Call call, IOException e) {

    }

    @Override
    public void onResponse(Call call, Response response) {
        InputStream is = null;
        byte[] buf = new byte[2048];
        int len = 0;
        ByteArrayOutputStream out = null;
        try {
            if(response.isSuccessful()){
                is = response.body().byteStream();
                out = new ByteArrayOutputStream();
                while((len = is.read(buf)) != -1){
                    out.write(buf,0,len);
                }
                out.flush();
                byte[] bytes = out.toByteArray();
                if(String.class == clazz ){
                    data = (T) new String(bytes);
                    return;
                }
                data = JsonUtils.parse(bytes,clazz);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if (is != null) is.close();
            } catch (IOException e) {  }
            try {
                if (out != null) out.close();
            } catch (IOException e)  { }
        }
    }
}
