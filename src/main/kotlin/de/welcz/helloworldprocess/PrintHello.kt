package de.welcz.helloworldprocess

import io.github.oshai.kotlinlogging.KotlinLogging
import org.camunda.bpm.engine.delegate.DelegateExecution
import org.camunda.bpm.engine.delegate.JavaDelegate
import org.camunda.bpm.engine.variable.value.IntegerValue
import org.camunda.bpm.engine.variable.value.StringValue
import org.springframework.stereotype.Component

@Component
class PrintHello : JavaDelegate {
  private val logger = KotlinLogging.logger { }

  override fun execute(execution: DelegateExecution) {
    val optionalName = execution.getVariableTyped<StringValue>("name")
    val name = optionalName?.value ?: "World"
    val desiredDish = execution.getVariableTyped<StringValue>("desiredDish").value
    val confidence = execution.getVariableTyped<IntegerValue>("confidence").value
    logger.info { "Hello $name!" }
    logger.info { "We recommend you serve your guests $desiredDish with a confidence of $confidence" }
  }
}
