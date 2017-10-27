package org.firstinspires.ftc.teamcode;

import android.graphics.Color;
import android.widget.ZoomButtonsController;

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
    enum states{DRIVE_FOURTY_FIVE,CHASE_BEACON,CHECK_COLOR,FOLLOW_BUTTON,PRESS_BUTTON,WAIT,BUMP}
    states currentState, lastState;
    DeviceInterfaceModule dim;
    ColorSensor colorSensor;
    OpticalDistanceSensor ods;
    DcMotor motfl,motfr,motrr,motrl, loader, shooter;
    CRServo gate, button;

    MekaDrive meka;
    MekaServo servoo;
    PikachuControl pika;

    boolean servoPressed;

    int buttonCount = 0;

    double stateStartTime = -1, stateTime;
    double[] stateTimes = {6, 1, 0.4}; //[0] = drive45, [1] = seekButton
    double[] strafeDir = {0, 1};

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

        buttonCount = 0;
    }

    @Override
    public void loop() {

        if(lastState != currentState){
            stateStartTime = getRuntime();
            stateTime = 0;
        }else{
            stateTime = (getRuntime() - stateStartTime);
        }

        telemetry.addData("STATETIME: " , stateTime);

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
                if(stateTime < stateTimes[0]) {
                    try {
                        meka.SetRawPower(pika.powerGraph(2, 315), pika.powerGraph(1, 315));
                    } catch (Exception e) {
                        telemetry.addData("DRIVEFORTYFIVE: ", e.toString());
                    }
                }else if(stateTime < stateTimes[1] + stateTimes[0]){
                    try {
                        meka.SetRawStrafe(-strafeDir[1], strafeDir[1]);
                    } catch (Exception e) {
                        telemetry.addData("DRIVEFORTYFIVE: ", e.toString());
                    }
                }else{
                    meka.ZeroMotors();
                    currentState = states.CHASE_BEACON;
                }
                break;
            case CHASE_BEACON:
                ods.enableLed(true);
                if(ods.getLightDetected() > 0.145 && stateTime < 1.5){
                    currentState = states.CHECK_COLOR;
                    meka.ZeroMotors();
                }else{
                    meka.SetRawPower(0.5,0.5);
                }
                break;

            case CHECK_COLOR:
                if(hsvValues[0] >= teamHueMin && hsvValues[0] <= teamHueMax){
                    currentState = states.PRESS_BUTTON;
                }else if(stateTime > 1){
                    currentState = states.FOLLOW_BUTTON;
                }
                break;

            case FOLLOW_BUTTON:
                if(stateTime <= stateTimes[2]){
                    meka.SetRawPower(0.4, 0.4);
                }else{
                    meka.ZeroMotors();
                    servoPressed = false;
                    currentState = states.PRESS_BUTTON;
                }
                break;

            case PRESS_BUTTON:
                if(stateTime < 2.7){
                    if(!servoPressed) {
                        servoo.pressButton();
                        servoPressed = true;
                    }
                }else{
                    if(buttonCount > 0){
                        meka.ZeroMotors();
                        currentState = states.WAIT;
                    }else{
                        buttonCount++;
                        currentState = states.CHASE_BEACON;
                    }
                }
                break;

            case BUMP:
                if(stateTime < 0.5){
                    meka.SetRawPower(-1,-1);
                }else if(stateTime < 5){
                    meka.SetRawStrafe(-strafeDir[0], strafeDir[1]);
                }
                break;

            case WAIT:
                break;
        }

        lastState = currentState;
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

