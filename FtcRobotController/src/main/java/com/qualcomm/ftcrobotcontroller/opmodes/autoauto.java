package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.HardwareDevice;

/*
 * Created by 10051 on 11/17/2015.
 */
public class autoauto extends OpMode{
    DcMotor l;
    DcMotor r;
    @Override
    public void init() {
        l = hardwareMap.dcMotor.get("left");
        r = hardwareMap.dcMotor.get("right");
        l.setDirection(DcMotor.Direction.REVERSE);
    }
    @Override
    public void loop() {

    }
}
class myDcMotor extends DcMotor implements HardwareDevice{
    private double TargetPosition;
    public myDcMotor(DcMotorController controller, int portNumber) {
        super(controller, portNumber);
    }

    public myDcMotor(DcMotorController controller, int portNumber, Direction direction) {
        super(controller, portNumber, direction);
    }

    @Override
    public void setTargetPosition(int position) {// probably only works in loop mode
        TargetPosition = position;
        if (super.getCurrentPosition()<TargetPosition) {
            super.setPower(1);
        } else if (super.getCurrentPosition()>TargetPosition){
            super.setPower(-1);
        } else {
            super.setPower(0);
        }
    }
}