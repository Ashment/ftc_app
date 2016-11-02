package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by eric02px2020 on 9/29/16.
 */

// DECLARE MOTORS
public class SomeOpmode extends OpMode{
    DcMotor motorLeft = null;
    DcMotor motorRight = null;

    //DECLARE SERVO
    Servo servoArm = null;

    //SET DEFAULT POSITION FOR SERVO TO FIT IN 18 INCH BOX
    double ARM_MIN = 0.2;
    double ARM_MAX= 0.8;

    public void init(){
        //WAIT FOR GAME TO START
        waitForStart();


        //DO STUFF
        while(opModeIsActive()){
            if(updateGamepads()){
                //DRIVE
                motorLeft.setPower(gamepad1.left_stick_y);
                motorRight.setPower(gamepad1.right_stick_y);

                //MOVE ARM
                if(gamepad2.a){
                    servoArm.setPosition(ARM_MIN);
                }
                else if(gamepad2.b){
                    servoArm.setPosition(ARM_MAX);
                }
            }
        }
    }

    @Override public void main() throws InterruptedException{

        //INITIALIZE MOTORS
        motorLeft = hardwareMap.dcMotor.get("motorLeft");
        motorRight = hardwareMap.dcMotor.get("motorRight");

        //SET CHANNEL MODE?
        //motorLeft.setChannelMode(DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);

        //REVERSE ONE OF THE MOTORS OR THE MOTORS WILL BE SPINNING IN OPPOSITE DIRECTIONS
        motorLeft.setDirection(DcMotor.Direction.REVERSE);

        //INITIALIZE SERVO TO FIT IN BOX
        servoArm.setPosition(ARM_MAX);





    }
}
