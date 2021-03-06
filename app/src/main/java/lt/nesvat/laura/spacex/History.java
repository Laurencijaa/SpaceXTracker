package lt.nesvat.laura.spacex;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class History extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Flight>> {

    private static final String SPACEX_URL_PAST = "https://api.spacexdata.com/v3/launches/past?order=desc";
    private static final int FLIGHT_LOADER_ID = 1;
    private FlightAdapter flightAdapter;
    private TextView noFlightsTextView;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        //Find TextView responsible for showing no data information
        noFlightsTextView = findViewById(R.id.empty_view);

        //Get a reference to the LoaderManager, in order to interact with loaders.
        LoaderManager loaderManager = getLoaderManager();

        //Check for internet connection before initializing loader
        ConnectivityManager cm =(ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);

        if(cm != null) {
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

            //Update Loader
            if (isConnected) {
                loaderManager.initLoader(FLIGHT_LOADER_ID, null, this);
            } else {
                progressBar = findViewById(R.id.progress_bar);
                progressBar.setVisibility(View.GONE);
                noFlightsTextView.setText(R.string.no_internet);
            }
        }

        //Find reference to the ListView in the layout
        ListView listview = findViewById(R.id.list_history);
        //MakeSure that in case no data is available user will be informed
        listview.setEmptyView(noFlightsTextView);

        //Create new flight adapter and set it on ListView
        flightAdapter = new FlightAdapter(this, new ArrayList<Flight>());
        listview.setAdapter(flightAdapter);


        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                //Find current Flight that was clicked
                Flight currentFlight = flightAdapter.getItem(position);
                Intent detailsIntent = new Intent(History.this, DetailsActivity.class);
                detailsIntent.putExtra("FlightObject", currentFlight);
                History.this.startActivity(detailsIntent);
            }
        });

    }

    //When LoadManager determines that loader with our specified loader ID is not running,
    //new one will be created
    @Override
    public Loader<List<Flight>> onCreateLoader(int i, Bundle bundle) {
        return new SpaceXLoader(this, SPACEX_URL_PAST);
    }

    @Override
    public void onLoadFinished(Loader<List<Flight>> loader, List<Flight> flights) {

        progressBar = findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.GONE);
        noFlightsTextView.setText(String.valueOf(R.string.no_flights));

        //Clear adapter if there is previous flight data
        flightAdapter.clear();
        //If there is valid list of flights then add them to adapter
        if (flights != null && !flights.isEmpty()) {
            flightAdapter.addAll(flights);
        }
    }

    //When data on our loader is no longer valid
    @Override
    public void onLoaderReset(Loader<List<Flight>> loader) {
        flightAdapter.clear();
    }
}