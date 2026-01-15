package gcsrobotics.opmodes;

import gcsrobotics.framework.AutoBase;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

public abstract class autoRedANDBLUEFar extends AutoBase {
    public static double LAUNCHER_VELOCITY = 0;
    public static double KICKER_POSITION_UP = 0.5;
    public static double INTAKE_RIGHT = -1;
    public static double GROUND_INTAKE_SPEED = -1;
    public static double target_launcher_speed = 1200;
    public static int targetAngle = 0;


    public DcMotorEx launcher;
    public Servo kicker;
    public CRServo left_servo_feeder, right_servo_feeder;
    public DcMotor intake;
    public ElapsedTime intakeTime = new ElapsedTime();
    public boolean intakeDone = false;
    boolean kickerActive = false;
    ElapsedTime kickerTimer = new ElapsedTime();
    public int TARGET_X = 50;
    public int target_Y = 0;
    private int SHOOTER_VELOCITY = 1700;

    /// The code that runs during start
    protected abstract void runSequence();

    @Override
    protected void runInit() {
        initSequence();
    }

    @Override
    protected void run() {
        runSequence();

        while (opModeIsActive()) {
            launcher.setVelocity(SHOOTER_VELOCITY);
            if (!kickerActive) {
                kickerTimer.reset();
            }
            kickerActive = true;
            kicker.setPosition(0.5);   // Move Down
            if (kickerActive && kickerTimer.milliseconds() >1000) {
                right_servo_feeder.setPower(-1);
                left_servo_feeder.setPower(1);
                kickerTimer.reset();
                while (kickerTimer.milliseconds() < 3000) {
                    right_servo_feeder.setPower(-0.1);
                    left_servo_feeder.setPower(0.1);
                    intake.setPower(-0.5);
                    path(12, 0);
                }
            }
        }
    }
}