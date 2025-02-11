package de.welcz.helloworldprocess

import org.camunda.bpm.engine.DecisionService
import org.camunda.bpm.engine.RuntimeService
import org.camunda.bpm.engine.variable.Variables
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
  fun hello(@PathVariable name: String): String {
    val params = mapOf("name" to name)
    val instance = runtimeService.startProcessInstanceByKey("HelloWorldProcess", params)
    return instance.id
  }

  @PostMapping("/dish")
  fun dish(@RequestBody inputs: DishInputs): String {
    val variables = Variables.createVariables()
      .putValue("season", inputs.season)
      .putValue("guestCount", inputs.guestCount)
    val result = decisionService.evaluateDecisionTableByKey("dish", variables)
    val desiredDish = result.singleResult["desiredDish"] as String
    return desiredDish
  }
}

data class DishInputs(val season: String = "", val guestCount: Int = 0)
