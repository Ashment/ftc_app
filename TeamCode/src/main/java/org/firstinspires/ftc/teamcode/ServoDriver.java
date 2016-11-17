package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.*;

/**
 * Created by Ashment on 10/28/16.
 */

/**
 * TODO
 * LEGEND: <Feature> [Completed][Tested][Interfaced]
 *
 * Sway                             √--
 * Toggle                           √--
 * Getters and Setters              ---
 */


public class ServoDriver extends OpMode {

    Servo servo;
    boolean toggle;
    double tolerance = 0.03;
    double neutralPos, leftPos, rightPos;

    public ServoDriver(Servo servoIn, boolean isToggle, double swayAmt) {
        servo = servoIn;
        toggle = isToggle;
        if(!toggle) {
            neutralPos = 0.5;
            leftPos = 0.5 - swayAmt;
            rightPos = 0.5 + swayAmt;
        }
    }

    public void init(){
        if(toggle){
            servo.setPosition(leftPos);
        }else{
            servo.setPosition(neutralPos);
        }
    }

    public void loop(){
    }

    public void toggle(){
        if(toggle) {
            if (servo.getPosition() < (leftPos + tolerance)) {
                servo.setPosition(rightPos);
            } else if (servo.getPosition() > (rightPos - tolerance)) {
            servo.setPosition(leftPos);
        }
    }
}

    public void sway(double direction) {
        if(!toggle) {
            if (servo.getPosition() < (neutralPos + tolerance) & servo.getPosition() > (neutralPos - tolerance)) {
                if (direction < 0) {
                    servo.setPosition(leftPos);
                } else {
                    servo.setPosition(rightPos);
                }
            }else{
                servo.setPosition(neutralPos);
            }
        }
    }
}
