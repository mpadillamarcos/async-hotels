package mpadillamarcos.asynchotels.infra;

import mpadillamarcos.asynchotels.infra.db.BaseRepository;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Optional;
import java.util.UUID;

import static mpadillamarcos.asynchotels.infra.db.ResultSetMappings.instant;
import static mpadillamarcos.asynchotels.infra.db.ResultSetMappings.uuid;

@Repository
public class TestRepository extends BaseRepository<TestEntity> {

    public TestRepository(DataSource dataSource) {
        super(dataSource);
    }

    public void insert(TestEntity entity) {
        write("INSERT INTO test(id, created_date) VALUES(:id, :created_date)", params()
                .set("id", entity.id())
                .set("created_date", entity.createdDate()));
    }

    public Optional<TestEntity> find(UUID id) {
        return find("SELECT id, created_date FROM test WHERE id = :id", params().set("id", id));
    }

    @Override
    protected RowMapper<TestEntity> rowMapper() {
        return (resultSet, index) -> new TestEntity(
            uuid(resultSet, "id"),
            instant(resultSet, "created_date")
        );
    }
}
