package com.senla.controller;

import com.senla.service.*;
import com.senla.view.AppDemoView;

import java.nio.file.Files;
import java.nio.file.Path;

public class AppDemoControllerExport {
    private final Rooms rooms;
    private final GuestRegistry guests;
    private final SortStats sorter;
    private final ServiceCatalog catalog;
    private final ServiceUsageRegistry usage;
    private final AppDemoView view;
    private final WorksWithFilesExport exportt;

    public AppDemoControllerExport(Rooms rooms,
                                   GuestRegistry guests,
                                   SortStats sorter,
                                   ServiceCatalog catalog,
                                   ServiceUsageRegistry usage,
                                   AppDemoView view,
                                   WorksWithFilesExport exportt) {
        this.rooms = rooms;
        this.guests = guests;
        this.sorter = sorter;
        this.catalog = catalog;
        this.usage = usage;
        this.view = view;
        this.exportt = exportt;
    }

    // === ЭКСПОРТ КОМНАТ ===
    public void roomsExport() {
        view.showMessage("\n=== Экспорт комнат ===\n");
        try {
            Path path;
            while (true) {
                path = view.askPath("Введите путь для сохранения файла (.csv): ");

                // если путь указывает на папку
                if (Files.exists(path) && !Files.isRegularFile(path)) {
                    throw new IllegalArgumentException("Это не файл, а папка: " + path);
                }

                String fileName = path.getFileName().toString().toLowerCase();
                if (!fileName.endsWith(".csv")) {
                    throw new IllegalArgumentException("Это не .csv формат: " + path);
                }

                // проверяем, что родительская папка существует
                Path parent = path.getParent();
                if (parent != null && !Files.exists(parent)) {
                    view.showMessage("Файла не было на компьютере, поэтому он был создан. Расположение - " + path);
                    Files.createFile(path);
                }
                exportt.exportRooms(path);
                view.showMessage("Экспорт комнат завершён: " + path + "\n");
                return;
            }
        } catch (Exception e) {
            System.out.println("Error");
            throw new RuntimeException(e);
        }

    }

    // === ЭКСПОРТ ГОСТЕЙ ===
    public void guestExport() {
        view.showMessage("\n=== Экспорт гостей ===\n");
        try {
            Path path;
            while (true) {
                path = view.askPath("Введите путь для сохранения файла (.csv): ");

                if (Files.exists(path) && !Files.isRegularFile(path)) {
                    throw new IllegalArgumentException("Это не файл, а папка: " + path);
                }

                String fileName = path.getFileName().toString().toLowerCase();
                if (!fileName.endsWith(".csv")) {
                    throw new IllegalArgumentException("Это не .csv формат: " + path);
                }

                Path parent = path.getParent();
                if (parent != null && !Files.exists(parent)) {
                    view.showMessage("Файла не было на компьютере, поэтому он был создан. Расположение - " + path);
                    Files.createFile(path);
                }

                exportt.exportGuest(path);
                view.showMessage("Экспорт гостей завершён: " + path + "\n");
                return;
            }
        } catch (Exception e) {
            System.out.println("Error");
            throw new RuntimeException(e);
        }
    }

    // === ЭКСПОРТ СЕРВИСОВ ===
    public void serviceExport() {
        view.showMessage("\n=== Экспорт сервисов ===\n");
        try {
            Path path;
            while (true) {
                path = view.askPath("Введите путь для сохранения файла (.csv) или 0 для возврата: ");
                // если у тебя уже реализована логика "0 для возврата" в commandBackPath — можно сделать так же, как в импортe
                if (view.commandBackPath(path)) {
                    view.showMessage("Вы вернулись назад");
                    return;
                }

                if (Files.exists(path) && !Files.isRegularFile(path)) {
                    throw new IllegalArgumentException("Это не файл, а папка: " + path);
                }

                String fileName = path.getFileName().toString().toLowerCase();
                if (!fileName.endsWith(".csv")) {
                    throw new IllegalArgumentException("Это не .csv формат: " + path);
                }

                Path parent = path.getParent();
                if (parent != null && !Files.exists(parent)) {
                    view.showMessage("Файла не было на компьютере, поэтому он был создан. Расположение - " + path);
                    Files.createFile(path);
                }

                exportt.exportService(path);
                view.showMessage("Экспорт сервисов завершён: " + path + "\n");
                return;
            }
        } catch (Exception e) {
            System.out.println("Error");
            throw new RuntimeException(e);
        }
    }
}