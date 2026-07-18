package mpadillamarcos.asynchotels.domain.uploads;

import java.util.UUID;

import static mpadillamarcos.asynchotels.util.Checks.require;

public record FileMetadata(UUID id, String path, String name) {

    public FileMetadata {
        require("id", id);
        require("path", path);
        require("name", name);
    }
}
