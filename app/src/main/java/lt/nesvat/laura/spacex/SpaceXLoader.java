package lt.nesvat.laura.spacex;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;


public class SpaceXLoader extends AsyncTaskLoader <List<Flight>> {
    private String mUrl;

    SpaceXLoader(Context context, String mUrl) {
        super(context);
        this.mUrl = mUrl;
    }

    @Override
    public List<Flight> loadInBackground() {
        //Check if provided URL is valid
        if(mUrl == null) {
            return null;
        }

        //Read JSON response and return list of flights
        return QueryUtils.extractFlights(mUrl);
    }

    //Triggering loadInBackground method to execute
    @Override
    protected void onStartLoading() {
        forceLoad();
    }
}
