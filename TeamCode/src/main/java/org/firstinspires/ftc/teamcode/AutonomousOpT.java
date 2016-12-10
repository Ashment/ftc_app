package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;


/**
 * Created by Emma on 11/30/16.
 */

@Autonomous(name="Auto Testie WhimRed", group="Autonomous")
public class AutonomousOpT extends OpMode {

    DcMotor motfl,motfr,motrr,motrl, loader, shooter;
    CRServo gate, button;

    //Drivers
    MekaDrive meka;
    MekaServo servoo;
    Firing fire;

    double timeOne = 0, fireDuration = 7, timeTwo = 7.2, gateDuration = 0.3, timeThree = 9.5, timeFour = 17, drivingDuration = 2.1, timeFive = 20, strafeDuration = 3, startTime;
    double runTime;


    public void SetupMotors(){
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

    void SetupServos(){
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
    }

    public void SetupDrivers(){
        meka = new MekaDrive(motfl, motfr, motrr, motrl, true, true);
    }

    @Override
    public void init() {
        //Hardware Setup
        SetupMotors();
        SetupServos();
        SetupDrivers();

        startTime = getRuntime();

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
    }

    public void localTimer(double startingTime) {
        double localRunTime = runTime - startingTime;

    }

    @Override
    public void loop() {
        runTime = getRuntime() - startTime;
        telemetry.addData("Run time: ", runTime + "Actual Run time: " + getRuntime());

        if ((runTime > timeOne && runTime <= timeOne + fireDuration)
                || (runTime > timeThree && runTime <= timeThree + fireDuration)) {
            fire.SetShootingPower(-1);
        }else{
            fire.SetShootingPower(0);
        }

        if (runTime > timeTwo + gateDuration) {
            servoo.ToggleGate(0);
        }else if (runTime > timeTwo) {
            servoo.ToggleGate(1);
        }

        if (runTime > timeFour + drivingDuration) {
            meka.ZeroMotors();
        }else if (runTime > timeFour) {
            meka.SetRawPower(1, 1);
        }

        if (runTime > timeFive + strafeDuration) {
            meka.ZeroMotors();
        }else if (runTime > timeFive) {
            meka.SetRawStrafe(-1, 1);
        }
    }

}
