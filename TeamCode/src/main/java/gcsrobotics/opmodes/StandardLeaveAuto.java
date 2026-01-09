package gcsrobotics.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import gcsrobotics.framework.AutoBase;

@Autonomous(name="StandardLeaveAuto")
public class StandardLeaveAuto extends AutoBase {
    public DcMotorEx launcher;

    @Override
    protected void runInit() {
        //call a super incase there is super mumbo jumbo AutoBase Abstraction
        super.runInit();

        launcher = hardwareMap.get(DcMotorEx.class, "launcher");
        launcher.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    @Override
    public void run() {
        while (opModeIsActive()){
            runSequence();
        }
    }

    @Override
    public void runSequence() {
        //commands get run here...
        path(72,0,Axis.Y);
    }
}
