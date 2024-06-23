package tr.com.minesoft.minetrack.db;

import org.joda.time.DateTime;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface DAO<T, V>
{
    boolean insert(T t);

    boolean update(T t, String[] params);

    boolean delete(List<V> list);

    Map<V, T> get(String[] params);
    
    List<T> get(String tid, DateTime dt1, DateTime dt2);
    List<T> get(String tid, LocalDateTime dt1, LocalDateTime dt2);
}
