// Hi I'm testing commit

package org.firstinspires.ftc.teamcode;

import android.widget.Button;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Created by LockonS on 10/4/16.
 */

@TeleOp (name="test chassis", group="test")
public class TestChassis extends OpMode {
    public TestChassis(){

    }

    public DcMotor motL, motR, collectionMotor, slingshotMotor;
    //public FWD fwd;
    public boolean fwdSet = true;
    public boolean lastA, lastX;
    public ButtonState buttonState;

    @Override
    public void init() {
        setupMotors();
        try{
            //fwd = new FWD(motL,motR);
            //fwd.setEnableAcc(false);
            telemetry.addData("GGWP", "fwd created");
        }catch (Exception e){
            telemetry.addData("ERROR",e.toString());
        }
        buttonState = new ButtonState();

    }

    @Override
    public void loop() {
        float leftStick = gamepad1.left_stick_y;
        float rightStick = gamepad1.right_stick_y;
        if(fwdSet){
            //fwd.setPower(leftStick, rightStick);
        }
        if(!lastA){
            slingshotMotor.setPower(0);
        }
        if(!lastX){
            collectionMotor.setPower(0);
        }

        if(gamepad1.a){
            slingshotMotor.setPower(1);
            lastA = true;
        }else{
            lastA = false;
        }
        if(gamepad1.x){
            collectionMotor.setPower(-1);
            lastX = true;
        }else{
            lastX = false;
        }
    }

    public void setupMotors(){
        /*try {
            motL=hardwareMap.dcMotor.get("l");
            telemetry.addData("GGWP", "MOTOR L");
        }catch (Exception e){
            telemetry.addData("ERROR",e.toString());
            fwdSet = false;
        }
        try {
            motR=hardwareMap.dcMotor.get("r");
            telemetry.addData("GGWP", "MOTOR R");
        }catch (Exception e){
            telemetry.addData("ERROR",e.toString());
            fwdSet = false;
        }*/

        try {
            collectionMotor=hardwareMap.dcMotor.get("collect");
            telemetry.addData("GGWP", "collection Motor");
        }catch (Exception e){
            telemetry.addData("ERROR",e.toString());
        }

        try {
            slingshotMotor=hardwareMap.dcMotor.get("slingshot");
            telemetry.addData("GGWP", "slingshot Motor");
        }catch (Exception e){
            telemetry.addData("ERROR",e.toString());
        }
    }

}
