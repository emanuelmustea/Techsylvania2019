package com.mindorks.demo;

import com.mindorks.demo.Models.Attraction;

public class DistanceCalculator {
    public static String getDistanceInKilometers(Attraction attraction)
    {
        double distanceInMeters = getDistance(attraction) + 300;
        double kms = distanceInMeters / 1000;
        kms=Math.round(kms * 10);
        kms /=10.0;

        return Double.toString(kms);
    }
    public static double getDistance(Attraction attraction)
    {
        double x = Double.parseDouble(attraction.getXLoc());
        double y= Double.parseDouble(attraction.getYLoc());
        return gps2m(x, y, 47.7962289, 22.8718055);
    }

    public static double gps2m(double lat_a, double lng_a, double lat_b, double lng_b) {
        double pk = (double) (180/3.14169);

        double a1 = lat_a / pk;
        double a2 = lng_a / pk;
        double b1 = lat_b / pk;
        double b2 = lng_b / pk;

        double t1 = Math.cos(a1)*Math.cos(a2)*Math.cos(b1)*Math.cos(b2);
        double t2 = Math.cos(a1)*Math.sin(a2)*Math.cos(b1)*Math.sin(b2);
        double t3 = Math.sin(a1)*Math.sin(b1);
        double tt = Math.acos(t1 + t2 + t3);

        return 6366000*tt;
    }
}
