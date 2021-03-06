package cs309.sr2.chirrupfrontend;

import android.app.Application;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * class used to keep track of all requests across the app, as well as manage showing of fragments
 */
public class AppController extends Application {

    /**
     * global http request queue
     */
    private RequestQueue requestQueue;

    /**
     * global instance of the app controller
     */
    private static AppController instance;

    /**
     * fragment manager for app
     */
    private static FragmentManager fragmentManager;

    /**
     * set instance to the instance created on app launch
     */
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    /**
     * get the app controller instance
     *
     * @return instance of the app controller
     */
    public static synchronized AppController getInstance() {
        return instance;
    }

    /**
     * get the global request queue, or make one if it doesn't exist
     *
     * @return the request queue
     */
    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return requestQueue;
    }

    /**
     * add a request to the request queue
     *
     * @param req request to send
     * @param <T> data type of request
     */
    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(AppController.class.getSimpleName());
        getRequestQueue().add(req);
    }

    /**
     * get the fragment manager for the app
     *
     * @return fragment manager for the app
     */
    public static FragmentManager getFragmentManager() {
        return AppController.fragmentManager;
    }

    /**
     * set the app fragment manager
     *
     * @param fragmentManager fragment manager
     */
    public static void setFragmentManager(FragmentManager fragmentManager) {
        AppController.fragmentManager = fragmentManager;
    }

    /**
     * show a fragment
     *
     * @param fragment fragment to show
     */
    public static void showFragment(Fragment fragment) {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.nav_host_fragment, fragment);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();
    }
}
