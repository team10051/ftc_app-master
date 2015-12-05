package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.Servo;
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
    String xbutt;
    boolean resetting;

    @Override
    public void init() {
        l = hardwareMap.dcMotor.get("left");
        r = hardwareMap.dcMotor.get("right");
        l.setDirection(DcMotor.Direction.REVERSE);
        telearm = hardwareMap.dcMotor.get("arm");
        telearm.setMode(DcMotorController.RunMode.RUN_TO_POSITION);
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
        telearm.setTargetPosition(telearm.getTargetPosition() - (int) gamepad2.left_stick_y);
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
