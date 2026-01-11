package gcsrobotics.framework;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

@Autonomous(name="Leave Auto Simple", group="Auto")
class LeaveAutoSimple extends LinearOpMode {

    private DcMotor leftFront;
    private DcMotor rightFront;
    private DcMotor leftBack;
    private DcMotor rightBack;

    @Override
    public void runOpMode() {

        // Map motors (use your config names)
        leftFront  = hardwareMap.get(DcMotor.class, "leftFront");
        rightFront = hardwareMap.get(DcMotor.class, "rightFront");
        rightBack  = hardwareMap.get(DcMotor.class, "rightBack");
        leftBack   = hardwareMap.get(DcMotor.class, "leftBack");
        // Reverse one side so robot goes forward
        rightFront.setDirection(DcMotor.Direction.REVERSE);

        waitForStart();

        // Drive forward
        leftFront.setPower(0.5);
        rightFront.setPower(-0.5);
        leftBack.setPower(0.5);
        rightBack.setPower(-0.5);
        sleep(3000); // 3 seconds

        // Stop
        leftFront.setPower(0);
        rightFront.setPower(0);
    }
}
