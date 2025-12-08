package gcsrobotics.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;// You won't need this

import gcsrobotics.framework.AutoBase;

@Autonomous(name="Auto1")
public class Auto1 extends AutoBase {

    //Not necessary, but this is the code to run during init
    @Override
    protected void initSequence(){
    }

    @Override
    protected void runSequence() {
        path(24,0);
    }
}


