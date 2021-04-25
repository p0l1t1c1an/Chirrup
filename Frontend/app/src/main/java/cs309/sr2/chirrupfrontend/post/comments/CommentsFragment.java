package cs309.sr2.chirrupfrontend.post.comments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import cs309.sr2.chirrupfrontend.R;
import cs309.sr2.chirrupfrontend.newpost.NewPostFragment;
import cs309.sr2.chirrupfrontend.utils.AppController;

/**
 * screen used to display a thread of comments linked to a post
 *
 * @author Jeremy Noesen
 */
public class CommentsFragment extends Fragment {

    /**
     * id of main post
     */
    private int postID;

    /**
     * create a comments feed from a post
     *
     * @param postID id of main post
     */
    public CommentsFragment(int postID) {
        this.postID = postID;
    }

    /**
     * retrieve the post data to generate the reply posts
     *
     * @param inflater           layout inflater
     * @param container          view group
     * @param savedInstanceState saved instance state
     * @return comments feed of post replies
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_comments, container, false);

        CommentsPresenter commentsPresenter = new CommentsPresenter(root);
        commentsPresenter.loadData(AppController.getInstance().getString(R.string.base_url) +
                        "posts/" + postID);

        Button reply = root.findViewById(R.id.comments_reply);

        reply.setOnClickListener(v -> {

            NewPostFragment newPostFragment = new NewPostFragment(postID);
            AppController.showFragment(newPostFragment);

        });

        root.findViewById(R.id.comments_reload).setOnClickListener(v -> commentsPresenter.reload());

        return root;
    }

}
