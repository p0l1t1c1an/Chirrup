package cs309.sr2.chirrupfrontend.settings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

import cs309.sr2.chirrupfrontend.R;
import cs309.sr2.chirrupfrontend.utils.AppController;

/**
 * This class is the page where a user can see and update their profile data.
 *
 * @author wszogg
 */
public class SettingsFragment extends Fragment {

    //Textbox where users input a new username.
    private TextView username;
    //Textbox where users input a new first name.
    private TextView firstname;
    //Textbox where users input a new last name.
    private TextView lastname;
    //Textbox where users input a new bio.
    private TextView bio;
    //Textbox to show update status.
    private TextView testView;
    //Textbox to show some settings.
    private TextView jacobText1;
    //Textbox to show some settings.
    private TextView jacobText2;

    /**
     * This is the method that runs when opening the page. The parameters are given to it by program that calls it.
     *
     * @param inflater Turns the corresponding XML file into a layout.
     * @param container A group of views.
     * @param savedInstanceState A map of string keys.
     * @return New view for this fragment.
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_settings, container, false);

        //set text fields to current user data
        getUser(CurrentUserData.currUser.getID());

        root.findViewById(R.id.sendButton).setOnClickListener((v) -> {
            try {
                readyUserToUpdate();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });

        root.findViewById(R.id.cancelButton).setOnClickListener((v) -> {
            clearUserText();
        });

        return root;
    }

    /**
     * This method clears all text editing fields.
     */
    public void clearUserText() {
        username.setText("");
        firstname.setText("");
        lastname.setText("");
        bio.setText("");
    }

    /**
     * This method grabs all user data and sends it to the server (calls sendUser()), and clears the text fields.
     *
     * @throws JSONException This method creates a json object to send to the server and so it may throw a JSONException.
     */
    public void readyUserToUpdate() throws JSONException {
        //create Json to send to the server
        JSONObject toSend = new JSONObject();

        toSend.put("email", CurrentUserData.currUser.getEmail());
        toSend.put("username", username.getText().toString());
        toSend.put("firstname", firstname.getText().toString());
        toSend.put("lastname", lastname.getText().toString());
        toSend.put("password", CurrentUserData.currUser.getPassword());
        toSend.put("telephone", CurrentUserData.currUser.getTelephone());
        toSend.put("role", CurrentUserData.currUser.getRole());
        toSend.put("birthday", CurrentUserData.currUser.getBirthday());
        toSend.put("biography", bio.getText().toString());
        String jsonString = toSend.toString();

        //send to Server
        sendUser(jsonString, CurrentUserData.currUser.getID());

        //update user
        float currTime = System.nanoTime() / 1000000;
        while ((System.nanoTime() / 1000000) - currTime < 50) {
        }
        getUser(CurrentUserData.currUser.getID());
        username.setText("");
        firstname.setText("");
        lastname.setText("");
        bio.setText("");

        //JACOB SETTINGS
        getUserSettings(9);
    }

    /**
     *This user sends a GET request to get user data for a specific user, and updates their data.
     *
     * @param userId The id of the user you want to update.
     */
    public void getUser(int userId) {
        RequestQueue requestQueue = Volley.newRequestQueue(AppController.getInstance());

        String baseUrl = getResources().getString(R.string.base_url);
        String url = baseUrl + "user/" + userId;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, response -> {},
                error -> {}) {
            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                String responseString = "";
                if (response != null) {
                    String responseBody = new String(response.data, StandardCharsets.UTF_8);
                    try {
                        //set fields
                        username = (TextView) getView().findViewById(R.id.usernameField);
                        firstname = (TextView) getView().findViewById(R.id.firstNameField);
                        lastname = (TextView) getView().findViewById(R.id.lastNameField);
                        bio = (TextView) getView().findViewById(R.id.bioField);
                        testView = (TextView) getView().findViewById(R.id.testView);

                        //parse JSON
                        JSONObject res = new JSONObject(responseBody);
                        username.setHint(res.getString("username"));
                        firstname.setHint(res.getString("firstname"));
                        lastname.setHint(res.getString("lastname"));
                        bio.setHint(res.getString("biography"));

                        //set user fields
                        CurrentUserData.currUser.setUserName(res.getString("username"));
                        CurrentUserData.currUser.setFirstName(res.getString("firstname"));
                        CurrentUserData.currUser.setLastName(res.getString("lastname"));
                        CurrentUserData.currUser.setBirthday(res.getString("birthday"));
                        CurrentUserData.currUser.setEmail(res.getString("email"));
                        CurrentUserData.currUser.setPassword(res.getString("password"));
                        CurrentUserData.currUser.setID(res.getInt("id"));
                        CurrentUserData.currUser.setRole(res.get("role").toString());
                        CurrentUserData.currUser.setBio(res.getString("biography"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
            }
        };

        requestQueue.add(stringRequest);
    }

    /**
     * This method sends a user to the server via a PATCH request. It will print whether or not it was successful in a textView box.
     *
     * @param user JSON String representing user info to send.
     * @param userID ID of the user you are sending.
     */
    public void sendUser(String user, int userID) {
        //for debugging
        RequestQueue requestQueue = Volley.newRequestQueue(AppController.getInstance());
        String baseUrl = getResources().getString(R.string.base_url);
        String url = baseUrl + "user/" + userID;
        final String requestBody = user;

        StringRequest stringRequest = new StringRequest(Request.Method.PATCH, url, response -> {},
                error -> {}) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() {
                try {
                    return requestBody == null ? null : requestBody.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    return null;
                }
            }

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                String responseString = "";
                if (response != null) {
                    String responseBody = new String(response.data, StandardCharsets.UTF_8);
                    testView.setText("Upload Successful");
                }
                return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
            }
        };

        requestQueue.add(stringRequest);
    }

    /**
     * Gets settings for a specific user via a GET request.
     *
     * @param userId The id of the user you want to get settings for.
     */
    public void getUserSettings(int userId) {
        RequestQueue requestQueue = Volley.newRequestQueue(AppController.getInstance());

        String baseUrl = getResources().getString(R.string.base_url);
        String url = baseUrl + "settings/" + userId;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, response -> {},
                error -> {}) {
            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                String responseString = "";
                if (response != null) {
                    String responseBody = new String(response.data, StandardCharsets.UTF_8);
                    try {
                        JSONObject userData = new JSONObject(responseBody);
                        jacobText1 = (TextView) getView().findViewById(R.id.jacobText1);
                        jacobText2 = (TextView) getView().findViewById(R.id.jacobText2);
                        jacobText1.setText("Dark Mode: " + userData.get("darkMode").toString());
                        jacobText2.setText("Text Size: " + userData.get("textSize").toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
            }
        };

        requestQueue.add(stringRequest);
    }
}