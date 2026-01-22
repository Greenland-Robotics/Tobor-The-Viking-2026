package gcsrobotics.opmodes;

import gcsrobotics.framework.AutoBase;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;// You won't need this

import gcsrobotics.framework.AutoBase;

@Autonomous(name="Auto1")
@Disabled
public class Auto1 extends AutoBase {

    //Not necessary, but this is the code to run during init
    @Override
    protected void initSequence(){
    }

    @Override
    protected void runSequence() {
        path(12,12);
        turn(90);
        path(12,20);
    }

    private void setPowers(int i) {
    }
}