package org.firstinspires.ftc.teamcode;

import android.widget.Button;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Emma on 11/15/16.
 */

public class MekaServo extends OpMode {

    CRServo button, gate;
    protected boolean gateGo;

    protected double buttonPwr = 0.5;
    protected double gatePwr = 0.15;

    //Constructor
    public MekaServo(CRServo butt, CRServo gatt){
        button = butt;
        gate = gatt;
        button.setPower(0);
        gateGo = false;
    }

    @Override
    public void init() {

    }

    @Override
    public void loop() {

    }

    public void pressButton() {
        Timer timer = new Timer();
        button.setPower(buttonPwr);
        timer.schedule(new TimerTask() {
            public void run() {
                button.setPower(-buttonPwr);
            }
        }, 1000);
        timer.schedule(new TimerTask() {
            public void run() {
                button.setPower(0);
            }
        }, 1000);
    }

    public void turnGate() {
        Timer timer = new Timer();
        gate.setPower(gatePwr);
        timer.schedule(new TimerTask() {
            public void run() {
                gate.setPower(0);
            }
        }, 300);
    }

    //Toggle between spinning and not spinning
    public void ToggleGate(int state){
        gate.setPower(gatePwr * state);

        /*if(state == 0){
            gate.setPower(gatePwr);
        }else if (state == 1){
            gate.setPower(0);
        }else {
            gate.setPower(-gatePwr);
        }
        */
    }

    public boolean getGateGo(){
        return gateGo;
    }

    public double getGatePower(){
        return gate.getPower();
    }

    public double getButtonPwr() {
        return button.getPower();
    }


}
