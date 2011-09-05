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
package nh.osgi.jdbc.test;

import java.sql.Connection;
import java.sql.Statement;
import java.util.Properties;

import javax.sql.DataSource;

import junit.framework.TestCase;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;
import org.osgi.service.jdbc.DataSourceFactory;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
public class H2DataSourceTest extends TestCase {

  private final BundleContext context = FrameworkUtil.getBundle(this.getClass()).getBundleContext();

  public void test_createDataSource() throws Exception {

    // dump installed bundles for debugging
    dumpBundles();

    // obtain the H2 DataSource Factory from Service Registry
    DataSourceFactory dataSourceFactory = getH2DataSourceFactory();

    // obtain a data source
    DataSource dataSource = dataSourceFactory.createDataSource(getDataSourceProperties());
    assertNotNull(dataSource);

    // execute a query to make sure retrieved datasource works
    Connection connection = dataSource.getConnection();
    Statement stmt = connection.createStatement();
    stmt.execute("CREATE TABLE osgi_test(primary_key VARCHAR(256))");
  }

  protected Properties getDataSourceProperties() {

    // Properties for an in-memory Database
    Properties props = new Properties();
    props.put(DataSourceFactory.JDBC_URL, "jdbc:h2:mem:db1");
    props.put(DataSourceFactory.JDBC_USER, "sa");
    props.put(DataSourceFactory.JDBC_PASSWORD, "");

    return props;
  }

  protected DataSourceFactory getH2DataSourceFactory() throws Exception {
    // Get service reference to JdbcDataSource
    ServiceReference[] serviceReferences = context.getServiceReferences(DataSourceFactory.class.getName(), "("
        + DataSourceFactory.OSGI_JDBC_DRIVER_CLASS + "=org.h2.jdbcx.JdbcDataSource)");
    assertNotNull(serviceReferences);
    assertEquals(1, serviceReferences.length);

    // get the DataSourceFactory-service
    ServiceReference serviceReference = serviceReferences[0];
    DataSourceFactory dataSourceFactory = (DataSourceFactory) context.getService(serviceReference);
    assertNotNull(dataSourceFactory);

    return dataSourceFactory;
  }

  private void dumpBundles() throws Exception {
    Bundle[] bundles = context.getBundles();

    System.out.println("Installed bundles: ");
    for (Bundle bundle : bundles) {
      System.out.println("Bundle: " + bundle.getSymbolicName() + " (" + getStateDescription(bundle.getState()) + ")");
      ServiceReference[] registeredServices = bundle.getRegisteredServices();
      if (registeredServices != null) {
        for (ServiceReference serviceReference : registeredServices) {
          System.out.printf("  Service: %s%n", serviceReference.toString());
        }
      }
    }
  }

  private String getStateDescription(int state) {

    switch (state) {
    case 1:
      return "UNINSTALLED";
    case 2:
      return "INSTALLED";
    case 4:
      return "RESOLVED";
    case 8:
      return "STARTING";
    case 16:
      return "STOPPING";
    case 32:
      return "ACTIVE";
    default:
      return "UNDEFINED";
    }
  }

}
