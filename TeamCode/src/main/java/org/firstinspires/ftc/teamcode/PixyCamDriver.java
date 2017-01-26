package org.firstinspires.ftc.teamcode;

import android.util.Log;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.I2cAddr;
import com.qualcomm.robotcore.hardware.I2cControllerPortDevice;
import com.qualcomm.robotcore.hardware.I2cDevice;
import com.qualcomm.robotcore.hardware.I2cAddressableDevice;
import java.lang.Object;

/**
 * Created by Emma on 1/17/17.
 */

@TeleOp(name="PixyCamDriver", group="Test")
public class PixyCamDriver extends OpMode {

    private I2cDevice cam;
    private byte[] data;
    private short[] shortData;
    private static final String TAG = "CamDebugTag";


    public byte[] getData() {

        Log.d(TAG, "getData Started.");
        telemetry.addData("Get data started ", ":)");
        cam.enableI2cReadMode(new I2cAddr(0x54), 0, 14);
        cam.getI2cReadCacheLock();
        data = cam.getI2cReadCache();
        Log.d(TAG, "getData Returning.");
        telemetry.addData("Going to return data ", ":)");
        return data;
    }

    public short mergeBytes(byte b1, byte b2) {
        Log.d(TAG, "mergeBytes merging bytes");
        return (short) ((b1 << 8) | (b2 & 0xFF));
    }

    public short[] byteToShort(byte[] original) {
        Log.d(TAG, "byteToShort start");
        telemetry.addData("Byte to short started ", ":)");
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
        Log.d(TAG, "byteToShort bit shifting done... Returning data now.");
        telemetry.addData("Byte to short done ", ":)");
        return result;
    }

    @Override
    public void init() {

        Log.i(TAG, "init Start");
        telemetry.addData("Init started ", ":)");

        try {
            cam = hardwareMap.i2cDevice.get("Camera");
            telemetry.addData("Confirmed: ", "PixyCam");
        }catch (Exception e){
            telemetry.addData("ERROR",e.toString());
        }
        Log.i(TAG, "init Done");
        telemetry.addData("Init done ", ":)");
    }

    @Override
    public void loop() {

        Log.i(TAG, "loop start");
        getData();
        shortData = byteToShort(data);
        //String toPrintString = DatatoHexString(shortData);
        telemetry.addData("length of array: ", data.length);
        //telemetry.addData("HexString: ", toPrintString);

        Log.d(TAG, "loop teletry updating...");
        telemetry.addData("array: ", data[0] + " " + data[1] + " " + data[2] + " " + data[3]
                + " " + data[4] + " " + data[5] + " ");
        telemetry.addData("array: ", shortData[0] + " " + shortData[1] + " " + shortData[2] + " "+ shortData[3]
                + " " + shortData[4] + " " + shortData[5] + " ");
        telemetry.addData("Now In Loop ",":)");
        Log.d(TAG, "loop telemetry update done.");

    }

    public String DatatoHexString(short[] byteInput){
        Log.d(TAG, "DatatoHexString start");
        String returnString = "Data Array: ";
        for(int i=0; i<byteInput.length; i++){
            String inHexString = Integer.toHexString(byteInput[i] & 0xFF);
            String shortHexString = inHexString.substring(4);

            returnString = returnString + "\n" + inHexString;
        }

        Log.d(TAG, "DatatoHexString returning now");
        return returnString;
    }
}
