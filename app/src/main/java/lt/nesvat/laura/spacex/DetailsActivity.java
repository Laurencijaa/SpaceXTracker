package lt.nesvat.laura.spacex;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Intent i = getIntent();
        final Flight flight = (Flight) i.getSerializableExtra("FlightObject");

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

        TextView videoText = findViewById(R.id.video_info);
        if(flight.isUpcoming()) {
            videoText.setVisibility((View.GONE));
        }

        ImageButton video = findViewById(R.id.video_button);
        if(flight.isUpcoming()) {
            video.setVisibility(View.GONE);
        }else {
            video.setVisibility(View.VISIBLE);
            video.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse(flight.getVideoUrl())));
                }
            });
        }
    }

}

//todo: Add back arrow
//Todo: Details returns null if empty, fix that