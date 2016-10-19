package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Created by Ashment on 10/17/16.
 */
public class MekaOp extends OpMode{

    ////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////// VARIABLES ////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////

    //Basics
    DcMotor motfl,motfr,motrr,motrl;

    //Drivers
    ButtonState joy1, joy2;
    MekaDrive meka;

    @Override
    public void init(){
        //Hardware Setup
        setupMotors();

        //Drivers Setup
        try{
            meka = new MekaDrive(motfl, motfr, motrr, motrl, false);
            telemetry.addData("Sucess: ", "MekaDrive Setup Complete.");
        }catch (Exception e){
            telemetry.addData("ERROR: ", "MekaDrive Setup Failure.");
        }

        joy1 = new ButtonState();
        joy2 = new ButtonState();

    }

    @Override
    public void loop(){
        joy1.update(gamepad1);
        joy2.update(gamepad2);


        //Input Detection
        //Reverse Motor Power Polarity
        if(joy1.right_stick_button_press()){
            meka.motorPolarity = meka.motorPolarity * -1;
        }

        //Loop Methods
        UpdateMovementInput();

        //Telemetry
        telemetry.addData("LStickY: ", joy1.left_stick_y);
        telemetry.addData("RStickY: ", joy1.right_stick_y);
        telemetry.addData("LTrigger: ", joy1.left_trigger);
        telemetry.addData("RTrigger: ", joy1.right_trigger);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////// CORE METHODS //////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////

    public void UpdateMovementInput(){
        meka.SetPower(joy1.left_stick_y, joy1.right_stick_y);

        //Strafe Input (Trigger analog input between 0 and 1)
        if(joy1.right_trigger > meka.getInputThreshold()){
            if(joy1.left_stick_y < meka.getInputThreshold() && joy1.right_stick_y < meka.getInputThreshold()) {
                meka.Strafe(joy1.left_trigger, 1);
            }
        }
        if(joy1.left_trigger > meka.getInputThreshold()){
            if(joy1.left_stick_y < meka.getInputThreshold() && joy1.right_stick_y < meka.getInputThreshold()) {
                meka.Strafe(joy1.left_trigger, -1);
            }
        }
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
