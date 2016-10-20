package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

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

    //Drivers
    MekaDrive meka;

    @Override
    public void init(){
        //Hardware Setup
        setupMotors();

        //Drivers and Objects Setup
        try{
            meka = new MekaDrive(motfl, motfr, motrr, motrl, false);
            meka.setEnableExpo(false);
            telemetry.addData("Sucess: ", "MekaDrive Setup Complete.");
        }catch (Exception e){
            telemetry.addData("ERROR: ", "MekaDrive Setup Failure.");
        }
    }

    @Override
    public void loop(){
        //Input Detection

        //Loop Methods
        UpdateMovementInput();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////// CORE METHODS //////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////

    public void UpdateMovementInput(){
        float lSticky, rSticky;
        lSticky = gamepad1.left_stick_y;
        rSticky = gamepad1.right_stick_y;
        meka.SetPower(lSticky, rSticky);

        telemetry.addData("LStickY: ", lSticky);
        telemetry.addData("RStickY: ", rSticky);
        telemetry.addData("LTrigger: ", gamepad1.left_trigger);
        telemetry.addData("RTrigger: ", gamepad1.right_trigger);

        //Strafe Input (Trigger analog input between 0 and 1)
        /*if(joy1.right_trigger > meka.getInputThreshold()){
            if(joy1.left_stick_y < meka.getInputThreshold() && joy1.right_stick_y < meka.getInputThreshold()) {
                meka.Strafe(joy1.left_trigger, 1);
            }
        }
        if(joy1.left_trigger > meka.getInputThreshold()){
            if(joy1.left_stick_y < meka.getInputThreshold() && joy1.right_stick_y < meka.getInputThreshold()) {
                meka.Strafe(joy1.left_trigger, -1);
            }
        }*/
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

}
