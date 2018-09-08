package lt.nesvat.laura.spacex;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ArrayList <Flight> futureFlights = new ArrayList<>();
        futureFlights.add(new Flight(
                1533619080,
                67,
                "Falcon 9",
                "Cape Canaveral Air Force Station Space Launch Complex 40",
                true,
                true,
                "Indonesian comsat intended to replace the aging Telkom 1 at 108° E. First reflight of a Block 5-version booster.",
                "https://www.youtube.com/watch?v=FjfQNBYv2IY",
                true));
        futureFlights.add(new Flight(
                1533619080,
                68,
                "Falcon 9",
                "Cape Canaveral Air Force Station Space Launch Complex 40",
                true,
                true,
                "Indonesian comsat intended to replace the aging Telkom 1 at 108° E. First reflight of a Block 5-version booster.",
                "https://www.youtube.com/watch?v=FjfQNBYv2IY",
                true));
        futureFlights.add(new Flight(
                1533619080,
                69,
                "Falcon 9",
                "Cape Canaveral Air Force Station Space Launch Complex 40",
                true,
                true,
                "Indonesian comsat intended to replace the aging Telkom 1 at 108° E. First reflight of a Block 5-version booster.",
                "https://www.youtube.com/watch?v=FjfQNBYv2IY",
                true));
        futureFlights.add(new Flight(
                1533619080,
                70,
                "Falcon 9",
                "Cape Canaveral Air Force Station Space Launch Complex 40",
                true,
                true,
                "Indonesian comsat intended to replace the aging Telkom 1 at 108° E. First reflight of a Block 5-version booster.",
                "https://www.youtube.com/watch?v=FjfQNBYv2IY",
                true));
        futureFlights.add(new Flight(
                1533619080,
                71,
                "Falcon 9",
                "Cape Canaveral Air Force Station Space Launch Complex 40",
                true,
                true,
                "Indonesian comsat intended to replace the aging Telkom 1 at 108° E. First reflight of a Block 5-version booster.",
                "https://www.youtube.com/watch?v=FjfQNBYv2IY",
                true));


        FlightAdapter adapter = new FlightAdapter(this, futureFlights);
        ListView listview = (ListView) findViewById(R.id.list);
        listview.setAdapter(adapter);


        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent detailsIntent = new Intent(MainActivity.this, DetailsActivity.class);
                detailsIntent.putExtra("FlightObject", futureFlights.get(position));
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
}
