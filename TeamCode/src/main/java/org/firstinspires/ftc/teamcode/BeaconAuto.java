package org.firstinspires.ftc.teamcode;

import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DeviceInterfaceModule;
import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;
import com.qualcomm.robotcore.robocol.TelemetryMessage;

/**
 * Created by Emma on 12/15/16.
 */

@Autonomous(name="Beacon Auto", group="Autonomous")
public class BeaconAuto extends OpMode {
    enum states{DRIVE_FOURTY_FIVE,CHASE_BEACON,CHECK_COLOR,FOLLOW_BUTTON,PRESS_BUTTON,CHASE_BEACON_2,WAIT};
    states currentState;
    DeviceInterfaceModule dim;
    ColorSensor colorSensor;
    OpticalDistanceSensor ods;
    DcMotor motfl,motfr,motrr,motrl, loader, shooter;
    CRServo gate, button;

    MekaDrive meka;
    MekaServo servoo;
    PikachuControl pika;


    float teamHueMin, teamHueMax;

    @Override
    public void init() {
        try {
            dim = hardwareMap.deviceInterfaceModule.get("dim");
        }catch(Exception e){
            telemetry.addData("ERROR: " , e);
        }

        SetupSensors();
        SetupMotors();
        SetupServos();

        pika = new PikachuControl();
                                                    
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

        teamHueMin = 200;
        teamHueMax = 260;
        currentState = states.DRIVE_FOURTY_FIVE;


    }

    @Override
    public void loop() {
        double light;
        float[] hsvValues = {0,0,0};
        try{
            light = ods.getLightDetected();
            telemetry.addData("ODS", "Light: " + light);
        }catch(Exception e){
            telemetry.addData("ERROR: " , e);
        }
        try{
            Color.RGBToHSV(colorSensor.red()*8, colorSensor.green()*8, colorSensor.blue()*8, hsvValues);
            telemetry.addData("Color", "Colors: " + hsvValues[0] + ", " + hsvValues[1] + ", " + hsvValues[2]);
            //BLUE 200-260  //RED 300-360
        }catch(Exception e){
            telemetry.addData("ERROR: " , e);
            telemetry.addData("ff", meka.toString());
        }

        telemetry.addData("State: ", currentState.name());


        //STATES
        switch (currentState) {
            case DRIVE_FOURTY_FIVE:
                try{
                    meka.SetRawStrafe(0, 1);
                }catch(Exception e){
                    telemetry.addData("DRIVEFORTYFIVE: ", e.toString());
                }
                break;
            case CHASE_BEACON:
                ods.enableLed(true);
                if(ods.getLightDetected() > 0.125){
                    currentState = states.CHECK_COLOR;
                }
                break;

            case CHECK_COLOR:
                if(hsvValues[0] >= teamHueMin && hsvValues[0] <= teamHueMax){

                }
                break;

            case FOLLOW_BUTTON:
                break;

            case PRESS_BUTTON:
                break;

            case CHASE_BEACON_2:
                break;

            case WAIT:
                break;
        }
    }

    public void SetupSensors(){
        try {
            colorSensor = hardwareMap.colorSensor.get("cs");
        }catch(Exception e){
            telemetry.addData("ERROR: " , e);
        }
        try{
            ods = hardwareMap.opticalDistanceSensor.get("ods");
        }catch(Exception e){
            telemetry.addData("ERROR: " , e);
        }
    }


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

    public void SetupServos(){
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

}
