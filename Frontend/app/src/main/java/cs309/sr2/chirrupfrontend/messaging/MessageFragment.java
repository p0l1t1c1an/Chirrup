package cs309.sr2.chirrupfrontend.messaging;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.toolbox.ImageLoader;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;

import cs309.sr2.chirrupfrontend.R;

import cs309.sr2.chirrupfrontend.settings.CurrentUserData;
import cs309.sr2.chirrupfrontend.volley.VolleyListener;
import cs309.sr2.chirrupfrontend.volley.VolleyRequester;

import java.util.ArrayList;

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
    //person currently messaging
    private TextView personMessaging;
    //message view text
    private TextView messageView;
    //message composer box
    private EditText createMessage;
    //websocket
    private WebSocketClient cc;
    //messages
    private ArrayList<String> messages;
    //server response
    private String volleyResponse;

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
        personMessaging = root.findViewById(R.id.UserCurrentlyMessaging);
        messageView = root.findViewById(R.id.MessageView);
        createMessage = root.findViewById(R.id.MessageComposer);
        VolleyRequester = new VolleyRequester(this);


        root.findViewById(R.id.searchButton).setOnClickListener((v) -> {
            try {
                messages.clear();
                updateMessageBox();
                cc.close();
            } catch (Exception e) {}

            try {
                cc = new WebSocketClient(new URI("ws://coms-309-016.cs.iastate.edu:8080/api/dm/" + CurrentUserData.currUser.getID())) {
                @Override
                public void onOpen(ServerHandshake serverHandshake) {
                    //set messages
                    /*
                     *baseurl/api/group/groupID
                     * Group gives list of poeple in the group and a list of messages which includes sender, date, and content
                     */
                    VolleyRequester.getString(getResources().getString(R.string.base_url) + "groups/" + searchText.getText());
                    personMessaging.setText(searchText.getText());
                    try {
                        JSONArray toParseArray = new JSONArray(volleyResponse);
                        for (int i = 0; i < toParseArray.length(); ++i) {
                            JSONObject toParse = new JSONObject(toParseArray.getString(i));
                            String toAdd = "";
                            toAdd += toParse.getString("sender" + ": ");
                            toAdd += toParse.getString("content");
                            messages.add(toAdd + "\n");
                        }
                    } catch (Exception e) {}
                    updateMessageBox();
                }

                @Override
                public void onMessage(String s) {
                    /*
                    On recieve:
                        Getting a single message of an ID in the database
                        GET request to get content of the message at /api/directmessages/ID
                     */

                    //add user's name
                    VolleyRequester.getString(getResources().getString(R.string.base_url) + "directmessages/" + s);
                    try {
                        JSONObject toParse = new JSONObject(volleyResponse);
                        String toAdd = "";
                        toAdd += toParse.getString("sender" + ": ");
                        toAdd += toParse.getString("content");
                        messages.add(toAdd + "\n");
                    } catch (Exception e) {}
                    messages.add(s);
                    updateMessageBox();
                }

                @Override
                public void onClose(int i, String s, boolean b) {

                }

                @Override
                public void onError(Exception e) {

                }
                };
            } catch (Exception e) {}
        });

        root.findViewById(R.id.sendMessageButton).setOnClickListener((v) -> {
            //send the message that the user has composed to the server (Message Thread)
            //get the message
            try {
                JSONObject messageToSend = new JSONObject();
                messageToSend.put("content", createMessage.getText().toString());
                VolleyRequester.setString(getResources().getString(R.string.base_url) + "directmessages",
                        messageToSend.toString(), Request.Method.POST);
                cc.send(volleyResponse);
                JSONArray peopleMessaging = new JSONArray();
                peopleMessaging.put(Integer.valueOf(personMessaging.getText().toString()));
                JSONObject toSend = new JSONObject();
                toSend.put("from", CurrentUserData.currUser.getID());
                toSend.put("to", peopleMessaging);
                toSend.put("message", createMessage.getText().toString());
                VolleyRequester.setString(getResources().getString(R.string.base_url) + "directmessages",
                        toSend.toString(), Request.Method.POST);
            } catch (Exception e){}
            /*
                    To send:
                    Make a post request to baseURL/api/directmessages
                    Get a response that's the id of the message just sent
                    take that ID and send it to the group with websockets.
                    Take the people in the group and list them in a JSONArray
                    Put all this in a JSON objust and send that as a string

                    {"from": 2, "to": [2, 1], "message": 73}
                     */
        });

        return root;
    }

    /**
     * Updates the messaging box on the messages page
     */
    private void updateMessageBox() {
        String message = "";
        for (int i = 0; i < messages.size(); ++i) {
            message += messages.get(i);
            message += "\n";
        }
        messageView.setText(message);
    }

    /**
     * Runs when a string response is received
     *
     * @param response response from request
     */
    public void onStringResponse(String response) {
        volleyResponse = response;
    }

    /**
     * Runs when a json object response is received
     *
     * @param response response from request
     */
    public void onObjectResponse(JSONObject response) {

    }

    /**
     * Runs when an image response is received
     *
     * @param response response from request
     */
    public void onImageResponse(ImageLoader.ImageContainer response) {

    }

    /**
     * Runs when an array response is received
     *
     * @param response response from request
     */
    public void onArrayResponse(JSONArray response) {

    }
}
