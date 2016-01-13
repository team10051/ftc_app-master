package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.hardware.UltrasonicSensor;

public class autonomous2016 extends OpMode {
    double d = 3.65;
    double w = 17.0;
    DcMotor l;//make the left motor
    DcMotor r;//make the right motor
    DcMotor telearm;//make the arm up and down
    DcMotor extend;//make the extend motor
    Servo lf;//make the left servo
    Servo rf;//make the right servos

    public void init() {
        l = hardwareMap.dcMotor.get("left");//reference all of the things in the config file
        r = hardwareMap.dcMotor.get("right");
        r.setDirection(DcMotor.Direction.REVERSE);
        telearm = hardwareMap.dcMotor.get("arm");
        extend = hardwareMap.dcMotor.get("extend");
    }

    public void loop() {
        /*if (l.getCurrentPosition() / 1440.0 * d * Math.PI / 12 < 5) {
            l.setPower(0.5);
            r.setPower(0.5);
        } else {
            l.setPower(0);
            r.setPower(0);
        }*///go straight 5 ft

        //dpfn'asgkjfa;dsl/k

        if (l.getCurrentPosition() / 1440.0 * d * Math.PI < w * Math.PI * 0.25) {
            l.setPower(0.25);
            r.setPower(-0.25);
        } else {
            l.setPower(0);
            r.setPower(0);
        }
        telemetry.addData("lfeet", l.getCurrentPosition() / 1440.0 * d * Math.PI / 12);
        telemetry.addData("rfeet", r.getCurrentPosition() / 1440.0 * d * Math.PI / 12);
    }
}
