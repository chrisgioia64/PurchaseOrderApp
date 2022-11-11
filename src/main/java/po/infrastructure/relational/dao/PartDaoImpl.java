package po.infrastructure.relational.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import po.domain.dto.Part;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PartDaoImpl implements PartDao {

    private DataSource dataSource;
    private JdbcTemplate template;
    private SimpleJdbcInsert insert;

    public static final String TABLE_NAME = "po_part";
    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String PRICE = "price";

    public static class PartRowMapper implements RowMapper<Part> {

        @Override
        public Part mapRow(ResultSet rs, int rowNum) throws SQLException {
            int id = rs.getInt(ID);
            String name = rs.getString(NAME);
            double price = rs.getDouble(PRICE);
            Part part = Part.builder().id(id).name(name).price(price).build();
            return part;
        }
    }

    public PartDaoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
        this.template = new JdbcTemplate(dataSource);
        this.insert = new SimpleJdbcInsert(dataSource).withTableName(TABLE_NAME);
    }

    @Override
    public int addPart(String name, double price) {
        Map<String, Object> params = new HashMap<>();
        params.put(NAME, name);
        params.put(PRICE, price);
        return insert.execute(params);
    }

    @Override
    public Part getPart(int id) {
        String sql = String.format("SELECT * FROM %s WHERE %s = ?", TABLE_NAME, ID);
        Part part = template.queryForObject(sql, new PartRowMapper(), id);
        return part;
    }

    @Override
    public boolean updatePart(Part part) {
        String sql = String.format("UPDATE %s SET %s = ?, %s = ? WHERE %s = ?",
                TABLE_NAME, NAME, PRICE, ID);
        return template.update(sql, part.getName(), part.getPrice(), part.getId()) == 1;
    }

    @Override
    public List<Part> getParts() {
        String sql = String.format("SELECT * FROM %s", TABLE_NAME);
        return template.query(sql, new PartRowMapper());
    }

    @Override
    public void clearEntries() {
//        String sql = String.format("TRUNCATE %s", TABLE_NAME);
        String sql = String.format("DELETE FROM %s", TABLE_NAME);
        template.update(sql);
        String sql2 = String.format("ALTER TABLE %s AUTO_INCREMENT = 1", TABLE_NAME);
        template.update(sql2);
    }
}
