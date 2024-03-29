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
        typeMap.put(Types.BIT, new JdbcTypeInformation("BIT",
                new FullyQualifiedJavaType(Boolean.class.getName())));
        typeMap.put(Types.BLOB, new JdbcTypeInformation("BLOB", //$NON-NLS-1$
                new FullyQualifiedJavaType(String.class.getName()))); //$NON-NLS-1$
    }
}