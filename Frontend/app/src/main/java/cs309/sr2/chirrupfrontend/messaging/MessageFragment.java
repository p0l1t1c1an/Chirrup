package cs309.sr2.chirrupfrontend.messaging;

import android.os.Build;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URI;

import cs309.sr2.chirrupfrontend.R;

import cs309.sr2.chirrupfrontend.settings.CurrentUserData;

import cs309.sr2.chirrupfrontend.volley.VolleyListener;
import cs309.sr2.chirrupfrontend.volley.VolleyRequester;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
    private ArrayList<MessageInfo> messages = new ArrayList<>();
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
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_messaging, container, false);

        searchText = root.findViewById(R.id.searchUser);
        personMessaging = root.findViewById(R.id.UserCurrentlyMessaging);
        messageView = root.findViewById(R.id.MessageView);
        createMessage = root.findViewById(R.id.MessageComposer);
        VolleyRequester = new VolleyRequester(this);
        volleyResponse = "";


        root.findViewById(R.id.searchButton).setOnClickListener((v) -> {
            try {
                messages.clear();
                updateMessageBox();
                personMessaging.setText(searchText.getText());
                cc.close();
            } catch (Exception e) {}

            try {
                String uri = "ws://coms-309-016.cs.iastate.edu:8080/api/dm/" + CurrentUserData.currUser.getID();

                cc = new WebSocketClient(new URI(uri)) {

                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void onOpen(ServerHandshake serverHandshake) {
                    //set messages
                    /*
                     *baseurl/api/group/groupID
                     * Group gives list of poeple in the group and a list of messages which includes sender, date, and content
                     */
                    VolleyRequester.getString(getResources().getString(R.string.base_url) + "groups/" + searchText.getText());
                    SystemClock.sleep(100);
                    try {
                        //JSONArray toParseArray = new JSONArray(volleyResponse);
                        JSONObject serverResponse = new JSONObject(volleyResponse);
                        String messagesR = serverResponse.getString("messages");
                        JSONArray toParseArray = new JSONArray(messagesR);
                        for (int i = 0; i < toParseArray.length(); ++i) {
                            JSONObject toParse = new JSONObject(toParseArray.getString(i));
                            LocalDateTime time = LocalDateTime.parse(toParse.getString("dateSent"));
                            MessageInfo toAdd = new MessageInfo(toParse.getString("message"), time, toParse.getString("from"));
                            messages.add(toAdd);
                        }
                    } catch (Exception e) {
                        personMessaging.setText(e.getMessage());
                    }
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            personMessaging.setText(searchText.getText());
                            SystemClock.sleep(100);
                            updateMessageBox();
                        }
                    });
                }

                @RequiresApi(api = Build.VERSION_CODES.O)
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
                        LocalDateTime time = LocalDateTime.parse(toParse.getString("dateSent"));
                        MessageInfo toAdd = new MessageInfo(toParse.getString("message"), time, toParse.getString("from"));
                        messages.add(toAdd);
                    } catch (Exception e) {}
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            SystemClock.sleep(100);
                            updateMessageBox();
                        }
                    });
                }

                @Override
                public void onClose(int i, String s, boolean b) {

                }

                @Override
                public void onError(Exception e) {

                }
                };
            } catch (Exception e) {}

            cc.connect();
        });

        root.findViewById(R.id.sendMessageButton).setOnClickListener((v) -> {
            try {
                JSONObject messageToSend = new JSONObject();
                messageToSend.put("message", createMessage.getText().toString());
                VolleyRequester.setObject(getResources().getString(R.string.base_url) + "directmessages/" +
                                CurrentUserData.currUser.getID() + "/" + searchText.getText(), messageToSend, Request.Method.POST);
//                JSONArray peopleMessaging = new JSONArray();
//                peopleMessaging.put(Integer.valueOf(personMessaging.getText().toString()));
//                JSONObject toSend = new JSONObject();
//                toSend.put("from", CurrentUserData.currUser.getID());
//                toSend.put("to", peopleMessaging);
//                toSend.put("message", createMessage.getText().toString());
                LocalDateTime time = LocalDateTime.now();
                MessageInfo toAdd = new MessageInfo(createMessage.getText().toString(), time, String.valueOf(CurrentUserData.currUser.getID()));
                messages.add(toAdd);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        messageView.setText("Loading...");
                        SystemClock.sleep(100);
                        updateMessageBox();
                    }
                });

            } catch (Exception e){
                messageView.setText("error sending message: " + e.getMessage());
            }
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
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void updateMessageBox() {
        SystemClock.sleep(100);
        for (int i = 0; i < messages.size(); ++i) {
            int low = i;
            for (int j = i; j < messages.size(); ++j) {
                if (messages.get(j).getDate().isBefore(messages.get(low).getDate())) {
                    low = j;
                }
            }
            Collections.swap(messages, low, i);
        }

        String message = "";
        for (int i = 0; i < messages.size(); ++i) {
            message += messages.get(i).getSender();
            message += ": ";
            message += messages.get(i).getMessage();
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
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void onObjectResponse(JSONObject response) {
        try {
            VolleyRequester.getString(getResources().getString(R.string.base_url) + "groups/" + personMessaging.getText().toString());
            JSONObject group = new JSONObject(volleyResponse);
            String members = group.getString("members");
            JSONArray memberArray = new JSONArray(members);
            List<Integer> memberIDs = new ArrayList<>();
            for (int i = 0; i < memberArray.length(); ++i) {
                if (Integer.parseInt(memberArray.get(i).toString()) != CurrentUserData.currUser.getID()) {
                    memberIDs.add(Integer.parseInt(memberArray.get(i).toString()));
                }
            }
            JSONArray membersToSend = new JSONArray(memberIDs.toArray());
            JSONObject toSend = new JSONObject();
            toSend.put("from", CurrentUserData.currUser.getID());
            toSend.put("to", membersToSend);
            toSend.put("message", response.getInt("id"));
            cc.send(toSend.toString());
        } catch(Exception e) {}
    }

    /**
     * Runs when an image response is received
     *
     * @param response response from request
     */
    @Override
    public void onImageResponse(Bitmap response) {

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
