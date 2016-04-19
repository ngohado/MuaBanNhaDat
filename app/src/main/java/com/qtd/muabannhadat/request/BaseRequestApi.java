package com.qtd.muabannhadat.request;

import android.content.Context;
import android.os.AsyncTask;

import com.qtd.muabannhadat.callback.ResultRequestCallback;
import com.qtd.muabannhadat.constant.ApiConstant;
import com.qtd.muabannhadat.util.DebugLog;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.net.UnknownHostException;

/**
 * Created by Ngo Hado on 13-Apr-16.
 */
public class BaseRequestApi {
    protected ResultRequestCallback callback;
    protected Context context;
    protected AsyncTask<Void, Void, String> requestAsync;

    public BaseRequestApi(Context context, final String dataRequest, final String methodName, final ResultRequestCallback callback) {
        this.context = context;
        this.callback = callback;
        initAsyncTask(dataRequest, methodName);
    }

    public void initAsyncTask(final String dataRequest,final String methodName) {
        requestAsync = new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                try {
                    String soapAction = ApiConstant.NAME_SPACE + methodName;
                    SoapObject request = new SoapObject(ApiConstant.NAME_SPACE, methodName);
                    DebugLog.i("json: " + dataRequest);
                    request.addProperty("json", dataRequest);
                    SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                    envelope.dotNet = true;
                    envelope.setOutputSoapObject(request);
                    HttpTransportSE transport = new HttpTransportSE(ApiConstant.MAIN_URL);
                    transport.call(soapAction, envelope);
                    SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
                    return response.toString();
                } catch (UnknownHostException u) {
                    return null;
                } catch (Exception e) {
                    DebugLog.d(e);
                    return null;
                }
            }

            @Override
            protected void onPostExecute(String s) {
                if (s == null || s.equalsIgnoreCase("null")) {
                    callback.onFailed("response is null");
                    return;
                }
                DebugLog.i("result: " + s);
                callback.onSuccess(s);
            }
        };
    }

    public void close() {
        if (!requestAsync.isCancelled()) {
            requestAsync.cancel(true);
        }
    }

    public void executeRequest() {
        if (requestAsync == null) {
            callback.onFailed("request is null");
            return;
        }
        requestAsync.execute();
    }
}
