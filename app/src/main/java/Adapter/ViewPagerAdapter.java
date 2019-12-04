package Adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.messageapp.ContactsFragment;
import com.example.messageapp.GroupsFragment;

import java.util.ArrayList;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private final ArrayList<Fragment> fragmenttList = new ArrayList();
    private final ArrayList<String> fragmentTitle = new ArrayList();


    public ViewPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragmenttList.get(position);
    }

    @Override
    public int getCount() {
        return fragmenttList.size();
    }

    public void addFragmnet(Fragment fragment , String title){

        fragmenttList.add(fragment);
        fragmentTitle.add(title);
    }



    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return fragmentTitle.get(position);
    }
}