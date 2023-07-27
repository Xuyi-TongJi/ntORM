package edu.seu.ntorm.type;

import edu.seu.ntorm.exception.TypeNotFoundException;
import edu.seu.ntorm.transaction.jdbc.JdbcTransactionFactory;
import org.springframework.stereotype.Component;

import java.util.Map;

public class TypeAliasRegistry {

    private final Map<String, Class<?>> typeAlias;

    public TypeAliasRegistry(Map<String, Class<?>> typeAlias) {
        this.typeAlias = typeAlias;
    }

    public Class<?> getJavaClass(String type) {
        Class<?> typeClass = typeAlias.get(type);
        if (typeClass == null) {
            throw new TypeNotFoundException();
        }
        return typeClass;
    }

    public void register(String alias, Class<?> type) {
        typeAlias.put(alias, type);
    }
}
