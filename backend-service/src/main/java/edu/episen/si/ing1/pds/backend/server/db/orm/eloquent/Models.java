package edu.episen.si.ing1.pds.backend.server.db.orm.eloquent;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.episen.si.ing1.pds.backend.server.db.orm.eloquent.collection.CollectionModel;
import edu.episen.si.ing1.pds.backend.server.db.orm.eloquent.collection.ICollectionModel;
import edu.episen.si.ing1.pds.backend.server.network.exchange.socket.SocketParams;
import edu.episen.si.ing1.pds.backend.server.db.orm.builder.Builder;
import edu.episen.si.ing1.pds.backend.server.db.orm.builder.DbTable;
import edu.episen.si.ing1.pds.backend.server.db.pool.DataSource;
import edu.episen.si.ing1.pds.backend.server.utils.Utils;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.util.stream.Collectors;

public class Models implements Serializable {
    private String tableName;
    private String primaryKey = "id";
    private final ObjectMapper mapper = Utils.jsonMapper;
    private static Class<? extends Models> currentModel;

    public static void setCurrentModel(Class<? extends Models> model) {
        currentModel = model;
    }

    public final  <T extends Models> CollectionModel<T> all() throws SQLException, ClassNotFoundException {
        final List<T> alll = new LinkedList<>();
        DbTable sqlTable = new DbTable(this.tableName != null ? this.tableName : this.getClass().getSimpleName().toLowerCase(Locale.ROOT));
        List<String> fields = Arrays.stream(this.getClass().getDeclaredFields()).map(Field::getName).collect(Collectors.toList());
        String sql = Builder.selectBuilder().from(sqlTable).build();
        Connection connection = SocketParams.getConnection();
        System.out.println("Con :" + connection);
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        while (rs.next()) {
            Map<String, Object> lhm = new LinkedHashMap<>();

            for (String col: fields) {
                int i = rs.findColumn(col);
                Class clzz = Class.forName(rs.getMetaData().getColumnClassName(i));
                if(clzz == Integer.class)
                    lhm.put(col, rs.getInt(i));
                else if(clzz == String.class)
                    lhm.put(col, rs.getString(i));
            }
            T t = (T) mapper.convertValue(lhm, this.getClass());
            alll.add(t);
        }

        return new ICollectionModel<>(alll);
    }

    public static String getNameTOTO() {
        String className = currentModel.getName();
        return className;
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

    public <T extends Models> Map<Object, T> map() {
        Map<Object, T> hm = new HashMap<>();
        return mapper.convertValue(this,  hm.getClass());
    }
}
