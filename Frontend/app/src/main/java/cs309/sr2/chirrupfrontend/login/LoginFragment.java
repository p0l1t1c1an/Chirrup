package cs309.sr2.chirrupfrontend.login;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONException;
import org.json.JSONObject;

import cs309.sr2.chirrupfrontend.R;
import cs309.sr2.chirrupfrontend.account.Session;
import cs309.sr2.chirrupfrontend.utils.AppController;
import cs309.sr2.chirrupfrontend.volley.VolleyListener;
import cs309.sr2.chirrupfrontend.volley.VolleyRequester;

/**
 * This class is what runs when the user enters the app, where they are prompted
 * to create a new profile or create a new one
 *
 * @author William Zogg, Jeremy Noesen
 */
public class LoginFragment extends Fragment implements VolleyListener {

    /**
     * volley requester for class
     */
    private VolleyRequester volleyRequester;

    /**
     * This is the method that runs when opening the page. The parameters are given to it by program that calls it.
     *
     * @param inflater           Turns the corresponding XML file into a layout.
     * @param container          A group of views.
     * @param savedInstanceState A map of string keys.
     * @return New view for this fragment.
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_login, container, false);

        hideNavigation();

        root.findViewById(R.id.loginSubmit).setOnClickListener((v) -> {
            //send login info to server and get user back (onResponse)
            try {
                String username = ((EditText) root.findViewById(R.id.login_usernameText)).getText().toString();
                String password = ((EditText) root.findViewById(R.id.login_passwordText)).getText().toString();

                volleyRequester = new VolleyRequester(this);
                volleyRequester.setString(getResources().getString(R.string.base_url) + "login?user={" +
                        username + "}&pass={" + password + "}", null, Request.Method.POST);
            } catch (Exception e) {
            }
        });

        return root;
    }

    /**
     * Runs when a string response is received
     *
     * @param response response from request
     */
    @Override
    public void onStringResponse(String response) {
        try {
            int userID = Integer.parseInt(response);
            if (userID == -1) {
                Toast.makeText(AppController.getInstance(), "Login failed!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(AppController.getInstance(), "Login succeeded! You may now " +
                        "navigate to other parts of the app.", Toast.LENGTH_SHORT).show();
                Session.setUser(userID);
                volleyRequester.getObject(getResources().getString(R.string.base_url) + "user/" + userID);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(AppController.getInstance(), "Login failed!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onObjectResponse(JSONObject response) {
        try {
            int role = response.getInt("role");
            showNavigation(role);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * hide the navigation buttons not related to login
     */
    public void hideNavigation() {
        NavigationView navigationView = (NavigationView) this.getActivity().findViewById(R.id.nav_view);
        Menu nav_Menu = navigationView.getMenu();
        nav_Menu.findItem(R.id.nav_mainFeed).setVisible(false);
        nav_Menu.findItem(R.id.nav_adminFeed).setVisible(false);
        nav_Menu.findItem(R.id.nav_childList).setVisible(false);
        nav_Menu.findItem(R.id.nav_messaging).setVisible(false);
        nav_Menu.findItem(R.id.nav_newPost).setVisible(false);
        nav_Menu.findItem(R.id.nav_profile).setVisible(false);
        nav_Menu.findItem(R.id.nav_search).setVisible(false);
        nav_Menu.findItem(R.id.nav_settings).setVisible(false);
    }

    /**
     * show the navigation based on the role
     *
     * @param role int representing role
     */
    public void showNavigation(int role) {
        NavigationView navigationView = (NavigationView) this.getActivity().findViewById(R.id.nav_view);
        Menu nav_Menu = navigationView.getMenu();
        nav_Menu.findItem(R.id.nav_login).setVisible(false);
        nav_Menu.findItem(R.id.nav_newProfile).setVisible(false);

        nav_Menu.findItem(R.id.nav_mainFeed).setVisible(true);
        nav_Menu.findItem(R.id.nav_messaging).setVisible(true);
        nav_Menu.findItem(R.id.nav_newPost).setVisible(true);
        nav_Menu.findItem(R.id.nav_profile).setVisible(true);
        nav_Menu.findItem(R.id.nav_search).setVisible(true);
        nav_Menu.findItem(R.id.nav_settings).setVisible(true);

        if (role == 3) {
            nav_Menu.findItem(R.id.nav_childList).setVisible(true);
        } else if (role == 4) {
            nav_Menu.findItem(R.id.nav_adminFeed).setVisible(true);
        }
    }

}
