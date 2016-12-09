package post.it.project.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import post.it.project.fragment.DraftFragment;
import post.it.project.fragment.PostFragment;
import post.it.project.fragment.SettingsFragment;
import post.it.project.postit.MainActivity;

/**
 * Created by Михаил on 09.12.2016.
 */

public class SectionsPagerAdapter extends FragmentPagerAdapter {

    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new DraftFragment();
            case 1:
                return new PostFragment();
            case 2:
                return new SettingsFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        // Show 3 total pages.
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "DRAFT";
            case 1:
                return "POST";
            case 2:
                return "SETTINGS";
        }
        return null;
    }
}