package com.ws.myntra.androidweb;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {

    private ListView listView;
    private ProgressDialog dlg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.listView);

        if(isNetworkAvailable()){
            dlg = ProgressDialog.show(this,"", "Getting City List. Please Wait...");
            cityTask task = new cityTask();
            task.execute("http://api.geonames.org/citiesJSON?north=44.1&south=-9.9&east=-22.4&west=55.2&lang=de&username=prateek.mehra");
        }
        else{
            Toast.makeText(this, "No network connectivity, please try again.", Toast.LENGTH_SHORT).show();
        }
    }

    private  boolean isNetworkAvailable(){
        ConnectivityManager cManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);

        NetworkInfo info = cManager.getActiveNetworkInfo();
        if(info!=null && info.isConnected())
            return true;
        return false;

    }

    private class cityTask extends AsyncTask<String, Void, JSONObject>{

        private ArrayList<City> cityList = new ArrayList<City>();
        @Override
        protected JSONObject doInBackground(String... params) {
            // communicate with web service and return the JSONObject response

            JSONObject response = null;
            String urls = params[0];
            try {
                URL url = new URL(urls);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                InputStream inputStream = urlConnection.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
                String line = br.readLine();
                String res="";
                while(line!=null){
                    res +=line;
                    line = br.readLine();
                }

            response = new JSONObject(res);

            } catch (Exception e) {
                Log.e("CityTask", "Error getting data from web service");
            }

            return response;
        }

        @Override
        protected void onPostExecute(JSONObject response) {
            dlg.cancel();
            if(response!=null){

                try {
                    JSONArray array = response.getJSONArray("geonames");
                    for( int i=0; i<array.length(); i++){
                        City city = new City();
                        JSONObject entry = array.getJSONObject(i);
                        city.name = entry.getString("name");
                        city.countryCode = entry.getString("countrycode");
                        city.wiki = entry.getString("wikipedia");
                        city.population = entry.getLong("population");
                        cityList.add(city);

                    }

                    //Bind city data to ListView
                    final ArrayAdapter<City> cityArrayAdapter = new ArrayAdapter<City>(MainActivity.this, android.R.layout.simple_expandable_list_item_1, android.R.id.text1, cityList);
                    listView.setAdapter(cityArrayAdapter);
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("http://" + cityList.get(position).wiki));
                            startActivity(i);
                        }
                    });



                } catch (JSONException e) {
                    Toast.makeText(MainActivity.this, "Error in parsing:" + e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }

            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
