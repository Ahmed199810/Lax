package com.ama.lax.adapters;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.ama.lax.ui.fragments.ExercisesFragment;
import com.ama.lax.ui.fragments.GiftsFragments;
import com.ama.lax.ui.fragments.NavigationFragment;
import com.ama.lax.ui.fragments.NotificationFragment;
import com.ama.lax.ui.fragments.PeopleFragment;

public class NavPagerAdapter extends FragmentPagerAdapter {

    public NavPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {

            case 0:

                ExercisesFragment exercisesFragment = new ExercisesFragment();
                return exercisesFragment;

            case 1:

                NavigationFragment navigationFragment = new NavigationFragment();
                return navigationFragment;

            case 2:

                PeopleFragment peopleFragment = new PeopleFragment();
                return peopleFragment;

            case 3:

                NotificationFragment notificationFragment = new NotificationFragment();
                return notificationFragment;

            case 4:

                GiftsFragments giftsFragments = new GiftsFragments();
                return giftsFragments;

            default:
                return null;

        }


    }

    @Override
    public int getCount() {
        return 5;
    }

}
