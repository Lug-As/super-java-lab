package ru.skaliush.superlab.server.app;

import ru.skaliush.superlab.common.network.ActionAlias;
import ru.skaliush.superlab.common.network.Request;
import ru.skaliush.superlab.common.network.Response;
import ru.skaliush.superlab.server.actions.*;

import java.util.Map;

public class Router {
    private final Map<ActionAlias, Action<?>> routeList = Map.of(
            ActionAlias.LOGIN, new LoginAction(),
            ActionAlias.REGISTER, new RegisterAction(),
            ActionAlias.SHOW, new ShowAction(),
            ActionAlias.ADD, new CreateAction(),
            ActionAlias.UPDATE, new UpdateAction(),
            ActionAlias.REMOVE, new DeleteAction(),
            ActionAlias.CLEAR, new ClearAction()
    );

    public Response resolve(Request request) {
        ActionAlias alias = request.getActionAlias();
        if (routeList.containsKey(alias)) {
            Action<?> action = routeList.get(alias);
            Object result = action.execute(request);
            return new Response(request, result);
        } else {
            throw new RuntimeException("Команда не реализована - " + alias.name());
        }
    }

    public Map<ActionAlias, Action<?>> getRouteList() {
        return routeList;
    }
}
