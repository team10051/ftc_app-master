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
    direction lr;//which way it should be currently turning.
    boolean turning;//should the robot be turning toward the light?
    direction lightdir;//the direction it thinks the light is in at beginning of 'update' cycle
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
        lr = direction.STOP;
    }

    public void loop() {
        re = (sensorRGB.red() * 255) / 800;
        gr = (sensorRGB.green() * 255) / 800;
        bl = (sensorRGB.blue() * 255) / 800;
        bright = (sensorRGB.alpha() * 255) / 800;
        /*if (turning) {
            if (bright > prebri) {
                
            }
        }*/ //this is a thing
        if (update) {
            prebri = bright;
            turning = true;
        }
        telemetry.addData("bright", bright);
        telemetry.addData("prebri", prebri);
        telemetry.addData("l=true, r=false", lr);
        telemetry.addData("update", update);
    }

    void setMotors(direction d) {
        l.setPower(d.lwheel);
        r.setPower(d.rwheel);
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

enum direction {
    LEFT(-1, 1), RIGHT(1, -1), STOP(0, 0);
    double lwheel;
    double rwheel;
    static double turnspeed;

    direction(double l, double r) {
        lwheel = l;
        rwheel = r;
    }

    void switchDir() {
        if (this == LEFT) {
            this.direction = RIGHT;
        }
    }
}
