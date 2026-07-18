package mpadillamarcos.asynchotels.infra.db;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

public abstract class BaseRepository<T> {

    protected final DataSource dataSource;

    public BaseRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    protected int write(String query, QueryParams params) {
        return new NamedParameterJdbcTemplate(dataSource).update(query, params);
    }

    protected List<T> findAll(String query) {
        return new NamedParameterJdbcTemplate(dataSource).query(query, rowMapper());
    }

    protected List<T> findAll(String query, QueryParams params) {
        return new NamedParameterJdbcTemplate(dataSource).query(query, params, rowMapper());
    }

    protected Optional<T> find(String query, QueryParams params) {
        final var results = findAll(query, params);

        if (results.size() == 1) {
            return results.stream().findFirst();
        }
        throw new IllegalStateException("Expected one result but found " + results.size());
    }

    protected T get(String query, QueryParams params) {
        return find(query, params).orElseThrow(NotFoundException::new);
    }

    protected abstract RowMapper<T> rowMapper();

    protected static QueryParams params() {
        return new QueryParams();
    }

    protected static final class QueryParams extends MapSqlParameterSource {

        private QueryParams() {}

        public QueryParams set(String name, Object value) {
            addValue(name, value);
            return this;
        }

        public QueryParams set(String name, Instant value) {
            return set(name, Timestamp.from(value));
        }
    }
}
