package mpadillamarcos.asynchotels.domain.uploads;

import static java.util.UUID.randomUUID;
import static mpadillamarcos.asynchotels.util.Checks.require;

public class UploadsService {

    private final FileStorage fileStorage;

    public UploadsService(FileStorage fileStorage) {
        this.fileStorage = fileStorage;
    }

    public FileMetadata uploadCsv(UploadedFile file) {
        validateCsvFile(file);

        final var id = randomUUID();
        final var fileName = file.name();
        final var path = fileStorage.store(file.content(), "uploads/" + id + "/" + fileName);

        return new FileMetadata(id, path, fileName);
    }

    private static void validateCsvFile(UploadedFile file) {
        require("file", file);

        if (file.isEmpty()) {
            throw new IllegalArgumentException("file must not be empty");
        }
        if (!file.hasExtension("csv")) {
            throw new IllegalArgumentException("file must have csv extension");
        }
    }
}
