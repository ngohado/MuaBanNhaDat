package com.qtd.muabannhadat.activity;

import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.view.MenuItem;

import com.qtd.muabannhadat.R;
import com.qtd.muabannhadat.constant.AppConstant;
import com.qtd.muabannhadat.fragment.BlockNewsFragment;
import com.qtd.muabannhadat.fragment.FavoriteFragment;
import com.qtd.muabannhadat.fragment.NotificationFragment;
import com.qtd.muabannhadat.fragment.Tab2Fragment;
import com.qtd.muabannhadat.service.RegistrationIntentService;
import com.qtd.muabannhadat.util.SharedPrefUtils;

import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private PopupMenu popupMenu;
    private BroadcastReceiver broadcastReceiver;
    private boolean isReceiverRegistered;

    public static final String NOTIFICATION_IS_VISIBLE = "notification_is_visible";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        ButterKnife.bind(this);
        initComponent();
        initTabLayout();

    }

    private void initComponent() {
        getSupportFragmentManager().beginTransaction().replace(R.id.layout_container_home, new BlockNewsFragment()).commit();
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

            }
        };
        registerReceiver();
    }

    private void initTabLayout() {
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_home_white_36dp));
        tabLayout.addTab(tabLayout.newTab().setIcon(changeColor(R.drawable.ic_search_white_36dp, R.color.colorPrimaryDark)));
        tabLayout.addTab(tabLayout.newTab().setIcon(changeColor(R.drawable.ic_favorite_white_36dp, R.color.colorPrimaryDark)));
        tabLayout.addTab(tabLayout.newTab().setIcon(changeColor(R.drawable.ic_notifications_white_36dp, R.color.colorPrimaryDark)));
        tabLayout.addTab(tabLayout.newTab().setIcon(changeColor(R.drawable.ic_more_vert_white_36dp, R.color.colorPrimaryDark)));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        popEntireFragmentBackStack();
                        SharedPrefUtils.putBoolean(NOTIFICATION_IS_VISIBLE, false);
                        changeColorTabItem(R.drawable.ic_home_white_36dp, 0);
                        tabLayout.setElevation(5f);
                        getSupportFragmentManager().beginTransaction().replace(R.id.layout_container_home, new BlockNewsFragment()).commit();
                        break;
                    case 1:
                        popEntireFragmentBackStack();
                        SharedPrefUtils.putBoolean(NOTIFICATION_IS_VISIBLE, false);
                        changeColorTabItem(R.drawable.ic_search_white_36dp, 1);
                        tabLayout.setElevation(0f);
                        getSupportFragmentManager().beginTransaction().replace(R.id.layout_container_home, new Tab2Fragment()).commit();
                        break;
                    case 2:
                        if (SharedPrefUtils.getInt(AppConstant.ID, -1) == -1) {
                            startActivity(new Intent(HomeActivity.this, LoginActivity.class));
                        }
                        popEntireFragmentBackStack();
                        SharedPrefUtils.putBoolean(NOTIFICATION_IS_VISIBLE, false);
                        tabLayout.setElevation(0f);
                        changeColorTabItem(R.drawable.ic_favorite_white_36dp, 2);
                        getSupportFragmentManager().beginTransaction().replace(R.id.layout_container_home, new FavoriteFragment()).commit();
                        break;
                    case 3:
                        if (SharedPrefUtils.getInt(AppConstant.ID, -1) == -1) {
                            startActivity(new Intent(HomeActivity.this, LoginActivity.class));
                        }
                        changeColorTabItem(R.drawable.ic_notifications_white_36dp, 3);
                        SharedPrefUtils.putBoolean(NOTIFICATION_IS_VISIBLE, true);
                        tabLayout.setElevation(5f);
                        getSupportFragmentManager().beginTransaction().replace(R.id.layout_container_home, new NotificationFragment()).commit();
                        break;
                    case 4:
                        showPopupMenu();
                        break;
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                if (tab.getPosition() == 4) {
                    showPopupMenu();
                }
            }
        });
    }

    private void popEntireFragmentBackStack() {
        try {
            final FragmentManager fm = getSupportFragmentManager();
            final int backStackCount = fm.getBackStackEntryCount();
            for (int i = 0; i < backStackCount; i++) {
                fm.popBackStack();
            }
        } catch (Exception ignored) {

        }
    }

    private void showPopupMenu() {
        if (SharedPrefUtils.getInt(AppConstant.ID, -1) == -1) {
            Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
            startActivity(intent);
            return;
        }
        popupMenu = new PopupMenu(HomeActivity.this, HomeActivity.this.findViewById(R.id.anchor_view));
        popupMenu.getMenuInflater().inflate(R.menu.tab_more, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.sign:

                        SharedPrefUtils.putInt(AppConstant.ID, -1);
                        break;
                    case R.id.profile:
                        startActivity(new Intent(HomeActivity.this, ProfileActivity.class));
                        break;
                    case R.id.settings:
                        startActivity(new Intent(HomeActivity.this, NewpaperActivity.class));
                        break;
                }

                return true;
            }
        });
        popupMenu.show();
    }

    private Drawable changeColor(int id, int color) {
        Drawable a = ContextCompat.getDrawable(getApplicationContext(), id);
        a.setColorFilter(color, PorterDuff.Mode.SRC_IN);
        return a;
    }

    private void changeColorTabItem(int id, int index) {
        tabLayout.getTabAt(index).setIcon(id);
        switch (index) {
            case 0:
                tabLayout.getTabAt(1).setIcon(changeColor(R.drawable.ic_search_white_36dp, R.color.colorPrimaryDark));
                tabLayout.getTabAt(2).setIcon(changeColor(R.drawable.ic_favorite_white_36dp, R.color.colorPrimaryDark));
                tabLayout.getTabAt(3).setIcon(changeColor(R.drawable.ic_notifications_white_36dp, R.color.colorPrimaryDark));
                break;
            case 1:
                tabLayout.getTabAt(0).setIcon(changeColor(R.drawable.ic_home_white_36dp, R.color.colorPrimaryDark));
                tabLayout.getTabAt(2).setIcon(changeColor(R.drawable.ic_favorite_white_36dp, R.color.colorPrimaryDark));
                tabLayout.getTabAt(3).setIcon(changeColor(R.drawable.ic_notifications_white_36dp, R.color.colorPrimaryDark));
                break;
            case 2:
                tabLayout.getTabAt(1).setIcon(changeColor(R.drawable.ic_search_white_36dp, R.color.colorPrimaryDark));
                tabLayout.getTabAt(0).setIcon(changeColor(R.drawable.ic_home_white_36dp, R.color.colorPrimaryDark));
                tabLayout.getTabAt(3).setIcon(changeColor(R.drawable.ic_notifications_white_36dp, R.color.colorPrimaryDark));
                break;
            case 3:
                tabLayout.getTabAt(1).setIcon(changeColor(R.drawable.ic_search_white_36dp, R.color.colorPrimaryDark));
                tabLayout.getTabAt(2).setIcon(changeColor(R.drawable.ic_favorite_white_36dp, R.color.colorPrimaryDark));
                tabLayout.getTabAt(0).setIcon(changeColor(R.drawable.ic_home_white_36dp, R.color.colorPrimaryDark));
                break;
        }
    }

    private void registerReceiver() {
        if (!isReceiverRegistered) {
            LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver,
                    new IntentFilter(RegistrationIntentService.REGISTRATION_COMPLETE));
            isReceiverRegistered = true;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver();
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver);
        isReceiverRegistered = false;
        super.onPause();
    }
}
