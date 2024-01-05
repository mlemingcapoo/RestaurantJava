package main;

import junit.framework.TestCase;

public class main_classTest extends TestCase {

    public void testPrintString() {
        main_class main = new main_class();
        main.printString("hello", 3);
        main.main(null);


    }

}