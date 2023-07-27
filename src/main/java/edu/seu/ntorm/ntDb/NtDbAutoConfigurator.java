package edu.seu.ntorm.ntDb;

import edu.seu.ntorm.type.TypeAliasRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class NtDbAutoConfigurator {

    @Bean
    public TypeAliasRegistry getTypeAliasMap() {
        Map<String, Class<?>> maps = new HashMap<>();
        // ntDb目前支持string, int32, int64
        maps.put("int32", Integer.class);
        maps.put("int64", Long.class);
        maps.put("string", String.class);
        return new TypeAliasRegistry(maps);
    }
}
