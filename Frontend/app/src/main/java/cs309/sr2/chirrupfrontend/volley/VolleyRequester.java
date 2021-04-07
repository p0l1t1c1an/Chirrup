package cs309.sr2.chirrupfrontend.volley;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;

import cs309.sr2.chirrupfrontend.profile.personal.PersonalProfileFragment;
import cs309.sr2.chirrupfrontend.utils.AppController;

/**
 * class to handle server calls across the app, this acts as the model class for multiple screens
 *
 * @author Jeremy Noesen
 */
public class VolleyRequester {

    /**
     * volley listener for this requester
     */
    private VolleyListener volleyListener;

    /**
     * create a new volley requester
     */
    public VolleyRequester(VolleyListener volleyListener) {
        this.volleyListener = volleyListener;
    }

    /**
     * GET an image from the database
     *
     * @param url request url
     */
    public void getImage(String url) {
        ImageLoader imageLoader = AppController.getInstance().getImageLoader();
        imageLoader.get(url, new ImageLoader.ImageListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(PersonalProfileFragment.class.getSimpleName(), "Image get Error: "
                        + error.getMessage());
            }

            @Override
            public void onResponse(ImageLoader.ImageContainer response, boolean arg1) {
                if (response.getBitmap() != null) {
                    volleyListener.onImageResponse(response);
                }
            }
        });
    }

    /**
     * GET a string from the database
     *
     * @param url request url
     */
    public void getString(String url) {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> volleyListener.onStringResponse(response), error ->
                VolleyLog.d(VolleyRequester.class.getSimpleName(), "String get error: "
                        + error.getMessage()));

        AppController.getInstance().addToRequestQueue(stringRequest);
    }

    /**
     * GET an array from the database
     *
     * @param url request url
     */
    public void getArray(String url) {
        JsonArrayRequest jsonArrReq = new JsonArrayRequest(Request.Method.GET, url,
                null, response -> volleyListener.onArrayResponse(response), error ->
                VolleyLog.d(VolleyRequester.class.getSimpleName(), "JSON Object get error: "
                        + error.getMessage()));

        AppController.getInstance().addToRequestQueue(jsonArrReq);
    }

    /**
     * GET a json object from the database
     *
     * @param url request url
     */
    public void getObject(String url) {
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET, url,
                null, response -> volleyListener.onObjectResponse(response), error ->
                VolleyLog.d(VolleyRequester.class.getSimpleName(), "JSON Object get error: "
                        + error.getMessage()));

        AppController.getInstance().addToRequestQueue(jsonObjReq);
    }

    /**
     * set a string in the database
     *
     * @param url request url
     * @param string string to set
     * @param method method to use for setting, Request.Method.
     */
    public void setString(String url, String string, int method) {
        StringRequest stringRequest = new StringRequest(method, url,
                response -> volleyListener.onStringResponse(response), error ->
                VolleyLog.d(VolleyRequester.class.getSimpleName(), "String set error: "
                        + error.getMessage())) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() {
                return string == null ? null : string.getBytes(StandardCharsets.UTF_8);
            }
        };

        AppController.getInstance().addToRequestQueue(stringRequest);
    }

    /**
     * set an array in the database
     *
     * @param url request url
     * @param jsonArray json array to set
     * @param method method to use for setting, Request.Method.
     */
    public void setArray(String url, JSONArray jsonArray, int method) {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(method, url,
                null, response -> volleyListener.onArrayResponse(response), error ->
                VolleyLog.d(VolleyRequester.class.getSimpleName(), "JSON Array set error: "
                        + error.getMessage())) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() {
                return jsonArray == null ? null : jsonArray.toString().getBytes(StandardCharsets.UTF_8);
            }
        };

        AppController.getInstance().addToRequestQueue(jsonArrayRequest);
    }

    /**
     * set an object in the database
     *
     * @param url request url
     * @param jsonObject json object to set
     * @param method method to use for setting, Request.Method.
     */
    public void setObject(String url, JSONObject jsonObject, int method) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(method, url,
                jsonObject, response -> volleyListener.onObjectResponse(response), error ->
                VolleyLog.d(VolleyRequester.class.getSimpleName(), "JSON Object set error: "
                        + error.getMessage())) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() {
                return jsonObject == null ? null : jsonObject.toString().getBytes(StandardCharsets.UTF_8);
            }
        };

        AppController.getInstance().addToRequestQueue(jsonObjectRequest);
    }

}
