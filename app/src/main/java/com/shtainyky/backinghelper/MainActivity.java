package com.shtainyky.backinghelper;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.shtainyky.backinghelper.database_sharedprefences.QueryPreferences;
import com.shtainyky.backinghelper.services.PollService;
import com.shtainyky.backinghelper.services.PollServiceGipsy;
import com.shtainyky.backinghelper.utils.FirstInstallation;
import com.shtainyky.backinghelper.utils.TabPagerFragmentAdapter;

public class MainActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private int pos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initToolbar();
        initTabs();
        initFloatingButton();
        if (QueryPreferences.getStoredFirstInstallation(this)) {
            new FirstInstallation(this);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);

        return true;
    }

    @Override
    public void onBackPressed() {
        QueryPreferences.setStoredPosition(getApplicationContext(), 0);
        super.onBackPressed();
    }

    private void initTabs() {
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        TabPagerFragmentAdapter adapter = new TabPagerFragmentAdapter(this, getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        pos = QueryPreferences.getStoredPosition(this);
        Log.i("mLog", "+++++++++++++position = " + pos);
        viewPager.setCurrentItem(pos);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void initToolbar() {
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolBar);
        toolbar.setTitle(R.string.app_name);
        toolbar.setLogo(R.mipmap.ic_message_text);

        SwitchCompat switchViewStrategy = (SwitchCompat) toolbar.findViewById(R.id.switchForActionBar);
        switchViewStrategy.setChecked(QueryPreferences.getStoredPositionSwitchPokerStrategy(getApplicationContext()));
        switchViewStrategy.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (compoundButton.isChecked()) {
                    polingStrategy();
                    Toast.makeText(getApplicationContext(),
                            getResources().getString(R.string.start_polling_pokerStrategy),
                            Toast.LENGTH_LONG).show();

                } else {
                    polingStrategy();
                    Toast.makeText(getApplicationContext(),
                            getResources().getString(R.string.stop_polling),
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        SwitchCompat switchViewGipsy = (SwitchCompat) toolbar.findViewById(R.id.switchForActionBar2);
        switchViewGipsy.setChecked(QueryPreferences.getStoredPositionSwitchGipsy(getApplicationContext()));
        switchViewGipsy.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (compoundButton.isChecked()) {
                    polingGipsy();
                    Toast.makeText(getApplicationContext(),
                            getResources().getString(R.string.start_polling_gipsyTeam),
                            Toast.LENGTH_LONG).show();

                } else {
                    polingGipsy();
                    Toast.makeText(getApplicationContext(),
                            getResources().getString(R.string.stop_polling),
                            Toast.LENGTH_LONG).show();
                }
            }
        });


        setSupportActionBar(toolbar);
        toolbar.inflateMenu(R.menu.menu);

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.Quit:
                        finish();
                        break;
                    case R.id.filtr:
                        Intent intent = new Intent(getApplicationContext(), UsersFilter.class);
                        startActivity(intent);
                        break;
                }
                return false;
            }
        });

    }

    private void initFloatingButton() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.show();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                QueryPreferences.setStoredPosition(getApplicationContext(), viewPager.getCurrentItem());
                Intent intent = getIntent();
                finish();
                startActivity(intent);
                Log.i("mLog", "get toString =**************************************");
            }
        });
    }

    private void polingStrategy() {
        boolean shouldStartAlarm =
                !PollService.isServiceAlarmOn(getApplicationContext());
        PollService.setServiceAlarm(getApplicationContext(), shouldStartAlarm);
        QueryPreferences.setStoredPositionSwitchPokerStrategy(getApplicationContext(), shouldStartAlarm);
    }

    private void polingGipsy() {
        boolean shouldStartAlarm =
                !PollServiceGipsy.isServiceAlarmOn(getApplicationContext());
        PollServiceGipsy.setServiceAlarm(getApplicationContext(), shouldStartAlarm);
        QueryPreferences.setStoredPositionSwitchPokerGipsy(getApplicationContext(), shouldStartAlarm);
    }

}




