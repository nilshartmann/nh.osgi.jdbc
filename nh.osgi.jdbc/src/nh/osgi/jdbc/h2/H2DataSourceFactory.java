/*******************************************************************************
 * Copyright (c) 2011 Nils Hartmann
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *    Nils Hartmann - initial API and implementation
 ******************************************************************************/
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

/**
 * A very simple implementation of an OSGi {@link DataSourceFactory} for the H2 Database
 * 
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
@Component(properties = { DataSourceFactory.OSGI_JDBC_DRIVER_CLASS + "=org.h2.jdbcx.JdbcDataSource", //
    DataSourceFactory.OSGI_JDBC_DRIVER_NAME + "=H2 DataSource", //
    DataSourceFactory.OSGI_JDBC_DRIVER_VERSION + "=0.0.1" })
public class H2DataSourceFactory implements DataSourceFactory {

  @Override
  public DataSource createDataSource(Properties props) throws SQLException {
    return createH2DataSource(props);
  }

  @Override
  public ConnectionPoolDataSource createConnectionPoolDataSource(Properties props) throws SQLException {
    return createH2DataSource(props);
  }

  @Override
  public XADataSource createXADataSource(Properties props) throws SQLException {
    return createH2DataSource(props);
  }

  @Override
  public Driver createDriver(Properties props) throws SQLException {
    org.h2.Driver h2Driver = new org.h2.Driver();
    return h2Driver;
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
