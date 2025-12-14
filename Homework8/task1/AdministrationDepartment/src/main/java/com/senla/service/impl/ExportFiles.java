package com.senla.service.impl;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.senla.model.Room;
import com.senla.service.GuestRegistry;
import com.senla.service.Rooms;
import com.senla.service.ServiceCatalog;
import com.senla.service.WorksWithFilesExport;

public class ExportFiles implements WorksWithFilesExport {

    private final DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    private final Rooms rooms;
    private final GuestRegistry guests;
    private final ServiceCatalog services;

    public ExportFiles(Rooms rooms, GuestRegistry guests, ServiceCatalog services) {
        this.rooms = rooms;
        this.guests = guests;
        this.services = services;
    }

    @Override
    public void exportGuest(Path path) {
        try (BufferedWriter writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8)) {

            for (GuestRegistry.GuestEntry e : guests.getAllGuestEntries()) {
                LocalDate checkIn = e.checkIn;
                LocalDate checkOut = e.checkOut;
                long nights = java.time.temporal.ChronoUnit.DAYS.between(checkIn, checkOut);

                String line = String.join(",",
                        e.number,
                        e.guest,
                        checkIn.format(fmt),
                        String.valueOf(nights),
                        String.valueOf(e.id)
                );
                writer.write(line);
                writer.newLine();
            }

        } catch (IOException ex) {
            System.out.println("Ошибка записи файла гостей: " + path);
            return;
        }
        catch(RuntimeException e){
            System.out.println("Неверный формат введенных данных");
            return;
        }
    }

    @Override
    public void exportRooms(Path path) {
        try (BufferedWriter writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8)) {

            int id = 1;
            for (String num : rooms.getRoomsNumbers()) {
                Room r = rooms.getRoom(num);
                if (r == null) continue;

                String line = String.join(",",
                        r.getNumber(),
                        String.valueOf(r.getCapacity()),
                        String.valueOf(r.getStars()),
                        String.valueOf(r.getPrice()),
                        String.valueOf(id++)
                );
                writer.write(line);
                writer.newLine();
            }

        } catch (IOException ex) {
            System.out.println("Ошибка записи файла комнат: " + path);
            return;
        }
        catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Ты обращаешься к элементу массива по индексу, которого не существует");
            return;
        } catch(RuntimeException e){
            System.out.println("Неверный формат введенных данных");
            return;
        }
    }

    @Override
    public void exportService(Path path) {
        try (BufferedWriter writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8)) {

            int id = 1;

            for (String name : services.listServiceNames()) {
                double price = services.getServicePrice(name);

                String line =  name + "," + price + "," + id;
                writer.write(line);
                writer.newLine();

                id++;
            }

        } catch (IOException ex) {
            System.out.println("Ошибка записи файла услуг: " + path);
            return;
        } catch (RuntimeException e) {
            System.out.println("Неверный формат введенных данных");
            return;
        }
    }
}