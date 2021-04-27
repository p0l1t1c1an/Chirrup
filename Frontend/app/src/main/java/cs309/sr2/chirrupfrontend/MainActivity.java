package cs309.sr2.chirrupfrontend;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import cs309.sr2.chirrupfrontend.account.Session;
import cs309.sr2.chirrupfrontend.utils.AppController;

/**
 * Main activity the app opens to, will contain all the fragments for all the screens
 *
 * @author Jeremy Noesen, William Zogg
 */
public class MainActivity extends AppCompatActivity {

    /**
     * main app bar
     */
    private AppBarConfiguration mAppBarConfiguration;

    /**
     * initialize the main activity when it is created when the app is launched
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_login, R.id.nav_newProfile, R.id.nav_profile, R.id.nav_settings, R.id.nav_messaging,
                R.id.nav_newPost, R.id.nav_mainFeed, R.id.nav_search, R.id.nav_adminFeed, R.id.nav_childList).setOpenableLayout(drawer).build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        AppController.setFragmentManager(getSupportFragmentManager());
    }

    /**
     * This method is called whenever the user chooses to navigate Up within your application's
     * activity hierarchy from the action bar.
     *
     * @return true on navigation up
     */
    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    /**
     * override back button to disable it
     */
    @Override
    public void onBackPressed() {}
}