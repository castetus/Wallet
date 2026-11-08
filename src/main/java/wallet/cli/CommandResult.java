package wallet.cli;

public record CommandResult(
        boolean success,
        String message
) {}