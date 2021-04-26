package cs309.sr2.chirrupfrontend.messaging;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

import cs309.sr2.chirrupfrontend.R;

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
                cc = new WebSocketClient(new URI("asf")) {
                @Override
                public void onOpen(ServerHandshake serverHandshake) {

                }

                @Override
                public void onMessage(String s) {
                    //add user's name
                    messages.add(s);
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
            String messageToSend = createMessage.getText().toString();
            try {
                cc.send(messageToSend);
                messages.add("Me: " + messageToSend);
            } catch (Exception e){}
        });

        return root;
    }

    private void updatemessageBox() {
        String message = "";
        for (int i = 0; i < messages.size(); ++i) {
            message += messages.get(i);
            message += "\n";
        }
        messageView.setText(message);
    }
}
