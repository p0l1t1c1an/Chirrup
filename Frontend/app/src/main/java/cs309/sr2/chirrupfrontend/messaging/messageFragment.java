package cs309.sr2.chirrupfrontend.messaging;

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

import androidx.annotation.NonNull;

import org.json.JSONException;

import cs309.sr2.chirrupfrontend.R;
import cs309.sr2.chirrupfrontend.settings.CurrentUserData;

/**
 * This class represents the direct messaging fragment for our app.
 *
 * @author wszogg
 */
public class messageFragment extends Fragment {

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
        View root = inflater.inflate(R.layout.fragment_messaging, container, false);

//        //set text fields to current user data
//        getUser(CurrentUserData.currUser.getID());
//
//        root.findViewById(R.id.sendButton).setOnClickListener((v) -> {
//            try {
//                readyUserToUpdate();
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        });
//
//        root.findViewById(R.id.cancelButton).setOnClickListener((v) -> {
//            clearUserText();
//        });

        return root;
    }
}
