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

public class MekaDrive extends OpMode {

    ////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////// DEFAULT SETTINGS /////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////

    //Functional Constants
    private double exponent=3.5; //curviness of exponential curve
    private int speedUpdatePeriod = 33; //wait between update in milliseconds
    private double rawAcceleration = 0.1; //delta power per period
    private double inputThreshold = 0.01; //power input threshold

    //Autonomous Hardware Specifications
    private double wheelDiameter; //in inches
    private double vehicleWidth; //in centimeters
    private double MaxSpeedForward; //forward driving speed at 100% power, in cm per second
    private double MaxSpeedPivot; //angular speed at 100% power, in degrees per second
    private double encoderResolution; //encoder ticks per rotation


    ////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////// VARIABLES //////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////

    //Basic Stuff
    DcMotor motFL, motFR, motBR, motBL;
    private boolean enableExpo,enableAccel;
    private double speedFL=0.0,speedFR=0.0,speedBL=0.0,speedBR=0.0;
    private double targetFL=0.0,targetFR=0.0,targetBL=0.0,targetBR=0.0;
    public double motorPolarity;

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

    public MekaDrive(DcMotor mot1, DcMotor mot2, DcMotor mot3, DcMotor mot4, boolean isAutonomous) {
        motFL = mot1;
        motFR = mot2;
        motBR = mot3;
        motBL = mot4;

        MekaSetup();

        telemetry.addData("MekaDrive: ", "Activated.");
    }

    public void MekaSetup(){
        //speedUpdater = new Timer("speedUpdater",true);
        //speedUpdater.scheduleAtFixedRate(new speedUpdateTask(), 0, speedUpdatePeriod);
        motFL.setDirection(DcMotor.Direction.REVERSE);
        motBL.setDirection(DcMotor.Direction.REVERSE);

        telemetry.addData("MekaDrive: ", "Setup.");
    }

    private double Exponentiate(double input, double expoFactor, double max){
        double polarity;
        if(input < 0){
            polarity = -1.0;
        }else{
            polarity = 1.0;
        }
        input = Math.abs(input);
        double out = Math.pow(input, expoFactor);
        out = out * (max);
        out = out / (Math.pow(max, expoFactor));
        out = out * polarity;
        return(out);
    }

    public void SetRawPower(double L, double R){
        speedFL=Range.clip(L, -1, 1);
        speedFR=Range.clip(R, -1, 1);
        speedBL=Range.clip(L, -1, 1);
        speedBR=Range.clip(R, -1, 1);
        UpdateMotorPower();
    }

    public void SetPower(double L, double R){
        if(enableExpo){
            L = Exponentiate(L, exponent, 1);
            R = Exponentiate(R, exponent, 1);
        }
        if(Math.abs(L)<inputThreshold)L=0.0;
        if(Math.abs(R)<inputThreshold)R=0.0;
        targetFR=R;
        targetFL=R;
        targetFL=L;
        targetBL=L;
        if(!enableAccel){
            SetRawPower(L, R);
        }
    }

    public void Strafe(double pow, double dirPolarity){
        pow = pow * dirPolarity;
        pow = Range.clip(pow, -1, 1);

        targetFL = pow;
        targetBR = pow;
        targetFR = -pow;
        targetBL = -pow;
    }

    private void UpdateMotorPower(){
        try {
            motFL.setPower(speedFL * motorPolarity);
            telemetry.addData("motFL", speedFL);
            motFR.setPower(speedFR * motorPolarity);
            telemetry.addData("motFR", speedFR);
            motBL.setPower(speedBL * motorPolarity);
            telemetry.addData("motBL", speedBL);
            motBR.setPower(speedBR * motorPolarity);
            telemetry.addData("motBR", speedBR);
            telemetry.addData("Confirmed: ", "MOTORS OPERATIONAL");
        }catch (Exception e){
            telemetry.addData("ERROR: ", e.toString());
        }
    }

    /*private class speedUpdateTask extends TimerTask{
        @Override
        public void run() {
            if (enableAccel) {
                if (speedFL < targetFL) {
                    speedFL += rawAcceleration;
                    if (speedFL > targetFL) {
                        speedFL = targetFL;
                    }
                } else if (speedFL > targetFL) {
                    speedFL -= rawAcceleration;
                    if (speedFL < targetFL) {
                        speedFL = targetFL;
                    }
                }
                if (speedFR < targetFR) {
                    speedFR += rawAcceleration;
                    if (speedFR > targetFR) {
                        speedFR = targetFR;
                    }
                } else if (speedFR > targetFR) {
                    speedFR -= rawAcceleration;
                    if (speedFR < targetFR) {
                        speedFR = targetFR;
                    }
                }
                if (speedBL < targetBL) {
                    speedBL += rawAcceleration;
                    if (speedBL > targetBL) {
                        speedBL = targetBL;
                    }
                } else if (speedBL > targetBL) {
                    speedBL -= rawAcceleration;
                    if (speedBL < targetBL) {
                        speedBL = targetBL;
                    }
                }
                if (speedBR < targetBR) {
                    speedBR += rawAcceleration;
                    if (speedBR > targetBR) {
                        speedBR = targetBR;
                    }
                } else if (speedBR > targetBR) {
                    speedBR -= rawAcceleration;
                    if (speedBR < targetBR) {
                        speedBR = targetBR;
                    }
                }
            }
            UpdateMotorPower();
        }
    }*/
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
    public double getSpeedFL(){
        return speedFL;
    }
    public double getSpeedFR(){
        return speedFR;
    }
    public double getSpeedBL(){
        return speedBL;
    }
    public double getSpeedBR(){
        return speedBR;
    }

    public double getExponent() {
        return this.exponent;
    }
    public void setExponent(double in) {
        this.exponent = in;
    }
    public double getMotorPolarity() {
        return this.motorPolarity;
    }
    public void setMotorPolarity(double in) {
        this.motorPolarity = in;
    }
    public double getInputThreshold() {
        return this.inputThreshold;
    }
    public void setInputThreshold(double in) {
        this.inputThreshold = in;
    }

    public boolean isEnableExpo() {
        return this.enableExpo;
    }
    public void setEnableExpo(boolean in){
        this.enableExpo = in;
    }
}
