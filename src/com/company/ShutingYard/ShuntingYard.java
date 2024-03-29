package com.company.ShutingYard;

import com.company.FileReader.FileReader;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ShuntingYard {
    private static Map<String, ShuntingYard.Operator> ops = new HashMap<String, ShuntingYard.Operator>() {
        {
            this.put("+", ShuntingYard.Operator.ADD);
            this.put("-", ShuntingYard.Operator.SUBTRACT);
            this.put("*", ShuntingYard.Operator.MULTIPLY);
            this.put("/", ShuntingYard.Operator.DIVIDE);
            this.put("^", ShuntingYard.Operator.POW);
            this.put("sin", ShuntingYard.Operator.SIN);
        }
    };

    public ShuntingYard() {
    }

    private static boolean isHigerPrec(String op, String sub) {
        return ops.containsKey(sub) && (ops.get(sub)).precedence >= (ops.get(op)).precedence;
    }

    public static QueueArray toPostfix(String str, FileReader fileReader) throws FileNotFoundException {
        fileReader.readFileAndReturnCondition();
        HashMap<String,String> hashMap=fileReader.returnHashMap();
        ArrayList<String> tokens = StringParser.parseString(str);
        System.out.println("TOKENS1");
        System.out.println(tokens);
        StackArray<String> s = new StackArray(tokens.size());
        QueueArray q = new QueueArray(tokens.size());
        Iterator var4 = tokens.iterator();

        while(true) {
            while(var4.hasNext()) {
                String elem = (String)var4.next();
                if(Character.isLetter(elem.charAt(0))){
                    q.add(elem);
                    continue;
                }
                if (StringParser.isDigit(elem)) {
                    q.add(elem);
                } else if (elem.equals("(")) {
                    s.push(elem);
                } else if (elem.equals(")")) {
                    while(!((String)s.peek()).equals("(")) {
                        q.add(s.pop());
                    }

                    s.pop();
                } else if (!StringParser.isDigit(elem)) {
                    while(!s.isEmpty() && isHigerPrec(elem, (String)s.peek())) {
                        q.add(s.pop());
                    }

                    s.push(elem);
                }
            }

            int size = s.getSize();

            for(int i = 0; i < size; ++i) {
                q.add(s.pop());
            }

            q.print();
            return q;
        }
    }public static QueueArray toPostfix(String str) throws FileNotFoundException {
        ArrayList<String> tokens = StringParser.parseString(str);
        StackArray<String> s = new StackArray(tokens.size());
        QueueArray q = new QueueArray(tokens.size());
        Iterator var4 = tokens.iterator();

        while(true) {
            while(var4.hasNext()) {
                String elem = (String)var4.next();
                if (StringParser.isDigit(elem)) {
                    q.add(elem);
                } else if (elem.equals("(")) {
                    s.push(elem);
                } else if (elem.equals(")")) {
                    while(!((String)s.peek()).equals("(")) {
                        q.add(s.pop());
                    }

                    s.pop();
                } else if (!StringParser.isDigit(elem)) {
                    while(!s.isEmpty() && isHigerPrec(elem, (String)s.peek())) {
                        q.add(s.pop());
                    }

                    s.push(elem);
                }
            }

            int size = s.getSize();

            for(int i = 0; i < size; ++i) {
                q.add(s.pop());
            }

            q.print();
            return q;
        }
    }

    static enum Operator {
        ADD(1),
        SUBTRACT(2),
        MULTIPLY(3),
        DIVIDE(4),
        POW(5),
        SIN(6);

        final int precedence;

        private Operator(int p) {
            this.precedence = p;
        }
    }
}