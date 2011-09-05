package nh.osgi.jdbc.h2;

import java.sql.Driver;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.ConnectionPoolDataSource;
import javax.sql.DataSource;
import javax.sql.XADataSource;

import org.h2.jdbcx.JdbcDataSource;
import org.osgi.service.jdbc.DataSourceFactory;

import aQute.bnd.annotation.component.Component;

@Component(properties={DataSourceFactory.OSGI_JDBC_DRIVER_CLASS+"=org.h2.jdbcx.JdbcDataSource", //
    DataSourceFactory.OSGI_JDBC_DRIVER_NAME+"=H2 DataSource", //
    DataSourceFactory.OSGI_JDBC_DRIVER_VERSION+"=0.0.1"
})
public class H2DataSourceFactory implements DataSourceFactory {
  
	@Override
	public DataSource createDataSource(Properties props) throws SQLException {
    return createH2DataSource(props);
	}

	@Override
	public ConnectionPoolDataSource createConnectionPoolDataSource(
			Properties props) throws SQLException {
    return createH2DataSource(props);
	}

	@Override
	public XADataSource createXADataSource(Properties props)
			throws SQLException {
	  return createH2DataSource(props);
	}

	@Override
	public Driver createDriver(Properties props) throws SQLException {
	  throw new SQLException("Method not supported: createDriver");
	}
	
	protected JdbcDataSource createH2DataSource(Properties props) throws SQLException {
	  // Create new DataSource
	  JdbcDataSource jdbcDataSource = new JdbcDataSource();
	  
	  // configure from properties
	  jdbcDataSource.setURL(props.getProperty(JDBC_URL));
	  jdbcDataSource.setUser(JDBC_USER);
	  jdbcDataSource.setPassword(JDBC_PASSWORD);
	  jdbcDataSource.setDescription(JDBC_DESCRIPTION);

	  // return the datasource
	  return jdbcDataSource;
	}
	

}
