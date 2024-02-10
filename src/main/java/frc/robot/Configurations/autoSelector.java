// This file is for selecting an auto mode in the SmartDashboard

package frc.robot.Configurations;

import java.util.Optional;

import com.pathplanner.lib.auto.AutoBuilder;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Commands.auto.auto1;

/**
 * Builds an Auto Mode Chooser by merging the
 * PathPlanner generated Autos with manually specified auto commands
 */
public class autoSelector
{
    private final SendableChooser<Command> modeSelector;

    private final String defaultModeKey = "None";
    private final Command defaultMode = new WaitCommand(15.0);

    private Optional<Command> currentMode;

    public autoSelector()
    {
        // build the chooser with the Path Planner Autos
        modeSelector = AutoBuilder.buildAutoChooser();

        // Add any manually created auto commands here
        add("Auto 1", new auto1());

        setDefault(defaultModeKey, defaultMode);

        // push the chooser to the dashboard
        SmartDashboard.putData("Auto Mode", modeSelector);
    }

    /**
     * Add a new auto mode option
     */
    public void add(String name, Command command)
    {
        modeSelector.addOption(name, command);
    }

    /**
     * Set the default auto mode
     */
    public void setDefault(String name, Command command)
    {
        modeSelector.setDefaultOption(name, command);
    }

    /**
     * Call to get the latest mode from the Dashboard
     */
    public void update()
    {
        Command desiredMode = modeSelector.getSelected();

        if (desiredMode == null)
        {
            desiredMode = defaultMode;
        }

        if (!currentMode.equals(Optional.of(desiredMode)))
        {
            System.out.println("Auto: " + desiredMode);
            currentMode = Optional.of(desiredMode);
        }
    }

    /**
     * Reset the current mode to the default mode
     */
    public void reset()
    {
        currentMode = Optional.of(defaultMode);
    }

    /**
     * Get the currently selected Auto Mode
     */
    public Optional<Command> getAutoMode()
    {
        return currentMode;
    }
}
