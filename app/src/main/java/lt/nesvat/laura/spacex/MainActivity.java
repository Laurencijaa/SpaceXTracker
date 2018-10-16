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
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Flight>> {

    private static final String SPACEX_URL_PAST = "https://api.spacexdata.com/v3/launches/past";
    private static final String SPACEX_URL_FUTURE = "https://api.spacexdata.com/v3/launches/upcoming";
    private static final int FLIGHT_LOADER_ID = 1;
    private FlightAdapter flightAdapter;
    private TextView noFlightsTextView;
    private ProgressBar progressBar;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //So that the name next to icon is different than activity label
        this.setTitle(getResources().getString(R.string.main_label));

        //Find TextView responsible for showing no data information
        noFlightsTextView = findViewById(R.id.empty_view);

        //Get a reference to the LoaderManager, in order to interact with loaders.
        LoaderManager loaderManager = getLoaderManager();

        //Check for internet connection before initializing loader
        ConnectivityManager cm =(ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

        //Update Loader
        if(isConnected){
            loaderManager.initLoader(FLIGHT_LOADER_ID, null, this);
        } else {
            progressBar = (ProgressBar) findViewById(R.id.progress_bar);
            progressBar.setVisibility(View.GONE);
            noFlightsTextView.setText("No Internet Connection");
        }

        //Find reference to the ListView in the layout
        ListView listview = (ListView) findViewById(R.id.list);
        //MakeSure that in case no data is available user will be informed
        listview.setEmptyView(noFlightsTextView);

        //Create new flight adapter and set it on ListView
        flightAdapter = new FlightAdapter(this, new ArrayList<Flight>());
        listview.setAdapter(flightAdapter);


        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                //Find current Flight that was clicked
                Flight currentFlight = (Flight) flightAdapter.getItem(position);
                Intent detailsIntent = new Intent(MainActivity.this, DetailsActivity.class);
                detailsIntent.putExtra("FlightObject", currentFlight);
                MainActivity.this.startActivity(detailsIntent);
            }
        });


        Button historyButton = (Button) findViewById(R.id.button_history);
        historyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), History.class);
                view.getContext().startActivity(intent);

            }
        });

    }

    //When LoadManager determines that loader with our specified loader ID is not running,
    //new one will be created
    @Override
    public Loader<List<Flight>> onCreateLoader(int i, Bundle bundle) {
        return new SpaceXLoader(this, SPACEX_URL_FUTURE);
    }

    @Override
    public void onLoadFinished(Loader<List<Flight>> loader, List<Flight> flights) {

        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.GONE);
        noFlightsTextView.setText("No flights found");

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

//Todo:Store all Strings in the String resource
//Todo: make cat meow
//Todo: Fix landscape mode
//Todo: When app is inslalled it should appear on desktop - https://stackoverflow.com/questions/16873256/how-to-add-shortcut-to-home-screen-in-android-programmatically
//Todo: Make youtube link more readable