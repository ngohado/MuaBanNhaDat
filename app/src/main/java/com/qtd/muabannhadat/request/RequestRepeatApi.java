package com.qtd.muabannhadat.request;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;

import com.qtd.muabannhadat.callback.ResultRequestCallback;
import com.qtd.muabannhadat.constant.ApiConstant;
import com.qtd.muabannhadat.util.DebugLog;
import com.qtd.muabannhadat.util.Utility;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpResponseException;
import org.ksoap2.transport.HttpTransportSE;

import java.net.ConnectException;
import java.net.UnknownHostException;

/**
 * Created by Dell on 4/15/2016.
 */
public class RequestRepeatApi extends BaseRequestApi {
    private View view;

    public RequestRepeatApi(Context context, String dataRequest, String methodName, ResultRequestCallback callback, View view) {
        super(context, dataRequest, methodName, callback);
        this.view = view;
    }

    @Override
    public void initAsyncTask(final String dataRequest, final String methodName) {
        requestAsync = new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                try {
                    String soapAction = ApiConstant.NAME_SPACE + methodName;
                    SoapObject soapObject = new SoapObject(ApiConstant.NAME_SPACE, methodName);
                    DebugLog.i(dataRequest);
                    soapObject.addProperty("json", dataRequest);
                    SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                    envelope.dotNet = true;
                    envelope.setOutputSoapObject(soapObject);
                    HttpTransportSE transportSE = new HttpTransportSE(ApiConstant.MAIN_URL);
                    while (true) {
                        if (!Utility.isNetworkAvailable(context, view, false)) {
                            Thread.sleep(1000);
                        } else {
                            try {
                                transportSE.call(soapAction, envelope);
                                break;
                            } catch (UnknownHostException unknown) {
                                Thread.sleep(1000);
                            } catch (ConnectException connectEx) {
                                DebugLog.d(connectEx);
                                Thread.sleep(1000);
                            } catch (HttpResponseException httpE) {
                                DebugLog.d(httpE);
                                Thread.sleep(1000);
                            }
                        }
                    }
                    SoapPrimitive result = (SoapPrimitive) envelope.getResponse();
                    return result.toString();
                } catch (Exception ex) {
                    return "";
                }
            }

            @Override
            protected void onPostExecute(String s) {
                if (s == "") {
                    callback.onFailed("Error");
                    return;
                }
                DebugLog.i(s);
                callback.onSuccess(s);
            }
        };
    }
}
