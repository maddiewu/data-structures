import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 COMS 3134 Homework 2
 Madeline Wu (mcw2175)
 October 7, 2016

 This class uses file I/O to read a file and check that
 all { }'s, ( )'s, [ ]'s, " "'s, and comment slashes are properly balanced.
Characters within literal strings and comment blocks are ignored.
 */

public class SymbolBalance {

    /*Stack wil be used to push and pop symbols.
    * boolean string is used to check whether or not the current character
    * is part of a string (because it's contents should be ignored),
    * and the same thing goes for comments but separate booleans are required
    * in the event that there are any string markers like '' and "" in the comments. */
    static boolean string = false;
    static boolean comment = false;
    static MyStack<Character> symbols = new MyStack<>();

    public static void main(String[] args) {
        try {
            FileReader fr = new FileReader(args[0]);
            BufferedReader br = new BufferedReader(fr);
            String thisLine; //line currently being read
            /*Check if each line of the file is balanced. */
            while ((thisLine = br.readLine()) != null) {
                balanced(thisLine);
            }
            fr.close();
            /*If the whole file is balanced, the stack should be empty.
            * If it's not, there could be matches still contained in the stack.
             * The check() method checks for these. */
            if (!symbols.empty()) {
                check();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*Method to check for symbol balance by treating each line as a string
    * and going through the string character by character. */
    public static void balanced(String line) {
        for (int i = 0; i < line.length(); i++) {
            char curr = line.charAt(i);
            /*Skip over all letters, semicolons, tabs, spaces and periods. */
            if (curr == ' ' | "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz.\t"
                    .contains(String.valueOf(curr))) {
                continue;
            }
            /*Check to see if the line is a comment. */
            if (curr == '/' && i + 1 < line.length()) {
                if (line.charAt(i + 1) == '*') { /*Marks start of this kind of comment. */
                    comment = true;
                    i++;
                }
            } else if (curr == '*' && i + 1 < line.length() && comment) {
                if (line.charAt(i + 1) == '/') {
                    comment = false; /*marks the end of a comment like this. */
                    i++;
                }
            }
            /*If the character being examined it not part of a string or comment. */
            if (!comment && !string(curr)) {
                /*Push open braces onto the stack. */
                if (curr == '[' | curr == '{' | curr == '(') {
                    symbols.push(curr);
                }
                /*For closed braces, check if its matching open brace is on the stack.*/
                else if ((curr == ']' | curr == ')' | curr == '}')) {
                    if (!symbols.empty()) {
                        /*Check if closed braces have a match on the stack. */
                        if (curr == ']') {
                            if (symbols.peek() == '[') {
                                symbols.pop();
                            } else {
                                symbols.push(curr);
                            }
                        } else if (curr == '}') {
                            if (symbols.peek() == '{') {
                                symbols.pop();
                            } else {
                                symbols.push(curr);
                            }
                        } else if (curr == ')') {
                            if (symbols.peek() == '(') {
                                symbols.pop();
                            } else {
                                symbols.push(curr);
                            }
                        }
                    } else {
                    /*If the stack is empty, push the closed brace onto it. */
                        symbols.push(curr);
                    }
                }
            }
        }
    } //end method

    /*If it's not a comment, it could be a string. */
    public static boolean string(char x) {
        if (!comment) {
            if ((x == '\'' | x == '\"') && !string) { // For strings and characters.
                symbols.push(x);
                string = true;
            } else if (x == '\'' && string) { //Check the balance of ''s and ""s.
                if (symbols.pop() != '\'') {
                    System.out.println("Unbalanced: symbol " + x + " has no match (string 1) ");
                }
                string = false; //to end a string
            } else if (x == '\"' && string) {
                if (symbols.pop() != '\"') {
                    System.out.println("Unbalanced: symbol " + x + " has no match (string 2) ");
                }
                string = false;
            } else if ((x == ';') && string) {
                System.out.println("Unbalanced: symbol " + symbols.pop() + " has no match 4");
                string = false;
            }
        }
        return string;
    }

    /*Method to check is there are any remaining symbol pairs on the stack.
    * An instance of MyStack was used as a temporary holder to make sure that
    * when the items from symbols were added to the array list, they were added
    * in the right order (open braces should come first). */
    public static void check() {
        MyStack<Character> temp = new MyStack<>();
        ArrayList<Character> remaining = new ArrayList<>();
        int index1;
        while (!symbols.empty()) {
            temp.push(symbols.pop());
        }
        while (!temp.empty()) {
            remaining.add(temp.pop());
        }
        /*Check all open braces for matches within the array list. */
        for (int i = 0; i < remaining.size();) {
            char x = remaining.get(i);
            if (x == '[') {
                index1 = remaining.indexOf(x);
                if (remaining.contains(']')) {
                    remaining.remove(remaining.indexOf(']'));
                    remaining.remove(index1);
                } else {
                    i++;
                }
            } else if (x == '(') {
                index1 = remaining.indexOf(x);
                if (remaining.contains(')')) {
                    remaining.remove(remaining.indexOf(')'));
                    remaining.remove(index1);
                } else {
                    i++;
                }
            } else if (x == '{') {
                index1 = remaining.indexOf(x);
                if (remaining.contains('}')) {
                    remaining.remove(remaining.indexOf('}'));
                    remaining.remove(index1);
                } else {
                    i++;
                }
            } else {
                i++;
            }
        }
        /*The remaining symbols have no matches. */
        if (!remaining.isEmpty()) {
            for (char y: remaining) {
                System.out.println("Unbalanced: symbol " + y + " has no match (check)");
            }
        }
    }

}//end class
