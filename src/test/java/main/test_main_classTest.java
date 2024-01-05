package main;

import junit.framework.TestCase;

public class test_main_classTest extends TestCase {
// test main_class
    public void test_main_class() {
        main_class main = new main_class();
        main.main(null);
        main.printString("hello", 3);
    }

}