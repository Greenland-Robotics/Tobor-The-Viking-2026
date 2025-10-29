package gcsrobotics.framework;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;

import gcsrobotics.framework.hardware.Claw;
import gcsrobotics.framework.hardware.DcMotorEnhanced;
import gcsrobotics.framework.hardware.GoBildaPinpointDriver;

/// The base for all OpModes. Has global variables and methods, and handles hardware initialization.
/// @author Josh Kelley
public abstract class OpModeBase extends LinearOpMode {
    protected DcMotorEnhanced fl,fr,bl,br,arm;
    protected Servo servo;
    protected Claw claw;
    protected GoBildaPinpointDriver odo;


    protected abstract void runInit();
    protected abstract void run();

    private void initHardware(){
        //TODO: Update this config for your robot
        try {
            fl = new DcMotorEnhanced("fl", hardwareMap);
            fr = new DcMotorEnhanced("fr", hardwareMap);
            bl = new DcMotorEnhanced("bl", hardwareMap);
            br = new DcMotorEnhanced("br", hardwareMap);
            odo = hardwareMap.get(GoBildaPinpointDriver.class, "odo");

            // Example
//          arm = new DcMotorEnhanced(hardwareMap.get(DcMotor.class, "arm"));
//          claw = new Claw(hardwareMap.get(Servo.class, "claw"));

        }catch (NullPointerException e){
            telemetry.addData("Error",e.getMessage());
            telemetry.addLine("Hardware initialization failed");
            telemetry.update();
            sleep(5000);
            stop();
        }

        odo.setEncoderResolution(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD);
        odo.setEncoderDirections(Constants.xPodDirection, Constants.yPodDirection);
        odo.resetPosAndIMU();

        //TODO: Set motor directions. Some motors will be reversed, so you must change that here
        //Note: Typically the right side is reversed, but change it as you need
        fl.setDirection(Constants.flDirection);
        fr.setDirection(Constants.frDirection);
        bl.setDirection(Constants.blDirection);
        br.setDirection(Constants.brDirection);
    }


    @Override
    public void runOpMode(){
        // Initialize telemetry for dashboard and Driver Hub
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());

        //Initialize hardware
        initHardware();

        //Run the init sequence
        runInit();

        //Wait for the start button to be pressed
        waitForStart();

        //Run the main logic when the start button is pressed
        run();

    }

    /// Resets the odometry position to 0
    protected void resetPosition(){
        odo.resetPosAndIMU();
    }


    /// Getter method for the x coordinate
    /// @return the current x coordinate
    protected double getX(){
        return odo.getX();
    }

    /// Getter method for the y coordinate
    /// @return the current y coordinate
    protected double getY(){
        return odo.getY();
    }

    protected double getAngle(){
        return odo.getAngle();
    }


}
