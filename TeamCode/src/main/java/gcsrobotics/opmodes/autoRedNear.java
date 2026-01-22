package gcsrobotics.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import gcsrobotics.framework.AutoBase;

/**
 *
 * @author Daniel Sabalakov- Timmy and Luke and Mack. and idk prob someone else too (PLEASE CONTACT ME (daniel) IF YOU HAVE ANY QUESTIONS)
 */
@Autonomous(name="autoRedNear")
public class autoRedNear extends AutoBase {
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
    public double SHOOTER_VELOCITY = 1085;

    //public int TARGET_X = 50;
    //public int target_Y = 0;
    @Override
    protected void runInit() {
        //call a super incase there is super mumbo jumbo AutoBase Abstraction

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
    public void run() {
        path(50, 0);
//            runSequence();
//        }
        runSequence();
    }

    @Override
    public void runSequence() {
        //commands get run here...
        kicker.setPosition(0.5);
        while (opModeIsActive()) {
            kicker.setPosition(0);
            launcher.setVelocity(SHOOTER_VELOCITY);
            if (!kickerActive) {
                kickerTimer.reset();
            }
            kickerActive = true;
            if (kickerActive && kickerTimer.milliseconds() > 4000) {
                kicker.setPosition(0.5);   // Move Down
                kickerTimer.reset();
                while (kickerTimer.milliseconds() < 5000) {
                    right_servo_feeder.setPower(-0.1);
                    left_servo_feeder.setPower(0.1);
                    intake.setPower(-0.5);
                }
                kicker.setPosition(0);
                turn(130);
                path(38, 3);
                wait(1000);
                path(22,21);
                path(50,0);
                kickerActive = true;
                if (kickerActive && kickerTimer.milliseconds() > 4000) {
                    kicker.setPosition(0.5);   // Move Down
                    kickerTimer.reset();
                    while (kickerTimer.milliseconds() < 5000) {
                        right_servo_feeder.setPower(-0.1);
                        left_servo_feeder.setPower(0.1);
                        intake.setPower(-0.5);

            }
        }
    }
}}}
