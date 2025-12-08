package gcsrobotics.opmodes;

import static com.qualcomm.robotcore.hardware.DcMotorSimple.Direction.REVERSE;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.util.ElapsedTime;

import gcsrobotics.framework.TeleOpBase;

@TeleOp(name="GoBuildaStartBotTeleOp")
public abstract class GoBuildaStartBotTeleOp extends TeleOpBase {

    private static final double STOP_SPEED = 0.0;
    private static final double LAUNCHER_TARGET_VELOCITY = 2000.0; // adjust as needed
    private static final double LAUNCHER_MIN_VELOCITY = 1800.0;

    private static final double FEED_TIME_SECONDS = 0.20;
    private static final double FULL_SPEED = 1.0;

    private DcMotorEx launcher;
    private CRServo leftFeeder;
    private CRServo rightFeeder;

    private final ElapsedTime feederTimer = new ElapsedTime();

    private LaunchState launchState = LaunchState.IDLE;

    // Simple state machine enum
    private enum LaunchState {
        IDLE,
        SPIN_UP,
        LAUNCH,
        LAUNCHING
    }

    @Override
    protected void inInit() {
        launcher = hardwareMap.get(DcMotorEx.class, "launcher_motor");
        leftFeeder = hardwareMap.get(CRServo.class, "leftFeeder");
        rightFeeder = hardwareMap.get(CRServo.class, "rightFeeder");

        leftFeeder.setDirection(REVERSE);
        rightFeeder.setDirection(REVERSE);

        leftFeeder.setPower(STOP_SPEED);
        rightFeeder.setPower(STOP_SPEED);
    }

    @Override
    protected void runLoop() {
        implementDriveLogic();
        toggleFieldCentric(true);
        telemetry.addLine("INIT RUNNING");
        telemetry.update();

        launcher = hardwareMap.get(DcMotorEx.class, "launcher_motor");
        leftFeeder = hardwareMap.get(CRServo.class, "leftFeeder");
        rightFeeder = hardwareMap.get(CRServo.class, "rightFeeder");
        // Drive speed control
        if (gamepad2.a) {
            setSpeed(0.3);
        } else if (gamepad2.b) {
            setSpeed(0.5);
        } else if (gamepad2.x) {
            setSpeed(0.7);
        } else if (gamepad2.y) {
            setSpeed(1.0);
        }

        // Start spinning flywheel
        if (gamepad1.y) {
            launcher.setVelocity(LAUNCHER_TARGET_VELOCITY);
            if (launchState == LaunchState.IDLE) {
                launchState = LaunchState.SPIN_UP;
            }
        }

        // Stop flywheel
        if (gamepad1.b) {
            launcher.setVelocity(STOP_SPEED);
            launchState = LaunchState.IDLE;
        }

        // Request a shot when right bumper is pressed
        boolean shotRequested = gamepad1.right_bumper;

        switch (launchState) {
            case IDLE:
                if (shotRequested) {
                    launchState = LaunchState.SPIN_UP;
                }
                break;

            case SPIN_UP:
                launcher.setVelocity(LAUNCHER_TARGET_VELOCITY);
                if (launcher.getVelocity() >= LAUNCHER_MIN_VELOCITY) {
                    launchState = LaunchState.LAUNCH;
                }
                break;

            case LAUNCH:
                leftFeeder.setPower(FULL_SPEED);
                rightFeeder.setPower(FULL_SPEED);
                feederTimer.reset();
                launchState = LaunchState.LAUNCHING;
                break;

            case LAUNCHING:
                if (feederTimer.seconds() >= FEED_TIME_SECONDS) {
                    leftFeeder.setPower(STOP_SPEED);
                    rightFeeder.setPower(STOP_SPEED);
                    launchState = LaunchState.IDLE;
                }
                break;
        }
    }
}
