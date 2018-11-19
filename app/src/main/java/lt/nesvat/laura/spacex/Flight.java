package lt.nesvat.laura.spacex;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

class Flight implements Serializable{

    private int flightDateUnix;
    private int flightNumber;
    private String rocketName;
    private String launchSiteName;
    private Boolean reuse;
    private String flightDetails;
    private String videoUrl;
    private boolean launchSuccess;
    private boolean isUpcoming;

    Flight(int flightDateUnix, int flightNumber, String rocketName, String launchSiteName, boolean launchSuccess, Boolean reuse, String flightDetails, String videoUrl, boolean isUpcoming) {
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

    int getFlightDateUnix() {
        return flightDateUnix;
    }

    String getFlightNumber() {
        return "Flight Number: " + flightNumber;
    }

    String getRocketName() {
        return rocketName;
    }

    String getLaunchSiteName() {
        return launchSiteName;
    }

    String isLaunchSuccess() {
        if(launchSuccess) {
            return "Launch Success!";
        } else {
            return "Launch Failed";
        }

    }

    boolean getLaunchSuccessState(){
        return launchSuccess;
    }

    String isReuse() {
        if(reuse == null) {
            return "Reuse: NO information";
        } else if (reuse){
            return "Reuse: YES";
        } else {
            return "Reuse: NO";
        }
    }

    String getFlightDetails() {
        return flightDetails;
    }

    String getVideoUrl() {
        return videoUrl;
    }

    boolean isUpcoming() {
        return isUpcoming;
    }

    String getLocalDate(int flightDateUnix) {
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
