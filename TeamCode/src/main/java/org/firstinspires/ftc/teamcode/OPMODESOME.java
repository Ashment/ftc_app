package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by eric02px2020 on 10/25/16
 */
public class OPMODESOME extends OpMode{

    public OPMODESOME() {

    }

    DcMotor motL,motR, motD, motA;

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

        flapPos = 0.2;

        double swayDefaultPos = 0;


    }
    double tapeVel;

    @Override
    public void loop() {
        controller1.update(gamepad1);
        controller2.update(gamepad2);

        double rStick = (double)gamepad1.right_stick_y;
        double lStick = (double)gamepad1.left_stick_y;

        double rStick2 = (double)gamepad2.right_stick_y;
        double lStick2 = (double)gamepad2.left_stick_y;

        double rTrig2 = (double)gamepad2.right_trigger;
        double lTrig2 = (double)gamepad2.left_trigger;
        rTrig2=(rTrig2+1.0)/2.0;
        lTrig2=(lTrig2+1.0)/2.0;
        rStick2=(rStick2+1.0)/2.0;
        lStick2/=1.2;

        //Writing the throttle values to the motors.
        fwd.setPower(lStick, rStick);
        telemetry.addData("LftStick: ", lStick);
        telemetry.addData("RhtStick: ", rStick);



        telemetry.addData("LftStick2: ", lStick2);
        telemetry.addData("RhtStick2: " , rStick2);
        telemetry.addData("LftTrigger2: ", lTrig2 );
        telemetry.addData("RhtTrigger2: " , rTrig2);

        if(controller1.x_press()) {
            fwd.invertDirections();
        }



        if(controller2.left_bumper_press()){
            if(flapPos <= 0.9) {
                flapPos += 0.1;
            }
        }
        if(controller2.right_bumper_press()){
            if(flapPos >= 0.1) {
                flapPos -= 0.1;
            }
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
