package de.welcz.helloworldprocess

import io.github.oshai.kotlinlogging.KotlinLogging
import org.camunda.bpm.engine.delegate.DelegateExecution
import org.camunda.bpm.engine.delegate.JavaDelegate
import org.camunda.bpm.engine.variable.value.StringValue
import org.springframework.stereotype.Service

@Service
class PrintHello : JavaDelegate {
  private val logger = KotlinLogging.logger { }

  override fun execute(execution: DelegateExecution) {
    val optionalName = execution.getVariableTyped<StringValue>("name")
    val name = optionalName?.value ?: "World"
    logger.info { "Hello $name!" }
  }
}

class HelloWorldException(message: String) : Throwable(message)
