package cs309.sr2.chirrupfrontend.utils;

import android.graphics.Bitmap;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONObject;

import java.nio.charset.StandardCharsets;

import cs309.sr2.chirrupfrontend.profile.ProfileFragment;

/**
 * class to handle server calls across the app, this acts as the model class for multiple screens
 *
 * @author Jeremy Noesen
 */
public class VolleyRequester {

    /**
     * bitmap returned with getBitmap
     */
    private Bitmap bitmap;

    /**
     * string returned with getString
     */
    private String string;

    /**
     * json object returned with getObject
     */
    private JSONObject jsonObject;

    /**
     * create a new volley requester
     */
    public VolleyRequester() {
        bitmap = null;
        string = null;
        jsonObject = null;
    }

    /**
     * GET a bitmap image from the database
     *
     * @param url request url
     */
    private Bitmap getBitmap(String url) {
        bitmap = null;

        ImageLoader imageLoader = AppController.getInstance().getImageLoader();
        imageLoader.get(url, new ImageLoader.ImageListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(ProfileFragment.class.getSimpleName(), "Image get Error: "
                        + error.getMessage());
            }

            @Override
            public void onResponse(ImageLoader.ImageContainer response, boolean arg1) {
                if (response.getBitmap() != null) {
                    bitmap = response.getBitmap();
                }
            }
        });

        return bitmap;
    }

    /**
     * GET a string from the database
     *
     * @param url request url
     */
    private String getString(String url) {
        string = null;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> string = response, error ->
                VolleyLog.d(VolleyRequester.class.getSimpleName(), "String get error: "
                        + error.getMessage()));

        AppController.getInstance().addToRequestQueue(stringRequest);

        return string;
    }

    /**
     * GET a json object from the database
     *
     * @param url request url
     */
    private JSONObject getObject(String url) {
        jsonObject = null;

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET, url,
                null, response -> jsonObject = response, error ->
                VolleyLog.d(VolleyRequester.class.getSimpleName(), "JSON Object get error: "
                        + error.getMessage()));

        AppController.getInstance().addToRequestQueue(jsonObjReq);

        return jsonObject;
    }

    /**
     * set a string in the database using PATCH
     *
     * @param url request url
     * @param string string to set
     */
    public void setString(String url, String string) {
        StringRequest stringRequest = new StringRequest(Request.Method.PATCH, url,
                response -> {}, error ->
                VolleyLog.d(VolleyRequester.class.getSimpleName(), "String patch error: "
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
     * set a string in the database using PUT
     *
     * @param url request url
     * @param jsonObject json object to set
     */
    public void setObject(String url, JSONObject jsonObject) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, url,
                null, response -> {}, error ->
                VolleyLog.d(VolleyRequester.class.getSimpleName(), "JSON Object put error: "
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
