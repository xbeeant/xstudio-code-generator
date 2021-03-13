package io.github.xbeeant.mybatis.extend;

import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.internal.types.JavaTypeResolverDefaultImpl;

import java.sql.Types;

/**
 * @author huangxiaobiao
 */
public class JavaTypeResolver extends JavaTypeResolverDefaultImpl {
    public JavaTypeResolver() {
        super();
        typeMap.put(Types.SMALLINT, new JdbcTypeInformation("SMALLINT",
                new FullyQualifiedJavaType(Integer.class.getName())));
        typeMap.put(Types.TINYINT, new JdbcTypeInformation("TINYINT",
                new FullyQualifiedJavaType(Integer.class.getName())));
    }
}
