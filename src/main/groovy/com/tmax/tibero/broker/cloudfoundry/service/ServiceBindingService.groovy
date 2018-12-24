package com.tmax.tibero.broker.cloudfoundry.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.EnvironmentAware
import org.springframework.core.env.Environment
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Service

import java.security.MessageDigest


@Service
class ServiceBindingService implements EnvironmentAware {
  @Autowired JdbcTemplate jdbcTemplate
  @Autowired ServiceInstanceService instanceService

  Environment environment

  @Override
  void setEnvironment(Environment environment) {
    this.environment = environment
  }

  ServiceBinding findById(String id, String instanceId) {
    ServiceInstance instance = instanceService.findById(instanceId)
    ServiceBinding binding = new ServiceBinding(id, instance, environment)
    return binding
  }

  boolean isExists(ServiceBinding binding) {
    List<String> users = jdbcTemplate.queryForList("SELECT USERNAME FROM DBA_USERS WHERE USERNAME = '${binding.credentials.username}'")
    return users?.size() > 0
  }

  def save(ServiceBinding binding) {
    jdbcTemplate.execute("CREATE USER ${binding.credentials.username} IDENTIFIED BY '${binding.credentials.password}'")
    jdbcTemplate.execute("GRANT CONNECT, RESOURCE TO ${binding.credentials.username}")
  }

  def destroy(ServiceBinding binding) {
    jdbcTemplate.execute("DROP USER ${binding.credentials.username} CASCADE")
  }
}

class ServiceBinding {
  Credentials credentials

  ServiceBinding(String id, ServiceInstance instance, Environment environment) {
    String username = "USER_" + id.replaceAll('-', '_').toUpperCase()
    credentials = new Credentials(username, environment);
  }
}

class Credentials {
  String uri, username, password

  Credentials(String username, Environment environment) {
    this.uri = environment.getProperty('spring.datasource.url')
    this.username = username
    this.password = UUID.randomUUID().toString()
  }
}

