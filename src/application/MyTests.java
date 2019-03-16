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
    	String line =("(/ 10 0)");
    	ArrayList<String> result = new ArrayList<String>();
    	result= MiDecodificador.add(line);
    	assertEquals("Error division por cero", result.get(0), "Este resultado debe dar error por division por cero");
        
        
    }

	
}