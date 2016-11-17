package org.firstinspires.ftc.teamcode;

import android.widget.Button;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

/**
 * Created by Emma on 11/15/16.
 */

public class MekaServo extends OpMode {

    Servo button, gate;
    int ButtonState = 0;
    boolean gateGo;

    double lPos = 0.08;
    double nPos = 0.5;
    double rPos = 0.93;
    double tolerance = 0.05;
    private double targetPos;

    //Constructor
    public MekaServo(Servo butt, Servo gatt){
        button = butt;
        gate = gatt;
        gateGo = false;

        button.setPosition(nPos);
    }

    @Override
    public void init() {

    }

    @Override
    public void loop() {

    }

    //Toggle the position of the button press servo
    public void ChangeButtonPosition(double dir){
        if(button.getPosition() < targetPos + tolerance && button.getPosition() > targetPos - tolerance) {
            if (button.getPosition() < nPos + tolerance && button.getPosition() > nPos - tolerance) {
                if (dir > 0) {
                    targetPos = rPos;
                } else if (dir < 0) {
                    targetPos = lPos;
                }
            } else if (button.getPosition() < lPos + tolerance || button.getPosition() > rPos - tolerance) {
                targetPos = nPos;
            }
        }

        Range.clip(targetPos, 0, 1);
        button.setPosition(targetPos);

        /*
        if(mode && ButtonState < 1){
            ButtonState++;
        }else if(ButtonState > -1){
            ButtonState--;
        }

        double temp = (ButtonState / 2) + (0.5);
        temp = Range.clip(temp, 0.1, 0.9);
        button.setPosition(temp);
        */

    }

    //Toggle between spinning and not spinning
    public void ToggleGate(){
        gateGo = !gateGo;

        if(gateGo){
            gate.setPosition(0.9);
        }else{
            gate.setPosition(0.5);
        }
    }
}
