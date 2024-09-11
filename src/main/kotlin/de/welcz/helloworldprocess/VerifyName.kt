package de.welcz.helloworldprocess

import org.camunda.bpm.engine.delegate.BpmnError
import org.camunda.bpm.engine.delegate.DelegateExecution
import org.camunda.bpm.engine.delegate.JavaDelegate
import org.camunda.bpm.engine.variable.value.StringValue
import org.springframework.stereotype.Component

@Component
class VerifyName : JavaDelegate {
  override fun execute(execution: DelegateExecution) {
    val optionalName = execution.getVariableTyped<StringValue>("name")
    val name = optionalName?.value ?: "World"
    if (name == "Chris") {
      throw BpmnError("VerifyFailed")
    }
  }
}