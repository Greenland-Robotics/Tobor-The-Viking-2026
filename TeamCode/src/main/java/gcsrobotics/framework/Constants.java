package gcsrobotics.framework;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import gcsrobotics.framework.hardware.GoBildaPinpointDriver;

/// <strong>A class that holds all of the robot constants</strong>
@Config("Robot Constants") //This Config tag allows for these values to be tuned in real time using the dashboard
public class Constants {
    public final static double clawClose;
    public final static double clawOpen;
    public final static double KpDrive;
    public final static double KdDrive;
    public final static double KpTurn;

    public final static double PATH_TOLERANCE_IN;
    public final static double CHAIN_TOLERANCE_IN;
    public final static double TURN_TOLERANCE_DEG;
    public final static double PATH_SETTLE_TIME_MS;
    public final static double TURN_SETTLE_TIME_MS;
    public final static double PATH_TIMEOUT_MS;
    public final static int armUp;
    public final static int armMiddle;
    public final static int armDown;
    public final static double wristUp;
    public final static double wristDown;
    public final static double GLOBAL_DEFAULT_MOTOR_SPEED;
    public final static int ENCODER_TOLERANCE;
    public final static double autoMaxPower;

    public final static DcMotorSimple.Direction flDirection;
    public final static DcMotorSimple.Direction frDirection;
    public final static  DcMotorSimple.Direction blDirection;
    public final static DcMotorSimple.Direction brDirection;

    public final static GoBildaPinpointDriver.EncoderDirection xPodDirection;
    public final static GoBildaPinpointDriver.EncoderDirection yPodDirection;

    static{

        /* Examples of constants
        --- Use this to set things like servo and motor positions ---
        This allows you to change numbers in only one spot if you need
        to make an adjustment
         */

        clawClose = 0;
        clawOpen = 1;

        KpDrive = 0.01;
        KdDrive = 0.001;
        KpTurn = 0.1;

        PATH_TOLERANCE_IN = 1;
        CHAIN_TOLERANCE_IN = 3;
        TURN_TOLERANCE_DEG = 1.5;
        PATH_SETTLE_TIME_MS = 100;
        TURN_SETTLE_TIME_MS = 150;
        PATH_TIMEOUT_MS = 3000;

        //More examples here
        armUp = 500;
        armDown = 0;
        armMiddle = 250;

        wristUp = 0.8;
        wristDown = 0;

        //This must be between 0 and 1
        autoMaxPower = 0.6;


        ENCODER_TOLERANCE = 10;
        GLOBAL_DEFAULT_MOTOR_SPEED = 1;

        flDirection = DcMotorSimple.Direction.REVERSE;
        frDirection = DcMotorSimple.Direction.FORWARD;
        blDirection = DcMotorSimple.Direction.REVERSE;
        brDirection = DcMotorSimple.Direction.FORWARD;

        //Reverse this if going forward makes the x coordinate go down
        xPodDirection = GoBildaPinpointDriver.EncoderDirection.FORWARD;
        //Reverse this if going left makes the y coordinate go down
        yPodDirection = GoBildaPinpointDriver.EncoderDirection.FORWARD;


    }


}
