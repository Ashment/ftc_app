package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

/**
 * Created by Emma on 12/6/16.
 */

public class PikachuControl extends OpMode {

    double epsilon = 0.01;
    double angle = 0;

    public PikachuControl(){

    }

    @Override
    public void init() {

    }

    @Override
    public void loop() {

    }

    // The Quadrants go 1 for right, and 2 to 8 counterclockwise; and then 0 in the middle.
    public double rQuadrant(double x, double yy){
        double y = - yy;
        if (Math.abs(x) <= epsilon && Math.abs(y) <= epsilon) {
            return 0;
        }
        else if (Math.abs(x) <= epsilon && Math.abs(y) >= epsilon) {
            if (y > 0) {
                angle = 90;
                return angle;
            }
            else if (y < 0) {
                angle = 270;
                return angle;
            }
        }
        else {
            double angle = Math.atan2(y, x);
            return (int) Math.ceil((angle + 22.5) /45);
        }
        return angle;
    }

    public double powerFLRR (double x, double y) {
        double angle = rQuadrant(x,y);
        double k = (Math.max(Math.abs(Math.sin(angle - 45)), Math.abs(-Math.sin(angle - 135))));
        return Math.sin(angle - 45) * (1/k);
    }

    public double powerFRBL (double x, double y) {
        double angle = Math.atan2(x,y);
        double k = (Math.max(Math.abs(Math.sin(angle - 45)), Math.abs(-Math.sin(angle - 135))));
        return -Math.sin(angle - 135) * (1/k);
    }

    public double rDistance(double x, double y) {
        double distance = Math.sqrt(x * x + y * y);
        return distance;
    }
}
