package swag.theokanning.airhorn.ui.base;


import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import swag.theokanning.airhorn.R;


public class BaseActivity extends AppCompatActivity {

    private static final String TAG = BaseActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    public void setFragment(BaseFragment fragment, boolean animate) {
        // First, get the current fragment. If there isn't a current fragment, then add the new
        // fragment.  If there is, then potentially replace the current fragment with the new one.
        // However, if the user tries to navigate to the exact same fragment, that will look silly,
        // so only do so if the new fragment has a different canonical name than the current one.
        if (getCurrentFragment() == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit();
        } else if (!getCurrentFragmentName().equals(fragment.getClass().getCanonicalName())) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.fragment_container, fragment);
            ft.addToBackStack(fragment.getClass().getSimpleName());
            if (animate) {
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            }
            ft.commit();
        }
    }

    public BaseFragment getCurrentFragment() {
        return (BaseFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);
    }

    private String getCurrentFragmentName() {
        return getCurrentFragment().getClass().getCanonicalName();
    }
}
