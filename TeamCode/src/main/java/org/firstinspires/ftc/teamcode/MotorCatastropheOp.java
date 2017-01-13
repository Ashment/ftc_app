package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;


/**
 * Created by Emma on 01/13/17.
 */

@Autonomous(name="Motor Catastrophe", group="Autonomous")
public class MotorCatastropheOp extends OpMode {

    DcMotor shooter;

    //Drivers
    Firing fire;

    double timeOne = 0, fireDuration = 8;
    double startTime;
    double runTime;

    public void SetupMotors(){
        try {
            shooter=hardwareMap.dcMotor.get("shooter");
            telemetry.addData("Confirmed: ", "Shooter");
        }catch (Exception e){
            telemetry.addData("ERROR",e.toString());
        }
    }

    @Override
    public void init() {
        //Hardware Setup
        SetupMotors();

        startTime = getRuntime();

        try{
            fire = new Firing(shooter);
            telemetry.addData("Sucess: ", "Firing Setup Complete");
        }catch (Exception e) {
            telemetry.addData("ERROR: ", "Firing Setup Failure.");
        }

    }


    @Override
    public void loop() {
        runTime = getRuntime() - startTime;
        telemetry.addData("Run time: ", runTime + "Actual Run time: " + getRuntime());
        if (runTime > timeOne && runTime <= timeOne + fireDuration) {
            fire.SetShootingPower(-1);
        }else{
            fire.SetShootingPower(0);
        }
    }

}
