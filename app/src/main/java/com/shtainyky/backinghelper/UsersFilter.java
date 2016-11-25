package com.shtainyky.backinghelper;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.shtainyky.backinghelper.database_sharedprefences.DatabaseHelper;

import java.util.List;

public class UsersFilter extends Activity {
    private DatabaseHelper helper = new DatabaseHelper(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_filter);
        initToolBar();

        final EditText userName = (EditText) findViewById(R.id.user_name);
        final List<String> users = helper.getAllUsers();
        ListView lvMain = (ListView) findViewById(R.id.lvMain);
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                R.layout.my_list_item, users);
        lvMain.setAdapter(adapter);

        lvMain.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                final String messageText = getResources().getString(R.string.filtr_delete)
                        + " " + users.get(position) + getResources().getString(R.string.filtr_delete_end);
                final String messageToastText = getResources().getString(R.string.filtr_user)
                        + " " + users.get(position) + " "
                        + getResources().getString(R.string.filtr_is_deleted);

                AlertDialog.Builder builder = new AlertDialog.Builder(UsersFilter.this);
                builder.setTitle(getResources().getString(R.string.app_filtr))
                        .setMessage(messageText)
                        .setCancelable(false)
                        .setNegativeButton(getResources().getString(R.string.filtr_no),
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                })
                        .setPositiveButton(getResources().getString(R.string.filtr_ok),
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Log.i("mLog", "users.get(pos) " + users.get(position));
                                        helper.deleteUser(users.get(position));
                                        users.remove(users.get(position));
                                        adapter.notifyDataSetChanged();
                                        Toast.makeText(getApplicationContext(), messageToastText, Toast.LENGTH_LONG).show();
                                    }
                                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });

        Button buttonAddUser = (Button) findViewById(R.id.user_add_button);
        buttonAddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (TextUtils.isEmpty(userName.getText().toString()))
                    return;
                String newUsersName = userName.getText().toString();
                userName.setText("");
                users.add(newUsersName);
                adapter.notifyDataSetChanged();
                helper.insertUser(newUsersName);
            }
        });
    }


    private void initToolBar() {
        Toolbar toolBar = (Toolbar) findViewById(R.id.toolbar_filtr);
        toolBar.setTitle(R.string.app_filtr);
        toolBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.filtr_back:
                        onBackPressed();
                        break;
                }
                return false;
            }
        });
        toolBar.inflateMenu(R.menu.menu_filtr);
    }
}
