package ru.skaliush.superlab.client.commands;

import ru.skaliush.superlab.client.app.StopProgramException;

public class ExitCommand extends Command {
    public void exec(String argument) {
        throw new StopProgramException();
    }

    public String getDescription() {
        return "завершить программу (без сохранения в файл)";
    }
    
    public boolean needToAuthorize() {
        return false;
    }
}
