package com.senla.service.impl;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
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
import com.senla.service.ServiceCatalog;
import com.senla.service.WorksWithFilesImport;

public class ImportFiles implements WorksWithFilesImport {
    DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    private final Rooms rooms;
    private final GuestRegistry guest;
    private final ServiceCatalog catalog;

    public ImportFiles(Rooms rooms, GuestRegistry guest, ServiceCatalog catalog) {
        this.rooms = rooms;
        this.guest = guest;
        this.catalog = catalog;
    }

    public void importGuest(Path path){
        try (BufferedReader read = Files.newBufferedReader(path)) {
            String line;
            while ((line = read.readLine()) != null) {
                String[] str = line.split("\\s*[,;]\\s*");

                String number = str[0];
                String name = str[1];
                LocalDate date = LocalDate.parse(str[2], fmt);
                int nights = Integer.parseInt(str[3]);
                int id = Integer.parseInt(str[4]);
                if(guest.getGuestId().contains(id)){
                    System.out.println("Данный гость уже есть в отеле, поэтому перезапишем данные");
                    guest.setGuestStats(number, name, date, date.plusDays(nights), id);
                }
                else {
                    guest.addHuman(number, name, date, nights);

                }

            }
        }catch (IOException e) {
            System.out.println("Ошибка чтения файла: " + path);
            throw new RuntimeException(e);
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            System.out.println("Некорректный формат данных в файле: " + path);
            throw new RuntimeException(e);
        }
    }

    public void importRooms(Path path) {
        try (BufferedReader read = Files.newBufferedReader(path)) {
            String line;
            while ((line = read.readLine()) != null) {
                String[] str = line.split("\\s*[,;]\\s*");

                String number = str[0];
                int capacity = Integer.parseInt(str[1]);
                int stars = Integer.parseInt(str[2]);
                int id = Integer.parseInt(str[3]);
                if(rooms.getRoomId().contains(id)){
                    System.out.println("Данная комната уже есть в отеле, поэтому перезапишем данные");
                    rooms.setRoomStars(number, stars);
                    rooms.setRoomCapacity(number, capacity);
                }
                else {
                    rooms.addRoom(number, capacity, stars);

                }
            }
        } catch (IOException e) {
            System.out.println("Ошибка чтения файла: " + path);
            throw new RuntimeException(e);
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            System.out.println("Некорректный формат данных в файле: " + path);
            throw new RuntimeException(e);
        }
    }


    public void importService(Path path){
        try (BufferedReader read = Files.newBufferedReader(path)) {
            String line;
            while ((line = read.readLine()) != null){
                String[] str = line.split("\\s*,\\s*");
                String nameService = str[0];
                int price = Integer.parseInt(str[1]);
                int id = Integer.parseInt(str[2]);

                if(catalog.getServicesId().contains(id)){
                    System.out.println("Данный сервис уже есть в отеле, поэтому перезапишем данные");
                    catalog.setServicePrice(nameService, price);
                }
                else {
                    catalog.addService(nameService, price);
                }

            }
        }catch (IOException e) {
            System.out.println("Ошибка чтения файла: " + path);
            throw new RuntimeException(e);
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            System.out.println("Некорректный формат данных в файле: " + path);
            throw new RuntimeException(e);
        }
    }

}
