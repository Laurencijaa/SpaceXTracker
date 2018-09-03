package lt.nesvat.laura.spacex;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class History extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        ArrayList<Flight> historyFlights = new ArrayList<>();
        historyFlights.add(new Flight(
                1533619080,
                "Falcon 9",
                true,
                false));
        historyFlights.add(new Flight(
                1533619080,
                "Falcon 9",
                true,
                false));
        historyFlights.add(new Flight(
                1533619080,
                "Falcon 9",
                true,
                false));
        historyFlights.add(new Flight(
                1533619080,
                "Falcon 9",
                false,
                false));
        historyFlights.add(new Flight(
                1533619080,
                "Falcon 9",
                true,
                false));
        historyFlights.add(new Flight(
                1533619080,
                "Falcon 9",
                true,
                false));
        historyFlights.add(new Flight(
                1533619080,
                "Falcon 9",
                true,
                false));
        historyFlights.add(new Flight(
                1533619080,
                "Falcon 9",
                true,
                false));
        historyFlights.add(new Flight(
                1533619080,
                "Falcon 9",
                true,
                false));
        historyFlights.add(new Flight(
                1533619080,
                "Falcon 9",
                false,
                false));
        historyFlights.add(new Flight(
                1533619080,
                "Falcon 9",
                true,
                false));






        FlightAdapter adapter = new FlightAdapter(this, historyFlights);
        ListView listview = (ListView) findViewById(R.id.list_history);
        listview.setAdapter(adapter);

    }
}
