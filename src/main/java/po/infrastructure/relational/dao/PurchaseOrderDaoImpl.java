package po.infrastructure.relational.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import po.domain.dto.Part;
import po.domain.dto.PurchaseOrder;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PurchaseOrderDaoImpl implements PurchaseOrderDao {

    private DataSource dataSource;
    private JdbcTemplate template;
    private SimpleJdbcInsert insert;

    public static final String TABLE_NAME = "po_purchase_order";
    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String APPROVED_LIMIT = "approvedLimit";

    public PurchaseOrderDaoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
        this.template = new JdbcTemplate(dataSource);
        this.insert = new SimpleJdbcInsert(dataSource).withTableName(TABLE_NAME)
                .usingGeneratedKeyColumns(ID);
    }

    public static class PurchaseOrderRowMapper implements RowMapper<PurchaseOrder> {

        @Override
        public PurchaseOrder mapRow(ResultSet rs, int rowNum) throws SQLException {
            int id = rs.getInt(ID);
            String name = rs.getString(NAME);
            int approvedLimit = rs.getInt(APPROVED_LIMIT);
            PurchaseOrder po = PurchaseOrder
                    .builder().id(id).name(name).approvedLimit(approvedLimit).build();
            return po;
        }
    }

    @Override
    public int addPurchaseOrder(PurchaseOrder order) {
        Map<String, Object> params = new HashMap<>();
        params.put(NAME, order.getName());
        params.put(APPROVED_LIMIT, order.getApprovedLimit());
        Number number = insert.executeAndReturnKey(params);
        return number.intValue();
    }

    @Override
    public List<PurchaseOrder> getAllPurchaseOrders() {
        String sql = String.format("SELECT * FROM %s", TABLE_NAME);
        return template.query(sql, new PurchaseOrderRowMapper());
    }

    @Override
    public PurchaseOrder getPurchaseOrder(int id) {
        String sql = String.format("SELECT * FROM %s WHERE %s = ?", TABLE_NAME, ID);
        PurchaseOrder po = template.queryForObject(sql, new PurchaseOrderRowMapper(), id);
        return po;
    }

    @Override
    public boolean updatePurchaseOrder(PurchaseOrder order) {
        String sql = String.format("UPDATE %s SET %s = ?, %s = ? WHERE %s = ?",
                TABLE_NAME, NAME, APPROVED_LIMIT, ID);
        return template.update(sql,
                order.getName(), order.getApprovedLimit(), order.getId()) == 1;
    }

    @Override
    public void clearEntries() {
        String sql = String.format("DELETE FROM %s", TABLE_NAME);
        template.update(sql);
        String sql2 = String.format("ALTER TABLE %s AUTO_INCREMENT = 1", TABLE_NAME);
        template.update(sql2);
    }
}
