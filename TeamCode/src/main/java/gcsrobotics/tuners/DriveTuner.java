package gcsrobotics.tuners;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import gcsrobotics.framework.AutoBase;

/**This class allows you to test distances in the x and y directions,
 * so you can tune the drive constants, Constants.KpDrive and Constants.KdDrive live with
 * FTC Dashboard
 * @see ServoTuner
 * @see TurnTuner
 * @see OdoTuner
 */
@TeleOp(name="Drive Tuner")
@Config
//@Disabled
public class DriveTuner extends AutoBase {

    public static int targetX = 0, targetY = 0;

    protected void runSequence(){
        while(opModeIsActive()){

            path(targetX, targetY);

        }
    }

}
