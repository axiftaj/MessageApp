import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.messageapp.ContactsFragment;
import com.example.messageapp.GroupsFragment;

public class FragmentPagerAdapter extends androidx.fragment.app.FragmentPagerAdapter {

    private Context mContext;

    public FragmentPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    // This determines the fragment for each tab
    @Override
    public Fragment getItem(int position) {

        if (position == 0) {
            return new ContactsFragment();
        } else {
            return new GroupsFragment();
        }
    }

    // This determines the number of tabs
    @Override
    public int getCount() {
        return 4;
    }

    // This determines the title for each tab
    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        switch (position) {
            case 0:
               String  title = "Contacts";
                case 1:
                 String   titles = "Groups";

            default:
                return null;
        }
    }
}
