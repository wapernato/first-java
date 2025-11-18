package com.senla.service.impl;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

import com.senla.service.GuestRegistry;
import com.senla.service.Rooms;
import com.senla.service.WorksWithFilesImport;

public class ImportFiles implements WorksWithFilesImport {
    DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    private final Rooms rooms;
    private final GuestRegistry guest;

    public ImportFiles(Rooms rooms, GuestRegistry guest) {
        this.rooms = rooms;
        this.guest = guest;
    }

    public void importGuest(){
        Path ListGuest = Paths.get("C:\\Users\\wapernato\\import\\Guest.csv");
        try (BufferedReader read  = Files.newBufferedReader(ListGuest)) {
            String line;
            while ((line = read.readLine()) != null) {
                String[] str = line.split("\\s*,\\s*");

                String number = str[0];
                String name = str[1];
                LocalDate date = LocalDate.parse(str[2], fmt);
                int nights = Integer.parseInt(str[3]);
                guest.addHuman(number, name, date, nights);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void importRooms(){
        Path ListGuest = Paths.get("C:\\Users\\wapernato\\import\\Rooms.csv");
        try (BufferedReader read  = Files.newBufferedReader(ListGuest)) {
            String line;
            while ((line = read.readLine()) != null){
                String[] str = line.split("\\s*,\\s*");
                String numer = str[0];
                int capacity = Integer.parseInt(str[1]);
                int stars = Integer.parseInt(str[2]);
                rooms.addRoom(numer, capacity, stars);
            }
        } catch (Exception e) {
            System.out.println("Error");
            throw new RuntimeException(e);
        }
    }


    public void importService(){

    }

}
