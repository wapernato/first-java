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
import java.util.List;
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
        try {

            long lines = Files.lines(path).count();

            if (lines <= rooms.countCapacity()) {

                try (BufferedReader read = Files.newBufferedReader(path)) {
                    String line;
                    while ((line = read.readLine()) != null) {
                        String[] str = line.split("\\s*[,;]\\s*");
                        LocalDate today = LocalDate.now();
                        String number = str[0];
                        String name = str[1];
                        LocalDate date = LocalDate.parse(str[2], fmt);
                        int nights = Integer.parseInt(str[3]);
                        int id = Integer.parseInt(str[4]);

                        if (!date.isBefore(today)) {
                            if (guest.getGuestId().contains(id)) {
                                System.out.println("Данный гость уже есть в отеле, поэтому перезапишем данные");
                                guest.setGuestStats(number, name, date, date.plusDays(nights), id);
                            } else {
                                guest.addHuman(number, name, date, nights);
                                System.out.println("Добавил гостя: " + name + " в номер: " + number);
                            }
                        } else {
                            System.out.println("Дата должна быть раньше " + today); // или логику переделать
                        }
                    }
                }
            } else {
                System.out.println("В отеле нету мест для такого количества гостей");
            }

        } catch (IOException e) {
            System.out.println("Ошибка чтения файла: " + path);
            return;
        }catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Отсутствуют данные для импорта, либо их излишне");
            return;
        }catch (RuntimeException e) {
            System.out.println("Неверный формат введенных данных");
            return;
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
                double price = Double.parseDouble(str[3]);
                int id = Integer.parseInt(str[4]);

                    if (rooms.getRoomId().contains(id)) {
                        System.out.println("Данная комната уже есть в отеле, поэтому перезапишем данные");
                        rooms.setRoomStars(number, stars);
                        rooms.setRoomCapacity(number, capacity);
                    } else {
                        rooms.addRoom(number, capacity, stars, price);
                        System.out.println("Комната: " + number + " - добавлена");
                    }

                }
        } catch (IOException e) {
            System.out.println("Ошибка чтения файла: " + path);
            return;
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Отсутствуют данные для импорта, либо их излишне");
            return;
        } catch(RuntimeException e){
            System.out.println("Неверный формат введенных данных");
            return;
        }
    }


    public void importService(Path path){
        try (BufferedReader read = Files.newBufferedReader(path)) {
            String line;
            while ((line = read.readLine()) != null){
                String[] str = line.split("\\s*,\\s*");
                String nameService = str[0];
                double price = Double.parseDouble(str[1]);
                int id = Integer.parseInt(str[2]);

                if(catalog.getServicesId().contains(id)){
                    System.out.println("Данный сервис уже есть в отеле, поэтому перезапишем данные");
                    catalog.setServicePrice(nameService, price);
                }
                else {
                    catalog.addService(nameService, price);
                    System.out.println("Услуга: " + nameService + " - добавлена в отель");
                }

            }
        }catch (IOException e) {
            System.out.println("Ошибка чтения файла: " + path);
            return;
        }catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Отсутствуют данные для импорта, либо их излишне");
            return;
        } catch(RuntimeException e){
            System.out.println("Неверный формат введенных данных");
            return;
        }
    }

}
