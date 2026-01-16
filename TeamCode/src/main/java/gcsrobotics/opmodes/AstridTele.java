package gcsrobotics.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;// You don't need to include this
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import gcsrobotics.framework.TeleOpBase;

@TeleOp(name="AstridTele")
public class AstridTele extends TeleOpBase {
    @Override
    public void inInit(){
    fl.setDirection(DcMotorSimple.Direction.REVERSE);
    }
    @Override
    public void run() {runLoop();}
    @Override
    public void runLoop(){
    while (opModeIsActive())
        /*
            Implements all drive logic necessary
            By default, fieldCentric is true.
            If you want to turn it off permanently, add this line:
            fieldCentric = false; //right before the call to implementDriveLogic()
        */
        implementDriveLogic();
        // This toggles field-centric drive when the left bumper is pressed

        //toggleFieldCentric(gamepad1.left_bumper);

        //Example usage of the setMotorPosition() Method

        //Speed control
        if(gamepad1.a){
            setSpeed(0.3);
        } else if(gamepad1.b){
            setSpeed(0.5);
        } else if(gamepad1.x){
            setSpeed(0.7);
        } else if(gamepad1.y){
            setSpeed(1);
        }

    }
}