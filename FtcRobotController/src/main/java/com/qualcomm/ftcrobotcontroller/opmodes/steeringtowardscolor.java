package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DeviceInterfaceModule;
import com.qualcomm.robotcore.hardware.DigitalChannelController;

/*
 * Created by 10051 on 12/1/2015.
 */
public class steeringtowardscolor extends OpMode {
    static final int LED_CHANNEL = 5;
    public static boolean update;
    ColorSensor sensorRGB;
    DeviceInterfaceModule cdim;
    DcMotor l;
    DcMotor r;
    double re;
    double gr;
    double bl;
    double bright;
    double prebri;
    int diff = 2;//why an int? idfk man
    boolean lr;//true = left, false = right
    Runnable job = new sleeping();
    Thread t = new Thread(job);

    public void init() {
        hardwareMap.logDevices();
        cdim = hardwareMap.deviceInterfaceModule.get("dim");
        cdim.setDigitalChannelMode(LED_CHANNEL, DigitalChannelController.Mode.OUTPUT);
        sensorRGB = hardwareMap.colorSensor.get("color");
        boolean bEnabled = true;
        cdim.setDigitalChannelState(LED_CHANNEL, bEnabled);
        l = hardwareMap.dcMotor.get("left");
        r = hardwareMap.dcMotor.get("right");
        l.setDirection(DcMotor.Direction.REVERSE);

    }

    public void start() {
        t.start();
    }

    public void loop() {
        re = (sensorRGB.red() * 255) / 800;
        gr = (sensorRGB.green() * 255) / 800;
        bl = (sensorRGB.blue() * 255) / 800;
        bright = (sensorRGB.alpha() * 255) / 800;
        if (lr) {
            if (Math.abs(bright - prebri) < diff) {
                l.setPower(0);
                r.setPower(0);
                lr = false;
            } else if (bright > prebri) {
                l.setPower(-1.0);
                r.setPower(1.0);
                lr = true;
            } else {
                lr = false;
            }
        } else if (!lr) {// THIS IS THE DIFFERENCE BETWEEN IF AND ELSE IF! Yay. we will see what works better.
            if (Math.abs(bright - prebri) < diff) {
                l.setPower(0);
                r.setPower(0);
                lr = true;
            } else if (bright > prebri) {
                l.setPower(1.0);
                r.setPower(-1.0);
                lr = false;
            } else {
                lr = true;
            }
        }
        if (update) {
            prebri = bright;
        }
        telemetry.addData("bright", bright);
        telemetry.addData("prebri", prebri);
        telemetry.addData("l=true, r=false", lr);
        telemetry.addData("update", update);
    }
}

class sleeping implements Runnable {
    public void run() {
        while (true) {
            go();
        }
    }

    void go() {

        steeringtowardscolor.update = false;
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        steeringtowardscolor.update = true;
    }
}
