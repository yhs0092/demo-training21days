package microservice.demo.training21days.edge.metrics;

import microservice.demo.training21days.edge.metrics.InvocationState.InvocationProcedureState;

public class RequestProcedureTimer {
  long serverRequestReceive = System.currentTimeMillis();

  long preparing;

  long transferringRequestBody;

  long waitingForResponse;

  long transferringResponseBody;

  long end;

  public void record(InvocationProcedureState state) {
    switch (state) {
      case PREPARING:
        if (0 == preparing) {
          preparing = System.currentTimeMillis();
        }
        break;
      case TRANSFERRING_REQUEST_BODY:
        if (0 == transferringRequestBody) {
          transferringRequestBody = System.currentTimeMillis();
        }
        break;
      case WAITING_FOR_RESPONSE:
        if (0 == waitingForResponse) {
          waitingForResponse = System.currentTimeMillis();
        }
        break;
      case TRANSFERRING_RESPONSE_BODY:
        if (0 == transferringResponseBody) {
          transferringResponseBody = System.currentTimeMillis();
        }
        break;
      case END:
        if (0 == end) {
          end = System.currentTimeMillis();
        }
    }
  }
}
