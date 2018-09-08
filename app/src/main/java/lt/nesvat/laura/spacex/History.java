package lt.nesvat.laura.spacex;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class History extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        final ArrayList<Flight> historyFlights = new ArrayList<>();
        historyFlights.add(new Flight(
                1533619080,
                67,
                "Falcon 9",
                "Cape Canaveral Air Force Station Space Launch Complex 40",
                true,
                true,
                "Indonesian comsat intended to replace the aging Telkom 1 at 108° E. First reflight of a Block 5-version booster.",
                "https://www.youtube.com/watch?v=FjfQNBYv2IY",
                false));
        historyFlights.add(new Flight(
                1533619080,
                68,
                "Falcon 9",
                "Cape Canaveral Air Force Station Space Launch Complex 40",
                true,
                true,
                "Indonesian comsat intended to replace the aging Telkom 1 at 108° E. First reflight of a Block 5-version booster.",
                "https://www.youtube.com/watch?v=FjfQNBYv2IY",
                false));
        historyFlights.add(new Flight(
                1533619080,
                69,
                "Falcon 9",
                "Cape Canaveral Air Force Station Space Launch Complex 40",
                false,
                true,
                "Indonesian comsat intended to replace the aging Telkom 1 at 108° E. First reflight of a Block 5-version booster.",
                "https://www.youtube.com/watch?v=FjfQNBYv2IY",
                false));
        historyFlights.add(new Flight(
                1533619080,
                70,
                "Falcon 9",
                "Cape Canaveral Air Force Station Space Launch Complex 40",
                true,
                true,
                "Indonesian comsat intended to replace the aging Telkom 1 at 108° E. First reflight of a Block 5-version booster.",
                "https://www.youtube.com/watch?v=FjfQNBYv2IY",
                false));
        historyFlights.add(new Flight(
                1533619080,
                71,
                "Falcon 9",
                "Cape Canaveral Air Force Station Space Launch Complex 40",
                true,
                true,
                "Indonesian comsat intended to replace the aging Telkom 1 at 108° E. First reflight of a Block 5-version booster.",
                "https://www.youtube.com/watch?v=FjfQNBYv2IY",
                false));



        FlightAdapter adapter = new FlightAdapter(this, historyFlights);
        ListView listview = (ListView) findViewById(R.id.list_history);
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent detailsIntent = new Intent(History.this, DetailsActivity.class);
                detailsIntent.putExtra("FlightObject", historyFlights.get(position));
                History.this.startActivity(detailsIntent);
            }
        });

    }
}
