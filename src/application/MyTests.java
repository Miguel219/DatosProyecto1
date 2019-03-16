package application;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

import org.hamcrest.Matcher;
import org.junit.jupiter.api.Test;

public class MyTests {

    @Test
    public void DecodificatorTester() {
    	Decodificador MiDecodificador = new Decodificador();
    	String line =("(DEFUN sumaCon10 (num) (+ num 10))");    			
    	ArrayList<String> result = new ArrayList<String>();
    	MiDecodificador.add(line);
    	line="(sumaCon10 50)";
    	result= MiDecodificador.add(line);
    	assertEquals("5", result.get(0), "Deberia de dar 100 porque le caemos bien a Diego");
    }

	
}