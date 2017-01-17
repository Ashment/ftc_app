package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.I2cAddr;
import com.qualcomm.robotcore.hardware.I2cControllerPortDevice;
import com.qualcomm.robotcore.hardware.I2cDevice;
import com.qualcomm.robotcore.hardware.I2cAddressableDevice;

/**
 * Created by Emma on 1/17/17.
 */

@TeleOp(name="PixyCamDriver", group="Test")
public class PixyCamDriver extends OpMode {

    private I2cDevice cam;
    private byte[] data;

    public PixyCamDriver() {
        cam.enableI2cReadMode(new I2cAddr(0x54), 0, 14);
        cam.getI2cReadCache();

    }


    @Override
    public void init() {

        try {
            cam = hardwareMap.i2cDevice.get("Camera");
            telemetry.addData("Confirmed: ", "PixyCam");
        }catch (Exception e){
            telemetry.addData("ERROR",e.toString());
        }

        PixyCamDriver namesss= new PixyCamDriver();

    }

    @Override
    public void loop() {
        cam.getI2cReadCacheLock();
        data = cam.getCopyOfReadBuffer();
    }
}
