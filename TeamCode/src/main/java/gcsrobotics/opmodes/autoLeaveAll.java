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
 * @author Daniel Sabalakov (PLEASE CONTACT ME IF YOU HAVE ANY QUESTIONS)
 */
@Autonomous(name="autoLeaveAll")
public class autoLeaveAll extends AutoBase {
    public static double LAUNCHER_VELOCITY = 0;
    public static double KICKER_POSITION = 0.3;
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
    public boolean fakeVariable = true;
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
        while(opModeIsActive() && fakeVariable) {

            path(-15, 0, Axis.Y);


            //fakeVariable=false;

        }
            //turn(-45);
            //intakeTime.reset();
            //intakeAndLaunch(target_launcher_speed);
            //turn(-45);
            //path(-24,0,Axis.Y);


    }


    public void intakeAndLaunch(double TARGET_SPEED){

        while (opModeIsActive() && !intakeDone) {
            kicker.setPosition(KICKER_POSITION);
            right_servo_feeder.setPower(INTAKE_RIGHT);
            left_servo_feeder.setPower(-INTAKE_RIGHT);
            intake.setPower(GROUND_INTAKE_SPEED);


            launcher.setVelocity(TARGET_SPEED);
            if (intakeTime.seconds() > 4) {intakeDone = true;}

        }
    }

}
