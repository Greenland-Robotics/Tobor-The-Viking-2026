package gcsrobotics.opmodes;

public class PID_Controller {
    // PIDF gains
    private double kP;
    private double kI;
    private double kD;
    private double kF;

    // State variables
    private double setpoint;
    private double integralSum;
    private double lastError;

    // Output constraints
    private double minOutput;
    private double maxOutput;

    // Constructor
    public PID_Controller(double kP, double kI, double kD, double kF, double minOutput, double maxOutput) {
        this.kP = kP;
        this.kI = kI;
        this.kD = kD;
        this.kF = kF;
        this.minOutput = minOutput;
        this.maxOutput = maxOutput;
        this.integralSum = 0;
        this.lastError = 0;
    }

    // Method to set the setpoint
    public void setSetpoint(double setpoint) {
        this.setpoint = setpoint;
    }

    // Method to calculate output
    public double[] calculate(double currentValue) {
        double error = setpoint - currentValue;

        // Proportional term
        double pTerm = kP * error;

        // Integral term
        integralSum += error;
        double iTerm = kI * integralSum;

        // Derivative term
        double dTerm = kD * (error - lastError);

        // Feedforward term
        double fTerm = kF * setpoint;

        // Update last error
        lastError = error;

        // Calculate total output
        double output = pTerm + iTerm + dTerm;
        if (output < 0) {
            output = output - fTerm;
        } else {
            output = output + fTerm;
        }

        // Constrain output
        output = Math.max(minOutput, Math.min(maxOutput, output));

        double[] result = new double[8];
        result[0] = output;
        result[1] = error;
        result[2] = pTerm;
        result[3] = iTerm;
        result[4] = dTerm;
        result[5] = fTerm;
        result[6] = minOutput;
        result[7] = maxOutput;
        return result;
    }

    // Method to reset the controller
    public void reset() {
        integralSum = 0;
        lastError = 0;
    }

    // Optionally, add getters and setters for gains
    public void setGains(double kP, double kI, double kD, double kF) {
        this.kP = kP;
        this.kI = kI;
        this.kD = kD;
        this.kF = kF;
    }
}