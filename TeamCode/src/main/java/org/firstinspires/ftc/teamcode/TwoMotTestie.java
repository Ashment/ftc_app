package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by David Hou on 09/16/16.
 */

@TeleOp (name = "TwoMotTestie", group = "Testing")
public class TwoMotTestie extends OpMode{

    public TwoMotTestie() {

    }

    DcMotor motL,motR;

    FWD fwd;

    ButtonState controller1;
    ButtonState controller2;

    double flapPos;
    static double swayMin = 0.02, swayMax = 0.78;

    @Override
    public void init() {

        setupMotors();

        try{
            fwd = new FWD(motL,motR);
            fwd.setEnableAcc(false);
            telemetry.addData("GGWP", "fwd created");
        }catch (Exception e){
            telemetry.addData("ERROR",e.toString());
        }

        controller1 = new ButtonState();
        controller2 = new ButtonState();
    }

    @Override
    public void loop() {
        controller1.update(gamepad1);
        controller2.update(gamepad2);

        double rStick = (double)gamepad1.right_stick_y;
        double lStick = (double)gamepad1.left_stick_y;

        //Writing the throttle values to the motors.
        fwd.setPower(lStick, rStick);
        telemetry.addData("LftStick: ", lStick);
        telemetry.addData("RhtStick: ", rStick);

        if(controller1.x_press()) {
            fwd.invertDirections();
        }

        controller1.pushButtonHistory();
        controller2.pushButtonHistory();

    }


    public void setupMotors(){
        try {
            motL=hardwareMap.dcMotor.get("l");
            telemetry.addData("GGWP", "MOTOR L");
        }catch (Exception e){
            telemetry.addData("ERROR",e.toString());
        }
        try {
            motR=hardwareMap.dcMotor.get("r");
            telemetry.addData("GGWP", "MOTOR R");
        }catch (Exception e){
            telemetry.addData("ERROR",e.toString());
        }
    }
}