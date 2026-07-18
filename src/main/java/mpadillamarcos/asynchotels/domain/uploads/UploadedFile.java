package mpadillamarcos.asynchotels.domain.uploads;

import static mpadillamarcos.asynchotels.util.Checks.require;

public record UploadedFile(byte[] content, String name) {

    public UploadedFile {
        require("content", content);
        require("name", name);
    }

    public boolean isEmpty() {
        return content.length == 0;
    }

    public boolean hasExtension(String extension) {
        return name.endsWith("." + extension);
    }
}
