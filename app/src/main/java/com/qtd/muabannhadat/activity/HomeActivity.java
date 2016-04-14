package com.qtd.muabannhadat.activity;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;

import com.qtd.muabannhadat.R;
import com.qtd.muabannhadat.fragment.BlockNewsFragment;
import com.qtd.muabannhadat.fragment.FavoriteFragment;

public class HomeActivity extends AppCompatActivity {
    private TabLayout tabLayout;

    private PopupMenu popupMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        initView();
        initTabLayout();
    }

    private void initView() {
        getSupportFragmentManager().beginTransaction().replace(R.id.layout_container_home, new BlockNewsFragment()).commit();
    }

    private void initTabLayout() {
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_home_white_36dp));
        tabLayout.addTab(tabLayout.newTab().setIcon(changeColor(R.drawable.ic_search_white_36dp, R.color .colorPrimaryDark)));
        tabLayout.addTab(tabLayout.newTab().setIcon(changeColor(R.drawable.ic_favorite_white_36dp, R.color.colorPrimaryDark)));
        tabLayout.addTab(tabLayout.newTab().setIcon(changeColor(R.drawable.ic_notifications_white_36dp, R.color.colorPrimaryDark)));
        tabLayout.addTab(tabLayout.newTab().setIcon(changeColor(R.drawable.ic_more_vert_white_36dp, R.color.colorPrimaryDark)));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        changeColorTabItem(R.drawable.ic_home_white_36dp, 0);
                        getSupportFragmentManager().beginTransaction().replace(R.id.layout_container_home, new BlockNewsFragment()).commit();

                        break;
                    case 1:
                        changeColorTabItem(R.drawable.ic_search_white_36dp, 1);
                        break;
                    case 2:
                        changeColorTabItem(R.drawable.ic_favorite_white_36dp, 2);
                        getSupportFragmentManager().beginTransaction().replace(R.id.layout_container_home, new FavoriteFragment()).commit();
                        break;
                    case 3:
                        changeColorTabItem(R.drawable.ic_notifications_white_36dp, 3);
                        break;
                    case 4:
//                        popupMenu = new PopupMenu();
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private Drawable changeColor(int id, int color) {
        Drawable a = ContextCompat.getDrawable(getApplicationContext(), id);
        a.setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
        return a;
    }

    private void changeColorTabItem(int id, int index) {
        tabLayout.getTabAt(index).setIcon(id);
        switch (index) {
            case 0:
                tabLayout.getTabAt(1).setIcon(changeColor(R.drawable.ic_search_white_36dp, R.color.colorGray));
                tabLayout.getTabAt(2).setIcon(changeColor(R.drawable.ic_favorite_white_36dp, R.color.colorGray));
                tabLayout.getTabAt(3).setIcon(changeColor(R.drawable.ic_notifications_white_36dp, R.color.colorGray));
                break;
            case 1:
                tabLayout.getTabAt(0).setIcon(changeColor(R.drawable.ic_home_white_36dp, R.color.colorGray));
                tabLayout.getTabAt(2).setIcon(changeColor(R.drawable.ic_favorite_white_36dp, R.color.colorGray));
                tabLayout.getTabAt(3).setIcon(changeColor(R.drawable.ic_notifications_white_36dp, R.color.colorGray));
                break;
            case 2:
                tabLayout.getTabAt(1).setIcon(changeColor(R.drawable.ic_search_white_36dp, R.color.colorGray));
                tabLayout.getTabAt(0).setIcon(changeColor(R.drawable.ic_home_white_36dp, R.color.colorGray));
                tabLayout.getTabAt(3).setIcon(changeColor(R.drawable.ic_notifications_white_36dp, R.color.colorGray));
                break;
            case 3:
                tabLayout.getTabAt(1).setIcon(changeColor(R.drawable.ic_search_white_36dp, R.color.colorGray));
                tabLayout.getTabAt(2).setIcon(changeColor(R.drawable.ic_favorite_white_36dp, R.color.colorGray));
                tabLayout.getTabAt(0).setIcon(changeColor(R.drawable.ic_home_white_36dp, R.color.colorGray));
                break;
        }
    }
}
