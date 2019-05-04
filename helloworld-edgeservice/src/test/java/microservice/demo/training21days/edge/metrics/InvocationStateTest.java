package microservice.demo.training21days.edge.metrics;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import microservice.demo.training21days.edge.metrics.InvocationState.InvocationProcedureState;

public class InvocationStateTest {

  @Test
  public void switchStateNormal() {
    InvocationState invocationState = new InvocationState();
    assertEquals(InvocationProcedureState.SERVER_REQUEST_RECEIVE, invocationState.state);
    assertNotEquals(0, invocationState.timer.serverRequestReceive);
    invocationState.switchState(true);
    assertEquals(InvocationProcedureState.PREPARING, invocationState.state);
    assertNotEquals(0, invocationState.timer.preparing);
    invocationState.switchState(true);
    assertEquals(InvocationProcedureState.TRANSFERRING_REQUEST_BODY, invocationState.state);
    assertNotEquals(0, invocationState.timer.transferringRequestBody);
    invocationState.switchState(true);
    assertEquals(InvocationProcedureState.WAITING_FOR_RESPONSE, invocationState.state);
    assertNotEquals(0, invocationState.timer.waitingForResponse);
    invocationState.switchState(true);
    assertEquals(InvocationProcedureState.TRANSFERRING_RESPONSE_BODY, invocationState.state);
    assertNotEquals(0, invocationState.timer.transferringResponseBody);
    invocationState.switchState(true);
    assertEquals(InvocationProcedureState.END, invocationState.state);
    long end = invocationState.timer.end;
    assertNotEquals(0, end);
    invocationState.switchState(true);
    assertEquals(InvocationProcedureState.END, invocationState.state);
    assertEquals(end, invocationState.timer.end);
    invocationState.switchState(false);
    assertEquals(InvocationProcedureState.END, invocationState.state);
    assertEquals(end, invocationState.timer.end);
  }

  @Test
  public void switchStatePreparingFail() {
    InvocationState invocationState = new InvocationState();
    assertTrue(invocationState.timer.serverRequestReceive > 0);
    assertEquals(0, invocationState.timer.preparing);
    assertEquals(0, invocationState.timer.transferringRequestBody);
    assertEquals(0, invocationState.timer.waitingForResponse);
    assertEquals(0, invocationState.timer.transferringResponseBody);
    assertEquals(0, invocationState.timer.end);
    invocationState.switchState(true);
    assertTrue(invocationState.timer.preparing >= invocationState.timer.serverRequestReceive);
    invocationState.switchState(false);
    assertEquals(InvocationProcedureState.END, invocationState.state);
    assertTrue(invocationState.timer.end >= invocationState.timer.preparing);
    assertEquals(0, invocationState.timer.transferringRequestBody);
    assertEquals(0, invocationState.timer.waitingForResponse);
    assertEquals(0, invocationState.timer.transferringResponseBody);
    invocationState.switchState(true);
    assertEquals(InvocationProcedureState.END, invocationState.state);
    assertTrue(invocationState.timer.end >= invocationState.timer.preparing);
    assertEquals(0, invocationState.timer.transferringRequestBody);
    assertEquals(0, invocationState.timer.waitingForResponse);
    assertEquals(0, invocationState.timer.transferringResponseBody);
  }
}