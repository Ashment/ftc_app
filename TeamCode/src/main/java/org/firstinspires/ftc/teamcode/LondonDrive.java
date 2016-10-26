package org.firstinspires.ftc.teamcode;

import android.graphics.Path;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.Range;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Ashment on 10/13/16.
 */

/**
 * TODO
 * LEGEND: <Feature> [Completed][Tested][Interfaced]
 *
 * Motor takeover                   √--
 * Exponential                      √--
 * Acceleration                     √--
 * Mechanim Movement                √--
 * TeleOpControls                   √--
 * Go by encoder                    ---
 * Getters and Setters              √--
 */

public class LondonDrive extends OpMode {

    ////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////// DEFAULT SETTINGS /////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////

    //Functional Constants
    private double exponent=3.5; //curviness of exponential curve
    private int speedUpdatePeriod = 33; //wait between update in milliseconds
    private double rawAcceleration = 0.1; //delta power per period
    private double inputThreshold = 0.01; //power input threshold

    ////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////// VARIABLES //////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////

    //Basic Stuff
    DcMotor motFL, motFR, motBR, motBL;
    private double speedFL=0.0,speedFR=0.0,speedBL=0.0,speedBR=0.0;
    public double[] motorPolarity = {1,1,1,1};
    double[] leftPolarity = {-1, 1, 1, -1}, rightPolarity = {1, -1, -1, 1}, regularPolarity = {1,1,1,1};

    //Loops
    private Timer speedUpdater,runstopper;


    ////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////// CORE METHODS ////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void init() {

    }

    @Override
    public void loop() {

    }

    public LondonDrive(DcMotor mot1, DcMotor mot2, DcMotor mot3, DcMotor mot4) {
        motFL = mot1;
        motFR = mot2;
        motBL = mot3;
        motBR = mot4;

        LondonSetup();

        telemetry.addData("LondonDrive: ", "Activated.");
    }

    public void LondonSetup(){
        //speedUpdater = new Timer("speedUpdater",true);
        //speedUpdater.scheduleAtFixedRate(new speedUpdateTask(), 0, speedUpdatePeriod);
        motBL.setDirection(DcMotor.Direction.REVERSE);
        motBR.setDirection(DcMotor.Direction.REVERSE);

        //Reset all motors and run with encoder
        motFL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motFR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motBL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motBR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motFL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motFR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motBL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motBR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void SetPower(double L, double R, boolean std){
        if(std){
            motorPolarity = regularPolarity;
        }
        speedFL=L;
        speedFR=R;
        speedBL=L;
        speedBR=R;
        UpdateMotorPower();
    }

    public void SetStrafe(double input){
        if(Math.abs(input) > inputThreshold){
            if(input < 0){
                motorPolarity = leftPolarity;
            }else{
                motorPolarity = rightPolarity;
            }

            SetPower(input, input, false);
        }
    }

    private void UpdateMotorPower() {
        try {
            motFL.setPower(speedFL * motorPolarity[0]);
            telemetry.addData("motFL", speedFL);
            motFR.setPower(speedFR * motorPolarity[1]);
            telemetry.addData("motFR", speedFR);
            motBL.setPower(speedBL * motorPolarity[2]);
            telemetry.addData("motBL", speedBL);
            motBR.setPower(speedBR * motorPolarity[3]);
            telemetry.addData("motBR", speedBR);
            telemetry.addData("Confirmed: ", "MOTORS OPERATIONAL");
        } catch (Exception e) {
            telemetry.addData("ERROR: ", e.toString());
        }
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////// GETTERS AND SETTERS /////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////

    public DcMotor getMotFL() {
        return motFL;
    }
    public void setMotFL(DcMotor motFL) {
        this.motFL = motFL;
    }
    public DcMotor getMotFR() {
        return motFR;
    }
    public void setMotFR(DcMotor motFR) {
        this.motFR = motFR;
    }
    public DcMotor getMotBL() {
        return motBL;
    }
    public void setMotRL(DcMotor motRL) {
        this.motBL = motRL;
    }
    public DcMotor getMotBR() {
        return motBR;
    }
    public void setMotRR(DcMotor motRR) {
        this.motBR = motRR;
    }
    public double getInputThreshold() {
        return inputThreshold;
    }
    public void setInputThreshold(double in) {
        inputThreshold = in;
    }
}
