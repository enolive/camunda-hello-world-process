package de.welcz.helloworldprocess

import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import org.camunda.bpm.engine.ProcessEngine
import org.camunda.bpm.engine.test.Deployment
import org.camunda.bpm.engine.test.junit5.ProcessEngineExtension
import org.camunda.bpm.engine.variable.Variables
import org.camunda.bpm.engine.variable.value.IntegerValue
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

    run {
      val stringValue = result.singleResult.getEntryTyped<StringValue>("desiredDish")
      stringValue.shouldNotBeNull().value
    } shouldBe "Spareribs"
  }

  @Test
  fun `dry aged gourmet steak`() {
    val inputs = Variables.createVariables()
      .putValue("season", "Spring")
      .putValue("guestCount", 4)

    val result = processEngine.decisionService.evaluateDecisionTableByKey("dish", inputs)

    val desiredDish = result.singleResult.getEntryTyped<StringValue>("desiredDish").shouldNotBeNull().value
    desiredDish shouldBe "Dry Aged Gourmet Steak"
    val confidence = result.singleResult.getEntryTyped<IntegerValue>("confidence").shouldNotBeNull().value
    confidence shouldBe 42
  }

  @Test
  fun `fallback in case not decision hits`() {
    val inputs = Variables.createVariables()

    val result = processEngine.decisionService.evaluateDecisionTableByKey("dish", inputs)

    val desiredDish = result.singleResult.getEntryTyped<StringValue>("desiredDish").shouldNotBeNull().value
    desiredDish shouldBe "Fuck you!"
    val confidence = result.singleResult.getEntryTyped<IntegerValue>("confidence").shouldNotBeNull().value
    confidence shouldBe 100
  }
}

