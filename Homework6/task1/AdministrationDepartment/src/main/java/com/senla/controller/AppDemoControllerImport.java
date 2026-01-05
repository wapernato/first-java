package com.senla.controller;

import com.senla.service.*;
import com.senla.view.AppDemoView;

import java.io.IOException;
import java.nio.file.*;


public class AppDemoControllerImport {
    private final Rooms rooms;
    private final GuestRegistry guests;
    private final SortStats sorter;
    private final ServiceCatalog catalog;
    private final ServiceUsageRegistry usage;
    private final AppDemoView view;
    private final WorksWithFilesImport importt;

    public AppDemoControllerImport(Rooms rooms,
                                   GuestRegistry guests,
                                   SortStats sorter,
                                   ServiceCatalog catalog,
                                   ServiceUsageRegistry usage,
                                   AppDemoView view,
                                   WorksWithFilesImport importt) {
        this.rooms = rooms;
        this.guests = guests;
        this.sorter = sorter;
        this.catalog = catalog;
        this.usage = usage;
        this.view = view;
        this.importt = importt;
    }

    public void roomsImport(){
        view.showMessage("\n=== Импорт комнат ===\n");

        try {
            Path path;
            while (true) {
                path = view.askPath("Введите путь до файла ");
                if (!Files.exists(path)) {
                    throw new IllegalArgumentException("Файл не найден: " + path);
                }

                if (!Files.isRegularFile(path)) {
                    throw new IllegalArgumentException("Это не файл, а папка: " + path);
                }
                String fileName = path.getFileName().toString().toLowerCase();
                if (!fileName.endsWith(".csv")) {
                    throw new IllegalArgumentException("Это не .csv формат: " + path);
                }
                importt.importRooms(path);
                return;
            }
        }
        catch(RuntimeException e){
            System.out.println(e.getMessage());
            return;
        }

    }

    public void guestImport() {

            view.showMessage("\n=== Импорт гостей ===\n");
            try {
                Path path;
                while (true) {
                    path = view.askPath("Введите путь до файла ");
                    if (!Files.exists(path)) {
                        throw new IllegalArgumentException("Файл не найден: " + path);
                    }

                    if (!Files.isRegularFile(path)) {
                        throw new IllegalArgumentException("Это не файл, а папка: " + path);
                    }
                    String fileName = path.getFileName().toString().toLowerCase();
                    if (!fileName.endsWith(".csv")) {
                        throw new IllegalArgumentException("Это не .csv формат: " + path);
                    }
                    importt.importGuest(path);
                    return;
                }
            } catch(RuntimeException e){
                System.out.println(e.getMessage());
                return;
            }
        }

    public void serviceImport(){
        view.showMessage("\n=== Импорт сервисов ===\n");
        try {
            Path path;
            while (true) {
                path = view.askPath("Введите путь до файла или 0 для возврата");
                if(view.commandBackPath(path)){
                    view.showMessage("Вы вернулись назад");
                    return;
                }
                if (!Files.exists(path)) {
                    throw new IllegalArgumentException("Файл не найден: " + path);
                }

                if (!Files.isRegularFile(path)) {
                    throw new IllegalArgumentException("Это не файл, а папка: " + path);
                }
                String fileName = path.getFileName().toString().toLowerCase();
                if (!fileName.endsWith(".csv")) {
                    throw new IllegalArgumentException("Это не .csv формат: " + path);
                }
                importt.importService(path);
                return;
            }
        }
        catch(RuntimeException e){
            System.out.println(e.getMessage());
            return;
        }
    }
}
