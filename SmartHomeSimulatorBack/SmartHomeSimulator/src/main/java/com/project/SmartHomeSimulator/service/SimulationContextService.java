package com.project.SmartHomeSimulator.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.SmartHomeSimulator.model.HomeLayout;
import com.project.SmartHomeSimulator.model.Room;
import com.project.SmartHomeSimulator.model.SimulationContext;
import com.project.SmartHomeSimulator.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SimulationContextService {
    @Autowired
    private SimulationContext simulationContext;

    @Autowired
    public SimulationContextService(SimulationContext simulationContext) {
        this.simulationContext = simulationContext;
    }

    public boolean startSimulation(SimulationContext simulationContext) {
        if (simulationContext != null) {
            this.simulationContext.clone(simulationContext);
            return true;
        }
        return false;
    }

    public void stopSimulation() {
        simulationContext.setSimulationRunning(false);
    }

    public SimulationContext getSimulationContext() {
        return simulationContext;
    }

    public void editInsideTemp(int insideTemp) {
        simulationContext.setInsideTemp(insideTemp);
    }

    public void editOutsideTemp(int outsideTemp) {
        simulationContext.setOutsideTemp(outsideTemp);
    }

    public void editTime(String time) {
        simulationContext.setTime(time);
    }

    public void editDate(String date) {
        simulationContext.setDate(date);
    }

    public void setCurrentSimulationUser(User user) {
        simulationContext.setCurrentSimulationUser(user);
    }

    public boolean blockWindow(String roomName) {
        Room roomToBeBlocked = simulationContext.getHomeLayout().getRoomByName(roomName);
        if (roomToBeBlocked != null && roomToBeBlocked.getWindow() != null) {
            roomToBeBlocked.getWindow().setBlocked(true);
            return true;
        }
        return false;
    }

    public boolean unblockWindow(String roomName) {
        Room roomToBeBlocked = simulationContext.getHomeLayout().getRoomByName(roomName);
        if (roomToBeBlocked != null && roomToBeBlocked.getWindow() != null) {
            roomToBeBlocked.getWindow().setBlocked(false);
            return true;
        }
        return false;
    }

    public HomeLayout loadLayout(String homeLayoutFile) {
        HomeLayout homeLayout = simulationContext.getHomeLayout();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            homeLayout = objectMapper.readValue(homeLayoutFile, HomeLayout.class);
            simulationContext.setHomeLayout(homeLayout);
            return homeLayout;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

}
