package gcsrobotics.tuners;


import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import gcsrobotics.framework.TeleOpBase;

/**
 * Flywheel Tuner tunes the flywheels used in the 25-26 FTC season
 * Static integers can be set in FTC dashboard via http://192.168.43.1:8080/dash
 *
 * @note: LAUNCHER_VELOCITY will run once and will not stop unless you set the velocity to 0
 *
 * @author Daniel Sabalakov
 */
@TeleOp(name="Flywheel Tuner")
@Config
public class FlywheelTuner extends TeleOpBase {

    public static double LAUNCHER_VELOCITY = 0;
    public DcMotorEx launcher;

    @Override
    protected void inInit() {
        launcher = hardwareMap.get(DcMotorEx.class, "launcher");
        launcher.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    @Override
    protected void run() {
        while(opModeIsActive()){runLoop();}
    }

    @Override
    protected void runLoop() {
        telemetry.addLine("LAUNCHER_VELOCITY: " + launcher.getVelocity());
        telemetry.addLine("LAUNCHER_VELOCITY can be set to the right.");


        launcher.setVelocity(LAUNCHER_VELOCITY);


        telemetry.update();
    }
}
