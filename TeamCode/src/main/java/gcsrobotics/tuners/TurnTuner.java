package gcsrobotics.tuners;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import gcsrobotics.framework.AutoBase;

/**This class allows you to test turning,
 * so you can tune the turning constants: Constants.KpTurn live with FTC Dashboard
 * @see ServoTuner
 * @see DriveTuner
 * @see OdoTuner
 */
@TeleOp(name="Turn Tuner")
@Config
//@Disabled
public class TurnTuner extends AutoBase {

    public static int targetAngle = 0;

    protected void runSequence() {
        while (opModeIsActive()) {
            turn(targetAngle);
        }
    }
}