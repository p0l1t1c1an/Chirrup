package cs309.sr2.chirrupfrontend.post;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;

import cs309.sr2.chirrupfrontend.R;
import cs309.sr2.chirrupfrontend.utils.AppController;

/**
 * A UI element that will show the contents of a user post, including details about who posted, what
 * was posted, and likes. a post can be added to a layout easily by doing
 * <br>
 * <code>layout.addView(new PostCard(inflater, container, savedInstanceState, postID).getView());</code>
 *
 * @author Jeremy Noesen
 */
public class PostView {

    /**
     * root view for post
     */
    private final View root;

    /**
     * create a new post card within another view from a userID and postID.
     *
     * @param inflater LayoutInflater from parent view onCreate
     * @param container ViewGroup from parent view onCreate
     */
    public PostView(@NonNull LayoutInflater inflater, ViewGroup container, int postID) {
        root = inflater.inflate(R.layout.ui_postcard, container, false);

        PostPresenter postPresenter = new PostPresenter(root);
        postPresenter.loadData(AppController.getInstance().getString(R.string.base_url) +
                "posts/" + postID,AppController.getInstance().getString(R.string.base_url) +
                "user/#","https://api.androidhive.info/volley/volley-image.jpg");

        Button like = root.findViewById(R.id.postlike);
        Button share = root.findViewById(R.id.postshare);
        Button comment = root.findViewById(R.id.postcomment);

        like.setOnClickListener(v -> {
            //add or remove like
        });

        share.setOnClickListener(v -> {
            //show share options or just copy a link to the post
        });

        comment.setOnClickListener(v -> {
            //show compose UI to add reply
        });
    }

    /**
     * get the view of the post layout
     *
     * @return view of post layout
     */
    public View getView() {
        return root;
    }

}
