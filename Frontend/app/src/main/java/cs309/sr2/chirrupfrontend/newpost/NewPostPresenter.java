package cs309.sr2.chirrupfrontend.newpost;

import android.view.View;
import android.widget.EditText;

import com.android.volley.Request;

import org.json.JSONException;
import org.json.JSONObject;

import cs309.sr2.chirrupfrontend.R;
import cs309.sr2.chirrupfrontend.volley.VolleyListener;
import cs309.sr2.chirrupfrontend.volley.VolleyRequester;

public class NewPostPresenter implements VolleyListener {

    /**
     * view of fragment
     */
    private View view;

    /**
     * create a new presenter for the page
     *
     * @param view fragment view
     */
    public NewPostPresenter(View view) {
        this.view = view;
    }

    /**
     * create a new post
     *
     * @param url url for post creation
     */
    public void createPost(String url) {
        VolleyRequester volleyRequester = new VolleyRequester(this);
        String postBody = ((EditText) view.findViewById(R.id.newpost_textbox)).getText().toString();
        if(!postBody.isEmpty()) {
            try {
                JSONObject post = new JSONObject();
                post.put("content", postBody);
                volleyRequester.setObject(url, post, Request.Method.POST);
                ((EditText) view.findViewById(R.id.newpost_textbox)).setText("");
                ((EditText) view.findViewById(R.id.newpost_textbox)).setHint("Post created!");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}
