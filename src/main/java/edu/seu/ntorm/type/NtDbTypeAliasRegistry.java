package edu.seu.ntorm.type;

import edu.seu.ntorm.ntDb.NtDbAutoConfigurator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;

import java.util.Map;

@ConditionalOnBean(value = {NtDbAutoConfigurator.class})
@Component
public class NtDbTypeAliasRegistry extends TypeAliasRegistry {

    public NtDbTypeAliasRegistry(@Qualifier("typeAliasMap") Map<String, Class<?>> typeAlias) {
        super(typeAlias);
    }

}
