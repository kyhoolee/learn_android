package com.example.infinitbank;


import android.support.v4.app.Fragment;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import static android.R.attr.tag;

public class MainActivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.e("kyhoolee", "init main activity");
        BottomBar bottomBar = (BottomBar) findViewById(R.id.bottomBar);
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                switch(tabId) {
                    case R.id.tab_home:
                        homeFragment();
                        break;
                    case R.id.tab_card:
                        cardFragment();
                        break;
                    case R.id.tab_more:
                        spendFragment();
                        break;
                    case R.id.tab_spending:
                        planFragment();
                        break;
                    case R.id.tab_settings:
                        settingFragment();
                        break;

                }

            }
        });
    }


    private void initFragment(Bundle savedInstanceState) {
        // if savedInstanceState is null we do some cleanup
        if (savedInstanceState != null) {
            // cleanup any existing fragments in case we are in detailed mode
            getFragmentManager().executePendingTransactions();
            Fragment fragmentById = getSupportFragmentManager().
                    findFragmentById(R.id.fragmentContainer);
            if (fragmentById!=null) {
                getSupportFragmentManager().beginTransaction()
                        .remove(fragmentById).commit();
            }
        }
        HomeFragment homeFragment = HomeFragment.newInstance();
        FrameLayout viewById = (FrameLayout) findViewById(R.id.fragmentContainer);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainer, homeFragment).commit();
    }

    private void homeFragment() {
        HomeFragment homeFragment = HomeFragment.newInstance();
        FrameLayout viewById = (FrameLayout) findViewById(R.id.fragmentContainer);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainer, homeFragment).commit();
    }

    private void cardFragment() {
        CardFragment cardFragment = CardFragment.newInstance();
        FrameLayout viewById = (FrameLayout) findViewById(R.id.fragmentContainer);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainer, cardFragment).commit();
    }

    private void spendFragment() {
        SpendFragment spendFragment = SpendFragment.newInstance();
        FrameLayout viewById = (FrameLayout) findViewById(R.id.fragmentContainer);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainer, spendFragment).commit();
    }

    private void planFragment() {
        PlanFragment planFragment = PlanFragment.newInstance();
        FrameLayout viewById = (FrameLayout) findViewById(R.id.fragmentContainer);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainer, planFragment).commit();
    }

    private void settingFragment() {
        SettingFragment settingFragment = SettingFragment.newInstance();
        FrameLayout viewById = (FrameLayout) findViewById(R.id.fragmentContainer);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainer, settingFragment).commit();
    }
}
