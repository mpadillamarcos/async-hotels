package mpadillamarcos.asynchotels.infra.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

public class ResultSetMappings {

    public static UUID uuid(ResultSet resultSet, String name) throws SQLException {
        return resultSet.getObject(name, UUID.class);
    }

    public static Instant instant(ResultSet resultSet, String name) throws SQLException {
        return Optional.ofNullable(resultSet.getTimestamp(name))
                .map(Timestamp::toInstant)
                .orElse(null);
    }
}
