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
    private short[] shortData;

    public PixyCamDriver() {
        cam.enableI2cReadMode(new I2cAddr(0x54), 0, 14);
        cam.getI2cReadCache();
    }

    public byte[] getData() {

        cam.enableI2cReadMode(new I2cAddr(0x54), 0, 14);
        cam.getI2cReadCache();
        cam.getI2cReadCacheLock();
        data = cam.getCopyOfReadBuffer();
        return data;

    }

    public short mergeBytes(byte b1, byte b2) {
        return (short) ((b1 << 8) | (b2 & 0xFF));
    }

    public short[] byteToShort(byte[] original) {
        int k = original.length;
        short[] result;
        if (k % 2 != 0) {
            telemetry.addData("??", original.length);
            k = k-1;
        }
        result = new short[k/2];
        for (int i = 0; i < k/2; i++) {
            result[i] = mergeBytes(original[2*i + 1], original[2*i]);
        }
        return result;
    }

    @Override
    public void init() {

        try {
            cam = hardwareMap.i2cDevice.get("Camera");
            telemetry.addData("Confirmed: ", "PixyCam");
        }catch (Exception e){
            telemetry.addData("ERROR",e.toString());
        }

    }

    @Override
    public void loop() {
        getData();
        shortData = byteToShort(data);
        telemetry.addData("length of array: ", data.length);
        telemetry.addData("array: ", data[0] + " " + data[1] + " " + data[2] + " "+ data[3]
                + " " + data[4] + " " + data[5] + " ");
        telemetry.addData("array: ", shortData[0] + " " + shortData[1] + " " + shortData[2] + " "+ shortData[3]
                + " " + shortData[4] + " " + shortData[5] + " ");
    }


    public String DataHexString(short[] byteInput){
        String returnString = "Data Array: ";
        for(int i=0; i<byteInput.length; i++){
            String inHexString = Integer.toHexString(byteInput[i] & 0xFF);
            returnString = returnString + " " + inHexString;
        }
        return returnString;
    }
}
