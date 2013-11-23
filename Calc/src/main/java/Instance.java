import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.Scanner;
import java.lang.String;

public class Instance {


    public static String error = "";
    public static int position = 0;
    public static int counter = 0;
    private Instance(){

    }

    public static boolean checkNumber(char a){
       if(a=='0' || a=='1' || a=='2' || a=='3' || a=='4' || a=='5' || a=='6' ||
               a=='7' || a=='8' || a=='9' || a=='.'){
           return true;
       }
    return false;
    }

    public static boolean checkOperator(char a){
        if(a=='+' || a=='-' || a=='*' || a=='/' || a == '^' || a == '%'){
            return true;
        }

        return false;
    }
    public static int checkBrace(char a){
        if(a=='('){
            return 1;
        }
        if(a==')'){
        return -1;
        }
        return 0;
    }
    public static boolean checkSpace(char a){
        if(a==' '){
            return true;
        }

        return false;
    }

    public static boolean checkString(String a){
        int size=0;
        size = a.length();
        boolean test = false;
        boolean ifOperator = false;
        boolean ifBracesOpen = false;
        int amountOfBracers = 0;
        int ifFirstElementIsDigit = 0;
        for(int i=0;i<size;i++){


            if(checkNumber(a.charAt(i)) == true ){

                if(ifFirstElementIsDigit == 0){
                    ifFirstElementIsDigit = 1;
                }

                test = true;
                ifOperator = false;
            }
            else if(i+3<a.length() && a.charAt(i+1) == 'i' && a.charAt(i+2) == 'n' && a.charAt(i) == 's' && a.charAt(i+3) == '(' ){
               i=i+2;                             //sinus
                ifOperator = true;
                ifFirstElementIsDigit = 1;
            }
            else if(i+3<a.length() && a.charAt(i+1) == 'o' && a.charAt(i+2) == 's' && a.charAt(i) == 'c' && a.charAt(i+3) == '(' ){
                i=i+2;                  //cosines
                ifOperator = true;
                ifFirstElementIsDigit = 1;
            }
            else if( checkSpace(a.charAt(i)) == true){
                test = true;
            }
            else if( checkOperator(a.charAt(i)) == true ){

                if(ifFirstElementIsDigit == 0 && a.charAt(i)!='-'){
                    ifFirstElementIsDigit = -1; // first element is not digit
                    test = false;
                    break;
                }
                test = true;
                ifOperator = true;
            }
            else if( checkBrace(a.charAt(i)) == 1){


                if(ifOperator == false){
                    if( i>0 && checkBrace(a.charAt(i-1))!=1){

                    //System.out.println("You have not any operator before: " + a.charAt(i) );
                   error ="You have not any operator before: " + a.charAt(i);
                        position = i;
                        return false;

                }
                }
                amountOfBracers++;

                ifBracesOpen = true;
                test = true;
            }
            else if(checkBrace(a.charAt(i)) == -1){
                test = true;
                amountOfBracers--;
                if(ifBracesOpen == true){
                ifBracesOpen = false;
                }

            }
            else{
                //System.out.println("U have some troubles in this position: " + a.charAt(i));
                error = "U have some troubles in this position:"  + a.charAt(i);
                position = i;
                amountOfBracers = 0;
                ifOperator = false;
                ifBracesOpen = false;
                test = false;
                return false;

            }

        }
        if(amountOfBracers > 0){
            test = false;
            //System.out.println("U have too much opening bracers");
            error ="U have too much opening bracers";

        }
        if(amountOfBracers < 0){
            test = false;
            //System.out.println("U missed opening bracer");
            error ="U missed opening bracer";

        }
        if(ifOperator == true){
            test = false;
            //System.out.println("Your expression can`t ending by operator");
            error ="Your expression can`t ending by operator";
        }
        if(ifBracesOpen == true){
            //System.out.println("You have brace that must be closed");
            error ="You have brace that must be closed";
        }
        if(ifFirstElementIsDigit == -1){
            //System.out.println("You have not first digit");
            error ="You have not first digit";
            position = 0;
        }
       return test;
    }


    static boolean isDelim(char c) { // true if space
        return c == ' ';
    }
    static boolean isOperator(char c) { // if one of these symbols then true
        return c == '+' || c == '-' || c == '*' || c == '/' || c == '%' || c == '^';
    }
    static int priority(char op) {
        switch (op) { // if + or - then return 1, if * / % return 2, if ^ return 3. Else -1
            case '+':
            case '-':
                return 1;
            case '*':
            case '/':
            case '%':
                return 2;
            case '^':
                return 3;
            default:
                return -1;
        }
    }
    static int processOperator(LinkedList<Double> st, char op) {
        double r = st.removeLast(); // take last element from list
        double l = st.removeLast(); // same
        switch (op) { // execute operation dependence in operator, then write result to the "st" list
            case '+':
                st.add(l + r);
                break;
            case '-':
                st.add(l - r);

                break;
            case '^':
                st.add(Math.pow(l,r));
                break;
            case '*':
                st.add(l * r);
                break;
            case '/':
                if(r == 0){
                    //U can not divide on 0
                   return -1;
                }  else{
                st.add(l / r);

                }
                break;
            case '%':
                st.add(l % r);
                break;
        }
        return 0;
    }
    public static double eval(String s) {
        boolean cheredovanie = false; //digit - operator - digit - operator
        boolean makeNegative = false; //operator - operator - digit
        boolean itWasPointBefore = false; // set in true when we find point in digit
        int divideOnNull = 0;
        int amountOfPoints = 0;
        boolean endBrace =false;
        int amountDigitsAfterPoint = 0;
        int counter = 0;//amount posledovatelnix operations
        LinkedList<Double> st = new LinkedList<Double>(); // list for digits
        LinkedList<Character> op = new LinkedList<Character>(); // list of operators
        for (int i = 0; i < s.length(); i++) { // parse and calculate string

            char c = s.charAt(i);
            if (isDelim(c))
                continue;
            if (c == '(')
            {

                if(makeNegative == true){
                    counter = 0;
                    makeNegative = false;
                    op.add('N');
                }
                op.add('(');
                endBrace = false;

            }
            else if (c == ')') {
                while (op.getLast() != '(')
                {divideOnNull = processOperator(st,op.removeLast());
                if(divideOnNull == -1){
                    //System.out.println("U can not divide on null");
                    error = "U can not divide on null";

                    return -1;
                }
                }
                op.removeLast();
                if(op.isEmpty() == false){
                if(op.getLast() == 'N')
                {   op.removeLast();
                    double result = st.removeLast() * -(1.0);
                    st.add(result);  }
                }
                endBrace = true;
            } else if (isOperator(c)) {

                counter++;

                if(cheredovanie == true){
                cheredovanie = false;
                    endBrace = false;
                    itWasPointBefore = false;
                    amountDigitsAfterPoint = 0;
                    amountOfPoints = 0;
                }else{
                    if (c == '-' && (counter == 2 || counter == 1)){
                        counter = 3;
                         makeNegative = true;
                        cheredovanie = false;

                        continue;
                    }else{
                        position = i;
                        //System.out.println("U have two or more operators without digit");
                        error = "U have two or more operators without digit";
                        return -1;
                    }

                }

                while (!op.isEmpty() && priority(op.getLast()) >= priority(c))
                {divideOnNull = processOperator(st, op.removeLast());
                    if(divideOnNull == -1){
                        error = "U can not divide on null";
                        //System.out.println("U can not divide on null");

                        return -1;
                    }
                }
                op.add(c);

            }else if (checkFunction(s,i) == 0){
                i = i+3;
                double result = sinus(s,i);
                if (result == -1 && error.equals("") == false){
                    return -1;
                }
                if (makeNegative == true)
                {st.add(result*-1);
                    cheredovanie = true;
                    i = Instance.counter;}
                else{
                    st.add(result);
                    cheredovanie = true;
                    i = Instance.counter;

                }

            }
            else if (checkFunction(s,i) == 1){
                i = i+3;
                double result = cosines(s, i);
                if (result == -1 && error.equals("") == false){
                    return -1;
                }
                if (makeNegative == true)
                {st.add(result*-1);
                    cheredovanie = true;
                    i = Instance.counter;}
                else{
                    st.add(result);
                    cheredovanie = true;
                    i = Instance.counter;

                }

            }else {




                String operand = "";
                while (i < s.length() && (Character.isDigit(s.charAt(i)) || s.charAt(i) == '.') )
                {
                    if (endBrace == true){
                        position = i;
                       // System.out.println("No operator after end bracer ");
                        error = "No operator after end bracer";
                        return -1;
                    }

                    if( amountOfPoints >= 1 && s.charAt(i) == '.'){

                        position = i;
                       // System.out.println("U can not set two points in one digit");
                        error = "U can not set two points in one digit";
                        return -1;
                    }
                    if (s.charAt(i) == '.' && cheredovanie == true){

                        if( amountOfPoints == 0){

                            itWasPointBefore = true;
                            amountOfPoints++;

                        }
                        }else if(s.charAt(i) == '.' && cheredovanie == false){
                        position = i;
                           // System.out.println("U can not set point without digit ");
                        error = "U can not set point without digit ";
                            return -1;
                        }

                        operand += s.charAt(i++);
                    cheredovanie = true;

                 /* if (itWasPointBefore == true){
                   amountDigitsAfterPoint++;

                  }   */
                }

                --i;
                if(makeNegative == true){
                    if(itWasPointBefore == true){


                        st.add(((Double.parseDouble(operand))*(-1.0)));
                        itWasPointBefore = false;
                        amountDigitsAfterPoint = 0;

                    }   else{

                    st.add(((Double.parseDouble(operand))*(-1.0)));
                    makeNegative = false;
                    }
                    counter = 0;
                }else{
                    if (itWasPointBefore == true){

                        st.add(Double.parseDouble(operand));
                        itWasPointBefore = false;
                        amountDigitsAfterPoint = 0;
                    }else{

                st.add(Double.parseDouble(operand));

                counter = 0;
                    }
                }
            }
        }
        while (!op.isEmpty())
        {divideOnNull = processOperator(st, op.removeLast());
            if(divideOnNull == -1){
                //System.out.println("U can not divide on null");
                error = "U can not divide on null ";
                return -1;
            }
        }

        if(!cheredovanie){
               error = "U have missed digit in your expression";
            return -1; }
        return st.get(0);  // return result
    }

    private static double cosines(String s, int i) {
        boolean point = false;
        boolean negative = false;
        int digit = 0;
        double answer = 0.0;


        while(s.charAt(i) != ')'){


            if(checkSpace(s.charAt(i)) && (digit == 0 || digit == 1)){
                i++;

                continue;

            }


            if(checkOperator(s.charAt(i))) {
                if(s.charAt(i) == '-' && digit == 0 && negative == false){
                    negative = true;

                }else{
                    error = "Only digit in this function";
                    position = i;
                    return -1;
                }
            }


            if(Character.isDigit(s.charAt(i)) == true || s.charAt(i) == '.'){

                digit++;
                if (digit > 1){
                    error = "Only one digit in this function";
                    position = i;
                    return -1;
                }
                String result = "";
                while((Character.isDigit(s.charAt(i)) || s.charAt(i) == '.' ) && (i <= s.length()) ) {
                    if(s.charAt(i) == '.' && point == false ){
                        if(Character.isDigit(s.charAt(i-1))) {
                            point = true;
                            result+= s.charAt(i++);
                            continue;}
                        else{
                            error = "U mast write digit before point";
                            position = i;
                            return -1;
                        }
                    }  if(s.charAt(i) == '.' && point == true){
                        error = "two points";
                        position = i;
                        return -1;
                    }
                    result+= s.charAt(i++);
                }
                i--;

                if(negative == true)
                {answer = Double.parseDouble(result) * -1.0;
                    answer = Math.cos(answer);}
                else{
                    answer = Double.parseDouble(result);
                    answer = Math.cos(answer);
                }
            }
            i++;
            if(i > s.length())
            {
                error = "no end bracer";
                return -1;
            }

        }
        if(s.charAt(i) == ')'){

            counter = i;

            return answer;
        }
        else {return -1; }
    }

    private static int checkFunction(String s, int i) {
        int result = -1;

        if((i + 3 <= s.length()) && (s.charAt(i) == 's') && (s.charAt(i+1) == 'i')
                && (s.charAt(i+2) == 'n' && (s.charAt(i+3) == '('))) {
        result = 0;
    }

        if((i + 3 <= s.length()) && (s.charAt(i) == 'c') && (s.charAt(i+1) == 'o')
                && (s.charAt(i+2) == 's' && (s.charAt(i+3) == '('))) {
            result = 1;
        }

        return result;
    }

    private static double sinus(String s, int i) {
        boolean point = false;
        boolean negative = false;
        int digit = 0;
        double answer = 0.0;


        while(s.charAt(i) != ')'){


            if(checkSpace(s.charAt(i)) && (digit == 0 || digit == 1)){
                i++;

                continue;

            }


            if(checkOperator(s.charAt(i))) {
                if(s.charAt(i) == '-' && digit == 0 && negative == false){
                    negative = true;

                }else{
                error = "Only digit in this function";
                position = i;
                return -1;
                }
            }


            if(Character.isDigit(s.charAt(i)) == true || s.charAt(i) == '.'){

                digit++;
                if (digit > 1){
                    error = "Only one digit in this function";
                    position = i;
                    return -1;
                }
                String result = "";
                while((Character.isDigit(s.charAt(i)) || s.charAt(i) == '.' ) && (i <= s.length()) ) {

                    if(s.charAt(i) == '.' && point == false ){

                        if(Character.isDigit(s.charAt(i-1))) {

                        point = true;
                        result+= s.charAt(i++);
                        continue;}
                        else{

                            error = "U mast write digit before point";
                            position = i;
                            return -1;
                        }
                    }  if(s.charAt(i) == '.' && point == true){
                        error = "two points";
                        position = i;
                       return -1;
                    }

                    result+= s.charAt(i++);

                }
                   i--;

                if(negative == true)
                {answer = Double.parseDouble(result) * -1.0;
                    answer = Math.sin(answer);}
                else{
                answer = Double.parseDouble(result);
                answer = Math.sin(answer);
                }
            }
            i++;
            if(i > s.length())
            {
                error = "no end bracer";
                return -1;
            }

        }
        if(s.charAt(i) == ')'){

            counter = i;

            return answer;
        }
        else {return -1; }
    }

    public static void main(String[] args) {
    Scanner input = new Scanner(System.in);
    boolean test = false;
        final boolean[] show = {false};
        final JFrame jf = new JFrame ("Some type of calculator");
        JButton execute = new JButton("Execute");
        JButton showConsole = new JButton("show console");
        final JTextField forInput = new JTextField(12);
        final JTextField forOutput = new JTextField(12);
        final JLabel erro = new JLabel();
        final JLabel rules = new JLabel();
        rules.setText("Hy! This is Mathematics-interpreter. Write your expression and I will calculate it. U can use operators" +
                " like: +,-,/,*,^. Functions like: sin(...), cos(...). And bracers.");
        jf.setBounds(10, 10, 900, 350);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setLayout(new FlowLayout());
        jf.add(rules);
        jf.add(forInput);
        jf.add(execute);
        jf.add(forOutput);
        jf.add(erro);
        jf.add(showConsole);
        jf.setVisible(true);
        execute.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Instance.error = "";
                forOutput.setText("");

                if (forInput.getText().equals("")) {
                    error = "U have no expression";
                    forOutput.setText("-1");
                } else {
                    //To change body of implemented methods use File | Settings | File Templates.
                    if (checkString(forInput.getText()) == true) {

                        forOutput.setText(String.valueOf(eval(forInput.getText())));
                    } else {

                        //erro.setText(Instance.error);
                        forOutput.setText("-1");
                    }
                }
                //forOutput.setText(String.valueOf(eval(forInput.getText())));
                //comment.setText(Instance.error);
                erro.setText(Instance.error);
                forInput.requestFocus();
                forInput.setCaretPosition(position);
                position = 0;

                if(show[0]){
                    System.out.println(forOutput.getText());
                    System.out.println(Instance.error);
                }

            }
        });
        showConsole.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                  show[0] = !show[0];

            }
        }
        );

        String example = input.nextLine();
        while(example.compareTo("exit")!=0){
        System.out.println(example);
        if(checkString(example)==true){
            //System.out.println("Result is: " + eval(example));
        }
        example = input.nextLine();
    }





    }
}
