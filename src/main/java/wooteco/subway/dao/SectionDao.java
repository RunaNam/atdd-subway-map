package wooteco.subway.dao;

import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import wooteco.subway.domain.Section;

@Repository
public class SectionDao {
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public SectionDao(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("SECTION")
                .usingGeneratedKeyColumns("id");
    }

    private final RowMapper<Section> sectionRowMapper = (resultSet, rowNum) ->
            Section.of(
                    resultSet.getLong("id"),
                    resultSet.getLong("up_station_id"),
                    resultSet.getLong("down_station_id"),
                    resultSet.getInt("distance")
            );

    public Section insert(Long upStationId, Long downStationId, Integer distance, Long lineId) {
        Long id = simpleJdbcInsert.executeAndReturnKey(
                Map.of("LINE_ID", lineId, "UP_STATION_ID", upStationId, "DOWN_STATION_ID", downStationId, "DISTANCE",
                        distance)).longValue();

        return Section.of(id, upStationId, downStationId, distance);
    }

    public List<Section> findByLineId(Long lineId) {
        final String sql = "SELECT * FROM SECTION WHERE line_id = ?";
        return jdbcTemplate.query(sql, sectionRowMapper, lineId);
    }
}