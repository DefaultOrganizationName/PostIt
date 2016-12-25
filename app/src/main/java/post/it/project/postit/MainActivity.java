package post.it.project.postit;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.IBinder;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.vk.sdk.VKSdk;

import post.it.project.VK.Constants;
import post.it.project.adapter.SectionsPagerAdapter;
import post.it.project.database.DatabaseHelper;
import post.it.project.storage.PersistantStorage;
import post.it.project.utils.Utils;

import static post.it.project.storage.PersistantStorage.addProperty;
import static post.it.project.storage.PersistantStorage.getProperty;

public class MainActivity extends AppCompatActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    boolean[] networks;
    public static DatabaseHelper dbHelper;
    PersistantStorage netStorage;
    SharedPreferences networksState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        networks = new boolean[4];

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

//        PagerTabStrip tabStrip = (PagerTabStrip) findViewById(R.id.toolbar);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        final Activity activity = this;
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //Если каждый раз обновлять то лагает
                //TODO: СРОЧНО ПЕРЕДЕЛАТЬ
                Utils.hidePhoneKeyboard(activity);
                if (getProperty("update")) {
                    mViewPager.getAdapter().notifyDataSetChanged();
                    addProperty("update", false);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        PersistantStorage.init(this);
//        dbHelper = new DatabaseHelper(this);

    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    //deleted PlaceHolder

}
