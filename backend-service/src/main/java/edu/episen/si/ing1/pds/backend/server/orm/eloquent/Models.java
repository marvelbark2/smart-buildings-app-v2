package edu.episen.si.ing1.pds.backend.server.orm.eloquent;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.node.ObjectNode;
import edu.episen.si.ing1.pds.backend.server.network.exchange.SocketParams;
import edu.episen.si.ing1.pds.backend.server.orm.builder.Builder;
import edu.episen.si.ing1.pds.backend.server.orm.builder.DbColumn;
import edu.episen.si.ing1.pds.backend.server.orm.builder.DbTable;
import edu.episen.si.ing1.pds.backend.server.pool.DataSource;
import edu.episen.si.ing1.pds.backend.server.test.models.Companies;
import edu.episen.si.ing1.pds.backend.server.utils.Utils;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.util.stream.Collectors;

@JsonDeserialize(as= Companies.class)
public class Models implements Serializable {
    private String tableName;
    private String primaryKey = "id";
    private final ObjectMapper mapper = Utils.jsonMapper;

    public final  <T extends Models> List<T> all() throws SQLException, ClassNotFoundException, JsonProcessingException {
        final List<T> alll = new LinkedList<>();
        DbTable sqlTable = new DbTable(this.tableName != null ? this.tableName : this.getClass().getSimpleName().toLowerCase(Locale.ROOT));
        List<String> fields = Arrays.stream(this.getClass().getDeclaredFields()).map(Field::getName).collect(Collectors.toList());
        String sql = Builder.selectBuilder().from(sqlTable).build();
        Connection connection = new DataSource(6, 10).getConnectionPool().getConnection();
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        while (rs.next()) {
            Map<String, Object> lhm = new LinkedHashMap<>();

            for (String col: fields) {
                int i = rs.findColumn(col);
                System.out.println(i);
                Class clzz = Class.forName(rs.getMetaData().getColumnClassName(i));
                if(clzz == Integer.class)
                    lhm.put(col, rs.getInt(i));
                else if(clzz == String.class)
                    lhm.put(col, rs.getString(i));
                else System.out.println("out of bound");
            }
            System.out.println(lhm);
            String json = mapper.writeValueAsString(lhm);
            T t = mapper.readValue(json, new TypeReference<T>() {});
            alll.add(t);
        }

        return alll;
    }

    private final String json() {
        try {
            return mapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }
    @Override
    public String toString() {
        return this.json();
    }
}
