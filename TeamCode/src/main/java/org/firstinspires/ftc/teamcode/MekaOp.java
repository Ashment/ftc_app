package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

import java.util.TimerTask;

/**
 * Created by Ashment on 10/17/16.
 */

@TeleOp (name="MekaOp", group="Test")
public class MekaOp extends OpMode{

    ////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////// VARIABLES ////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////

    //Basics
    DcMotor motfl,motfr,motrr,motrl;
    PID pidfl, pidfr, pidrr, pidrl;

    //Drivers
    MekaDrive meka;
    ButtonState joy1, joy2;

    @Override
    public void init(){
        //Hardware Setup
        setupMotors();

        //Drivers and Objects Setup
        try{
            joy1 = new ButtonState();
            joy2 = new ButtonState();
        }catch (Exception e){
            telemetry.addData("ERROR:", e.toString());
        }

        try{
            meka = new MekaDrive(motfl, motfr, motrr, motrl, false, false);
            meka.setEnableExpo(false);
            telemetry.addData("Sucess: ", "MekaDrive Setup Complete.");
        }catch (Exception e){
            telemetry.addData("ERROR: ", "MekaDrive Setup Failure.");
        }

        try{
            //create a PID for each motfl
            pidfl = new PID(motfl, this);
            pidfr = new PID(motfr, this);
            pidrr = new PID(motrr, this);
            pidrl = new PID(motrl, this);

            telemetry.addData("Success: ", "PID Initialization Complete.");
        }catch(Exception e){
            telemetry.addData("ERROR: ", "PIDs Initialization Failure.");
        }
    }

    @Override
    public void loop(){
        //Input Detection
        joy1.update(gamepad1);
        joy2.update(gamepad2);

        /////////////Loop Methods/////////////
        //update movements
        UpdateMovementInput();

        /*
        //update PID values and speeds
        pidfl.encUpdate(); telemetry.addData("PID Speed: ", Float.toString(pidfl.speed));
        pidfr.encUpdate(); telemetry.addData("PID Speed: ", Float.toString(pidfr.speed));
        pidrr.encUpdate(); telemetry.addData("PID Speed: ", Float.toString(pidrr.speed));
        pidrl.encUpdate(); telemetry.addData("PID Speed: ", Float.toString(pidrl.speed));
        */


    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////// CORE METHODS //////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////

    public void UpdateMovementInput(){
        float lSticky, rSticky;
        lSticky = gamepad1.left_stick_y;
        rSticky = gamepad1.right_stick_y;
        SetRawPower(lSticky, rSticky);

        telemetry.addData("LStickY: ", lSticky);
        telemetry.addData("RStickY: ", rSticky);
        telemetry.addData("LTrigger: ", joy1.left_trigger);
        telemetry.addData("RTrigger: ", joy1.right_trigger);
        telemetry.addData("SpeedFL", meka.getSpeedFL());
        telemetry.addData("SpeedFR", meka.getSpeedFR());
        telemetry.addData("SpeedBL", meka.getSpeedBL());
        telemetry.addData("SpeedBL", meka.getSpeedBR());

        //Strafe Input (Trigger analog input between 0 and 1)
        if(meka.getSpeedBL() + meka.getSpeedBR() + meka.getSpeedFL() + meka.getSpeedFR() == 0) {
            meka.Strafe(joy1.left_trigger, 1);
            meka.Strafe(joy1.left_trigger, -1);
        }
    }

    public void SetRawPower(double L, double R){
        motfl.setPower(Range.clip(L, -1, 1));
        motfr.setPower(Range.clip(R, -1, 1));
        motrl.setPower(Range.clip(L, -1, 1));
        motrr.setPower(Range.clip(R, -1, 1));
        telemetry.addData("SetPower", "SetPowerYEHHHH");
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////// SETUP METHODS /////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////

    public void setupMotors(){
        try {
            motfr=hardwareMap.dcMotor.get("fr");
            telemetry.addData("Confirmed: ", "Motor FR");
        }catch (Exception e){
            telemetry.addData("ERROR",e.toString());
        }
        try {
            motfl=hardwareMap.dcMotor.get("fl");
            telemetry.addData("Confirmed: ", "Motor FL");
        }catch (Exception e){
            telemetry.addData("ERROR",e.toString());
        }
        try {
            motrr=hardwareMap.dcMotor.get("rr");
            telemetry.addData("Confirmed: ", "Motor RR");
        }catch (Exception e){
            telemetry.addData("ERROR",e.toString());
        }
        try {
            motrl=hardwareMap.dcMotor.get("rl");
            telemetry.addData("Confirmed: ", "Motor RL");
        }catch (Exception e){
            telemetry.addData("ERROR",e.toString());
        }
    }

    public void AddTelemetry(String xx, String yy){
        telemetry.addData(xx, yy);
    }


    /*
    private class speedUpdateTask extends TimerTask {
        @Override
        public void run() {

        }
    }*/

}
