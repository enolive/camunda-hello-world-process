package de.welcz.helloworldprocess

import org.camunda.bpm.engine.DecisionService
import org.camunda.bpm.engine.RuntimeService
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class Controller(
  private val runtimeService: RuntimeService,
  private val decisionService: DecisionService,
) {
  @PostMapping("/hello/{name}")
  fun hello(@PathVariable name: String, @RequestBody(required = false) inputs: DishInputs?): String {
    val params = mapOf(
      "name" to name,
      "season" to inputs?.season,
      "guestCount" to inputs?.guestCount,
    ).filterValues { it != null }
    val instance = runtimeService.startProcessInstanceByKey("HelloWorldProcess", params)
    return instance.id
  }
}

data class DishInputs(val season: String = "", val guestCount: Int = 0)
