package cs309.sr2.chirrupfrontend.messaging;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

import cs309.sr2.chirrupfrontend.R;
import cs309.sr2.chirrupfrontend.utils.AppController;

import androidx.annotation.NonNull;

import org.json.JSONException;
import org.w3c.dom.Text;

import cs309.sr2.chirrupfrontend.R;
import cs309.sr2.chirrupfrontend.settings.CurrentUserData;
import cs309.sr2.chirrupfrontend.volley.VolleyListener;
import cs309.sr2.chirrupfrontend.volley.VolleyRequester;

/**
 * This class represents the direct messaging fragment for our app.
 *
 * @author William Zogg
 */
public class MessageFragment extends Fragment implements VolleyListener {

    //VolleyRequester
    private VolleyRequester VolleyRequester;
    //Message thread created upon starting the class
    private MessageThread userConversation;
    //search text box
    private EditText searchText;
    //status text box
    private TextView status;
    //message view text
    private TextView messageView;
    //message composer box
    private EditText createMessage;

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

        searchText = root.findViewById(R.id.searchUser);
        status = root.findViewById(R.id.MessageErrorBox);
        messageView = root.findViewById(R.id.MessageView);
        createMessage = root.findViewById(R.id.MessageComposer);
        VolleyRequester = new VolleyRequester(this);

        root.findViewById(R.id.searchButton).setOnClickListener((v) -> {
            //send user ID to the server and bring back the messages that user has with the specific ID
            String toFind = searchText.getText().toString();
            //user VolleyRequester to ask for a user from the server
            VolleyRequester.getString(getResources().getString(R.string.base_url) + "user/" + toFind);
            //Volley Requester will call onStringResponse
        });

        root.findViewById(R.id.sendMessageButton).setOnClickListener((v) -> {
            //send the message that the user has composed to the server (Message Thread)
            //get the message
            String messageToSend = createMessage.getText().toString();
            try {
                //create JSON object for message
                JSONObject toSend = new JSONObject();
                toSend.put("content", messageToSend);
                //send the message to the server
                VolleyRequester.setObject(getResources().getString(R.string.base_url) + "directmessages/" +
                        userConversation.getUserID() + "/" + userConversation.getToID(), toSend, Request.Method.POST);
            } catch (Exception e){}
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
            //create a json from our response
            JSONObject userMessage = new JSONObject(response);
            //get the messages
            //create message thread based on received information
            userConversation = new SingleThread(Integer.parseInt(searchText.getText().toString()), (String[]) userMessage.get("messagesId"));
            String toDisplay = "";
            //put messages into a string to display
            String[] messages = userConversation.getMessages();
            for (int i = 0; i < messages.length; ++i) {
                toDisplay += messages[i];
                toDisplay += "\n";
            }
            messageView.setText(toDisplay);
        } catch (Exception e) {}

    }

    /**
     * Runs when a json object response is received
     *
     * @param response response from request
     */
    @Override
    public void onObjectResponse(JSONObject response) {

    }

    /**
     * Runs when an image response is received
     *
     * @param response response from request
     */
    @Override
    public void onImageResponse(ImageLoader.ImageContainer response) {

    }

    /**
     * Runs when an array response is received
     *
     * @param response response from request
     */
    @Override
    public void onArrayResponse(JSONArray response) {

    }
}
