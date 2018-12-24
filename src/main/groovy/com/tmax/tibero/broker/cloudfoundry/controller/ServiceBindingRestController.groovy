package com.tmax.tibero.broker.cloudfoundry.controller

import com.tmax.tibero.broker.cloudfoundry.service.ServiceBinding
import com.tmax.tibero.broker.cloudfoundry.service.ServiceBindingService
import com.tmax.tibero.broker.cloudfoundry.service.ServiceInstance
import com.tmax.tibero.broker.cloudfoundry.service.ServiceInstanceService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.ResponseBody


@Controller
@RequestMapping("/v2/service_instances/{instanceId}/service_bindings/{bindingId}")
class ServiceBindingRestController {
  @Autowired ServiceBindingService bindingService

  @RequestMapping(method = RequestMethod.PUT)
  @ResponseBody
  ServiceBinding saveBinding(@PathVariable String instanceId, @PathVariable String bindingId) {
    ServiceBinding binding = bindingService.findById(bindingId, instanceId)
    bindingService.save(binding)
    return binding
  }

  @RequestMapping(method = RequestMethod.DELETE)
  @ResponseBody
  Map destroyBinding(@PathVariable String instanceId, @PathVariable String bindingId) {
    ServiceBinding binding = bindingService.findById(bindingId, instanceId)
    bindingService.destroy(binding)
    return [:]
  }
}

