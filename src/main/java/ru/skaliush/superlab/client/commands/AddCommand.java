package ru.skaliush.superlab.client.commands;

import ru.skaliush.superlab.client.app.ResponseWriter;
import ru.skaliush.superlab.common.models.Person;
import ru.skaliush.superlab.common.models.dto.PersonDTO;
import ru.skaliush.superlab.common.network.ActionAlias;
import ru.skaliush.superlab.common.network.Request;
import ru.skaliush.superlab.common.network.Response;

public class AddCommand extends Command {
    public void exec(String argument) {
        PersonForm form = new PersonForm();
        PersonDTO personDto = form.askPerson();
        personDto.setOwnerLogin(app.getUser().getLogin());
        ResponseWriter.write(personDto);
        Request request = new Request(ActionAlias.ADD, personDto);
        Response response = this.app.getRequestSender().send(request);
        Person person = (Person) response.getData();
        ResponseWriter.write("Создан чел с ID " + person.getId());
    }

    public String getDescription() {
        return "добавить новый элемент в коллекцию";
    }

}
