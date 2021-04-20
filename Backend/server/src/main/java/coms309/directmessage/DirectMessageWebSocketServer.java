package coms309.directmessage;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Component;


import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/dm/{username}")
@Component
public class DirectMessageWebSocketServer {

  // Store all socket session and their corresponding username.
  private static Map<Session, String> sessionUsernameMap = new Hashtable<>();
  private static Map<String, Session> usernameSessionMap = new Hashtable<>();

  private final Logger logger = LoggerFactory.getLogger(DirectMessageWebSocketServer.class);

  @OnOpen
  public void onOpen(Session session, @PathParam("username") String username)
  throws IOException {
    logger.info("Entered into Open");
    sessionUsernameMap.put(session, username);
    usernameSessionMap.put(username, session);
  }

  @OnMessage
  public void onMessage(Session session, String message) throws IOException {
    try {
        JSONObject jsonMessage = new JSONObject(message);
        logger.info("Entered into Message: Got Message:" + message);

        JSONArray groupsJsonArray = jsonMessage.getJSONArray("to");
        String toSend = jsonMessage.getString("message");

        for (int i = 0; i < groupsJsonArray.length(); i++) {
            String username = groupsJsonArray.get(i).toString();
            sendMessageToParticularUser(username, toSend);
        }
    } catch (JSONException e) {
        logger.error("Invalid message format");
    }
  }
  @OnClose
  public void onClose(Session session) throws IOException {
    logger.info("Entered into Close");

    String username = sessionUsernameMap.get(session);
    sessionUsernameMap.remove(session);
    usernameSessionMap.remove(username);
  }

  @OnError
  public void onError(Session session, Throwable throwable) {
    logger.info("Entered into Error");
  }

  private void sendMessageToParticularUser(String username, String message) {
    try {
      usernameSessionMap.get(username).getBasicRemote().sendText(message);
    } catch (IOException e) {
      logger.info("Exception: " + e.getMessage().toString());
      e.printStackTrace();
    }
  }
}