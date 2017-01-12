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
    //loader and button are not used
    DcMotor motfl,motfr,motrr,motrl, shooter, motorLeft, motorRight;
    CRServo gate, button, servoLeft, servoRight;
    PID pidfl, pidfr, pidrr, pidrl;
    PikachuControl pika;

    //Drivers
    MekaDrive meka;
    MekaServo servoo;
    CapballDriver cap;
    Firing fire;
    ButtonState joy1, joy2;

    @Override
    public void init(){
        //Hardware Setup
        setupMotors();
        setupServos();

        pika = new PikachuControl();

        //Drivers and Objects Setup
        try{
            joy1 = new ButtonState();
            joy2 = new ButtonState();
        }catch (Exception e){
            telemetry.addData("ERROR:", e.toString());
        }

        try{
            cap = new CapballDriver(servoLeft, servoRight, motorLeft, motorRight);
            telemetry.addData("Sucess: ", "CapballDriver Setup Complete");
        }catch (Exception e) {
            telemetry.addData("ERROR: ", "CapballDriver Setup Failure.");
        }

        try{
            fire = new Firing(shooter);
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
        telemetry.addData("Button Power: ", servoo.getButtonPwr());
        /*
        //update PID values and speeds
        pidfl.encUpdate(); telemetry.addData("PID Speed: ", Float.toString(pidfl.speed));
        pidfr.encUpdate(); telemetry.addData("PID Speed: ", Float.toString(pidfr.speed));
        pidrr.encUpdate(); telemetry.addData("PID Speed: ", Float.toString(pidrr.speed));
        pidrl.encUpdate(); telemetry.addData("PID Speed: ", Float.toString(pidrl.speed));
        */
    }

    public void UpdateMiscInput() {

        //LStickY 2 (moved up and down) controls
        float lStickyy, rStickyy;
        lStickyy = gamepad2.left_stick_y;
        rStickyy = gamepad2.right_stick_y;
        telemetry.addData("LStickY 2: ", lStickyy);
        telemetry.addData("RStickY 2: ", rStickyy);

        //Use b button to run the gate servo and y button to turn it the other way
        if(gamepad2.dpad_up){
            servoo.ToggleGate(1);
        }else if (gamepad2.dpad_down){
            servoo.ToggleGate(-1);
        }else {
            servoo.ToggleGate(0);
        }

        if (joy2.right_bumper){
            servoo.turnGate();
        }

        //Non-Toggle Button Motion
        if(joy2.left_bumper){
            servoo.pressButton();
        }

        cap.extendArm(lStickyy);
        cap.raiseArm(rStickyy);


        /*DEPRECATED METHOD
        if(joy2.left_bumper_press()){
            servoo.ChangeButtonPosition(-1);
        }else if(joy2.right_bumper_press()){
            servoo.ChangeButtonPosition(1);
        }
        */
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////// CORE METHODS //////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////

    public void UpdateMovementInput(){

        double inputAngle = pika.rQuadrant(gamepad1.left_stick_x, gamepad1.left_stick_y);

        /*telemetry.addData("LStickY: ", lSticky);
        telemetry.addData("RStickY: ", rSticky);
        telemetry.addData("LTrigger: ", joy1.left_trigger);
        telemetry.addData("RTrigger: ", joy1.right_trigger);
        telemetry.addData("SpeedFL", meka.getSpeedFL());
        telemetry.addData("SpeedFR", meka.getSpeedFR());
        telemetry.addData("SpeedBL", meka.getSpeedBL());
        telemetry.addData("SpeedBR", meka.getSpeedBR());*/

        if(joy1.right_stick_button_press()){
            meka.setMotorPolarity(meka.getMotorPolarity() * -1);
        }

        //Analog Movement Input
        if(joy1.left_trigger > 0){
            meka.SetRawPower(-1, 1);
            telemetry.addData("Turning: ", "Left" + joy1.left_trigger);
        }else if(joy1.right_trigger > 0){
            meka.SetRawPower(1, -1);
            telemetry.addData("Turning: ", "Right " + joy1.right_trigger);
        }else if(Math.abs(joy1.left_stick_x) > 0 || Math.abs(joy1.left_stick_y) > 0){
            meka.SetRawStrafe(pika.powerGraph(2, inputAngle)* pika.rDistance(gamepad1.left_stick_x, gamepad1.left_stick_y),
                    pika.powerGraph(1, inputAngle)* pika.rDistance(gamepad1.left_stick_x, gamepad1.left_stick_y));
        }else{
            meka.ZeroMotors();
        }

        telemetry.addData("Linearly Controlled Power: ", pika.powerGraph(2, inputAngle) + ", " + pika.powerGraph(1, inputAngle));
        telemetry.addData("Angle: ", (inputAngle) + ", " + Math.toRadians(inputAngle));
        telemetry.addData("FLBR: ", meka.getSpeedFL());
        telemetry.addData("FRBL: ", meka.getSpeedFR());

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

        //Shooting motors
        try {
            shooter=hardwareMap.dcMotor.get("shooter");
            telemetry.addData("Confirmed: ", "Shooter");
        }catch (Exception e){
            telemetry.addData("ERROR",e.toString());
        }

        //CapBall motors
        try {
            motorLeft=hardwareMap.dcMotor.get("motL");
            telemetry.addData("Confirmed: ", "motL");
        }catch (Exception e){
            telemetry.addData("ERROR",e.toString());
        }

        try {
            motorRight=hardwareMap.dcMotor.get("motR");
            telemetry.addData("Confirmed: ", "motR");
        }catch (Exception e){
            telemetry.addData("ERROR",e.toString());
        }

    }


    void setupServos(){
        //Button Servo
        try {
            button = hardwareMap.crservo.get("buttonServo");
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

        try {
            servoLeft=hardwareMap.crservo.get("servoL");
            telemetry.addData("Confirmed: ", "servoL");
        }catch (Exception e){
            telemetry.addData("ERROR",e.toString());
        }

        try {
            servoRight=hardwareMap.crservo.get("servoR");
            telemetry.addData("Confirmed: ", "servoR");
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
