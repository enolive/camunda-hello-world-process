package de.welcz.helloworldprocess

import io.mockk.every
import io.mockk.mockk
import org.camunda.bpm.engine.ProcessEngine
import org.camunda.bpm.engine.test.Deployment
import org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.*
import org.camunda.bpm.engine.test.mock.Mocks.register
import org.camunda.bpm.engine.variable.Variables
import org.camunda.community.process_test_coverage.junit5.platform7.ProcessEngineCoverageExtension
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(ProcessEngineCoverageExtension::class)
@Deployment(resources = ["process.bpmn", "dishes.dmn"])
class ProcessTest {
  private lateinit var processEngine: ProcessEngine
  private lateinit var verifyName: VerifyName
  private lateinit var printHello: PrintHello

  @BeforeEach
  fun setUp() {
    verifyName = mockk<VerifyName>(relaxed = true)
    printHello = mockk<PrintHello>(relaxed = true)
    register("verifyName", verifyName)
    register("printHello", printHello)
  }

  @Test
  fun `runs hello world process correctly`() {
    val inputs = Variables.createVariables()
      .putValue("season", "Spring")
      .putValue("guestCount", 4)
    val instance = processEngine.runtimeService.startProcessInstanceByKey("HelloWorldProcess", inputs)

    // necessary because of boundary step
    assertThat(instance).isWaitingAt("PrintHello")
    assertThat(instance).variables().containsEntry("desiredDish", "Dry Aged Gourmet Steak")

    execute(job())

    assertThat(instance).isEnded
    assertThat(instance).hasPassedInOrder("VerifyName", "PrintHello")
  }

  @Test
  fun `runs with a failure when verification of name fails`() {
    every { verifyName.execute(any()) } throws VerifyError()

    val instance = processEngine.runtimeService.startProcessInstanceByKey("HelloWorldProcess")

    assertThat(instance).isEnded
    assertThat(instance).hasPassedInOrder("VerifyName", "ExitWithFailure")
  }
}