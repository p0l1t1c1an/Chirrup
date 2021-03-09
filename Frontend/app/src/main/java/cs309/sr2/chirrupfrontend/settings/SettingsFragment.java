package cs309.sr2.chirrupfrontend.settings;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
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

public class SettingsFragment extends Fragment {

    private TextView username;
    private TextView firstname;
    private TextView lastname;
    private TextView bio;
    private TextView testView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_settings, container, false);

        //set text fields to current user data
        username = root.findViewById(R.id.usernameField);
        firstname = root.findViewById(R.id.firstNameField);
        lastname = root.findViewById(R.id.lastNameField);
        bio = root.findViewById(R.id.bioField);
        testView = root.findViewById(R.id.testView);
        username.setHint(CurrentUserData.currUser.getUserName());
        firstname.setHint(CurrentUserData.currUser.getFirstName());
        lastname.setHint(CurrentUserData.currUser.getLastName());
        bio.setHint(CurrentUserData.currUser.getBio());

        return root;
    }

    public void clearUserText(View view) {
        username.setText("");
        firstname.setText("");
        lastname.setText("");
        bio.setText("");
    }

    public void readyUserToUpdate(View view) throws JSONException {
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
        String jsonString = toSend.toString();

        //send to Server
        sendUser(jsonString, CurrentUserData.currUser.getID());

        //update user
        float currTime = System.nanoTime() / 1000000;
        while ((System.nanoTime() / 1000000) - currTime < 50) {}
        getUser(CurrentUserData.currUser.getID());
        username.setText("");
        firstname.setText("");
        lastname.setText("");
        bio.setText("");
    }

    public void getUser(int userId) {
        RequestQueue requestQueue = Volley.newRequestQueue(AppController.getInstance());

        String baseUrl = getResources().getString(R.string.base_url);
        String url = baseUrl + "user/" + userId;

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
                    try {
                        JSONObject res = new JSONObject(responseBody);
                        username.setHint(res.getString("username"));
                        firstname.setHint(res.getString("firstname"));
                        lastname.setHint(res.getString("lastname"));
                        bio.setHint(res.getString("bio"));

                        CurrentUserData.currUser.setUserName(res.getString("username"));
                        CurrentUserData.currUser.setFirstName(res.getString("firstname"));
                        CurrentUserData.currUser.setLastName(res.getString("lastname"));
                        CurrentUserData.currUser.setBirthday(res.getString("birthday"));
                        CurrentUserData.currUser.setEmail(res.getString("email"));
                        CurrentUserData.currUser.setPassword(res.getString("password"));
                        CurrentUserData.currUser.setID(res.getInt("id"));
                        CurrentUserData.currUser.setRole(res.get("role").toString());
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
        RequestQueue requestQueue = Volley.newRequestQueue(AppController.getInstance());
        String baseUrl = getResources().getString(R.string.base_url);
        String url = baseUrl + "user/" + userID;
        final String requestBody = user;

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
                    testView.setText("Upload Successful");
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