package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
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


    ////////////////////////////////////////////////////////////////////////////////////////////////
    /*
    Motors: fl, fr, rl, rr, loader, shooter
    Servos: buttonServo, gateServo
    */
    ////////////////////////////////////////////////////////////////////////////////////////////////

    //Basics
    DcMotor motfl,motfr,motrr,motrl, loader, shooter;
    Servo button;
    CRServo gate;
    PID pidfl, pidfr, pidrr, pidrl;

    //Drivers
    MekaDrive meka;
    MekaServo servoo;
    Firing fire;
    ButtonState joy1, joy2;

    @Override
    public void init(){
        //Hardware Setup
        setupMotors();
        setupServos();

        //Drivers and Objects Setup
        try{
            joy1 = new ButtonState();
            joy2 = new ButtonState();
        }catch (Exception e){
            telemetry.addData("ERROR:", e.toString());
        }

        try{
            fire = new Firing(loader, shooter);
            telemetry.addData("Sucess: ", "Firing Setup Complete");
        }catch (Exception e) {
            telemetry.addData("ERROR: ", "Firing Setup Failure.");
        }

        try{
            servoo = new MekaServo(button, gate);
            telemetry.addData("Sucess: ", "Servos Setup Complete");
        }catch (Exception e) {
            telemetry.addData("ERROR: ", "Servos Setup Failure.");
        }

        try{
            meka = new MekaDrive(motfl, motfr, motrr, motrl, true, false);
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
        UpdateMiscInput();

        telemetry.addData("GateGo State: ", servoo.getGateGo());
        telemetry.addData("Gate Power: ", servoo.getGatePower());

        /*
        //update PID values and speeds
        pidfl.encUpdate(); telemetry.addData("PID Speed: ", Float.toString(pidfl.speed));
        pidfr.encUpdate(); telemetry.addData("PID Speed: ", Float.toString(pidfr.speed));
        pidrr.encUpdate(); telemetry.addData("PID Speed: ", Float.toString(pidrr.speed));
        pidrl.encUpdate(); telemetry.addData("PID Speed: ", Float.toString(pidrl.speed));
        */
    }

    public void UpdateMiscInput() {
        float rStickyy, lStickyy;
        rStickyy = gamepad2.left_stick_y;
        lStickyy = gamepad2.right_stick_y;
        telemetry.addData("LStickY 2: ", lStickyy);
        telemetry.addData("RStickY 2: ", rStickyy);

        fire.SetLoadingPower(lStickyy);
        fire.SetShootingPower(rStickyy);

        if(joy2.b_press()){
            servoo.ToggleGate();
        }
        if(joy2.left_bumper_press()){
            servoo.ChangeButtonPosition(-1);
        }else if(joy2.right_bumper_press()){
            servoo.ChangeButtonPosition(1);
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////// CORE METHODS //////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////

    public void UpdateMovementInput(){
        float lSticky, rSticky;
        lSticky = gamepad1.left_stick_y;
        rSticky = gamepad1.right_stick_y;

        telemetry.addData("LStickY: ", lSticky);
        telemetry.addData("RStickY: ", rSticky);
        telemetry.addData("LTrigger: ", joy1.left_trigger);
        telemetry.addData("RTrigger: ", joy1.right_trigger);
        telemetry.addData("SpeedFL", meka.getSpeedFL());
        telemetry.addData("SpeedFR", meka.getSpeedFR());
        telemetry.addData("SpeedBL", meka.getSpeedBL());
        telemetry.addData("SpeedBR", meka.getSpeedBR());

        //Analog Movement Input
        if(Math.abs(lSticky) > meka.getInputThreshold() || Math.abs(rSticky) > meka.getInputThreshold()){
            meka.SetRawPower(lSticky, rSticky);
        }else if(joy1.left_trigger > 0){
            meka.SetRawStrafe(joy1.left_trigger, -joy1.left_trigger);
            telemetry.addData("Strafing: ", "Left");
        }else if(joy1.right_trigger > 0){
            meka.SetRawStrafe(-joy1.right_trigger, joy1.right_trigger);
            telemetry.addData("Strafing: ", "Right");
        }else{
            meka.ZeroMotors();
        }

        if(joy1.right_stick_button_press()){
            meka.setMotorPolarity(meka.getMotorPolarity() * -1);
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////// SETUP METHODS /////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////

    public void setupMotors(){
        //Drive Motors
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

        //Loading/Shooting motors
        try {
            loader=hardwareMap.dcMotor.get("loader");
            telemetry.addData("Confirmed: ", "Loader");
        }catch (Exception e){
            telemetry.addData("ERROR",e.toString());
        }
        try {
            shooter=hardwareMap.dcMotor.get("shooter");
            telemetry.addData("Confirmed: ", "Shooter");
        }catch (Exception e){
            telemetry.addData("ERROR",e.toString());
        }
    }

    void setupServos(){
        //Button Servo
        try {
            button = hardwareMap.servo.get("buttonServo");
            telemetry.addData("Confirmed: ", "ButtonServo");
        }catch (Exception e){
            telemetry.addData("ERROR",e.toString());
        }

        //loading gate servo
        try {
            gate = hardwareMap.crservo.get("gateServo");
            telemetry.addData("Confirmed: ", "GateServo");
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
