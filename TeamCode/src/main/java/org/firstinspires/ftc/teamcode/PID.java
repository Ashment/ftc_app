package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by LockonS on 10/21/16.
 */

public class PID extends OpMode {
    //--------------
    DcMotor thisMotor;

    Calendar c;
    float time;
    float deltaTime;

    float lastEncValue;

    public float speed;
    MekaOp master;

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

    public float rotSpeed(float encVal){
        float sp = (float)(Math.abs(encVal - lastEncValue)) / (deltaTime);
        lastEncValue = encVal;
        return sp;
    }
}
