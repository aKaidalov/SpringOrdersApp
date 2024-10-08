// 5

package util;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

public class DevDataSource implements DataSource {

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private ConnectionInfo connectionInfo;

    public DevDataSource(ConnectionInfo connectionInfo) {
        this.connectionInfo = connectionInfo;
    }

    @Override
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                connectionInfo.url,
                connectionInfo.user,
                connectionInfo.pass);
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        throw new RuntimeException("not implemented");
    }

    @Override
    public PrintWriter getLogWriter() throws SQLException {
        throw new RuntimeException("not implemente");
    }

    @Override
    public void setLogWriter(PrintWriter out) throws SQLException {
        throw new RuntimeException("not implement");
    }

    @Override
    public void setLoginTimeout(int seconds) throws SQLException {
        throw new RuntimeException("not implemen");
    }

    @Override
    public int getLoginTimeout() throws SQLException {
        throw new RuntimeException("not impleme");
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        throw new RuntimeException("not implem");
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        throw new RuntimeException("not imple");
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        throw new RuntimeException("not impl");
    }
}

