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

    private double buttonPwr = 0.5;
    private double gatePwr = 0.15;

    //In milliseconds
    private long gateTime = 300;
    private long buttonTime = 1400;

    Timer timer = new Timer();

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
        button.setPower(buttonPwr);
        timer.schedule(new TimerTask() {
            public void run() {
                button.setPower(-buttonPwr);
            }
        }, buttonTime);
        timer.schedule(new TimerTask() {
            public void run() {
                button.setPower(0);
            }
        }, 2*buttonTime);
    }

    public void turnGate() {
        gate.setPower(gatePwr);
        timer.schedule(new TimerTask() {
            public void run() {
                gate.setPower(0);
            }
        }, gateTime);
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

    public double getButtonTime() { return buttonTime; }


}
