package com.senla.service;

import java.nio.file.Path;

public interface WorksWithFilesImport {

    void importGuest(Path path);
    void importService(Path path);
    void importRooms(Path path);

}
