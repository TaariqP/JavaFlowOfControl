import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class StateMachine {

  private enum NumberStates {
    INITIAL,
    SIGN,
    NUMBER,
    FAIL
  }

  private enum NumberMachineState {
    POSITIVE,
    NEGATIVE,
    DECIMAL,
    WHOLE,
    FAIL
  }

  public static boolean binMachine(String input) {
    int i = 0;
    while (i < input.length()) {
      char c = input.charAt(i);
      if (c != '0' && c != '1') {
        return false;
      }
      i = i + 1;
    }
    return true;
  }

  public static int binConvMachine(String input) {
    int i = 0;
    int acc = 0;
    if (!binMachine(input)) {
      return -1;
    }
    while (i < input.length()) {
      char c = input.charAt(input.length() - i - 1);
      if (c == '1') {
        acc += Math.pow(2, i);
      }
      i = i + 1;
    }
    return acc;
  }

  public static boolean numMachine(String input) {
    for (int i = 0; i < input.length(); i++) {
      int c = input.charAt(i);
      if (!Character.isDigit(c)) {
        return false;
      }
    }
    return true;
  }

  public static boolean decMachine(String input) {
    int state = 0;
    for (int i = 0; i < input.length(); i++) {
      char c = input.charAt(i);
      if (state == 0) {
        if (c == '.' && i == 0) {
          state = 2;
          break;
        } else if (c == '.') {
          state = 1;
        } else if (!Character.isDigit(c)) {
          state = 2;
          break;
        }
      } else if (state == 1) {
        if (!(c >= '0' && c <= '9')) {
          state = 2;
          break;
        }
      }
    }
    switch (state) {
      case 0:
        //Pre decimal
        return true;
      case 1:
        //Post decimal
        return true;
      default:
        return false;
    }
  }

  public static boolean signMachine(String input) {
    NumberStates state = NumberStates.INITIAL;
    for (int i = 0; i < input.length(); i++) {
      char c = input.charAt(i);
      if ((c == '-' || c == '+') && i == 0) {
        state = NumberStates.SIGN;
      } else if (Character.isDigit(c)) {
        state = NumberStates.NUMBER;
      } else {
        state = NumberStates.FAIL;
        break;
      }
    }
    switch (state) {
      case FAIL:
        return false;
      default:
        return true;

    }
  }

  public static boolean numberMachine(String input) {
    NumberMachineState state = NumberMachineState.WHOLE;
    for (int i = 0; i < input.length(); i++) {
      char c = input.charAt(i);
      if (c == '+' || c == '-') {
        if (i == 0) {
          if (c == '+') {
            state = NumberMachineState.POSITIVE;
          } else {
            state = NumberMachineState.NEGATIVE;
          }
        } else {
          state = NumberMachineState.FAIL;
          break;
        }
      } else if (c == '.') {
        if (state == NumberMachineState.DECIMAL) {
          state = NumberMachineState.FAIL;
          break;
        }
        if (i == 0) {
          state = NumberMachineState.FAIL;
          break;
        } else {
          state = NumberMachineState.DECIMAL;
        }
      } else if (!Character.isDigit(c)) {
        state = NumberMachineState.FAIL;
        break;
      }

    }
    switch (state) {
      case FAIL:
        return false;
      default:
        return true;
    }
  }

  public static boolean wordMachine(String input) {
    for (int i = 0; i < input.length(); i++) {
      char c = input.charAt(i);
      if (!Character.isAlphabetic(c)) {
        return false;
      }
    }
    return true;
  }

  public static boolean wordyMachine(String input) {
    for (int i = 0; i < input.length(); i++) {
      char c = input.charAt(i);
      if (c >= '0' && c <= '9') {
        return false;
      } else if (i != 0 && (c >= 'A' && c <= 'Z')) {
        return false;
      }
    }
    return true;
  }

  public static boolean sentenceMachine(String input) {
    String[] words = input.split("\\s+");
    if (words.length < 2) {
      return false;
    }
    for (String word : words) {
      for (int i = 0; i < word.length(); i++) {
        char c = word.charAt(i);

        if (!Character.isAlphabetic(c)) {
          return false;
        }
      }
    }
    return true;
  }

  public static boolean grammarMachine(String input) {

    String[] sentences = input.split("[\\.?!]]");

    for (int i = 0; i < input.length(); i++) {
      char c = input.charAt(i);
      if (Character.toString(c).matches("[.!?]")) {
        if (i < input.length() - 2) {
          if (!Character.isUpperCase(input.charAt(i + 2))) {
            return false;
          }
        }
      }
    }
    for (String sentence : sentences) {
      if (!Character.isUpperCase(sentence.charAt(0))) {
        return false;
      }
      String[] words = sentence.split("\\s+");
      if (words.length < 2) {
        return false;
      }
      for (String word : words) {
        for (int i = 0; i < word.length(); i++) {
          char c = word.charAt(i);
          if (!Character.isAlphabetic(c) && !Character.toString(c)
              .matches("[.!?,-]")) {
            return false;
          }
        }
      }
    }
    return true;
  }

  private static String removeSpaces(String input) {
    StringBuilder string = new StringBuilder("");
    for (int i = 0; i < input.length(); i++) {
      char c = input.charAt(i);
      if (c != ' ') {
        string.append(c);
      }
    }
    return string.toString();
  }

  private static ArrayList getOperators(String input) {
    ArrayList operators = new ArrayList();
    for (int i = 0; i < input.length(); i++) {
      char c = input.charAt(i);
      if (Character.toString(c).matches("[-+*/=]")) {
        operators.add(c);
      }
    }
    return operators;
  }

  private static ArrayList getNums(String input) {
    String[] nums = input.split("[-+*/=]");
    ArrayList<String> newNums = new ArrayList<>();
    for (String num : nums) {
      if (!num.equals("")) {
        newNums.add(num);
      }
    }
    return newNums;
  }



  public static double calcMachine(String input) {
    input = removeSpaces(input);
    double result = 0;
    ArrayList<Character> ops = getOperators(input);
    if (ops.get(ops.size() - 1) != '=') {
      return Double.NaN;
    }
    ArrayList<String> nums = getNums(input);
    if (nums.size() != ops.size()) {
      return Double.NaN;
    }

    ArrayList<Double> values = new ArrayList();
    for (String num : nums) {
      if (!num.equals("")) {
        double num1 = Double.parseDouble(num);
        values.add(num1);
      }
    }

    result += values.get(0);

    for (int i = 0; i < ops.size(); i++) {
      char c = ops.get(i);
      if (c == '=') {
        return result;
      }
      double num1 = values.get(i);
      double num2 = values.get(i + 1);
      switch (c) {
        case '+':
          result = result + num2;
          break;
        case '-':
          result = result - num2;
          break;
        case '*':
          result = result * num2;
          break;
        case '/':
          result = result / num2;
          break;
      }
    }
    return Double.NaN;
  }
}
