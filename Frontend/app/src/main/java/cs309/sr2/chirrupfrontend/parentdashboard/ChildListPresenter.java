package cs309.sr2.chirrupfrontend.parentdashboard;

import android.view.View;
import android.widget.LinearLayout;

import androidx.fragment.app.FragmentTransaction;

import org.json.JSONArray;
import org.json.JSONException;

import cs309.sr2.chirrupfrontend.R;
import cs309.sr2.chirrupfrontend.parentdashboard.childuser.ChildUserFragment;
import cs309.sr2.chirrupfrontend.AppController;
import cs309.sr2.chirrupfrontend.volley.VolleyListener;
import cs309.sr2.chirrupfrontend.volley.VolleyRequester;

/**
 * class to handle visual changes for children list
 *
 * @author Jeremy Noesen
 */
public class ChildListPresenter implements VolleyListener {

    /**
     * volley requester for the fragment
     */
    private VolleyRequester volleyRequester;

    /**
     * view of fragment
     */
    private View view;

    /**
     * url for child list
     */
    private String listURL;

    /**
     * create a new presenter for fragment
     *
     * @param view fragment view
     */
    public ChildListPresenter(View view) {
        this.view = view;
    }

    /**
     * load the data for the list
     *
     * @param listURL url for children list
     */
    public void loadData(String listURL) {
        volleyRequester = new VolleyRequester(this);
        volleyRequester.getArray(listURL);
        this.listURL = listURL;
    }

    /**
     * fill list of users when the request comes in
     *
     * @param response response from request
     */
    @Override
    public void onArrayResponse(JSONArray response) {
        try {
            for (int i = response.length() - 1; i >= 0; i--) {
                ChildUserFragment fragment = new ChildUserFragment(response.getInt(i));
                AppController.getFragmentManager().beginTransaction().add(R.id.child_list_layout, fragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * reload followers list
     */
    public void reload() {
        LinearLayout layout = view.findViewById(R.id.child_list_layout);
        layout.removeAllViews();
        volleyRequester.getArray(listURL);
    }
}
