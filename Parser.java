import java.util.ArrayList;

public class Parser {
    ArrayList<String> args = new ArrayList<String>();
    String cmd;

    public void parse(String userInput){
        args.clear();
        String[] splits = userInput.trim().split("\\s+");

        cmd = splits[0];

        for (int i = 1; i < splits.length; i++) {
            args.add(splits[i]);
        }
    }

    public String getCmd() {
        return cmd;
    }

    public ArrayList<String> getArgs() {
        return args;
    }
}
