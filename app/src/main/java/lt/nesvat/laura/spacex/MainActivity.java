package lt.nesvat.laura.spacex;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Todo: make background color Indigo 50 (#E8EAF6, #C5CAE9, #9FA8DA, https://material.io/design/color/the-color-system.html#tools-for-picking-colors)

        ArrayList <Flight> futureFlights = new ArrayList<>();
        futureFlights.add(new Flight(
                1535254380,
                "Falcon 9",
                true));
        futureFlights.add(new Flight(
                1533619080,
                "Falcon 9",
                true));
        futureFlights.add(new Flight(
                1533619080,
                "Falcon 9",
                true));
        futureFlights.add(new Flight(
                1533619080,
                "Falcon 9",
                true));
        futureFlights.add(new Flight(
                1533619080,
                "Falcon 9",
                true));
        futureFlights.add(new Flight(
                1533619080,
                "Falcon 9",
                true));
        futureFlights.add(new Flight(
                1533619080,
                "Falcon 9",
                true));
        futureFlights.add(new Flight(
                1533619080,
                "Falcon 9",
                true));
        futureFlights.add(new Flight(
                1533619080,
                "Falcon 9",
                true));
        futureFlights.add(new Flight(
                1533619080,
                "Falcon 9",
                true));
        futureFlights.add(new Flight(
                1533619080,
                "Falcon 9",
                true));
        futureFlights.add(new Flight(
                1533619080,
                "Falcon 9",
                true));
        futureFlights.add(new Flight(
                1533619080,
                "Falcon 9",
                true));
        futureFlights.add(new Flight(
                1533619080,
                "Falcon 9",
                true));


        FlightAdapter adapter = new FlightAdapter(this, futureFlights);
        ListView listview = (ListView) findViewById(R.id.list);
        listview.setAdapter(adapter);

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
