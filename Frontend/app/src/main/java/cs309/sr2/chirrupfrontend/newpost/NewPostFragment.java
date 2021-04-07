package cs309.sr2.chirrupfrontend.newpost;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import cs309.sr2.chirrupfrontend.R;
import cs309.sr2.chirrupfrontend.account.Session;

public class NewPostFragment extends Fragment {

    /**
     * parent post id if post is a reply
     */
    private int parentID;

    /**
     * create a new post fragment for a new post without a parent
     */
    public NewPostFragment() {
        parentID = -1;
    }

    /**
     * create a new post fragment with a parent post for replies
     *
     * @param parentID parent id of post
     */
    public NewPostFragment(int parentID) {
        this.parentID = parentID;
    }

    /**
     * retrieve the profile data to fill in the text fields and avatar
     *
     * @param inflater           layout inflater
     * @param container          view group
     * @param savedInstanceState saved instance state
     * @return full profile page view
     */
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_newpost, container, false);

        NewPostPresenter newPostPresenter = new NewPostPresenter(root);

        Button createPost = root.findViewById(R.id.newpost_button);

        createPost.setOnClickListener(v -> {
            if (parentID == -1)
                newPostPresenter.createPost(getString(R.string.base_url) + "/posts/" + Session.getUser());
            else
                newPostPresenter.createPost(getString(R.string.base_url) + "/posts/" + Session.getUser() + "/" + parentID);
        });

        return root;
    }

}
