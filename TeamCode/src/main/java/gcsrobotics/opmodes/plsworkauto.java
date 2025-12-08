package gcsrobotics.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import gcsrobotics.framework.AutoBase;

@Autonomous(name="plsworkauto")
public class plsworkauto extends AutoBase {

    //Not necessary, but this is the code to run during init
    @Override
    protected void initSequence(){
        setPowers(0.5);
    }

    private void setPowers(double v) {
    }

    @Override
    protected void runSequence() {

    }
}

