package de.welcz.helloworldprocess

import org.camunda.bpm.engine.RuntimeService
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class Controller(private val runtimeService: RuntimeService) {
  @PostMapping("/hello/{name}")
  fun hello(@PathVariable name: String): String {
    val params = mapOf("name" to name)
    val instance = runtimeService.startProcessInstanceByKey("HelloWorldProcess", params)
    return instance.id
  }
}