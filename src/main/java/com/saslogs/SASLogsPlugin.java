package com.saslogs;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SASLogsPlugin extends JavaPlugin {

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yy HH:mm:ss");
    private File logFile;

    @Override
    public void onEnable() {
        createLogFile();
        logToFile("Server Started");

        Bukkit.getScheduler().runTaskLater(this, () -> logToFile("Server loaded"), 20L * 10); // 10 секунд
    }

    @Override
    public void onDisable() {
        logToFile("Server stops");
        Bukkit.getScheduler().runTaskLater(this, () -> logToFile("Server stopped"), 20L * 2); // 2 секунды
    }

    private void createLogFile() {
        logFile = new File(getDataFolder(), "logs.txt");
        if (!getDataFolder().exists()) {
            getDataFolder().mkdirs();
        }
        if (!logFile.exists()) {
            try {
                logFile.createNewFile();
            } catch (IOException e) {
                getLogger().severe("Could not create logs.txt file!");
                e.printStackTrace();
            }
        }
    }

    private void logToFile(String message) {
        String timeStamp = dateFormat.format(new Date());
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(logFile, true))) {
            writer.write(timeStamp + " - " + message);
            writer.newLine();
        } catch (IOException e) {
            getLogger().severe("Could not write to logs.txt file!");
            e.printStackTrace();
        }
    }
}
