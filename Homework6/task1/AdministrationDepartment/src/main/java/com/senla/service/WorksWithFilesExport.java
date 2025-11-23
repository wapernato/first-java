package com.senla.service;

import java.nio.file.Path;

public interface WorksWithFilesExport {
    void exportGuest(Path path);
    void exportService(Path path);
    void exportRooms(Path path);
}
