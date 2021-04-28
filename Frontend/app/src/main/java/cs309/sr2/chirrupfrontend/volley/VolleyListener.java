package cs309.sr2.chirrupfrontend.volley;

import android.graphics.Bitmap;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * volley listener for responses
 *
 * @author Jeremy Noesen
 */
public interface VolleyListener {

    /**
     * Runs when a string response is received
     *
     * @param response response from request
     */
    default void onStringResponse(String response) {}

    /**
     * Runs when a json object response is received
     *
     * @param response response from request
     */
    default void onObjectResponse(JSONObject response) {}

    /**
     * Runs when an image response is received
     *
     * @param response response from request
     */
    default void onImageResponse(Bitmap response) {}

    /**
     * Runs when an array response is received
     *
     * @param response response from request
     */
    default void onArrayResponse(JSONArray response) {}

}
