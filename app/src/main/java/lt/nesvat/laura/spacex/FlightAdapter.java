package lt.nesvat.laura.spacex;

import android.app.Activity;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class FlightAdapter extends ArrayAdapter {

    public FlightAdapter (Activity context, ArrayList<Flight> flights){
        super(context, 0, flights);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        Flight currentFlight = (Flight) getItem(position);

        TextView dateTextView = (TextView) listItemView.findViewById(R.id.date_text_view);
        dateTextView.setText(currentFlight.getLocalDate(currentFlight.getFlightDateUnix()));

        TextView rocketNameTextView = (TextView) listItemView.findViewById(R.id.rocket_name_text_view);
        rocketNameTextView.setText(currentFlight.getRocketName());

        TextView launchSuccessTextView = (TextView) listItemView.findViewById(R.id.launch_success_text_view);
        if(currentFlight.isUpcoming()) {
            launchSuccessTextView.setVisibility(View.GONE);
        }else {
            launchSuccessTextView.setText(currentFlight.isLaunchSuccess());
            launchSuccessTextView.setVisibility(View.VISIBLE);
            if (currentFlight.getLaunchSuccessState()) {
                launchSuccessTextView.setTextColor(ContextCompat.getColor(this.getContext(), R.color.launchSuccess));
            } else {
                launchSuccessTextView.setTextColor(ContextCompat.getColor(this.getContext(), R.color.launchFail));
            }
        }
        return listItemView;

    }
}
