# Greenland Robotics Framework: Documentation

## Notes
- When writing your opmodes, always include the line `///@author <your-name>` just above the `@TeleOp` or `@Autonomous` annotation. This provides clean documentation about who wrote the opmode, which is useful for collaborative purposes and knowing who to blame when something goes wrong. Jokes aside, it is very important when collaborating on code to sign your work.
  - Example:
    ```java
    ///@author Josh Kelley
    @TeleOp(name = "My TeleOp")
    public class MyTeleOp extends TeleOpBase {...}
    ```
- If you are using the PlayStation controllers, use the button map below
  | PlayStation    | Gamepad(in code) |
  | -------------- | ---------------- |
  | X              | A                |
  | O              | B                |
  | △              | Y                |
  | □              | X                |
  
  For example: `gamepad1.a` will be automatically mapped to the X button on the PlayStation controller
- In TeleOp, the following buttons are in use by the `implementDriveLogic()` method(gamepad1 only)
  - `left_stick_x`
  - `left_stick_y`
  - `right_stick_x`
  - `right_bumper`
  - `left_trigger`
  - `right_trigger`<br>
  by using these in your teleop code, that one action will control both the driving and whatever you mapped it to. If you want to change these, navigate to gcsrobotics/framework/TeleOpBase, and find the `implementDriveLogic()`. There you can change all of the buttons.
## Where is my code?
All code is under `TeamCode/src/main/java/gcsrobotics/`. Here you will find 4 packages
- `examples`: Example opmodes and programs that show how to use the framework for Autos and TeleOps
- `framework`: The base code for methods and not opmode-specific code. You will only go here to change the Constants.java file and your hardware configuration in OpModeBase.java
  - `hardware`: Hardware specific code for subsystems, wrappers and drivers. You will almost never need to interact with this package
- `opmodes`: This is where your opmodes will reside. It is the only package where you can change anything and make it your own
- `tuners`: Opmodes for tuning hardware and debugging. You will generally not put anything in here unless you write an opmode to tune a specific subsystem

# Installation/Setup
Setting up a new project is very easy, you only need to follow a few steps and you are ready to go!
1. Fork the repo - go to the main GitHub page for this repo and look for the button that says Fork. Click that and give it a name
2. Once you have done that, it should take you to a page that looks like the one for this repo, but with the name you gave it.
3. Clone the repo(put the code onto your computer) - Click the green button that says Code on the main page of YOUR repository. Don't click that button on this page. Copy the URL you see there
4. Go to Android Studio and click the top left three lines -> File -> New -> Project from Version Control. Paste in the URL you got in step 4 and continue
5. It may take a few minutes to set up, but you will know you are ready when you see three folders in the left hand side of Android Studio; `FtcRobotController`, `TeamCode`, and `Gradle Scripts`
6. You're done! Remember, your code is under `TeamCode`

# Method Documentation
This documentation covers **all classes and methods** in `TeamCode/src/main/java/gcsrobotics/framework` for the GreenlandRoboticsFramework.  
It includes explanations for:  
- **What each method does**
- **How and why to use them**
- **Common usage scenarios**

---

## Table of Contents

- [`OpModeBase`](#opmodebase)
- [`AutoBase`](#autobase)
- [`TeleOpBase`](#teleopbase)
- [`Constants`](#constants)
- [`DcMotorEnhanced`](#dcmotorenhanced)
- [`GoBildaPinpointDriver`](#gobildapinpointdriver)
---

## OpModeBase

**Purpose:**  
Abstract base for all OpModes (autonomous or teleop).  
Handles hardware initialization and provides access to motors, servos, and odometry.

### Key Properties
- Necessary
  - `fl`, `fr`, `bl`, `br` — Drivetrain motors (`DcMotorEnhanced`)
  - `odo` — Odometry computer (`GoBildaPinpointDriver`)
- Examples(Not needed)
  - `arm` — Arm motor (`DcMotorEnhanced`)
  - `claw` — Claw servo

### Main Methods

### `public double getX()`, `public double getY()`, and `public double getAngle()`
These do exactly what they look like, return the x, y, or angle of the robot, respectively, according to the GoBilda Pinpoint.
Having these in OpModeBase means you can call these methods in any Auto or TeleOp.

#### `private void initHardware()`
Initializes all hardware.  You will need to modify this method for your specific robot. Look at the current one for examples
- Configures motor/servo objects, odometry, directions.
<br>


The following are **internal** methods. You won't interact with them or have to worry about them.
#### `protected abstract void runInit()`
Override for code to run in `init` phase.

#### `protected abstract void run()`
Override for code to run once `start` is pressed.

#### `public void runOpMode()`
Main entrypoint for OpMode.  
- Sets up telemetry, hardware, runs `runInit()`, waits for start, then runs `run()`.
---
## AutoBase


**Purpose:**  
`AutoBase` is an abstract class for autonomous robot operation modes.  
It extends `OpModeBase`, providing accurate movement utilities and control structures for writing robust autonomous routines.

### Common Usage

- Extend `AutoBase` in your own autonomous OpMode.
- Override `initSequence()` (optional) for initialization logic.
- Override `runSequence()` for your autonomous sequence.
- Use movement methods like `path()`, `chain()`, `simpleDrive()`.

### Key Methods
#### Note
What does it mean to "Override" a function? Overriding just means implementing your own logic in a method, even if it is already defined. Here is what it looks like:
```java
@Override //Not strictly needed but recommended for readability purposes
protected void predefinedMethod(){
  // Example logic you might perform
  doSomeTask()
  doAnotherTask()
}
```
The reason these are overriden is because they are already used by the parent class of the opmode, eg. `AutoBase`, but **YOU** have to tell it what that method actually does.

#### `protected void initSequence()`
Override to add code you want to run in the `init` phase.
Example:
```java
@Override
protected void initSequence(){
  //Your init logic
}
```

#### `protected abstract void runSequence()`
Override to define the autonomous actions (your main logic).

#### `protected void simpleDrive(Axis direction, double power, int milliseconds)`
Moves the robot simply in the specified `Axis` at a set power for a duration. The options for `Axis` are `Axis.X`(forward & back) and `Axis.Y`(left & right)
- **Use:** For short, direct movements (e.g., nudging into position).
- **Example:** `simpleDrive(Axis.X, 0.5, 1000);`

#### `protected void setPowers(double power)`
Sets all drive motors to the same power.
- **Use:** To move all wheels together, usually straight.
- **Example:** `setPowers(0.5)`

#### `protected void wait(int milliseconds)`
Pauses execution for a number of milliseconds, updating odometry and telemetry.
- **Use:** Preferable to `sleep()` in FTC, as it keeps robot feedback alive during the wait.

#### `protected void path(int targetX, int targetY, Axis forgiveAxis = Axis.NONE)`
Accurate movement to a coordinate (`targetX`, `targetY`) with optional axis forgiveness.
- **Use:** For precise autonomous positioning.
- **`forgiveAxis`:** `Axis.X` or `Axis.Y`. This is used if you don't care about a certain axis being totally accurate before moving on. This **doesn't** mean it doesn't move along that axis, but it is not a factor in when the path ends and moves onto the next piece of logic.
- **Example:** `path(100,200)` or `path(100,200,Axis.X)`

#### `protected void chain(int targetX, int targetY, Axis forgiveAxis = Axis.NONE)`
Fast movement to a coordinate (less accurate than `path`).
- **Use:** When speed is more important than precision.
- **Example:** `chain(100,200)` or `chain(100,200,Axis.X)`
- 
#### `protected void waitUntil(@NonNull Supplier<Boolean> condition)`
Waits until a given condition is true.
- **Use:** For waiting on asynchronous events or sensor thresholds.
- **Example:** `waitUntil(() -> colorSensor.red() > 200)`. This is fake logic, but the idea is you put your condition right after the `() -> `

#### **Private Utility Methods**  
**You don't need to worry about these, they are internal**
- `pidDrivePower(double error, boolean isX)`  
  Calculates PID-like drive power for pathing routines.
- `setMotorPowers(double xPower, double yPower, double headingCorrection)`  
  Sets individual wheel powers for advanced movement.
- `sendTelemetry(String label, ...)`
  Sends detailed telemetry for debugging pathing.
- `stopMotors()`
  Stops all drive motors.
- `notStuck(double targetX, double targetY)`
  Detects if the robot is stuck (not making progress).
---

## TeleOpBase

**Purpose:**  
Base for teleop OpModes.  
Implements drive logic and framework for teleop control.

### Key Methods

#### `protected void runInit()`
- Sets drivetrain motors to `RUN_WITHOUT_ENCODER` (faster for teleop).
- Calls `inInit()` for your custom init code.

#### `protected void run()`
- Repeatedly calls `runLoop()` while OpMode is active.

#### `protected abstract void runLoop()`
Override to add the main teleop loop logic.

#### `protected abstract void inInit()`
Override to add code for the `init` phase of teleop.

#### `protected void setSpeed(double speed)`
- Set the drive speed multiplier.
- **Example:** `setSpeed(0.5) //Sets speed to half power`

#### `protected void implementDriveLogic()`
- Implements full mecanum drive logic using gamepads.
- Handles horizontal locking, slow mode, and trigger overrides.
- **Use:** Call this in your `runLoop()` to handle all drive movement.
---

## Constants

**Purpose:**  
Central location for tunable constants (motor positions, PID values, setpoints, etc).

### Key Fields
  **Example fields that many teams may use, feel free to delete them if they are unnecessary**
- `clawClose`, `clawOpen`: Servo positions for the claw.
- `armUp`, `armMiddle`, `armDown`: Encoder positions for arm levels.
- `wristUp`, `wristDown`: Servo positions for a wrist.
<br><br>
  **These fields are required, and are needed for basic functions. Do NOT delete these.**
- `ENCODER_TOLERANCE`: How close an encoder must be to target to count as "there".
- `KpDrive`, `KdDrive`, `KpTurn`: PID coefficients for drive and turn control.
- `autoMaxPower`: Max drive power for autonomous.
- `flDirection`,`frDirection`,`blDirection`,`brDirection`: Motor directions
- `xPodDirection`, `yPodDirection`: Direction of the GoBilda Pinpoint Odometry pods
- **All constants are static and can be tuned live with FTC Dashboard.**

---

## DcMotorEnhanced

**Purpose:**  
A wrapper around FTC's `DcMotor` providing easier position control, power management, and utility operations.

### Constructors

- `public DcMotorEnhanced(DcMotor motor)`

### Key Methods

#### Position Control

- `setPosAndWait(int targetPosition, OpModeBase opmode)`
  - Moves to a position at default speed, waits until there. Moves at the preset default speed
  - **Example:** `setPosAndWait(500, this)` The reason you add the `this` has to do with allowing the motor to control the control loop of the opMode.
- `setPosAndWait(int targetPosition, double speed, OpModeBase opmode)`
  - Moves to a position at given speed, waits until there.
  - **Example:** `setPosAndWait(500,0.7,this)// Make sure to include this`
- `setPosition(int targetPosition)`
  - Go to position at default speed (doesn’t wait).
- `setPosition(int targetPosition, double speed)`
  - Go to position at specified speed.

#### Speed & Power

- `setDefaultSpeed(double speed)`, `getDefaultSpeed()`
- `setPower(double power)`, `getPower()`. Note, this will override the motor mode to `DcMotor.RunMode.RUN_WITHOUT_ENCODER`. It will be set back when calling an encoder-based action like setPosition()

#### Encoder & State

- `reset()`  
  Resets encoder to 0. Will stop the motor to reset.
- `isAtTarget()`  
  Returns true if within `Constants.ENCODER_TOLERANCE` of target.
- `getCurrentPosition()`
  Return the encoder position
- `isBusy()`
  Returns true if moving

#### Run Modes & Directions

- `setMode(DcMotor.RunMode mode)`, `getMode()`  
  - Options:
  - `RUN_WITHOUT_ENCODER`
  - `RUN_USING_ENCODER`
  - `RUN_TO_POSITION`
  - `STOP_AND_RESET_ENCODER`

- `setZeroPowerBehavior(DcMotor.ZeroPowerBehavior behavior)`, `getZeroPowerBehavior()` Sets how the motor acts when no power is applied `BRAKE` or `FLOAT`
- `setDirection(DcMotorSimple.Direction direction)`, `getDirection()` `FORWARD` or `REVERSE`

#### Base Access

- `getBaseMotor()`  
  Returns the raw underlying `DcMotor`. It is very rate you will need to access this
---

## GoBildaPinpointDriver
### Look at `PinpointDocumentation.md` for more info

**Purpose:**  
Driver for the goBILDA® Pinpoint Odometry Computer.  
Handles communication, configuration, and reading robot pose/velocity.

### Instantiation

- Constructed by FTC SDK’s hardwareMap (`hardwareMap.get(GoBildaPinpointDriver.class,"odo")`).

### Key Methods

#### Data Update

- `update()`
  - Reads *all* odometry data (should be called each loop).
- `update(ReadData data)`
  - Reads only the heading (for performance).

#### Offsets & Encoder Setup

- `setOffsets(double xOffset, double yOffset)`
  - Set pod offsets in mm (deprecated: see overload with `DistanceUnit`).
- `setOffsets(double xOffset, double yOffset, DistanceUnit distanceUnit)`
- `setEncoderDirections(EncoderDirection x, EncoderDirection y)`
- `setEncoderResolution(GoBildaOdometryPods podType)`
- `setEncoderResolution(double ticks_per_mm)`
- `setEncoderResolution(double ticks_per_unit, DistanceUnit distanceUnit)`

#### IMU

- `recalibrateIMU()`
  - Zero the IMU (robot must be still).
- `resetPosAndIMU()`
  - Zero position and IMU.

#### Pose and Heading

- `setPosition(Pose2D pos)`
- `setPosX(double posX, DistanceUnit unit)`
- `setPosY(double posY, DistanceUnit unit)`
- `setHeading(double heading, AngleUnit unit)`
- `getPosition()`
- `getAngle()`, `getAngle(AngleUnit)`, `getAngle(UnnormalizedAngleUnit)`
- `getX()`, `getY()`, `getAngle()`

#### Status

- `getDeviceID()`, `getDeviceVersion()`
- `getDeviceStatus()`
- `getLoopTime()`, `getFrequency()`
- `getEncoderX()`, `getEncoderY()`
- `getYawScalar()`

#### Velocity

- `getVelX()`, `getVelX(DistanceUnit)`
- `getVelY()`, `getVelY(DistanceUnit)`
- `getHeadingVelocity()`, `getHeadingVelocity(UnnormalizedAngleUnit)`
- `getVelocity()`

#### Miscellaneous

- `getXOffset(DistanceUnit)`, `getYOffset(DistanceUnit)`
- `getPinpoint()`

---

# How to Use This Framework

1. **Create an OpMode** (autonomous: extend `AutoBase`; teleop: extend `TeleOpBase`)
2. **Override** the required abstract methods (`runSequence`, `runInit`, `runLoop`, etc.)
3. **Call movement and hardware methods** to control your robot.
4. **Tune constants** in `Constants.java` as needed (dashboard compatible).
5. **Use odometry and drive utilities** for accurate and efficient robot control.

---

## Questions?

- Each class and method is documented with purpose, usage, and scenarios.
- For detailed code reference, see the .java files.
- For FTC SDK integration, see [FTC documentation](https://ftc-docs.firstinspires.org/).
- If you can't find info about something or you need a quick answer, use GitHub Copilot with this codebase attached for info.
- If nothing else works, ask Josh. 

