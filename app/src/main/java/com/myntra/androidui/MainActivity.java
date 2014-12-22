package com.myntra.androidui;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity implements View.OnClickListener{
    private Button logonButton;
    private ListView lv;

    private String[] data = {"Android", "iOS", "Windows"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lv = (ListView) findViewById(R.id.listView);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, data);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selected = data[position];
                Toast.makeText(MainActivity.this, "Selection = "+ selected, Toast.LENGTH_SHORT).show();
            }
        });
        lv.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        logonButton = (Button) findViewById(R.id.LogonB);
        logonButton.setOnClickListener(this);
        registerForContextMenu(logonButton);

        //cancel notification
        NotificationManager nManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        nManager.cancel(1);
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.add(0, 1, 0, "CONTINUE");
        menu.add(0, 2, 0, "CANCEL");
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if(item.getItemId() == 1){
            Intent i = new Intent(this, AuthActivity.class);
            startActivity(i);
            return true;
        }
        else if(item.getItemId() == 2){
            return true;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        menu.add(0, 1, 0, "LOGON");
        menu.add(0, 2, 0, "EXIT");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == 1) {
            Intent i = new Intent(this, AuthActivity.class);
            startActivity(i);
            return true;
        }
        else if (id == 2){
            //finish();
            MyDialog dlg = new MyDialog();
            dlg.setCancelable(false);
            dlg.show(getFragmentManager(), "xyz");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.LogonB)
        {

            //read from sharedPref
            SharedPreferences pref = getSharedPreferences("auth", MODE_PRIVATE);
            String userid = pref.getString("userid", null);
            String password = pref.getString("password", null);

            /*
            String userid = null, password = null;
            DBWrapper wrapper  = new DBWrapper(this);
            Cursor c = wrapper.getCredentials();
            if(c.getCount()>0){
                c.moveToFirst();
                userid = c.getString(0);
                password = c.getString(1);

                /*if difficult to remember column numbers >>

                    password = c.getString(c.getColumnIndex(DBHelper.CLM_PASSWORD));

            }
        */

            if(userid==null || password==null) {
                Intent i = new Intent(this, AuthActivity.class);
                startActivity(i);
            }
            else{
                Toast.makeText(this, "Already Authenticated!", Toast.LENGTH_SHORT).show();
        }
        }

    }
}
