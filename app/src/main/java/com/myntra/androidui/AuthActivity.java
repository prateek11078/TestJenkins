package com.myntra.androidui;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class AuthActivity extends Activity {

    EditText userIDE;
    EditText passE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        displayNotification();
        userIDE = (EditText) findViewById(R.id.UserIDE);
        passE = (EditText) findViewById(R.id.PassE);
    }

    private void displayNotification() {
        NotificationManager nManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Notification.Builder builder = new Notification.Builder(this);
        builder.setSmallIcon(R.drawable.ic_launcher);
        builder.setTicker("Please Login!");
        builder.setContentTitle("Authentication");
        builder.setContentText("Action requires login");

        Intent i = new Intent(this, MainActivity.class);
        PendingIntent pi = PendingIntent.getActivity(this,0,i,0);

        builder.setContentIntent(pi);
        //builder.setAutoCancel(true);

        //Can add light, sound and vibrate.

        Notification myNotification = builder.build();
        nManager.notify(1,myNotification);
    }


   public void submitClick(View v){
        String userid = userIDE.getText().toString();
        String password = passE.getText().toString();

        if(userid.length()>0 && password.length()>0){
            Toast.makeText(this, "Userid: "+userid + "\nPassword: "+ password, Toast.LENGTH_LONG).show();

            //save data to sharedPref
            SharedPreferences pref = getSharedPreferences("auth", MODE_PRIVATE);
            SharedPreferences.Editor editor = pref.edit();
            editor.putString("userid", userid);
            editor.putString("password", password);
            editor.commit();

            /*
            DBWrapper wrapper = new DBWrapper(this);
            long rowid = wrapper.addCredentials(userid, password);
            if(rowid != -1)
                Toast.makeText(this, "Rowid = "+rowid, Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(this, "Error saving data", Toast.LENGTH_SHORT).show();

            wrapper.closeDatabase();
            */
        }
        else {
            Toast.makeText(this, "Please enter all fields", Toast.LENGTH_LONG).show();
        }

    }

    public void cancelClick(View v){
        finish();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_auth, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
