package cs309.sr2.chirrupfrontend.volley;

import com.android.volley.toolbox.ImageLoader;

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
    void onStringResponse(String response);

    /**
     * Runs when a json object response is received
     *
     * @param response response from request
     */
    void onObjectResponse(JSONObject response);

    /**
     * Runs when an image response is received
     *
     * @param response response from request
     */
    void onImageResponse(ImageLoader.ImageContainer response);

}
