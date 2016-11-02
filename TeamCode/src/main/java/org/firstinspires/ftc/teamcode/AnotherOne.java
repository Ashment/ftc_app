package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by eric02px2020 on 9/30/16.
 */

@Autonomous(name="AnotherOne")
@Disabled
public class AnotherOne extends OpMode{

    DcMotor motorLeft=null;
    DcMotor motorRight=null;

    Servo armServo=null;

    @Override public void main() throws InterruptedException{



        waitforstart();
        while(opModeisActive){
            if(updateGamepads){

            }
            telemetry.update();
            idle();
        }
    }

}
