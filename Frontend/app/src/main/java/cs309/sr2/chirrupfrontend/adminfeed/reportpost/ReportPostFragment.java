package cs309.sr2.chirrupfrontend.adminfeed.reportpost;

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
import cs309.sr2.chirrupfrontend.AppController;

/**
 * A UI element that will show the contents of a user post, including details about who posted, what
 * was posted, and buttons to dismiss or delete. a post can be added to a layout easily by doing:
 * <br>
 * <code>ReportPostFragment fragment = new ReportPostFragment(postID);
 * AppController.showFragment(fragment);</code>
 *
 * @author Jeremy Noesen
 */
public class ReportPostFragment extends Fragment {

    /**
     * id of post to show
     */
    private int postID;

    /**
     * presenter for post ui
     */
    private ReportPostPresenter reportPostPresenter;

    /**
     * create a post UI card from the post id
     *
     * @param postID id of post to show
     */
    public ReportPostFragment(int postID) {
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
        View root = inflater.inflate(R.layout.fragment_report_postcard, container, false);

        loadData(root);

        Button delete = root.findViewById(R.id.report_post_delete);
        Button dismiss = root.findViewById(R.id.report_post_dismiss);

        delete.setOnClickListener(v -> {
            reportPostPresenter.delete(AppController.getInstance().getString(R.string.base_url) + "posts/" + postID);
        });

        dismiss.setOnClickListener(v -> {
            reportPostPresenter.dismiss(AppController.getInstance().getString(R.string.base_url) + "admin/solve/" + postID);
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
        if (reportPostPresenter == null) reportPostPresenter = new ReportPostPresenter(view);
        reportPostPresenter.loadData(AppController.getInstance().getString(R.string.base_url) +
                "posts/" + postID, AppController.getInstance().getString(R.string.base_url) +
                "user/#", AppController.getInstance().getString(R.string.base_url) +
                "user/#/profilePicture", Session.getUser());
    }

    /**
     * show the profile of the post creator
     */
    public void showProfile() {
        reportPostPresenter.showProfile();
    }

    /**
     * set the post presenter manually
     *
     * @param reportPostPresenter post presenter
     */
    public void setReportPostPresenter(ReportPostPresenter reportPostPresenter) {
        this.reportPostPresenter = reportPostPresenter;
    }

    /**
     * get the post presenter for the fragment
     *
     * @return fragment's post presenter
     */
    public ReportPostPresenter getReportPostPresenter() {
        return reportPostPresenter;
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
