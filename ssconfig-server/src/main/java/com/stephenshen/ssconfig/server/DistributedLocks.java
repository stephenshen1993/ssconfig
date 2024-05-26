package com.stephenshen.ssconfig.server;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * distributed locks.
 *
 * @author stephenshen
 * @date 2024/5/26 18:51:42
 */
@Component
public class DistributedLocks {

    @Autowired
    DataSource dataSource;

    Connection connection;

    @Getter
    private final AtomicBoolean locked = new AtomicBoolean(false);
    ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

    @PostConstruct
    public void init() {
        try {
            connection = dataSource.getConnection();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        executor.scheduleWithFixedDelay(this::tryLock, 1000, 5000, TimeUnit.MILLISECONDS);
    }

    public boolean lock() throws SQLException {
        connection.setAutoCommit(false);
        connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
        connection.createStatement().execute("set innodb_lock_wait_timeout=5");
        connection.createStatement().execute("select app from locks where id=1 for update");

        if (locked.get()) {
            System.out.println(" ==>>>> reenter this dist lock.");
        } else {
            System.out.println(" ==>>>> get a dist lock.");
            // 重新加载以下数据库的数据
        }

        return true;
    }

    private void tryLock() {
        try {
            lock();
            locked.set(true);
        } catch (Exception ex) {
            System.out.println(" ==>>>> lock failed...");
            locked.set(false);
        }
    }


    @PreDestroy
    public void close() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.rollback();
                connection.close();
            }
        } catch (SQLException e) {
            System.out.println("ignore this close exception");
        }
    }
}
