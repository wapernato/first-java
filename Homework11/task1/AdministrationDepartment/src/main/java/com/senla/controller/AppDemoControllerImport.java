package com.senla.controller;

import com.senla.annotation.Inject;
import com.senla.service.*;
import com.senla.view.AppDemoView;

import java.io.IOException;
import java.nio.file.*;


public class AppDemoControllerImport {
    @Inject
    private AppDemoView view;
    @Inject
    private WorksWithFilesImport importt;

    public AppDemoControllerImport() {}

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
        catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Отсутствуют данные для импорта, либо их излишне");
            return;
        } catch(RuntimeException e){
            System.out.println("Неверный формат введенных данных");
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
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Ты обращаешься к элементу массива по индексу, которого не существует");
            return;
        } catch(RuntimeException e){
            System.out.println("Неверный формат введенных данных");
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
        catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Ты обращаешься к элементу массива по индексу, которого не существует");
            return;
        } catch(RuntimeException e){
            System.out.println("Неверный формат введенных данных");
            return;
        }
    }
}