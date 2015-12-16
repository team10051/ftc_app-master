package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.hardware.UltrasonicSensor;

/**
 * TeleOp Mode
 * <p/>
 * Enables control of the robot via the gamepad
 */
public class servotest extends OpMode {
    Servo lf;
    Servo rf;

    @Override
    public void init() {
        lf = hardwareMap.servo.get("lf");
        rf = hardwareMap.servo.get("rf");
    }

    @Override
    public void loop() {
        telemetry.addData("left", lf.getPosition());
        telemetry.addData("right", rf.getPosition());
    }
}