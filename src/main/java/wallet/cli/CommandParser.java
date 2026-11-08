
package wallet.cli;

import java.util.ArrayList;
import java.util.List;

public class CommandParser {

    public List<String> parse(String input) {
        List<String> arguments = new ArrayList<>();
        StringBuilder current = new StringBuilder();

        boolean inQuotes = false;
        boolean argumentStarted = false;

        for (char ch : input.toCharArray()) {
            if (ch == '"') {
                inQuotes = !inQuotes;
                argumentStarted = true;
            } else if (Character.isWhitespace(ch) && !inQuotes) {
                if (argumentStarted) {
                    arguments.add(current.toString());
                    current.setLength(0);
                    argumentStarted = false;
                }
            } else {
                current.append(ch);
                argumentStarted = true;
            }
        }

        if (inQuotes) {
            throw new IllegalArgumentException("Unclosed quotation mark");
        }

        if (argumentStarted) {
            arguments.add(current.toString());
        }

        return arguments;
    }
}
