package com.example.profilesettings;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

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
import org.w3c.dom.Text;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.logging.Logger;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //set text fields to current user data
        TextView username = findViewById(R.id.usernameField);
        TextView firstname = findViewById(R.id.firstNameField);
        TextView lastname = findViewById(R.id.lastNameField);
        TextView bio = findViewById(R.id.bioField);
        username.setHint(currentUserData.currUser.getUserName());
        firstname.setHint(currentUserData.currUser.getFirstName());
        lastname.setHint(currentUserData.currUser.getLastName());
        bio.setHint(currentUserData.currUser.getBio());
    }

    public void clearUserText(View view) {
        TextView username = findViewById(R.id.usernameField);
        TextView firstname = findViewById(R.id.firstNameField);
        TextView lastname = findViewById(R.id.lastNameField);
        TextView bio = findViewById(R.id.bioField);
        username.setText("");
        firstname.setText("");
        lastname.setText("");
        bio.setText("");
    }

    public void readyUserToUpdate(View view) throws JSONException {
        //create Json to send to the server
        JSONObject toSend = new JSONObject();

        TextView username = findViewById(R.id.usernameField);
        TextView firstname = findViewById(R.id.firstNameField);
        TextView lastname = findViewById(R.id.lastNameField);
        TextView bio = findViewById(R.id.bioField);

        toSend.put("email", currentUserData.currUser.getEmail());
        toSend.put("username", username.getText().toString());
        toSend.put("firstname", firstname.getText().toString());
        toSend.put("lastname", lastname.getText().toString());
        toSend.put("password", currentUserData.currUser.getPassword());
        toSend.put("telephone", currentUserData.currUser.getTelephone());
        toSend.put("role", currentUserData.currUser.getRole());
        toSend.put("birthday", currentUserData.currUser.getBirthday());
        String jsonString = toSend.toString();

        //send to Server
        final TextView testView = (TextView) findViewById(R.id.testView);
        sendUser(jsonString, currentUserData.currUser.getID());

        //update user
        float currTime = System.nanoTime() / 1000000;
        while ((System.nanoTime() / 1000000) - currTime < 50) {}
        getUser(currentUserData.currUser.getID());
        username.setText("");
        firstname.setText("");
        lastname.setText("");
        bio.setText("");
    }

    public void getUser(int userId) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        String baseUrl = getResources().getString(R.string.base_url);
        String url = baseUrl + "/user/" + userId;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }) {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                String responseString = "";
                if (response != null) {
                    String responseBody = new String(response.data, StandardCharsets.UTF_8);
                    TextView view = findViewById(R.id.testView);
                    try {
                        TextView username = findViewById(R.id.usernameField);
                        TextView firstname = findViewById(R.id.firstNameField);
                        TextView lastname = findViewById(R.id.lastNameField);
                        TextView bio = findViewById(R.id.bioField);
                        JSONObject res = new JSONObject(responseBody);
                        username.setHint(res.getString("username"));
                        firstname.setHint(res.getString("firstname"));
                        lastname.setHint(res.getString("lastname"));
                        bio.setHint(res.getString("bio"));

                        currentUserData.currUser.setUserName(res.getString("username"));
                        currentUserData.currUser.setFirstName(res.getString("firstname"));
                        currentUserData.currUser.setLastName(res.getString("lastname"));
                        currentUserData.currUser.setBirthday(res.getString("birthday"));
                        currentUserData.currUser.setEmail(res.getString("email"));
                        currentUserData.currUser.setPassword(res.getString("password"));
                        currentUserData.currUser.setID(res.getInt("id"));
                        currentUserData.currUser.setRole(res.get("role").toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
            }
        };

        requestQueue.add(stringRequest);
    }

    /*
    public void createNewUser(View view) {

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        final TextView textView = (TextView) findViewById(R.id.textResponse);
        String firstName = ((EditText) findViewById(R.id.editTextFirstName)).getText().toString();
        String lastName = ((EditText) findViewById(R.id.editTextLastName)).getText().toString();
        String address = ((EditText) findViewById(R.id.editTextAddress)).getText().toString();
        String phone = ((EditText) findViewById(R.id.editTextPhone)).getText().toString();

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("username", "ya");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        final String requestBody = jsonBody.toString();

        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="http://coms-309-016.cs.iastate.edu:8080/api/user/6";

        StringRequest stringRequest = new StringRequest(Request.Method.PATCH, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return requestBody == null ? null : requestBody.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    return null;
                }
            }

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                String responseString = "";
                if (response != null) {
                    String responseBody = new String(response.data, StandardCharsets.UTF_8);
                    textView.setText(responseBody);
                }
                return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
            }
        };

        requestQueue.add(stringRequest);
    }
*/
    public void sendUser(String user, int userID) {
        //for debugging
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String baseUrl = getResources().getString(R.string.base_url);
        String url = baseUrl + "/user/" + userID;
        final String requestBody = user;
        final TextView textView = (TextView) findViewById(R.id.testView);
        
        StringRequest stringRequest = new StringRequest(Request.Method.PATCH, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return requestBody == null ? null : requestBody.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    return null;
                }
            }

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                String responseString = "";
                if (response != null) {
                    String responseBody = new String(response.data, StandardCharsets.UTF_8);
                    textView.setText("Upload Successful");
                }
                return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
            }
//        StringRequest stringRequest = new StringRequest(Request.Method.PATCH, url, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//            }
//        }) {
//            @Override
//            public String getBodyContentType() {
//                return "application/json; charset=utf-8";
//            }
//
//            @Override
//            public byte[] getBody() throws AuthFailureError {
//                try {
//                    return requestBody == null ? null : requestBody.getBytes("utf-8");
//                } catch (UnsupportedEncodingException uee) {
//                    return null;
//                }
//            }
//
//            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
//            @Override
//            protected Response<String> parseNetworkResponse(NetworkResponse response) {
//                String responseString = "";
//                if (response != null) {
//                    //Returns the id of the user i am changing
//                    String responseBody = new String(response.data, StandardCharsets.UTF_8);
//                    testView.setText(responseBody);
//                    //for debugging//sends if it worked or not
//                }
//                return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
//            }
        };

        requestQueue.add(stringRequest);
    }
}