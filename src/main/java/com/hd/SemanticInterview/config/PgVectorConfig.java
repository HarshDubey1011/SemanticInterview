//package com.hd.SemanticInterview.config;
//
//import com.pgvector.PGvector;
//import jakarta.annotation.PostConstruct;
//import org.springframework.context.annotation.Configuration;
//
//import javax.sql.DataSource;
//import java.sql.Connection;
//import java.sql.SQLException;
//
//@Configuration
//public class PgVectorConfig {
//
//    private final DataSource dataSource;
//
//    public PgVectorConfig(DataSource dataSource) {
//        this.dataSource = dataSource;
//    }
//
//    @PostConstruct
//    public void registerVectorType() throws SQLException {
//        try (Connection connection = dataSource.getConnection()) {
//            PGvector.registerTypes(connection);
//        }
//    }
//}