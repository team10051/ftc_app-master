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
public class robot extends OpMode {
    final double d = 3.65;//lol
    DcMotor l;
    DcMotor r;
    DcMotor telearm;
    DcMotor extend;
    Servo lf;
    Servo rf;
    TouchSensor lim;
    final double servodownleft = 0.40392156862;//from 0 to 1
    final double servoupleft = 0.03921568627;//from 0 to 1
    final double servodownright = 0.43137254902;//from 0 to 1
    final double servoupright = 1.0;//from 0 to 1
    final double armupperlim = 380;
    final double armlowerlim = 0;
    String xbutt;
    boolean resetting;

    @Override
    public void init() {
        l = hardwareMap.dcMotor.get("left");
        r = hardwareMap.dcMotor.get("right");
        l.setDirection(DcMotor.Direction.REVERSE);
        telearm = hardwareMap.dcMotor.get("arm");
        lim = hardwareMap.touchSensor.get("lim");
        extend = hardwareMap.dcMotor.get("extend");
        lf = hardwareMap.servo.get("lf");
        rf = hardwareMap.servo.get("rf");
    }

    public void init_loop() {
        if (lim.isPressed()) {
            telearm.setPower(0);
            telearm.setMode(DcMotorController.RunMode.RESET_ENCODERS);
            telearm.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        } else {
            telearm.setPower(-0.1);
        }
    }

    @Override
    public void start() {
        l.setMode(DcMotorController.RunMode.RESET_ENCODERS);
        r.setMode(DcMotorController.RunMode.RESET_ENCODERS);
        l.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        r.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        telearm.setPower(0);
    }

    @Override
    public void loop() {
        l.setPower(gamepad1.left_stick_y);
        r.setPower(gamepad1.right_stick_y);
        xbutt = "not pressed";
        if (gamepad1.x) {
            l.setMode(DcMotorController.RunMode.RESET_ENCODERS);
            r.setMode(DcMotorController.RunMode.RESET_ENCODERS);
            resetting = true;
            xbutt = "pressed";
        }
        if (resetting && l.getCurrentPosition() == 0 && r.getCurrentPosition() == 0) {
            resetting = false;
            l.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
            r.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        }
        if (gamepad1.dpad_up) {
            telearm.setTargetPosition(telearm.getTargetPosition() + 1);
        }
        if (gamepad2.dpad_up) {
            lf.setPosition(servoupleft);
            rf.setPosition(servoupright);
        }
        if (gamepad2.dpad_down) {
            lf.setPosition(servodownleft);
            rf.setPosition(servodownright);
        }
        //if (telearm.getCurrentPosition() < armupperlim && telearm.getCurrentPosition() > armlowerlim) {
        telearm.setPower(-gamepad2.left_stick_y);

        //} else {
        //    telearm.setPower(0);
        //}
        if (gamepad2.a) {
            telearm.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        }
        if (telearm.getTargetPosition() != 0) {
            telearm.setMode(DcMotorController.RunMode.RESET_ENCODERS);
        }
       /* if (gamepad2.right_stick_y < 0) {
            extend.setPower(-0.25 * gamepad2.right_stick_y);
        } else if (gamepad2.right_stick_y > 0) {
            extend.setPower(-0.1 * gamepad2.right_stick_y);
        }*/
        extend.setPower(gamepad2.right_stick_y);
        telemetry.addData("extended by:", extend.getCurrentPosition());
        telemetry.addData("arm position", telearm.getCurrentPosition());
        telemetry.addData("lposition", l.getCurrentPosition());
        telemetry.addData("rposition", r.getCurrentPosition());
        telemetry.addData("lrotations", l.getCurrentPosition() / 1440.0);
        telemetry.addData("rrotations", r.getCurrentPosition() / 1440.0);
        telemetry.addData("lfeet", l.getCurrentPosition() / 1440.0 * d * Math.PI / 12);
        telemetry.addData("rfeet", r.getCurrentPosition() / 1440.0 * d * Math.PI / 12);
        telemetry.addData("x button is ", xbutt);
        telemetry.addData("resetting", resetting);
    }
}