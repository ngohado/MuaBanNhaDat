package com.qtd.muabannhadat.request;

import android.content.Context;
import android.os.AsyncTask;

import com.qtd.muabannhadat.callback.ResultRequestCallback;
import com.qtd.muabannhadat.constant.ApiConstant;
import com.qtd.muabannhadat.model.Apartment;

import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.net.UnknownHostException;

/**
 * Created by Ngo Hado on 13-Apr-16.
 */
public class GetApartmentInfoRequest {
    Context context;
    int idApartment;
    AsyncTask<Integer, Void, String> requestAsync;
    ResultRequestCallback<Apartment> callback;

    public GetApartmentInfoRequest(Context context, int idApartment, final ResultRequestCallback<Apartment> callback) {
        this.context = context;
        this.idApartment = idApartment;
        this.callback = callback;
        requestAsync = new AsyncTask<Integer, Void, String>() {
            @Override
            protected String doInBackground(Integer... params) {
                try {
                    String soapAction = ApiConstant.NAME_SPACE + ApiConstant.METHOD_APARTMENT;
                    SoapObject request = new SoapObject(ApiConstant.NAME_SPACE, ApiConstant.METHOD_APARTMENT);
                    String data = String.format("{\"ID\" : \"%d\"}", params[0]);
                    request.addProperty("strid", data);
                    SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                    envelope.dotNet = true;
                    envelope.setOutputSoapObject(request);
                    HttpTransportSE transport = new HttpTransportSE(ApiConstant.MAIN_URL);
                    transport.call(soapAction, envelope);
                    SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
                    return response.toString();
                } catch (UnknownHostException u) {
                    callback.onFailed("UnknownHostException");
                } catch (Exception e) {
                    callback.onFailed("Unknown Exception");
                }
                return null;
            }

            @Override
            protected void onPostExecute(String s) {
                if (s == null || s.equalsIgnoreCase("null")) {
                    callback.onFailed("response is null");
                    return;
                }
                handleResponse(s);
            }
        };
    }

    private void handleResponse(String s) {
        try {
            JSONObject object = new JSONObject(s);
            Apartment response = new Apartment();
            response.setAddress(object.getString("Address"));
            response.setId(object.getInt("A_ID"));
            response.setStatus(object.getString("Status"));
            response.setKind(object.getString("Kind"));
            response.setArea(object.getLong("Size"));
            response.setCity(object.getString("City"));
            response.setDistrict(object.getString("District"));
            response.setStreet(object.getString("Street"));
            response.setPrice(object.getInt("Price"));
            response.setDescribe(object.getString("Describe"));
            response.setNumberOfRoom(object.getInt("Room"));
            response.setLatitude(object.getLong("Latitude"));
            response.setLongitude(object.getLong("Longitude"));
            response.getUser().setId(object.getInt("UserID"));
            response.getUser().setUserName(object.getString("Username"));
            response.getUser().setName(object.getString("Name"));
            response.getUser().setDateOfBirth(object.getString("DOB"));
            response.getUser().setPhone(object.getString("Telephone"));
            callback.onSuccess(response);
        } catch (JSONException e) {
            callback.onFailed("JSONException : " + e.toString());
        }
    }

    public void executeRequest() {
        if (requestAsync == null) {
            callback.onFailed("request is null");
            return;
        }
        requestAsync.execute(idApartment);
    }
}
