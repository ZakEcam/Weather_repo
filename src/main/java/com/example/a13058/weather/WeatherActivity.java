package com.example.a13058.weather;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class WeatherActivity extends AppCompatActivity implements ItemAdapter.ItemAdapterOnClickHandler,LoaderManager.LoaderCallbacks <String>,SharedPreferences.OnSharedPreferenceChangeListener
    {
    //private TextView mDisplay ;
    private RecyclerView resultView;
    private ItemAdapter itemAdapter;
    private static final int QUERY_LOADER = 22;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        //mDisplay = (TextView) findViewById(R.id.user_input );
        resultView = (RecyclerView) findViewById(R.id.resultView );

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL , false );
        resultView.setLayoutManager(layoutManager);
        resultView.setHasFixedSize(true );

        itemAdapter = new ItemAdapter(this);
        resultView.setAdapter(itemAdapter);

        //new QueryTask().execute("https://andfun-weather.udacity.com/weather");
        Bundle queryURL = new Bundle();
        queryURL.putString("URL","https://andfun-weather.udacity.com/weather");

        LoaderManager loaderManager = getSupportLoaderManager();
        // restartLoader init the Loader if it doesn ’t exist
        loaderManager.restartLoader(QUERY_LOADER ,queryURL , this);//au lieu de sauver dans la variable--on sauvera dans le sharedPreferences

        SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(this);
        ///// implémenter une méthode qui utilise key +value boolean
        sharedPreferences.getBoolean("my_boolean",false);
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);


    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key){
        if(key.equals("my_boolean")){
            Context context = this;
            String textToShow = "Settings...";
            Toast.makeText(context, textToShow, Toast.LENGTH_SHORT).show();


            sharedPreferences.getBoolean(key,false);
        }
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        PreferenceManager.getDefaultSharedPreferences(this).unregisterOnSharedPreferenceChangeListener(this);

    }


    /*public class QueryTask extends AsyncTask<String, Void, ArrayList<details>> {

        @Override
        protected ArrayList<details> doInBackground(String... params) {
            String searchUrl = params[0];
            String json = null;
            ArrayList<details> queryResults = null;

            try {

                json = NetworkUtils.getResponseFromHttpUrl(searchUrl);
                details.parse(json);
                queryResults = details.getWeathersList();

            } catch (Exception e) {
                e.printStackTrace();
            }
            return queryResults;
            //return buffer.toString();
        }

        @Override
        protected void onPostExecute(ArrayList<details> queryResults) {
            //super.onPostExecute(queryResults);
            //if (queryResults != null) {
            itemAdapter.setData(queryResults);
            // }

        }
    }*/

    @Override
    public void onClick(int index) {
        Context context = this;
        Class destinationClass = weath.class;
        Intent intent = new Intent(context, destinationClass);
        intent.putExtra(Intent.EXTRA_INDEX, index);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.weather_list, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemThatWasClickedId = item.getItemId();
        int index=0;
        if (itemThatWasClickedId == R.id.action) {
            Context context = this;
            Class destinationClass = SettingsActivity.class;
            Intent intent = new Intent(context, destinationClass);
            intent.putExtra(Intent.EXTRA_INDEX, index);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public Loader<String> onCreateLoader(int id, final Bundle args) {
        return new AsyncTaskLoader<String>(this) {

            String json = null;

            @Override
            protected void onStartLoading() {
                if(json != null) {
                    deliverResult(json);
                } else {
                    forceLoad();
                }
            }

            @Override
            public String loadInBackground() {
                String searchUrl = args.getString("URL");
                try {
                    Log.i("ASyncTaskLoader", "start query");
                    return NetworkUtils.getResponseFromHttpUrl(searchUrl);
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }

            @Override
            public void deliverResult(String data) {
                json = data;
                super.deliverResult(data);
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<String> loader, String data) {
        try {
            Log.i("ASyncTaskLoader", "query loaded");
            details.parse(data);
            itemAdapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
        itemAdapter.setData(details.getWeathersList());
    }

    @Override
    public void onLoaderReset(Loader<String> loader) {

    }
}
