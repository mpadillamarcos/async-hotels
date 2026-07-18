package mpadillamarcos.asynchotels.domain.uploads;

public interface FileStorage {

    String store(byte[] content, String name);
}
