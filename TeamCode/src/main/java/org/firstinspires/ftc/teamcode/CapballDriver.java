package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

/**
 * Created by Emma on 1/11/17.
 */

public class CapballDriver extends OpMode {

    DcMotor motLeft, motRight;
    CRServo servoLeft, servoRight;

    public CapballDriver(CRServo left, CRServo right, DcMotor motl, DcMotor motr) {

        motLeft = motl;
        motRight = motr;
        servoLeft = left;
        servoRight = right;

        motLeft.setPower(0);
        motRight.setPower(0);
        servoLeft.setPower(0);
        servoRight.setPower(0);

    }

    @Override
    public void init() {

    }

    @Override
    public void loop() {

    }

    public void extendArm(double power) {
        double p = Range.clip(power, -1, 1);
        servoLeft.setPower(p);
        servoRight.setPower(p);
    }

    public void raiseArm(double power) {
        double p = Range.clip(power, -1, 1);
        motLeft.setPower(p);
        motRight.setPower(p);
    }

    public double getMotorPower() {
        return motLeft.getPower();
    }

    public double getServoPower() {
        return servoLeft.getPower();
    }

}
