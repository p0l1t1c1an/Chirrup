package cs309.sr2.chirrupfrontend.settings;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

import cs309.sr2.chirrupfrontend.R;
import cs309.sr2.chirrupfrontend.AppController;
import cs309.sr2.chirrupfrontend.volley.VolleyListener;
import cs309.sr2.chirrupfrontend.volley.VolleyRequester;

/**
 * This class is the page where a user can see and update their profile data.
 *
 * @author wszogg
 */
public class SettingsFragment extends Fragment implements VolleyListener {

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
    //VolleyRequester
    private VolleyRequester VolleyRequester;
    //Current theme settings.
    private boolean darktheme;
    //Current text size.
    private int textSize;
    //Current updateTime.
    private int updateTime;
    //response string volley gives
    private String response;

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
//        Switch themeSwitch = (Switch) root.findViewById(R.id.themeSwitch);
//        Switch textSwitch = (Switch) root.findViewById(R.id.textSizeSwitch);
//        Switch updateSwitch = (Switch) root.findViewById(R.id.updateSwitch);
        username = (TextView) root.findViewById(R.id.usernameField);
        firstname = (TextView) root.findViewById(R.id.firstNameField);
        lastname = (TextView) root.findViewById(R.id.lastNameField);
        bio = (TextView) root.findViewById(R.id.bioField);
        testView = (TextView) root.findViewById(R.id.testView);
        VolleyRequester = new VolleyRequester(this);

        //set text fields to current user data
        VolleyRequester.getString(getResources().getString(R.string.base_url) + "user/" + CurrentUserData.currUser.getID());

        //get settings
        VolleyRequester.getString(getResources().getString(R.string.base_url) + "settings/" +
                CurrentUserData.currUser.getID());
        //create a JSON object to parse settings from
//        try {
//            JSONObject toParse = new JSONObject(response);
//            darktheme = toParse.getBoolean("darkMode");
//            textSize = toParse.getInt("textSize");
//            updateTime = toParse.getInt("updateTime");
//
//            //set Fields
//            if (darktheme == true) {
//                themeSwitch.setText("Dark Mode");
//            }
//            else {
//                themeSwitch.setText("Light Mode");
//            }
//            if (textSize == 12) {
//                textSwitch.setText("Small Text");
//            }
//            else {
//                textSwitch.setText("Big Text");
//            }
//            if (updateTime == 5) {
//                updateSwitch.setText("Short Wait");
//            }
//            else {
//                updateSwitch.setText("Long Wait");
//            }
//        } catch(Exception e) {}

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

//        themeSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
//            //get settings
//            VolleyRequester.getString(getResources().getString(R.string.base_url) + "settings/" +
//                    CurrentUserData.currUser.getID());
//            //create a JSON object to parse settings from
//            try {
//                JSONObject toParse = new JSONObject(response);
//                darktheme = toParse.getBoolean("darkMode");
//            } catch(Exception e) {}
//
//            if (buttonView.isChecked()) {
//                if (darktheme == true) {
//                    //dark theme is already enabled and needs to be disabled
//                    darktheme = false;
//                    themeSwitch.setText("Light Mode");
//                }
//                else {
//                    //dark theme needs to be enabled
//                    darktheme = true;
//                    themeSwitch.setText("Dark Mode");
//                }
//            }
//            else {
//                if (darktheme == true) {
//                    //dark theme is already enabled and needs to be disabled
//                    darktheme = false;
//                    themeSwitch.setText("Light Mode");
//                }
//                else {
//                    //dark theme needs to be enabled
//                    darktheme = true;
//                    themeSwitch.setText("Dark Mode");
//                }
//            }
//        });
//
//        textSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
//            //get settings
//            VolleyRequester.getString(getResources().getString(R.string.base_url) + "settings/" +
//                    CurrentUserData.currUser.getID());
//            //create a JSON object to parse settings from
//            try {
//                JSONObject toParse = new JSONObject(response);
//                textSize = toParse.getInt("textSize");
//            } catch(Exception e) {}
//            if (buttonView.isChecked()) {
//                if (textSize == 12) {
//                    //text size is 12 right now and needs to be increased to 15
//                    textSize = 15;
//                    textSwitch.setText("Big Text");
//                }
//                else {
//                    //text size is not 12 and needs to become 12
//                    textSize = 12;
//                    textSwitch.setText("Small Text");
//                }
//            }
//            else {
//                if (textSize == 12) {
//                    //text size is 12 right now and needs to be increased to 15
//                    textSize = 15;
//                    textSwitch.setText("Big Text");
//                }
//                else {
//                    //text size is not 12 and needs to become 12
//                    textSize = 12;
//                    textSwitch.setText("Small Text");
//                }
//            }
//        });
//
//        updateSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
//            try {
//                JSONObject toParse = new JSONObject(response);
//                updateTime = toParse.getInt("updateTime");
//            } catch(Exception e) {}
//            if (buttonView.isChecked()) {
//                if (updateTime == 5) {
//                    //update time is 5 right now and should be increased to 8?
//                    updateTime = 8;
//                    updateSwitch.setText("Long Wait");
//                }
//                else {
//                    //update time is not 5 and should become 5
//                    updateTime = 5;
//                    updateSwitch.setText("Short Wait");
//                }
//            }
//            else {
//                if (updateTime == 5) {
//                    //update time is 5 right now and should be increased to 8?
//                    updateTime = 8;
//                    updateSwitch.setText("Long Wait");
//                }
//                else {
//                    //update time is not 5 and should become 5
//                    updateTime = 5;
//                    updateSwitch.setText("Short Wait");
//                }
//            }
//        });

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
//        //create Json to send to the server
//        JSONObject toSend = new JSONObject();
//
//        toSend.put("email", CurrentUserData.currUser.getEmail());
//        toSend.put("username", username.getText().toString());
//        toSend.put("firstname", firstname.getText().toString());
//        toSend.put("lastname", lastname.getText().toString());
//        toSend.put("password", CurrentUserData.currUser.getPassword());
//        toSend.put("telephone", CurrentUserData.currUser.getTelephone());
//        toSend.put("role", CurrentUserData.currUser.getRole());
//        toSend.put("birthday", CurrentUserData.currUser.getBirthday());
//        toSend.put("biography", bio.getText().toString());
//        String jsonString = toSend.toString();
//
//        //send to Server
//        sendUser(jsonString, CurrentUserData.currUser.getID());
//
//        //update user
//        float currTime = System.nanoTime() / 1000000;
//        while ((System.nanoTime() / 1000000) - currTime < 100) {
//        }
//        getUser(CurrentUserData.currUser.getID());
//        username.setText("");
//        firstname.setText("");
//        lastname.setText("");
//        bio.setText("");
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
        VolleyRequester.setObject(getResources().getString(R.string.base_url) + "user/" + CurrentUserData.currUser.getID(), toSend, Request.Method.PUT);

        //update current user screen
        //wait 100 milliseconds
        long currTime = System.currentTimeMillis();
        while (currTime + 100 > System.currentTimeMillis()) {}
        VolleyRequester.getString(getResources().getString(R.string.base_url) + "user/" + CurrentUserData.currUser.getID());
        username.setText("");
        firstname.setText("");
        lastname.setText("");
        bio.setText("");
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
                        String newUserName = res.getString("username");
                        String newFirstName = res.getString("firstname");

                        username.setHint(newUserName);
                        firstname.setHint(newFirstName);
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
                        //Insert User settings logic here
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
     * Dictates what the class should do upon receiving a string.
     *
     * @param response response from request
     */
    @Override
    public void onStringResponse(String response) {
        this.response = response;
        try {
            JSONObject res = new JSONObject(response);

            //delay for screen
            long currTime = System.currentTimeMillis();
            while (currTime + 50 > System.currentTimeMillis()) {}

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
        } catch(Exception e) {}
    }
}