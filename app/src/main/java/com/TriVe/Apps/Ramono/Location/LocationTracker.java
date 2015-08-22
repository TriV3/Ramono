package com.TriVe.Apps.Ramono.Location;


import android.location.Location;

/**
 * <b>Used for location.</b>
 *
 * <p>http://gabesechansoftware.com/location-tracking/</p>
 */
public interface LocationTracker {
    public interface LocationUpdateListener{
        public void onUpdate(Location oldLoc, long oldTime, Location newLoc, long newTime);
    }

    public void start();
    public void start(LocationUpdateListener update);

    public void stop();

    public boolean hasLocation();

    public boolean hasPossiblyStaleLocation();

    public Location getLocation();

    public Location getPossiblyStaleLocation();

}
