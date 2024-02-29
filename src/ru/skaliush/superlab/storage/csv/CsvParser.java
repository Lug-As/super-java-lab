package ru.skaliush.superlab.storage.csv;

import ru.skaliush.superlab.app.EndOfLineException;
import ru.skaliush.superlab.app.LineReader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class CsvParser {
    private char delimiter = ',';
    private char textQualifier = '"';

    public CsvParser() {
    }

    public CsvParser(char delimiter, char textQualifier) {
        this.delimiter = delimiter;
        this.textQualifier = textQualifier;
    }

    public List<Row> parse(String fileName) {
        File file = Path.of(fileName).toFile();
        if (!file.exists()) {
            throw new RuntimeException("Файл не существует.");
        }
        if (!file.canRead()) {
            throw new RuntimeException("Файл не доступен для чтения.");
        }
        LineReader lineReader;
        try {
            lineReader = new LineReader(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        List<Row> rows = new ArrayList<>();
        while (lineReader.hasNextLine()) {
            String line;
            try {
                line = lineReader.nextLine().trim();
            } catch (EndOfLineException e) {
                break;
            }
            if (line.equals("")) {
                break;
            }
            List<String> rowValues = new ArrayList<>();
            StringBuilder cell = new StringBuilder();
            boolean isQuotesOpen = false;
            boolean needToContinue = false;
            for (int i = 0; i < line.length(); i++) {
                if (needToContinue) {
                    needToContinue = false;
                    continue;
                }
                char symbol = line.charAt(i);
                if (symbol == textQualifier) {
                    if (!isQuotesOpen) {
                        isQuotesOpen = true;
                        continue;
                    } else {
                        if (i + 1 < line.length()) {
                            char nextSymbol = line.charAt(i + 1);
                            if (nextSymbol == textQualifier) {
                                cell.append(symbol);
                                needToContinue = true;
                                continue;
                            }
                        }
                        isQuotesOpen = false;
                    }
                } else if (symbol == delimiter) {
                    if (isQuotesOpen) {
                        cell.append(symbol);
                    } else {
                        rowValues.add(cell.toString());
                        cell = new StringBuilder();
                        continue;
                    }
                } else {
                    cell.append(symbol);
                }
                if (i == line.length() - 1) {
                    rowValues.add(cell.toString());
                }
            }
            rows.add(new Row(rowValues));
        }
        return rows;
    }
}