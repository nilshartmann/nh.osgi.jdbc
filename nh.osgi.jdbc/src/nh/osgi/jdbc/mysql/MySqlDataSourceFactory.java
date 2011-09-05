/*******************************************************************************
 * Copyright (c) 2011 Bundlemaker project team.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Bundlemaker project team - initial API and implementation
 ******************************************************************************/
package nh.osgi.jdbc.mysql;

import java.sql.Driver;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.ConnectionPoolDataSource;
import javax.sql.DataSource;
import javax.sql.XADataSource;

import org.osgi.service.jdbc.DataSourceFactory;

import aQute.bnd.annotation.component.Component;

import com.mysql.jdbc.jdbc2.optional.MysqlConnectionPoolDataSource;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import com.mysql.jdbc.jdbc2.optional.MysqlXADataSource;

/**
 * A simple implementation of a {@link DataSourceFactory} for the MySql Database
 * 
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
@Component(properties = { DataSourceFactory.OSGI_JDBC_DRIVER_CLASS + "=com.mysql.jdbc.MysqlDataSource", //
    DataSourceFactory.OSGI_JDBC_DRIVER_NAME + "=MySql DataSource", //
    DataSourceFactory.OSGI_JDBC_DRIVER_VERSION + "=0.0.1" })
public class MySqlDataSourceFactory implements DataSourceFactory {

  /*
   * (non-Javadoc)
   * 
   * @see org.osgi.service.jdbc.DataSourceFactory#createDataSource(java.util.Properties)
   */
  @Override
  public DataSource createDataSource(Properties props) throws SQLException {
    MysqlDataSource mysqlDataSource = new MysqlDataSource();
    configureDataSource(mysqlDataSource, props);
    return mysqlDataSource;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.osgi.service.jdbc.DataSourceFactory#createConnectionPoolDataSource(java.util.Properties)
   */
  @Override
  public ConnectionPoolDataSource createConnectionPoolDataSource(Properties props) throws SQLException {
    MysqlConnectionPoolDataSource mysqlConnectionPoolDataSource = new MysqlConnectionPoolDataSource();
    configureDataSource(mysqlConnectionPoolDataSource, props);
    // TODO more pool-specific config?
    return mysqlConnectionPoolDataSource;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.osgi.service.jdbc.DataSourceFactory#createXADataSource(java.util.Properties)
   */
  @Override
  public XADataSource createXADataSource(Properties props) throws SQLException {
    MysqlXADataSource xaDataSource = new MysqlXADataSource();
    configureDataSource(xaDataSource, props);
    // TODO more XA-specific config?
    return xaDataSource;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.osgi.service.jdbc.DataSourceFactory#createDriver(java.util.Properties)
   */
  @Override
  public Driver createDriver(Properties props) throws SQLException {
    com.mysql.jdbc.Driver mysqlDriver = new com.mysql.jdbc.Driver();
    return mysqlDriver;
  }

  private void configureDataSource(MysqlDataSource dataSource, Properties props) throws SQLException {

    if (props.containsKey(JDBC_URL)) {
      dataSource.setUrl(props.getProperty(JDBC_URL));
    }

    if (props.containsKey(JDBC_PASSWORD)) {
      dataSource.setPassword(props.getProperty(JDBC_PASSWORD));
    }

    if (props.containsKey(JDBC_USER)) {
      dataSource.setUser(props.getProperty(JDBC_USER));
    }

    if (props.containsKey(JDBC_DATABASE_NAME)) {
      dataSource.setDatabaseName(props.getProperty(JDBC_DATABASE_NAME));
    }

  }

}
