package cs309.sr2.chirrupfrontend.listui.post;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import cs309.sr2.chirrupfrontend.R;
import cs309.sr2.chirrupfrontend.account.Session;
import cs309.sr2.chirrupfrontend.utils.AppController;

/**
 * A UI element that will show the contents of a user post, including details about who posted, what
 * was posted, and likes. a post can be added to a layout easily by doing:
 * <br>
 * <code>PostFragment fragment = new PostFragment(userID);
 * AppController.getFragmentManager().beginTransaction().add(R.id.container, fragment).commit();</code>
 *
 * @author Jeremy Noesen
 */
public class PostFragment extends Fragment {


    /**
     * id of post to show
     */
    private int postID;

    /**
     * create a post UI card from the post id
     *
     * @param postID id of post to show
     */
    public PostFragment(int postID) {
        this.postID = postID;
    }

    /**
     * retrieve the post data to fill in the text fields and avatar to create a full post card
     *
     * @param inflater           layout inflater
     * @param container          view group
     * @param savedInstanceState saved instance state
     * @return fully filled post card
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.ui_postcard, container, false);

        PostPresenter postPresenter = new PostPresenter(root);
        postPresenter.loadData(AppController.getInstance().getString(R.string.base_url) +
                "posts/" + postID,AppController.getInstance().getString(R.string.base_url) +
                "user/#","https://api.androidhive.info/volley/volley-image.jpg");

        Button like = root.findViewById(R.id.post_like);
        Button share = root.findViewById(R.id.post_share);
        Button comment = root.findViewById(R.id.post_comment);

        like.setOnClickListener(v -> {
            postPresenter.likePost("http://coms-309-016.cs.iastate.edu:8080/api/posts/like/#/"
                    + postID, Session.getUser());
        });

        share.setOnClickListener(v -> {
            //show ui to send as a message
        });

        comment.setOnClickListener(v -> {
            //show compose UI to add reply keeping the parent data with it
        });

        return root;
    }
}
