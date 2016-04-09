/*
 * Copyright (c) 2016 Christopher Wells <cwellsny@nycap.rr.com>
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package oscp5recordinggenerator;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        if (args.length < 1) {
            System.err.println("No file given");
            System.exit(1);
        }

        String inputFileName = args[0];
        File inputFile = new File(inputFileName);
        Scanner fileScanner = null;
        try {
            fileScanner = new Scanner(inputFile);
        } catch (FileNotFoundException ex) {
            System.err.println("Invalid file: " + inputFileName);
            System.exit(1);
        }

        // Process the contents of the file
        int time = 0;
        String generatedXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";
        generatedXml += "<oscp5messages>\n";
        String channelStart = fileScanner.nextLine();
        while (fileScanner.hasNext()){
            String line = fileScanner.nextLine();
            String[] arguments = line.split(" ");

            if (arguments.length != 3) {
                System.err.println("Invalid line: " + line);
                System.exit(1);
            }

            String channel = arguments[0];
            String value = arguments[1];
            int timeIncrease = Integer.parseInt(arguments[2]);

            time += timeIncrease;

            //generatedXml += channel + ":" + time + ":" + value + "\n";
            generatedXml += formatMessage(channelStart + channel, value, time);
        } 

        generatedXml += "</oscp5messages>";

        System.out.println(generatedXml);
    }

    public static String formatMessage(String channel, String value, int time) {
        return "<message channel=\"" + channel + "\" time=\"" + time +
            "\" value=\"" + value + "\" />\n";
    }
}
