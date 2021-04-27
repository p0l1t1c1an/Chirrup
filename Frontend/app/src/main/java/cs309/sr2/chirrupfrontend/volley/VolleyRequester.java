package cs309.sr2.chirrupfrontend.volley;

import android.graphics.Bitmap;
import android.util.Base64;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import cs309.sr2.chirrupfrontend.AppController;

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
        ImageRequest imageRequest = new ImageRequest(url, response -> volleyListener.onImageResponse(response),
                0, 0, ImageView.ScaleType.CENTER, Bitmap.Config.RGB_565, error ->
                VolleyLog.d(VolleyRequester.class.getSimpleName(), "Image get error: "
                        + error.getMessage()));

        AppController.getInstance().addToRequestQueue(imageRequest);
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
                VolleyLog.d(VolleyRequester.class.getSimpleName(), "JSON Array get error: "
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
     * set a image in the database
     *
     * @param url    request url
     * @param image  image to set
     * @param method method to use for setting, Request.Method.
     */
    public void setImage(String url, Bitmap image, int method) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        final String imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT);

        StringRequest request = new StringRequest(method, url, null,
                error -> VolleyLog.d(VolleyRequester.class.getSimpleName(), "Image set error: "
                        + error.getMessage())) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> parameters = new HashMap<>();
                parameters.put("image", imageString);
                return parameters;
            }
        };

        AppController.getInstance().addToRequestQueue(request);
    }

    /**
     * set a string in the database
     *
     * @param url    request url
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
     * @param url       request url
     * @param jsonArray json array to set
     * @param method    method to use for setting, Request.Method.
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
     * @param url        request url
     * @param jsonObject json object to set
     * @param method     method to use for setting, Request.Method.
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
