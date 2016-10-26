package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.TimerTask;

/**
 * Created by Ashment on 10/17/16.
 */

@TeleOp (name="LondonOp", group="Test")
public class LondonOp extends OpMode{

    ////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////// VARIABLES ////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////

    //Basics
    DcMotor motfl,motfr,motrr,motrl;
    PID pidfl, pidfr, pidrr, pidrl;

    //Drivers
    LondonDrive london;
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
            london = new LondonDrive(motfl, motfr, motrl, motrr);
            telemetry.addData("Sucess: ", "LondonDrive Setup Complete.");
        }catch (Exception e){
            telemetry.addData("ERROR: ", "LondonDrive Setup Failure.");
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
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////// CORE METHODS //////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////

    public void UpdateMovementInput(){
        //Input
        float lSticky, rSticky;
        lSticky = gamepad1.left_stick_y;
        rSticky = gamepad1.right_stick_y;

        telemetry.addData("LStickY: ", lSticky);
        telemetry.addData("RStickY: ", rSticky);

        if(lSticky < london.getInputThreshold() && rSticky < london.getInputThreshold()){
            telemetry.addData("LTrigger: ", joy1.left_trigger);
            telemetry.addData("RTrigger: ", joy1.right_trigger);
            if(joy1.left_trigger > london.getInputThreshold()){
                london.SetStrafe(-1 * joy1.left_trigger);
                telemetry.addData("STRAFE L: ", (-1 * joy1.left_trigger));
            }else if(joy1.right_trigger > london.getInputThreshold()){
                london.SetStrafe(1 * joy1.right_trigger);
                telemetry.addData("STRAFE R: ", (1 * joy1.right_trigger));
            }else{
                london.SetPower(0, 0, true);
            }
        }else{
            //Analog Movement Input
            london.SetPower(lSticky, rSticky, true);
            telemetry.addData("FORWARD! ", "L: " + lSticky + ", R: " + rSticky);
        }


        //Strafe Input (Trigger analog input between 0 and 1)
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
