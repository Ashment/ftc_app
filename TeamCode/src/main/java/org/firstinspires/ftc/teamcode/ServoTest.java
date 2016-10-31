package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.*;

/**
 * Created by Ashment on 10/28/16.
 */

@TeleOp(name="ServoTest", group="Test")
public class ServoTest extends OpMode {

    ServoDriver ServoD;
    Servo servo;

    ButtonState joy1;

    public void init(){
        try{
            servo = hardwareMap.servo.get("s");
        }catch (Exception e){
            telemetry.addData("Error", e.toString());
        }

        try {
            ServoD = new ServoDriver(servo, false, 0.3);
        }catch (Exception e){
            telemetry.addData("Error", e.toString());
        }

        joy1 = new ButtonState();
    }

    public void loop(){
        joy1.update(gamepad1);

        if(joy1.a_press()){
            ServoD.sway(1);
        }
        if(joy1.b_press()){
            ServoD.sway(-1);
        }
    }

}
