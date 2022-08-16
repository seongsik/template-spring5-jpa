package com.ssk.dev.config;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;

/**
 * Created by sik371@ktnet.co.kr 2022-08-16 오후 4:20
 */
public class HibernatePhysicalNamingStrategy extends PhysicalNamingStrategyStandardImpl {
    private final static String TABLE_POSTFIX = "";

    @Override
    public Identifier toPhysicalTableName(Identifier name, JdbcEnvironment context) {
        if (name == null) {
            return null;
        }
        final String newName = convertToSnakeCase(name).getText() + TABLE_POSTFIX;
        return Identifier.toIdentifier(newName);
    }

    @Override
    public Identifier toPhysicalCatalogName(Identifier name, JdbcEnvironment jdbcEnvironment) {
        return convertToSnakeCase(name);
    }

    @Override
    public Identifier toPhysicalSchemaName(Identifier name, JdbcEnvironment jdbcEnvironment) {
        return convertToSnakeCase(name);
    }

    @Override
    public Identifier toPhysicalSequenceName(Identifier name, JdbcEnvironment jdbcEnvironment) {
        return convertToSnakeCase(name);
    }

    @Override
    public Identifier toPhysicalColumnName(Identifier name, JdbcEnvironment jdbcEnvironment) {
        return convertToSnakeCase(name);
    }


    private Identifier convertToSnakeCase(Identifier identifier) {
        if (identifier == null) {
            return null;
        }
        String name = identifier.getText();
        String newName = name.replaceAll("([a-z])([A-Z])", "$1_$2").toLowerCase();
        return Identifier.toIdentifier(newName);
    }
}
