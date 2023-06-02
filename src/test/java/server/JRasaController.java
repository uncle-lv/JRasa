package server;

import io.github.jrasa.ActionExecutor;
import io.github.jrasa.exception.ActionNotFoundException;
import io.github.jrasa.exception.RejectExecuteException;
import io.github.jrasa.rest.ActionCall;
import io.github.jrasa.rest.ActionName;
import io.github.jrasa.rest.ActionResponse;
import io.github.jrasa.rest.StatusResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class JRasaController {
    @Resource
    private ActionExecutor actionExecutor;

    @GetMapping("/health")
    public StatusResponse health() {
        return new StatusResponse("ok");
    }

    @PostMapping("/webhook")
    public ActionResponse webhook(@RequestBody ActionCall actionCall) throws ActionNotFoundException, RejectExecuteException {
        return actionExecutor.run(actionCall);
    }

    @GetMapping("/actions")
    public List<ActionName> actions() {
        return actionExecutor.getRegisteredActions()
                .stream()
                .map(ActionName::new)
                .collect(Collectors.toList());
    }
}
