package gcsrobotics.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;

@TeleOp(name="CRServoTest")
@Disabled
public class CRServoTest extends LinearOpMode {

    private CRServo feeder;

    @Override
    public void runOpMode() {
        feeder = hardwareMap.get(CRServo.class, "right_servo_feeder");
        feeder.setPower(0);

        telemetry.addLine("Ready. Press Y to spin forward, A backward.");
        telemetry.update();

        waitForStart();

        while(opModeIsActive()) {
            if(gamepad1.y) {
                feeder.setPower(1);
            } else if(gamepad1.a) {
                feeder.setPower(-1);
            } else {
                feeder.setPower(0);
            }

            telemetry.addData("Power", feeder.getPower());
            telemetry.update();
        }
    }
}

