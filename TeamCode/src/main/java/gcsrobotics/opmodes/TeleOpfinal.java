package gcsrobotics.opmodes;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Light;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.PwmControl;

import java.util.List;

import gcsrobotics.framework.TeleOpBase;
import gcsrobotics.framework.Constants;

@TeleOp(name="TeleOpfinal")
public class TeleOpfinal extends TeleOpBase {
    public Servo kicker;
    public Limelight3A limelight;
    public ElapsedTime time;
    public CRServo right_servo_feeder;
    public CRServo left_servo_feeder;
    public Servo light;
    public DcMotorEx launcher;
    public DcMotor intake;
    // Runs once when INIT is pressed
    ElapsedTime kickerTimer = new ElapsedTime();
    public double lastSpeed = 0;
    public double currentSpeed = 0;
    public double kP = 0;
    public double kI = 0;
    public double kD = 0;
    public double kF = 0;
    public double minOutput = -1;
    public double maxOutput = 1;

    PID_Controller PID = new PID_Controller(kP, kI, kD, kF, minOutput, maxOutput);

    //MAX_MAX VELOCITY is used for the max velocity that the y
    public double MAXIMUM_MAXIMUM_VELOCITY = 2000;
    //MAX VELOCITY IS USED FOR DPAD
    public double MAXIMUM_VELOCITY = 2000;
    boolean kickerActive = false;
    @Override
    protected void inInit() {
        kicker = hardwareMap.get(Servo.class, "kicker");
        left_servo_feeder = hardwareMap.get(CRServo.class, "left_servo_feeder");
        right_servo_feeder = hardwareMap.get(CRServo.class, "right_servo_feeder");
        launcher = hardwareMap.get(DcMotorEx.class, "launcher");
        launcher.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        intake = hardwareMap.get(DcMotor.class, "intake");
        limelight = hardwareMap.get(Limelight3A.class, "Ethernet Device");
        light = hardwareMap.get(Servo.class, "light");
        limelight.setPollRateHz(100);
        limelight.start();
        PwmControl lightPWM = (PwmControl) light;
        PwmControl.PwmRange range = new PwmControl.PwmRange(500,1500);
        lightPWM.setPwmRange(range);
        lightPWM.setPwmEnable();

//        fl.setDirection(DcMotor.Direction.REVERSE);
//        time = new ElapsedTime();

        // Any custom initialization can go here
        // For example: reset sensors, prepare servos, etc.
    }
    @Override
    protected void run() {
        telemetry.addLine("run START");
        telemetry.update();
        kicker.setPosition(0);
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
                right_servo_feeder.setPower(0);
                left_servo_feeder.setPower(0);
                kickerTimer.reset();
                while (kickerTimer.milliseconds() < 400) {
                    kickerActive = true;
                }
                kicker.setPosition(0.5);   // Move Down
                kickerTimer.reset();       // Start timer
            }


            if (gamepad2.dpad_down) {
                kicker.setPosition(0);
                kickerActive = false;
            }

            if (kickerActive && kickerTimer.milliseconds() > 1000) {
                right_servo_feeder.setPower(-1);
                left_servo_feeder.setPower(1);
                kickerTimer.reset();
                while (kickerTimer.milliseconds() < 3000) {
                    right_servo_feeder.setPower(-0.1);
                    left_servo_feeder.setPower(0.1);
                    intake.setPower(-0.5);
                    implementDriveLogic();
                    if (gamepad2.dpad_down) {
                        kicker.setPosition(0);
                        kickerActive = false;
                        break;
                    }
                }
                right_servo_feeder.setPower(-1);
                left_servo_feeder.setPower(1);
                kickerActive = false;
            }
            if (gamepad2.right_bumper) {
                right_servo_feeder.setPower(-1); /// Conveyor start
                left_servo_feeder.setPower(1);
            }if (gamepad2.left_bumper) {
                right_servo_feeder.setPower(0); /// Conveyor stop
                left_servo_feeder.setPower(0);
            }


            float lt = gamepad1.left_trigger;


            /// Left joystick on gamepad2 controls feeders
            //double speed1 = gamepad2.left_stick_y; /// REMEMBER IT'S Y AXIS
           // intake.setPower(speed1);
            if (gamepad2.left_stick_button) {
                intake.setPower(-1);
            }
            if (gamepad2.right_stick_button) {
                intake.setPower(0);
            }

            double speed2 = -gamepad2.right_stick_y; // Negative because joystick up is usually -1
            if (Math.abs(speed2) > 0.1){
                launcher.setVelocity(MAXIMUM_MAXIMUM_VELOCITY*speed2);
            }
            else if (gamepad2.b){
                launcher.setVelocity(MAXIMUM_VELOCITY*.5);
            }
            else if (gamepad2.y){
                launcher.setVelocity(MAXIMUM_VELOCITY*.6);
            }
            else if (gamepad2.x){
                launcher.setVelocity(MAXIMUM_VELOCITY*.81);
            }
            else if (gamepad2.a){
                launcher.setVelocity(0);
            }

            launcher.getCurrentPosition();
            double[] limeReadings = getlightlimeresults();
            if (limeReadings != null) {
                double lastTx = limeReadings[0];
                double lastTy = limeReadings[1];
                double distanceFromGoal = limeReadings[2];

                telemetry.addData("tx", lastTx);
                telemetry.addData("ty", lastTy);
                telemetry.addData("Distance from AprilTag", distanceFromGoal);
//                light.setPosition(0);
                if (Math.abs(lastTx) > Constants.limelightTargetThreshold){
                    if (lastTx < -1) {
                        light.setPosition(0.277);
                        telemetry.addLine("MOVE RIGHT");
                    } else if (lastTx > 1) {
                        light.setPosition(0.611);
                        telemetry.addLine("MOVE LEFT");
                    }
                } else {
                    light.setPosition(0.5);
                }
            } else {
                light.setPosition(0);
            }
            telemetry.update();
        }
    }


    public double[] getlightlimeresults() {
        LLResult result = limelight.getLatestResult();
        if (result != null && result.isValid()) {
            List<LLResultTypes.FiducialResult> resultS = result.getFiducialResults();
            for (LLResultTypes.FiducialResult individualAprilTags : resultS){
                if (individualAprilTags.getFiducialId() == 24 || individualAprilTags.getFiducialId() == 20){
                    // red or blue area
                    //view paper for what each means
                    double tx = result.getTx();
                    double ty = result.getTy();
                    double h2 = (Constants.targetAprilTagHeight + Constants.targetAprilTagSpaceToCenter) - Constants.limelightMountHeight;
                    double d = h2/Math.tan(ty);
                    double horiz = d*Math.tan(tx);

                    double normalizedX = Math.atan2(horiz + Constants.limelightMountOffset, d);
                    return new double[]{normalizedX, ty, d};

                }

            }

        }
        return null;
    }
}

