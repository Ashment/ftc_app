package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

/**
 * Created by Emma on 12/6/16.
 */

public class PikachuControl extends OpMode {

    double epsilon = 0.01;

    // angle will be in degrees
    double angle = 0;


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
            angle = Math.toDegrees(Math.atan2(y, x));
            return angle;
        }
        return angle;
    }

    public double powerFLRR (double x, double y) {
        double angle = rQuadrant(x,y);
        double k = (Math.max(Math.abs(Math.sin((Math.toRadians(angle - 45)))), Math.abs(-Math.sin((Math.toRadians(angle - 135))))));
        double a = Math.sin((Math.toRadians(angle - 45))) * (1/k);
        return a;
    }

    public double powerFRBL(double x, double y) {
        double angle = rQuadrant(x,y);
        double k = (Math.max(Math.abs(Math.sin((Math.toRadians(angle - 45)))), Math.abs(-Math.sin((Math.toRadians(angle - 135))))));
        double b = -Math.sin((Math.toRadians(angle - 135))) * (1/k);
        return b;
    }

    public double rDistance(double x, double y) {
        double distance = Math.sqrt(x * x + y * y);
        return distance;
    }

    public double powerGraph(int mode, double theta){
        //Call for power of each set of wheels!
        //Use modes to control results:
        //  mode 1 = up right, bottom left
        //  mode 2 = up left, bottom right
        double graphValue = 0;
        //Pi
        double p = Math.PI;

        if (theta < 0) {
            theta += 360;
        }


        //Angle in radians
        double ang = Math.toRadians(theta);
        //temporary angle (changeable)
        double temp = ang;
        if(mode == 1){
            if(ang <= (p/2)){
                //0-90
                graphValue = 1;
            }else if(ang <= p){
                //90-180
                temp -= (p/2);
                graphValue = 1 - (temp * (4/p));
            }else if(ang <= ((3*p)/2)){
                //180-270
                graphValue = -1;
            }else{
                //270-360
                temp -= ((3*p/2));
                graphValue = -1 + (temp * (4/p));
            }
        }else{
            if(ang <= (p/2)){
                //0-90
                graphValue = -1 + (temp * (4/p));
            }else if(ang <= p){
                //90-180
                graphValue = 1;
            }else if(ang <= ((3*p)/2)){
                //180-270
                temp -= p;
                graphValue = 1 - (temp * (4/p));
            }else{
                //270-360
                graphValue = -1;
            }
        }

        return graphValue;
    }

    @Override
    public void init() {

    }

    @Override
    public void loop() {

    }
}
