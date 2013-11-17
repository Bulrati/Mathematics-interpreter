import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.Scanner;
import java.lang.String;

public class Instance {


    //static final
    public static String error = "";
    public static int position = 0;
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

            //System.out.println("a.charAt(i):" + a.charAt(i));
            if(checkNumber(a.charAt(i)) == true ){
                //System.out.println("digit: " + a.charAt(i));
                if(ifFirstElementIsDigit == 0){
                    ifFirstElementIsDigit = 1; // first element is digit
                }
                //System.out.println("DIGIT");
                test = true;
                ifOperator = false;
            }
            else if( checkSpace(a.charAt(i)) == true){
                test = true;
            }
            else if( checkOperator(a.charAt(i)) == true ){
               // System.out.println("operator: " + a.charAt(i));
               // System.out.println("Operator");
                if(ifFirstElementIsDigit == 0 && a.charAt(i)!='-'){
                    ifFirstElementIsDigit = -1; // first element is not digit
                    test = false;
                    break;
                }
                test = true;
                ifOperator = true;
            }
            else if( checkBrace(a.charAt(i)) == 1){
                //System.out.println("Brace " + ifOperator);

                if(ifOperator == false){
                    if( i>0 && checkBrace(a.charAt(i-1))!=1){
                    //test = false;
                    System.out.println("You have not any operator before: " + a.charAt(i) );
                   error ="You have not any operator before: " + a.charAt(i);
                        position = i;
                        return false;
                    //break;
                }
                }
                amountOfBracers++;
                //System.out.println("Proshlo");
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
                System.out.println("U have some troubles in this position: " + a.charAt(i));
                error = "U have some troubles in this position:"  + a.charAt(i);
                position = i;
                amountOfBracers = 0;
                ifOperator = false;
                ifBracesOpen = false;
                test = false;
                return false;
               // break;
            }

        }
        if(amountOfBracers > 0){
            test = false;
            System.out.println("U have too much opening bracers");
            error ="U have too much opening bracers";

        }
        if(amountOfBracers < 0){
            test = false;
            System.out.println("U missed opening bracer");
            error ="U missed opening bracer";

        }
        if(ifOperator == true){
            test = false;
            System.out.println("Your expression can`t ending by operator");
            error ="Your expression can`t ending by operator";
        }
        if(ifBracesOpen == true){
            System.out.println("You have brace that must be closed");
            error ="You have brace that must be closed";
        }
        if(ifFirstElementIsDigit == -1){
            System.out.println("You have not first digit");
            error ="You have not first digit";
            position = 0;
        }
       return test;
    }


    static boolean isDelim(char c) { // тру если пробел
        return c == ' ';
    }
    static boolean isOperator(char c) { // возвращяем тру если один из символов ниже
        return c == '+' || c == '-' || c == '*' || c == '/' || c == '%' || c == '^';
    }
    static int priority(char op) {
        switch (op) { // при + или - возврат 1, при * / % 2 иначе -1
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
        double r = st.removeLast(); // выдёргиваем из упорядоченного листа последний элемент
        double l = st.removeLast(); // также
        switch (op) { // выполняем действие между l и r в зависимости от оператора в кейсе и результат валим в st
            case '+':
                st.add(l + r);
                break;
            case '-':
                st.add(l - r);
                //System.out.println("l: " + l + " r: " + r + " l - r: " + (l-r));
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
                    //System.out.println("l: " + l + " r: " + r + " l / r: " + (l / r));
                }
                break;
            case '%':
                st.add(l % r);
                break;
        }
        return 0;
    }
    public static double eval(String s) {
        boolean cheredovanie = false; //chislo - operator - chislo - operator
        boolean sdelatOtrizatelnim = false; //operator - operator - chislo
        boolean itWasPointBefore = false; // set in true when we find point in digit
        int divideOnNull = 0;
        int chisloTochek = 0;
        boolean endBrace =false;
        int amountDigitsAfterPoint = 0;
        int counter = 0;//kolvo posledovatelnix operacii
        LinkedList<Double> st = new LinkedList<Double>(); // сюда наваливают цифры
        LinkedList<Character> op = new LinkedList<Character>(); // сюда опрераторы и st и op в порядке поступления
        for (int i = 0; i < s.length(); i++) { // парсим строку с выражением и вычисляем
           //System.out.println("i: " + s.charAt(i) + " Chslotochec: " + chisloTochek);
            char c = s.charAt(i);
            if (isDelim(c))
                continue;
            if (c == '(')
            {

                if(sdelatOtrizatelnim == true){
                    counter = 0;
                    sdelatOtrizatelnim = false;
                    op.add('N');
                }
                op.add('(');
                endBrace = false;

            }
            else if (c == ')') {
                while (op.getLast() != '(')
                {divideOnNull = processOperator(st,op.removeLast());
                if(divideOnNull == -1){
                    System.out.println("U can not divide on null");
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
                //System.out.println("counter" + counter);
                counter++;

                if(cheredovanie == true){
                cheredovanie = false;
                    endBrace = false;
                    itWasPointBefore = false;
                    amountDigitsAfterPoint = 0;
                    chisloTochek = 0;
                }else{
                    if (c == '-' && (counter == 2 || counter == 1)){
                        counter = 3;
                         sdelatOtrizatelnim = true;
                        cheredovanie = false;

                        continue;
                    }else{
                        position = i;
                        System.out.println("U have two or more operators without digit");
                        error = "U have two or more operators without digit";
                        return -1;
                    }

                }

                while (!op.isEmpty() && priority(op.getLast()) >= priority(c))
                {divideOnNull = processOperator(st, op.removeLast());
                    if(divideOnNull == -1){
                        error = "U can not divide on null";
                        System.out.println("U can not divide on null");

                        return -1;
                    }
                }
                op.add(c);
            } else {




                String operand = "";
                while (i < s.length() && (Character.isDigit(s.charAt(i)) || s.charAt(i) == '.') )
                {   //suda
                    //System.out.println("i: " + s.charAt(i) + " Chslotochec v chisle: " + chisloTochek);
                    if (endBrace == true){
                        position = i;
                        System.out.println("No operator after end bracer ");
                        error = "No operator after end bracer";
                        return -1;
                    }
                    //System.out.println("C = " + s.charAt(i));
                    if( chisloTochek >= 1 && s.charAt(i) == '.'){
                        //System.out.println("Not First point");
                        position = i;
                        System.out.println("U can not set two points in one digit");
                        error = "U can not set two points in one digit";
                        return -1;
                    }
                    if (s.charAt(i) == '.' && cheredovanie == true){
                        //System.out.println("U have point");
                        if( chisloTochek == 0){
                            //System.out.println("First point");
                            itWasPointBefore = true;
                            chisloTochek++;
                            //i++;//?????????????????????????
                        }
                        }else if(s.charAt(i) == '.' && cheredovanie == false){
                        position = i;
                            System.out.println("U can not set point without digit ");
                        error = "U can not set point without digit ";
                            return -1;
                        }

                        operand += s.charAt(i++);
                    cheredovanie = true;
                    //System.out.println("operand: " + operand);
                  if (itWasPointBefore == true){
                   amountDigitsAfterPoint++;
                      //System.out.println("amountDigitsAfterPoint" + amountDigitsAfterPoint);
                  }
                }
                //System.out.println("Cycle off");
                --i;
                if(sdelatOtrizatelnim == true){
                    if(itWasPointBefore == true){
                        //System.out.println("itWasPointBefore");

                        st.add(((Double.parseDouble(operand))*(-1.0)));
                        itWasPointBefore = false;
                        amountDigitsAfterPoint = 0;

                    }   else{

                    st.add(((Double.parseDouble(operand))*(-1.0)));
                    sdelatOtrizatelnim = false;
                    }
                    counter = 0;
                }else{
                    if (itWasPointBefore == true){
                        //System.out.println("itWasPointBefore for +");
                        //System.out.println("(double)Integer.parseInt(operand): " + (double)Integer.parseInt(operand));
                        //System.out.println("(10.0 * amountDigitsAfterPoint): " + (10.0 * amountDigitsAfterPoint));
                        st.add(Double.parseDouble(operand));
                        itWasPointBefore = false;
                        amountDigitsAfterPoint = 0;
                    }else{
                        //System.out.println("No PointBefore");
                st.add(Double.parseDouble(operand));
                //System.out.println("cicle: " + counter);
                counter = 0;
                    }
                }
            }
        }
        while (!op.isEmpty())
        {divideOnNull = processOperator(st, op.removeLast());
            if(divideOnNull == -1){
                System.out.println("U can not divide on null");
                error = "U can not divide on null ";
                return -1;
            }
        }
        return st.get(0);  // возврат результата
    }


    public static void main(String[] args) {
    Scanner input = new Scanner(System.in);
    boolean test = false;

        final JFrame jf = new JFrame ("Some type of calculator");
        JButton a=new JButton("Execute");
        final JTextField forInput = new JTextField(12);
        final JTextField forOutput = new JTextField(12);
        final JLabel erro = new JLabel();
        jf.setBounds(10, 10, 200, 150);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setLayout(new FlowLayout());
        jf.add(forInput);
        jf.add(a);
        jf.add(forOutput);
        jf.add(erro);
        jf.setVisible(true);
        a.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Instance.error = "";
                forOutput.setText("");

                //To change body of implemented methods use File | Settings | File Templates.
                if(checkString(forInput.getText())==true){
                    System.out.println("stringcheck true");
                    forOutput.setText(String.valueOf(eval(forInput.getText())));
                } else{
                    System.out.println("stringcheck false");
                    erro.setText(Instance.error);
                    forOutput.setText("-1");
                }

                //forOutput.setText(String.valueOf(eval(forInput.getText())));
                //comment.setText(Instance.error);
                erro.setText(Instance.error);
                forInput.requestFocus();
                forInput.setCaretPosition(position);
                position = 0;

            }
        });


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
