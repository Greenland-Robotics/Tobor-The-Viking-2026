package gcsrobotics.framework.hardware;
import static gcsrobotics.framework.Constants.GLOBAL_DEFAULT_MOTOR_POWER;
import static gcsrobotics.framework.Constants.ENCODER_TOLERANCE;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import gcsrobotics.framework.OpModeBase;

@SuppressWarnings("unused")
public class DcMotorEnhanced {
    private final DcMotorEx motor;
    private double DEFAULT_POWER;

    public DcMotorEnhanced(String name, HardwareMap hardwareMap) {
        this(name, GLOBAL_DEFAULT_MOTOR_POWER, hardwareMap);
    }

    public DcMotorEnhanced(String name, double DEFAULT_POWER, HardwareMap hardwareMap){
        this.motor = hardwareMap.get(DcMotorEx.class, name);
        this.DEFAULT_POWER = DEFAULT_POWER;
    }

    public void setPosAndWait(int targetPosition, OpModeBase opmode){
        setPosAndWait(targetPosition,DEFAULT_POWER,opmode);
    }
    public void setPosAndWait(int targetPosition, double power,OpModeBase opmode){
        setPosition(targetPosition,power);
        while(!isAtTarget()){
            opmode.sleep(10);
        }
    }

    /// Sets the given motor to go to a certain position, at full power.
    /// If you want to vary the power, add another parameter with the power you want
    public void setPosition(int targetPosition){
        this.setPosition(targetPosition,DEFAULT_POWER);
    }
    /// Sets the given motor to go to a certain position at a given power
    public void setPosition(int targetPosition,double power){
        motor.setTargetPosition(targetPosition);
        motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motor.setPower(power);
    }

    public void setDefaultPower(double DEFAULT_POWER){
        this.DEFAULT_POWER = DEFAULT_POWER;
    }

    public double getDefaultPower(){return DEFAULT_POWER;}

    public void reset(){
        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    public boolean isAtTarget() {
        return Math.abs(getCurrentPosition() - motor.getTargetPosition()) <= ENCODER_TOLERANCE;
    }

    // === Forward motor control with minimal effort ===
    public void setPower(double power) {
        motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motor.setPower(power);
    }

    public double getPower() {
        return motor.getPower();
    }

    public void setTargetPosition(int position) {
        motor.setTargetPosition(position);
    }

    public int getCurrentPosition() {
        return motor.getCurrentPosition();
    }

    public boolean isBusy() {
        return motor.isBusy();
    }

    public void setMode(DcMotor.RunMode mode) {
        motor.setMode(mode);
    }

    public DcMotor.RunMode getMode() {
        return motor.getMode();
    }

    public void setZeroPowerBehavior(DcMotor.ZeroPowerBehavior behavior) {
        motor.setZeroPowerBehavior(behavior);
    }

    public DcMotor.ZeroPowerBehavior getZeroPowerBehavior() {
        return motor.getZeroPowerBehavior();
    }

    public void setDirection(DcMotorSimple.Direction direction){
        motor.setDirection(direction);
    }

    public DcMotorSimple.Direction getDirection(){
        return motor.getDirection();
    }

    /**
     * @return velocity of the motor in ticks per second
     */
    public double getVelocity() {
        return motor.getVelocity();
    }

    /**
     * @param unit the AngleUnit to return the velocity in
     * @return velocity of the motor in the given unit
     */
    public double getVelocity(AngleUnit unit){
        return motor.getVelocity(unit);
    }

    /**
     * Sets the velocity of the motor to the given angularRate
     * @param angularRate in radians per second
     */
    public void setVelocity(double angularRate){
        motor.setVelocity(angularRate);
    }

    /**
     * Sets the velocity of the motor to the given angularRate in the given AngleUnit
     * @param angularRate AngleUnit per second
     * @param unit the AngleUnit to use for measuring rotations
     */
    public void setVelocity(double angularRate, AngleUnit unit){
        motor.setVelocity(angularRate,unit);
    }

    /// --- Here you can still access motor directly ---
    public DcMotor getBaseMotor() {
        return motor;
    }
}
