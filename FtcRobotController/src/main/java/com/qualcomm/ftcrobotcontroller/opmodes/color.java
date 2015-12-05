/* Copyright (c) 2014, 2015 Qualcomm Technologies Inc

All rights reserved.

Redistribution and use in source and binary forms, with or without modification,
are permitted (subject to the limitations in the disclaimer below) provided that
the following conditions are met:

Redistributions of source code must retain the above copyright notice, this list
of conditions and the following disclaimer.

Redistributions in binary form must reproduce the above copyright notice, this
list of conditions and the following disclaimer in the documentation and/or
other materials provided with the distribution.

Neither the name of Qualcomm Technologies Inc nor the names of its contributors
may be used to endorse or promote products derived from this software without
specific prior written permission.

NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
"AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE. */

package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.LegacyModule;
import com.qualcomm.robotcore.hardware.UltrasonicSensor;
import com.qualcomm.hardware.HiTechnicNxtColorSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

import java.lang.Math.*;


/**
 * TeleOp Mode
 * <p/>
 * Enables control of the robot via the gamepad
 */
public class color extends LinearOpMode {
    /*ColorSensor c;
    LegacyModule leg;*/
    //ElapsedTime t;
    DcMotor l;
    DcMotor r;

    public void runOpMode() throws InterruptedException {
        /*c = hardwareMap.colorSensor.get("color");
        c.enableLed(true);
        leg.enable9v(4, false);// all this crap sucks
        leg.enableAnalogReadMode(4);//it doesn't work at all
        leg.setDigitalLine(4, 0, true);*/
        l = hardwareMap.dcMotor.get("left");
        r = hardwareMap.dcMotor.get("right");
        l.setDirection(DcMotor.Direction.REVERSE);
        //end init
        waitForStart();
        //t.reset();
//        t.time();
        setwheel(3, 0, 3, 15);
        /*telemetry.addData("red", c.red());
        telemetry.addData("green", c.green());
        telemetry.addData("blue", c.blue());
        telemetry.addData("light", c.alpha());
        telemetry.addData("data", c.getConnectionInfo());*/
    }

    private boolean setwheel(double dist, double turn, double whd, double rbw) {// distance, turn amount, wheel diameter, robot width
        //l.setChannelMode(DcMotorController.RunMode.RUN_TO_POSITION);
        double rotdis = Math.PI * whd;//distance per wheel rotation in inches
        double disr = (Math.PI * rbw) * turn;//distance in inches needed to turn
        double distr = disr / rotdis;// rotations needed to turn properly
        double disf = dist / rotdis;

        l.setPower(0.5);
        r.setPower(0.5);
        l.setTargetPosition(((int) Math.round(disf * 1440)) + ((int) Math.round(distr * 1440)));
        r.setTargetPosition(((int) Math.round(disf * 1440)) - ((int) Math.round(distr * 1440)));
        l.setMode(DcMotorController.RunMode.RUN_TO_POSITION);
        r.setMode(DcMotorController.RunMode.RUN_TO_POSITION);
        if (!l.isBusy() && !r.isBusy()) {
            return true;
        } else {
            return false;
        }
    }
}