package com.tmax.tibero.broker.cloudfoundry.controller

import com.tmax.tibero.broker.cloudfoundry.service.ServiceInstance
import com.tmax.tibero.broker.cloudfoundry.service.ServiceInstanceService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.ResponseBody


@Controller
@RequestMapping("/v2/service_instances/{id}")
class ServiceInstanceRestController {
  @Autowired
  private ServiceInstanceService service

  @RequestMapping(method = RequestMethod.PUT)
  @ResponseBody
  Map createInstance(@PathVariable String id) {
    ServiceInstance instance = service.findById(id)
    if (!service.isExists(instance)) {
      service.create(instance)
    }
    return [:]
  }

  @RequestMapping(method = RequestMethod.DELETE)
  @ResponseBody
  Map deleteInstance(@PathVariable String id) {
    ServiceInstance instance = service.findById(id)
    if (service.isExists(instance)) {
      service.delete(instance)
    }
    return [:]
  }
}

