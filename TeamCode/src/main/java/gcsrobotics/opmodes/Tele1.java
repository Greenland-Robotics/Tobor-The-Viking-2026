package gcsrobotics.opmodes;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import gcsrobotics.framework.TeleOpBase;

@TeleOp(name="TeleOp")
@Config
public abstract class Tele1 extends TeleOpBase {

    public static double velocity = 0;

    @Override
    public void inInit() {
    }
    @Override
    public void runLoop(){

//        implementDriveLogic();
//        toggleFieldCentric(gamepad1.left_bumper);


        //Speed control
//        if(gamepad1.a){
//            setSpeed(0.3);
//        } else if(gamepad1.b){
//            setSpeed(0.5);
//        } else if(gamepad1.x){
//            setSpeed(0.7);
//        } else if(gamepad1.y){
//            setSpeed(1);
//        }

        fl.setVelocity(velocity);

    }
}
