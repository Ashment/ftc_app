package org.firstinspires.ftc.teamcode;

import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DeviceInterfaceModule;
import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;
import com.qualcomm.robotcore.robocol.TelemetryMessage;

/**
 * Created by Emma on 12/15/16.
 */

@Autonomous(name="Beacon Auto", group="Autonomous")
public class BeaconAuto extends OpMode {
    enum states{CHASE_BEACON,CHECK_COLOR,FOLLOW_BUTTON,PRESS_BUTTON,CHASE_BEACON_2,WAIT};
    states currentState;
    DeviceInterfaceModule dim;
    ColorSensor colorSensor;
    OpticalDistanceSensor ods;

    @Override
    public void init() {
        dim = hardwareMap.deviceInterfaceModule.get("dim");
        SetupSensors();
    }

    @Override
    public void loop() {
        switch (currentState) {
            case CHASE_BEACON:
                ods.enableLed(true);
                break;

            case CHECK_COLOR:
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


        //SensorTelemetry
        try{
            double light = ods.getLightDetected();
            double rawLight = ods.getRawLightDetected();
            telemetry.addData("ODS", "Light: " + light + ", Raw:" + rawLight);
        }catch(Exception e){

        }
        try{
            float[] hsvValues = {0,0,0};
            Color.RGBToHSV(colorSensor.red()*8, colorSensor.green()*8, colorSensor.blue()*8, hsvValues);

            telemetry.addData("Color", "Colors: " + hsvValues[0] + ", " + hsvValues[1] + ", " + hsvValues[2]);
        }catch(Exception e){

        }
    }

    public void SetupSensors(){
        try {
            colorSensor = hardwareMap.colorSensor.get("cs");
        }catch(Exception e){

        }
        try{
            ods = hardwareMap.opticalDistanceSensor.get("ods");
        }catch(Exception e){

        }
    }
}
