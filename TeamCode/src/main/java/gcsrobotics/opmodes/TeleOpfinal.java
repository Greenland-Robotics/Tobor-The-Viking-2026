package gcsrobotics.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;


import gcsrobotics.framework.TeleOpBase;


@TeleOp(name="TeleOpfinal")
public class TeleOpfinal extends TeleOpBase {
    public Servo kicker;
    public ElapsedTime time;
    public CRServo right_servo_feeder;
    public CRServo left_servo_feeder;
    public DcMotorEx launcher;
    public DcMotor intake;
    // Runs once when INIT is pressed
    ElapsedTime kickerTimer = new ElapsedTime();
    public double lastSpeed = 0;
    public double currentSpeed = 0;

    //MAX_MAX VELOCITY is used for the max velocity that the y
    public double MAXIMUM_MAXIMUM_VELOCITY = 1800;
    //MAX VELOCITY IS USED FOR DPAD
    public double MAXIMUM_VELOCITY = 1700;
    boolean kickerActive = false;
    @Override
    protected void inInit() {
        kicker = hardwareMap.get(Servo.class, "kicker");
        left_servo_feeder = hardwareMap.get(CRServo.class, "left_servo_feeder");
        right_servo_feeder = hardwareMap.get(CRServo.class, "right_servo_feeder");
        launcher = hardwareMap.get(DcMotorEx.class, "launcher");
        launcher.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        intake = hardwareMap.get(DcMotor.class, "intake");
//        fl.setDirection(DcMotor.Direction.REVERSE);
//        time = new ElapsedTime();

        // Any custom initialization can go here
        // For example: reset sensors, prepare servos, etc.
    }
    @Override
    protected void run() {
        telemetry.addLine("run START");
        telemetry.update();
        runLoop();
    }
    // Runs repeatedly while the OpMode is active
    @Override
    protected void runLoop() {
        telemetry.addLine("Run loop worked");
        telemetry.update();
        while(opModeIsActive()) {
            //reset time
//            time.reset();
//            time.startTime();
            telemetry.addLine("Velocity? : " + launcher.getVelocity());
            telemetry.addLine("Loop is working");
            telemetry.update();

            /// THIS PART IS GAMEPAD 1
            implementDriveLogic();
            //toggleFieldCentric(false);
//          Speeds!!
            if (gamepad1.a) {
                setSpeed(0.3);
            } else if (gamepad1.b) {
                setSpeed(0.5);
            } else if (gamepad1.x) {
                setSpeed(0.7);
            } else if (gamepad1.y) {
                setSpeed(1.0);
            }
            /// FROM NOW ON, THE REST IS GAMEPAD 2
            if (gamepad2.dpad_up && !kickerActive) {
                kicker.setPosition(0.3);   // Move down
                kickerTimer.reset();       // Start timer
                kickerActive = true;
            }


            if (kickerActive && kickerTimer.milliseconds() > 100) {
                kicker.setPosition(0.6);   // Move up
                kickerActive = false;
            }
            if (gamepad2.right_bumper) {
                right_servo_feeder.setPower(-1); /// Conveyor start
                left_servo_feeder.setPower(1);
            }if (gamepad2.left_bumper) {
                right_servo_feeder.setPower(0); /// Conveyor start
                left_servo_feeder.setPower(0);
            }


            float lt = gamepad1.left_trigger;


            /// Left joystick on gamepad2 controls feeders
            double speed1 = gamepad2.left_stick_y; /// REMEMBER IT'S Y AXIS
            intake.setPower(speed1);


            double speed2 = -gamepad2.right_stick_y; // Negative because joystick up is usually -1
            if (Math.abs(speed2) > 0.1){
                launcher.setVelocity(MAXIMUM_MAXIMUM_VELOCITY*speed2);
            }
            else if (gamepad2.b){
                launcher.setVelocity(MAXIMUM_VELOCITY*.3);
            }
            else if (gamepad2.y){
                launcher.setVelocity(MAXIMUM_VELOCITY*.5);
            }
            else if (gamepad2.x){
                launcher.setVelocity(MAXIMUM_VELOCITY);
            } else {
                launcher.setVelocity(0);
            }


            launcher.getCurrentPosition();
        }
    }
}
