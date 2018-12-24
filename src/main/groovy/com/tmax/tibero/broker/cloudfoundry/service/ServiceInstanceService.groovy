package com.tmax.tibero.broker.cloudfoundry.service


import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Service

import javax.sql.DataSource


@Service
class ServiceInstanceService {
  @Autowired JdbcTemplate jdbcTemplate

  ServiceInstance findById(String id) {
    return new ServiceInstance(id)
  }

  boolean isExists(ServiceInstance instance) {
    List<String> instances = jdbcTemplate.queryForList("SELECT ID FROM CF_INSTANCES WHERE ID = '${instance.id}'", String)
    return instances?.size() > 0
  }

  def create(ServiceInstance instance) {
    jdbcTemplate.execute("INSERT INTO CF_INSTANCES VALUES ('${instance.id}', '${instance.plan}')")
  }

  def delete(ServiceInstance instance) {
    jdbcTemplate.execute("DELETE FROM CF_INSTANCES WHERE ID = '${instance.id}'")
  }
}

class ServiceInstance {
  String id
  String plan

  ServiceInstance(String id) {
    this.id = id.toLowerCase()
    this.plan = "basic"
  }
}

