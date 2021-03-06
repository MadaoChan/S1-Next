package cl.monsoon.s1next.view.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import cl.monsoon.s1next.R;
import cl.monsoon.s1next.view.fragment.FavouriteListFragment;

/**
 * An Activity shows the thread lists.
 */
public final class FavouriteListActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_base);

        if (savedInstanceState == null) {
            Fragment fragment = new FavouriteListFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.frame_layout, fragment,
                    FavouriteListFragment.TAG).commit();
        }
    }
}
