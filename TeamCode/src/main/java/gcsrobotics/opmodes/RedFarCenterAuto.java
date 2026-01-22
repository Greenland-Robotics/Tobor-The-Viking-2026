package gcsrobotics.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import gcsrobotics.framework.AutoBase;
@Autonomous(name="RedFarCenterAuto")
public class RedFarCenterAuto extends AutoBase {
    public static double LAUNCHER_VELOCITY = 0;
    public static double KICKER_POSITION_UP = 0.5;
    public static double INTAKE_RIGHT = -1;
    public static double GROUND_INTAKE_SPEED = -1;
    public static double target_launcher_speed = 1000;
    public static int targetAngle = -41;


    public DcMotorEx launcher;
    public Servo kicker;
    public CRServo left_servo_feeder, right_servo_feeder;
    public DcMotor intake;
    public ElapsedTime intakeTime = new ElapsedTime();
    public boolean intakeDone = false;
    boolean kickerActive = false;
    ElapsedTime kickerTimer = new ElapsedTime();
    public int TARGET_X = -70;
    public int target_Y = 2;
    public double SHOOTER_VELOCITY = 1050;

    /// The code that runs during start

    @Override
    protected void runInit() {
        super.runInit();

        launcher = hardwareMap.get(DcMotorEx.class, "launcher");
        launcher.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        kicker = hardwareMap.get(Servo.class, "kicker");
        left_servo_feeder = hardwareMap.get(CRServo.class, "left_servo_feeder");
        right_servo_feeder = hardwareMap.get(CRServo.class, "right_servo_feeder");
        intake = hardwareMap.get(DcMotor.class, "intake");

        left_servo_feeder.setPower(0);
        right_servo_feeder.setPower(0);
    }

    @Override
    protected void run() {
        runSequence();
    }

    @Override
    public void runSequence() {
            while (opModeIsActive()) {
                kicker.setPosition(0);
                path(TARGET_X, target_Y);
                launcher.setVelocity(SHOOTER_VELOCITY);
                if (!kickerActive) {
                    kickerTimer.reset();
                }
                kickerActive = true;
                if (kickerActive && kickerTimer.milliseconds() > 5000) {
                    turn(targetAngle);
                    kicker.setPosition(0.5);   // Move Down
                    right_servo_feeder.setPower(-1);
                    left_servo_feeder.setPower(1);
                    kickerTimer.reset();
                    while (kickerTimer.milliseconds() > 1000 && kickerTimer.milliseconds() < 6000) {
                        right_servo_feeder.setPower(-0.15);
                        left_servo_feeder.setPower(0.1);
                        intake.setPower(-0.5);
                    }
                    while (kickerTimer.milliseconds() > 6000) {
                        path(-63, 12);
                    }
                }
            }
        }
    }