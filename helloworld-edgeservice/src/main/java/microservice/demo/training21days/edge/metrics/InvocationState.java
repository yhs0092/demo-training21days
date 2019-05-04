package microservice.demo.training21days.edge.metrics;

import java.util.function.BiConsumer;

public class InvocationState {
  InvocationProcedureState state = InvocationProcedureState.SERVER_REQUEST_RECEIVE;

  RequestProcedureTimer timer = new RequestProcedureTimer();

  public void switchState(boolean isNormal) {
    this.state.act.accept(this, isNormal);
    this.timer.record(this.state);
  }

  public enum InvocationProcedureState {
    SERVER_REQUEST_RECEIVE(InvocationProcedureStateBehavior.SERVER_REQUEST_RECEIVE_ACT),
    PREPARING(InvocationProcedureStateBehavior.PREPARING_ACT),
    TRANSFERRING_REQUEST_BODY(InvocationProcedureStateBehavior.TRANSFERRING_REQUEST_BODY_ACT),
    WAITING_FOR_RESPONSE(InvocationProcedureStateBehavior.WAITING_FOR_RESPONSE_ACT),
    TRANSFERRING_RESPONSE_BODY(InvocationProcedureStateBehavior.TRANSFERRING_RESPONSE_BODY_ACT),
    END(InvocationProcedureStateBehavior.END_ACT);

    InvocationProcedureState(BiConsumer<InvocationState, Boolean> act) {
      this.act = act;
    }

    BiConsumer<InvocationState, Boolean> act;

    private static class InvocationProcedureStateBehavior {
      static final BiConsumer<InvocationState, Boolean> SERVER_REQUEST_RECEIVE_ACT = (invocationState, isNormal) -> {
        if (isNormal) {
          invocationState.state = InvocationProcedureState.PREPARING;
          return;
        }
        invocationState.state = InvocationProcedureState.END;
      };

      static final BiConsumer<InvocationState, Boolean> PREPARING_ACT = (invocationState, isNormal) -> {
        if (isNormal) {
          invocationState.state = InvocationProcedureState.TRANSFERRING_REQUEST_BODY;
          return;
        }
        invocationState.state = InvocationProcedureState.END;
      };

      static final BiConsumer<InvocationState, Boolean> TRANSFERRING_REQUEST_BODY_ACT = (invocationState, isNormal) -> {
        if (isNormal) {
          invocationState.state = InvocationProcedureState.WAITING_FOR_RESPONSE;
          return;
        }

        invocationState.state = InvocationProcedureState.END;
      };

      static final BiConsumer<InvocationState, Boolean> WAITING_FOR_RESPONSE_ACT = (invocationState, isNormal) -> {
        if (isNormal) {
          invocationState.state = InvocationProcedureState.TRANSFERRING_RESPONSE_BODY;
          return;
        }

        invocationState.state = InvocationProcedureState.END;
      };

      static final BiConsumer<InvocationState, Boolean> TRANSFERRING_RESPONSE_BODY_ACT = (invocationState, isNormal) -> {
        invocationState.state = InvocationProcedureState.END;
      };

      static final BiConsumer<InvocationState, Boolean> END_ACT = (invocationState, isNormal) -> {
      };
    }
  }
}
