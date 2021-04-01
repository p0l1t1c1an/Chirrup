package cs309.sr2.chirrupfrontend.utils;

import android.graphics.Bitmap;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

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
     * json object returned with getObject
     */
    private JSONObject jsonObject;

    /**
     * get a bitmap image from the database
     */
    private Bitmap getBitmap(String url) {
        bitmap = null;

        ImageLoader imageLoader = AppController.getInstance().getImageLoader();
        imageLoader.get(url, new ImageLoader.ImageListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(ProfileFragment.class.getSimpleName(), "Image Load Error: "
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
     * get a json object from the database
     */
    private JSONObject getObject(String url) {
        jsonObject = null;

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET, url,
                null, response -> jsonObject = response, error ->
                VolleyLog.d(ProfileFragment.class.getSimpleName(), "Error: " + error.getMessage()));

        AppController.getInstance().addToRequestQueue(jsonObjReq);

        return jsonObject;
    }

}
