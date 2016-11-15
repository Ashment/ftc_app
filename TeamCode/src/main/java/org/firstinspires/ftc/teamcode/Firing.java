package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

/**
 * Created by Emma on 11/15/16.
 */

public class Firing extends OpMode {

    DcMotor loader, shooter;

    //Constructor
    public Firing(DcMotor load, DcMotor shoot) {
        loader = load;
        shooter = shoot;
    }

    @Override
    public void init() {

    }

    @Override
    public void loop() {

    }

    //Set Motor Powers
    public void SetLoadingPower(double power){
        double p = Range.clip(power, -1, 1);
        loader.setPower(p);
    }

    public void SetShootingPower(double power){
        double p = Range.clip(power, -1, 1);
        shooter.setPower(p);
    }

}
