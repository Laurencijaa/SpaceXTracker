package lt.nesvat.laura.spacex;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Intent i = getIntent();
        Flight flight = (Flight) i.getSerializableExtra("FlightObject");

        TextView date = findViewById(R.id.date_text_view);
        date.setText(flight.getLocalDate(flight.getFlightDateUnix()));

        TextView flightNumber = findViewById(R.id.flight_number_text_view);
        flightNumber.setText(flight.getFlightNumber());

        TextView rocketName = findViewById(R.id.rocket_name_text_view);
        rocketName.setText(flight.getRocketName());

        TextView launchSite = findViewById(R.id.launch_site_text_view);
        launchSite.setText(flight.getLaunchSiteName());

        TextView launchSuccess = findViewById(R.id.launch_success_text_view);
        if(flight.isUpcoming()) {
            launchSuccess.setVisibility(View.GONE);
        }else {
            launchSuccess.setText(flight.isLaunchSuccess());
            launchSuccess.setVisibility(View.VISIBLE);
        }

        TextView reuse = findViewById(R.id.reuse_text_view);
        reuse.setText(flight.isReuse());

        TextView details = findViewById(R.id.details_text_view);
        if(flight.isUpcoming()) {
            details.setVisibility(View.GONE);
        }else {
            details.setText(flight.getFlightDetails());
            details.setVisibility(View.VISIBLE);
        }

        TextView video = findViewById(R.id.video_text_view);
        if(flight.isUpcoming()) {
            video.setVisibility(View.GONE);
        }else {
            video.setText(flight.getVideoUrl());
            video.setVisibility(View.VISIBLE);
        }
    }
}
