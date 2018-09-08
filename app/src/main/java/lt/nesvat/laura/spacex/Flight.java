package lt.nesvat.laura.spacex;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class Flight implements Serializable{

    private int flightDateUnix;
    private int flightNumber;
    private String rocketName;
    private String launchSiteName;
    private boolean reuse;
    private String flightDetails;
    private String videoUrl;
    private boolean launchSuccess;
    private boolean isUpcoming;

    public Flight(int flightDateUnix, int flightNumber, String rocketName, String launchSiteName, boolean launchSuccess, boolean reuse, String flightDetails, String videoUrl, boolean isUpcoming) {
        this.flightDateUnix = flightDateUnix;
        this.flightNumber = flightNumber;
        this.rocketName = rocketName;
        this.launchSiteName = launchSiteName;
        this.launchSuccess = launchSuccess;
        this.reuse = reuse;
        this.flightDetails = flightDetails;
        this.videoUrl = videoUrl;
        this.isUpcoming = isUpcoming;
    }

    public int getFlightDateUnix() {
        return flightDateUnix;
    }

    public String getFlightNumber() {
        return "Flight number: " + flightNumber;
    }

    public String getRocketName() {
        return rocketName;
    }

    public String getLaunchSiteName() {
        return launchSiteName;
    }

    public String isLaunchSuccess() {
        if(launchSuccess) {
            return "Launch Success";
        } else {
            return "Launch Failed";
        }

    }

    public boolean getLaunchSuccessState(){
        return launchSuccess;
    }

    public String isReuse() {
        if(reuse){
            return "Reuse: YES";
        } else {
            return "Reuse: NO";
        }
    }

    public String getFlightDetails() {
        return flightDetails;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public boolean isUpcoming() {
        return isUpcoming;
    }

    public String getLocalDate(int flightDateUnix) {
        //Convert timestamp to date
        Date date = new Date ();
        date.setTime((long) flightDateUnix*1000);

        //Create formatting for date and get users timezone
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy MMM dd \nhh:mm:ss a", Locale.getDefault());

        // set timezone to SimpleDateFormat
        sdf.setTimeZone(TimeZone.getTimeZone("Europe/Helsinki"));

        //return date in sdf format with timeZone as String
        return sdf.format(date);
    }
}
