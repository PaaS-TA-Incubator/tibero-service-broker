package com.tmax.tibero.broker.cloudfoundry.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.ResponseBody
import org.yaml.snakeyaml.Yaml


@Controller
@RequestMapping("/v2/catalog")
class CatalogRestController {
  def contents

  @RequestMapping(method=RequestMethod.GET)
  @ResponseBody
  synchronized Map getCatalog() {
    if (!contents) {
      Yaml yaml = new Yaml()
      contents = yaml.load(this.class.getClassLoader().getResourceAsStream("catalog.yml"))
    }

    return contents
  }
}

