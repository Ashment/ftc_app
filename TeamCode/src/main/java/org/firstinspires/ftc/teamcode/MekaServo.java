package org.firstinspires.ftc.teamcode;

import android.widget.Button;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

/**
 * Created by Emma on 11/15/16.
 */

public class MekaServo extends OpMode {

    Servo button;
    CRServo gate;
    private int ButtonState = 0;
    private boolean gateGo;

    private double lPos = 0.08;
    private double nPos = 0.5;
    private double rPos = 0.93;
    private double tolerance = 0.05;
    private double gatePwr = 0.3;
    private double targetPos;

    //Constructor
    public MekaServo(Servo butt, CRServo gatt){
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

    public void ButtonPositionUpate(double dir){
        if(dir > 0){
            button.setPosition(rPos);
        }else if(dir < 0){
            button.setPosition(lPos);
        }else{
            button.setPosition(nPos);
        }
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

    }

    //Toggle between spinning and not spinning
    public void ToggleGate(boolean state){
        if(state){
            gate.setPower(gatePwr);
        }else{
            gate.setPower(0);
        }
    }

    public boolean getGateGo(){
        return gateGo;
    }

    public double getGatePower(){
        return gate.getPower();
    }


}
