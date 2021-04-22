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
import cs309.sr2.chirrupfrontend.listui.post.comments.CommentsFragment;
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
     * presenter for post ui
     */
    private PostPresenter postPresenter;

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
        View root = inflater.inflate(R.layout.fragment_postcard, container, false);

        loadData(root);

        Button like = root.findViewById(R.id.post_like);
        Button share = root.findViewById(R.id.post_share);
        Button comment = root.findViewById(R.id.post_comment);

        like.setOnClickListener(v -> {
            likePost();
        });

        share.setOnClickListener(v -> {
            //show ui to send as a message
        });

        comment.setOnClickListener(v -> {
            showComments();
        });

        View.OnClickListener clickListener = v -> {
            showProfile();
        };

        root.findViewById(R.id.post_avatar).setOnClickListener(clickListener);
        root.findViewById(R.id.post_name).setOnClickListener(clickListener);
        root.findViewById(R.id.post_username).setOnClickListener(clickListener);

        return root;
    }

    /**
     * load the data from the database
     *
     * @param view fragment view
     */
    public void loadData(View view) {
        if (postPresenter == null) postPresenter = new PostPresenter(view);
        postPresenter.loadData(AppController.getInstance().getString(R.string.base_url) +
                        "posts/" + postID, AppController.getInstance().getString(R.string.base_url) +
                        "user/#", AppController.getInstance().getString(R.string.base_url) + "user/#/profilePicture",
                Session.getUser());
    }

    /**
     * like or dislike the post by the current user
     */
    public void likePost() {
        postPresenter.likePost("http://coms-309-016.cs.iastate.edu:8080/api/posts/like/" +
                Session.getUser() + "/" + postID);
    }

    /**
     * show the comments on this post
     */
    public void showComments() {
        CommentsFragment comments = new CommentsFragment(postID);
        AppController.getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, comments)
                .addToBackStack(null).commit();
    }

    /**
     * show the profile of the post creator
     */
    public void showProfile() {
        postPresenter.showProfile();
    }

    /**
     * set the post presenter manually
     *
     * @param postPresenter post presenter
     */
    public void setPostPresenter(PostPresenter postPresenter) {
        this.postPresenter = postPresenter;
    }

    /**
     * get the post presenter for the fragment
     *
     * @return fragment's post presenter
     */
    public PostPresenter getPostPresenter() {
        return postPresenter;
    }

    /**
     * get the id of the post
     *
     * @return id of post
     */
    public int getPostID() {
        return postID;
    }
}
