package gcsrobotics.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import gcsrobotics.framework.AutoBase;
import gcsrobotics.framework.Constants;

/**
 *
 * @author Daniel Sabalakov (PLEASE CONTACT ME IF YOU HAVE ANY QUESTIONS)
 */
@Autonomous(name="autoBlueNear")
public class autoBlueNear extends AutoBase {
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

    public int target_Y = 0;

    @Override
    protected void runInit() {
        //call a super incase there is super mumbo jumbo AutoBase Abstraction
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
    public void run() {
//        while (opModeIsActive()){
//            runSequence();
//        }
        runSequence();
    }
    @Override
    public void runSequence() {
        //commands get run here...
        //kicker.setPosition(0.5);
        while (opModeIsActive()) {
            kicker.setPosition(0);
            launcher.setVelocity(Constants.autoCloseShootSpeed);
            path(Constants.autoCloseTaxiX, target_Y, Axis.Y);
            if (!kickerActive) {
                kickerTimer.reset();
            }
            kickerActive = true;
            //tinker with time here for shooting delay
            if (kickerActive && kickerTimer.milliseconds() > 4000) {
                kicker.setPosition(0.5);   // Move Down
                kickerTimer.reset();
                //tinker with time for delay to move
                while (kickerTimer.milliseconds() < 5000) {
                    right_servo_feeder.setPower(-Constants.autoCloseFeedSpeed);
                    left_servo_feeder.setPower(Constants.autoCloseFeedSpeed);
                    intake.setPower(-0.5);
                }
                //tinker here for left right move for taxi
                target_Y = -Constants.autoCloseTaxiY;
            }

        }
    }
}
