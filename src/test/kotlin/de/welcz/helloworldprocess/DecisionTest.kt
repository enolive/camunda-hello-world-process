package de.welcz.helloworldprocess

import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import org.camunda.bpm.dmn.engine.DmnDecisionTableResult
import org.camunda.bpm.engine.ProcessEngine
import org.camunda.bpm.engine.test.Deployment
import org.camunda.bpm.engine.test.junit5.ProcessEngineExtension
import org.camunda.bpm.engine.variable.Variables
import org.camunda.bpm.engine.variable.value.StringValue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(ProcessEngineExtension::class)
@Deployment(resources = ["dishes.dmn"])
class DecisionTest {
  private lateinit var processEngine: ProcessEngine

  @Test
  fun `spareribs in fall`() {
    val inputs = Variables.createVariables()
      .putValue("season", "Fall")
      .putValue("guestCount", 4)

    val result = processEngine.decisionService.evaluateDecisionTableByKey("dish", inputs)

    result.getDesiredDishOrFail() shouldBe "Spareribs"
  }

  @Test
  fun `dry aged gourmet steak`() {
    val inputs = Variables.createVariables()
      .putValue("season", "Spring")
      .putValue("guestCount", 4)

    val result = processEngine.decisionService.evaluateDecisionTableByKey("dish", inputs)

    result.getDesiredDishOrFail() shouldBe "Dry Aged Gourmet Steak"
  }

  @Test
  fun `fallback in case not decision hits`() {
    val inputs = Variables.createVariables()

    val result = processEngine.decisionService.evaluateDecisionTableByKey("dish", inputs)

    result.getDesiredDishOrFail() shouldBe "Fuck you!"
  }
}

private fun DmnDecisionTableResult.getDesiredDishOrFail(): String {
  val stringValue = singleResult.getEntryTyped<StringValue>("desiredDish")
  return stringValue.shouldNotBeNull().value
}
