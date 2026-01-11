package gcsrobotics.tuners;


import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;

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
    public static double KICKER_POSITION = 0.3;
    public static double INTAKE_RIGHT = -1;
    public static double GROUND_INTAKE_SPEED = -1;
    public DcMotorEx launcher;
    public CRServo left_servo_feeder, right_servo_feeder;
    public Servo kicker;
    public DcMotor intake;

    @Override
    protected void inInit() {
        launcher = hardwareMap.get(DcMotorEx.class, "launcher");
        launcher.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        kicker = hardwareMap.get(Servo.class, "kicker");
        left_servo_feeder = hardwareMap.get(CRServo.class, "left_servo_feeder");
        right_servo_feeder = hardwareMap.get(CRServo.class, "right_servo_feeder");
        intake = hardwareMap.get(DcMotor.class, "intake");


        kicker.setPosition(0.3);
        left_servo_feeder.setPower(0);
        right_servo_feeder.setPower(0);
    }

    @Override
    protected void run() {
        while(opModeIsActive()){runLoop();}
    }

    @Override
    protected void runLoop() {
        telemetry.addLine("LAUNCHER_VELOCITY: " + launcher.getVelocity());
        telemetry.addLine("LAUNCHER_VELOCITY can be set to the right.");
        telemetry.addLine("Kicker Position: " + kicker.getPosition());
        telemetry.addLine("Left Servo Feeder Power: " + left_servo_feeder.getPower());
        telemetry.addLine("Right Servo Feeder Power: " + right_servo_feeder.getPower());


        kicker.setPosition(KICKER_POSITION);
        right_servo_feeder.setPower(INTAKE_RIGHT);
        left_servo_feeder.setPower(-INTAKE_RIGHT);
        intake.setPower(GROUND_INTAKE_SPEED);


        launcher.setVelocity(LAUNCHER_VELOCITY);


        telemetry.update();
    }
}
