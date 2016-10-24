package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;
import java.util.GregorianCalendar;

/**
 * Created by LockonS on 10/21/16.
 */

public class PID extends OpMode {
    //--------------
    DcMotor thisMotor;

    float time;
    float deltaTime;

    float lastEncValue;

    public float speed;
    private double curSpeed;
    MekaOp master;

    Calendar c;
    Timer deltaTimer;

    public PID(DcMotor mot, MekaOp m){
        c = Calendar.getInstance();
        time = 0;
        thisMotor = mot;
        speed = 0;
        init();
        master = m;
    }

    @Override
    public void init() {
        c = Calendar.getInstance();
        time = c.getTimeInMillis();

        thisMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        lastEncValue = 0;
        thisMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);


    }

    @Override
    public void loop() {

    }

    public void encUpdate(){
        //Set deltaTime based on device time
        c = Calendar.getInstance();
        deltaTime = c.getTimeInMillis() - time;
        time = c.getTimeInMillis();
        master.AddTelemetry("PID TIME " + thisMotor.getDeviceName() + ": ", Float.toString(time));

        //get motor's position and find speed
        int enc = thisMotor.getCurrentPosition();
        master.AddTelemetry("PID ENC " + thisMotor.getDeviceName() + ": ", Float.toString(enc));
        speed = rotSpeed(enc);
    }

    //Returns speed
    public float rotSpeed(float encVal){
        // Δpulses / Δtime
        float sp = (float)(Math.abs(encVal - lastEncValue)) / (deltaTime);
        lastEncValue = encVal;
        return sp;
    }
}
