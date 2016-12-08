package org.firstinspires.ftc.teamcode;

import android.location.SettingInjectorService;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

/**
 * Created by Emma on 12/6/16.
 */

@TeleOp(name="PikaOp", group="Testie")
public class PikaOp extends OpMode {

    PikachuControl pika;

    DcMotor motfl, motfr, motrr, motrl;

    private double speedFL = 0.0, speedFR = 0.0, speedBL = 0.0, speedBR = 0.0;
    private double motorPolarity = 1;

    private double inputAngle;

    public void SetRawPower(double L, double R) {
        speedFL = Range.clip(L, -1, 1);
        speedFR = Range.clip(R, -1, 1);
        speedBL = Range.clip(R, -1, 1);
        speedBR = Range.clip(L, -1, 1);
        UpdateMotorPower();
    }


    public void pikaSetup() {
        //speedUpdater = new Timer("speedUpdater",true);
        //speedUpdater.scheduleAtFixedRate(new speedUpdateTask(), 0, speedUpdatePeriod);
        motfr.setDirection(DcMotor.Direction.REVERSE);
        motfl.setDirection(DcMotor.Direction.REVERSE);

        telemetry.addData("MekaDrive: ", "Setup.");
    }


    private void UpdateMotorPower() {
        try {
            motfl.setPower(speedFL * motorPolarity);
            telemetry.addData("motFL", speedFL);
            motfr.setPower(speedFR * motorPolarity);
            telemetry.addData("motFR", speedFR);
            motrl.setPower(speedBL * motorPolarity);
            telemetry.addData("motBL", speedBL);
            motrr.setPower(speedBR * motorPolarity);
            telemetry.addData("motBR", speedBR);
            telemetry.addData("Confirmed: ", "MOTORS OPERATIONAL");
        } catch (Exception e) {
            telemetry.addData("ERROR: ", e.toString());
        }
    }


    @Override
    public void init() {
        pika = new PikachuControl();
        setupMotors();
        pikaSetup();
    }

    @Override
    public void loop() {
        inputAngle = pika.rQuadrant(gamepad1.left_stick_x, gamepad1.left_stick_y);
        telemetry.addData("Stick: ", "Quadrant: " + pika.rQuadrant(gamepad1.left_stick_x, gamepad1.left_stick_y));
        telemetry.addData("Stick: ", "Magnitude: " + pika.rDistance(gamepad1.left_stick_x, gamepad1.left_stick_y));
        telemetry.addData("FL & RR Power: ", pika.powerFLRR(gamepad1.left_stick_x, gamepad1.left_stick_y));
        telemetry.addData("FR & RL Power: ", pika.powerFRBL(gamepad1.left_stick_x, gamepad1.left_stick_y));
        telemetry.addData("Linearly Controlled Power: ", pika.powerGraph(2, inputAngle) + ", " + pika.powerGraph(1, inputAngle));
        telemetry.addData("Angle: ", (inputAngle) + ", " + Math.toRadians(inputAngle));
        UpdateMovementInput();
        UpdateMotorPower();
    }


    public void UpdateMovementInput() {

        //The first one is not linear, the second one is linear.

        /*SetRawPower(pika.powerFLRR(gamepad1.left_stick_x, gamepad1.left_stick_y) * pika.rDistance(gamepad1.left_stick_x, gamepad1.left_stick_y),
                pika.powerFRBL(gamepad1.left_stick_x, gamepad1.left_stick_y) * pika.rDistance(gamepad1.left_stick_x, gamepad1.left_stick_y));*/
        SetRawPower(pika.powerGraph(2, inputAngle)* pika.rDistance(gamepad1.left_stick_x, gamepad1.left_stick_y) , pika.powerGraph(1, inputAngle)* pika.rDistance(gamepad1.left_stick_x, gamepad1.left_stick_y));
    }


    public void setupMotors() {
        //Drive Motors
        try {
            motfr = hardwareMap.dcMotor.get("fr");
            telemetry.addData("Confirmed: ", "Motor FR");
        } catch (Exception e) {
            telemetry.addData("ERROR", e.toString());
        }
        try {
            motfl = hardwareMap.dcMotor.get("fl");
            telemetry.addData("Confirmed: ", "Motor FL");
        } catch (Exception e) {
            telemetry.addData("ERROR", e.toString());
        }
        try {
            motrr = hardwareMap.dcMotor.get("rr");
            telemetry.addData("Confirmed: ", "Motor RR");
        } catch (Exception e) {
            telemetry.addData("ERROR", e.toString());
        }
        try {
            motrl = hardwareMap.dcMotor.get("rl");
            telemetry.addData("Confirmed: ", "Motor RL");
        } catch (Exception e) {
            telemetry.addData("ERROR", e.toString());
        }

    }
}